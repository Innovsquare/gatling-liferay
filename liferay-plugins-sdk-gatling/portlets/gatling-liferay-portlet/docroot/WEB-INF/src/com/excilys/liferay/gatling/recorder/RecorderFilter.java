/**
 * Copyright 2014 eBusiness Information, Groupe Excilys (www.ebusinessinformation.fr)
 */
package com.excilys.liferay.gatling.recorder;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;

import com.excilys.liferay.gatling.model.Record;
import com.excilys.liferay.gatling.service.RecordLocalServiceUtil;
import com.excilys.liferay.gatling.service.UrlRecordLocalServiceUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.SessionParamUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;


/**
 * Servlet Filter implementation class RecordFilter
 */
@MultipartConfig
public class RecorderFilter implements Filter {
	private static final Log LOG = LogFactoryUtil.getLog(RecorderFilter.class);
	private static final String NAMESPACE = "_gatling_WAR_gatlingliferayportlet_";
	private static final String URL_CONTROL_PANEL = "/group/control_panel/manage";
	private static final List<String> FORBIDDEN_PARAMS = new ArrayList<String>();

	static {
		FORBIDDEN_PARAMS.add("doAsGroupId");
		FORBIDDEN_PARAMS.add("p_p_auth");
	}


	/**
	 * Default constructor. 
	 */
	public RecorderFilter() {
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}


	private Map<String,String[]> filterParameters(Map<String,String[]> parameters) {
		Map<String,String[]> params = new HashMap<String, String[]>(parameters);
		for (String key : FORBIDDEN_PARAMS) {
			params.remove(key);
		}
		return params;
	}
	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;

		HttpSession session = httpRequest.getSession();

		String actionToggleRecord = ParamUtil.getString(httpRequest, NAMESPACE+"javax.portlet.action",null);
		if(actionToggleRecord != null && actionToggleRecord.equals("toggleRecord")) {
			String recordState = ParamUtil.getString(httpRequest, NAMESPACE+"nextRecordState", null);
			String recordName = ParamUtil.getString(httpRequest, NAMESPACE+"useCaseRecordName", null);
			String portletId = ParamUtil.getString(httpRequest, NAMESPACE+"pagePortletId", null);
			if(recordState != null && recordName != null && portletId != null) {
				session.setAttribute("GATLING_RECORD_STATE", portletId+","+recordState+","+recordName);
			} else {
				session.removeAttribute("GATLING_RECORD_STATE");
			}
		}
		/*
		 * Recording
		 */
		String infosRecorder = SessionParamUtil.getString(httpRequest, "GATLING_RECORD_STATE", null);
		if(infosRecorder != null) { 
			String[] infos = infosRecorder.split(",");

			if(session.getAttribute("recordURL") == null) { // if empty session recordURL create one
				session.setAttribute("recordURL", new ArrayList<String>());
			}
			// get the recorded Urls list
			List<RecordURL> recordURLs = (List<RecordURL>) session.getAttribute("recordURL");
			// cases (Java 6)
			if (infos[1].equalsIgnoreCase("RECORD")) { 
				if(httpRequest.getParameter("doAsGroupId") != null) {  // we only record request with doAsGroupId (= portlet tested)
					// get the parameters
					String params = HttpUtil.parameterMapToString(filterParameters(request.getParameterMap()));
					String requestURL = httpRequest.getRequestURI().replace(URL_CONTROL_PANEL, "");
					RecordURL record = new RecordURL(httpRequest.getMethod(), requestURL, params);
//					if(record.getMethod().equalsIgnoreCase("post")) {
//						/*
//						 * Get the content of multipart/form-data 
//						 */
//						ResettableStreamHttpServletRequest wrappedRequest = new ResettableStreamHttpServletRequest(httpRequest);
//						
//						if(ServletFileUpload.isMultipartContent(httpRequest)) {
//							try {
//								//ON ne peut lire qu'une fois le stream du coup on reste coincé la
//								List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(wrappedRequest);
//								for (FileItem item : items) {
//									LOG.info("\t"+item.getFieldName()+" : "+item.getString());
//								}
//							} catch (FileUploadException e) {
//								throw new ServletException("Cannot parse multipart request.", e);
//							}
//						}
//						wrappedRequest.resetInputStream();
//						httpRequest = wrappedRequest;
//					}
					// Display for debug
					LOG.info(record);
					// Save
					recordURLs.add(record);
					// Store it in the session
					session.setAttribute("recordURL", recordURLs);
				}
			} else if(infos[1].equalsIgnoreCase("STOP")) {
				// Remove from session
				session.removeAttribute("recordURL");
				// Check if we have something to record
				if(!recordURLs.isEmpty()) {
					LOG.info("Saving ...");
					try {
						//Save use case table
						String portletVersion = PortletLocalServiceUtil.getPortletById(infos[0]).getPluginPackage().getVersion();
						LOG.info("version de portlet "+portletVersion);
						Record record = RecordLocalServiceUtil.save(infos[2], infos[0], portletVersion);
						LOG.info("...1/2");
						//Save url table
						for (int i = 1; i < recordURLs.size(); i++) {
							String url = recordURLs.get(i).getUrl()+recordURLs.get(i).getParams();
							String type = recordURLs.get(i).getMethod();
							UrlRecordLocalServiceUtil.save(url, type, i, record.getRecordId());
							LOG.info("...");
						}
						LOG.info("...2/2");
					} catch (SystemException e) {
						LOG.error("Error saving use case ...\n"+e.getMessage());
					}
				}
			}
		}
		// pass the request along the filter chain

		chain.doFilter(request, response);
	}

	private static class ResettableStreamHttpServletRequest extends
	HttpServletRequestWrapper {

		private byte[] rawData;
		private HttpServletRequest request;
		private ResettableServletInputStream servletStream;

		public ResettableStreamHttpServletRequest(HttpServletRequest request) {
			super(request);
			this.request = request;
			this.servletStream = new ResettableServletInputStream();
		}


		public void resetInputStream() {
			servletStream.stream = new ByteArrayInputStream(rawData);
		}

		@Override
		public ServletInputStream getInputStream() throws IOException {
			if (rawData == null) {
				rawData = IOUtils.toByteArray(this.request.getReader());
				servletStream.stream = new ByteArrayInputStream(rawData);
			}
			return servletStream;
		}

		@Override
		public BufferedReader getReader() throws IOException {
			if (rawData == null) {
				rawData = IOUtils.toByteArray(this.request.getReader());
				servletStream.stream = new ByteArrayInputStream(rawData);
			}
			return new BufferedReader(new InputStreamReader(servletStream));
		}

		private class ResettableServletInputStream extends ServletInputStream {

			private InputStream stream;

			@Override
			public int read() throws IOException {
				return stream.read();
			}
		}
	}

	private class RecordURL {
		private String method;
		private String url;
		private String params;

		public RecordURL(String method, String requestURL, String params) {
			this.method = method;
			this.url = requestURL;
			this.params = params;
		}

		public String getMethod() {
			return method;
		}

		public void setMethod(String method) {
			this.method = method;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getParams() {
			return params;
		}

		public void setParams(String params) {
			this.params = params;
		}

		@Override
		public String toString() {
			return "RecordURL [method=" + method + ", url=" + url + ", params="
					+ params + "]";
		}

	}
}
