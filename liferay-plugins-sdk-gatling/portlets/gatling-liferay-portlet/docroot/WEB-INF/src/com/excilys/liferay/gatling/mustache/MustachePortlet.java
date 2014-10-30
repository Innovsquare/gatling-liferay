package com.excilys.liferay.gatling.mustache;

import java.util.ArrayList;
import java.util.List;

import com.excilys.liferay.gatling.model.Record;
import com.excilys.liferay.gatling.model.UrlRecord;
import com.excilys.liferay.gatling.mustache.util.NameAndUrl;
import com.excilys.liferay.gatling.mustache.util.NameUrlAndPlid;
import com.excilys.liferay.gatling.mustache.util.NameUrlType;
import com.excilys.liferay.gatling.mustache.util.RecorderGet;
import com.excilys.liferay.gatling.service.UrlRecordLocalServiceUtil;

public class MustachePortlet {

	private String name, url;
	private double pourcentage;
	private boolean last;
	private NameAndUrl assetPublisherSimple;
	private NameAndUrl messageBoardSimple;
	private NameAndUrl messageBoardPost;
	private NameUrlAndPlid wikiDisplaySimple;
	private List<RecorderGet> recorderGet;
	private List<MustacheScript> scripts;

	public MustachePortlet(String name, String url, double pourcentage, boolean last) {
		this.name = name;
		this.url = url;
		this.pourcentage = pourcentage;
		this.last = last;
	}

	{
		scripts = new ArrayList<MustacheScript>();
		recorderGet = new ArrayList<RecorderGet>();
	}

	/*--- Scripts ---*/

	public void addScript(MustacheScript script) {
		this.scripts.add(script);
	}

	public List<MustacheScript> getScripts() {
		return scripts;
	}

	public void setScripts(List<MustacheScript> scripts) {
		this.scripts = scripts;
	}

	/**
	 * remove the comma for the last one
	 * transform weight to percentage
	 */
	public void setLastScript() {
		double total = 0;
		double subTotal = 0;
		double inter = 0;
		for(MustacheScript mustacheScript: scripts){
			total += mustacheScript.getPourcentage();
		}
		for(MustacheScript mustacheScript: scripts){
			inter = (double) ((int) ((int)mustacheScript.getPourcentage()*10000/total))/100;
			subTotal += inter;
			mustacheScript.setPourcentage(inter);
		}
		scripts.get(scripts.size()-1).setPourcentage((double)((int)((inter+subTotal-100)*100))/100);

	}

	/* --- Asset Publisher --- */

	public NameAndUrl getAssetPublisherSimple() {
		return assetPublisherSimple;
	}

	public void setAssetPublisherSimple(String name, String url, double weight) {
		addScript(new MustacheScript(name, weight));
		this.assetPublisherSimple = new NameAndUrl(name , url);
	}

	/* --- Message Board --- */

	public NameAndUrl getMessageBoardSimple() {
		return messageBoardSimple;
	}

	public void setMessageBoardSimple(String name, String url, double weight) {
		addScript(new MustacheScript(name, weight));
		this.messageBoardSimple = new NameAndUrl(name , url);
	}

	public NameAndUrl getMessageBoardPost() {
		return messageBoardPost;
	}

	public void setMessageBoardPost(String name, String url, double weight) {
		addScript(new MustacheScript(name, weight));
		this.messageBoardPost = new NameAndUrl(name , url);
	}

	/* --- Wiki Display --- */

	public NameUrlAndPlid getWikiDisplaySimple() {
		return wikiDisplaySimple;
	}

	public void setWikiDisplaySimple(String name, String url, long plid, double weight) {
		addScript(new MustacheScript(name, weight));
		this.wikiDisplaySimple = new NameUrlAndPlid(name, url, plid);
	}

	/* --- Recorder Url --- */

	public void addRecorderGet(RecorderGet recorderUrl) {
		this.recorderGet.add(recorderUrl);
	}

	public List<RecorderGet> getRecorderGet() {
		return recorderGet;
	}

	public void setRecorderGet(List<RecorderGet> recorderUrl) {
		this.recorderGet = recorderUrl;
	}


	/* --- getter setter generic --- */


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public double getPourcentage() {
		return pourcentage;
	}

	public MustachePortlet setPourcentage(double pourcentage) {
		this.pourcentage = pourcentage;
		return this;
	}

	public boolean isLast() {
		return last;
	}

	public MustachePortlet setLast(boolean last) {
		this.last = last;
		return this;
	}

	public void addRecorder(Record record, double weight, String beginningUrl) throws Exception{
		String nameVariable = "record_" + record.getName().replace(" ", "");
		addScript(new MustacheScript(nameVariable, weight));
		List<NameUrlType> listNameUrlType = new ArrayList<NameUrlType>();
		List<UrlRecord> listUrlRecord = UrlRecordLocalServiceUtil.findByRecordId(record.getRecordId());
		for (int i = 0; i < listUrlRecord.size(); i++) {
			UrlRecord URLrecord = listUrlRecord.get(i);
			String url = URLrecord.getUrl();
			//if post -> last request was a form
			if(listUrlRecord.get(i).getType().equalsIgnoreCase("post")) {
				if(i > 0) {
					listNameUrlType.get(i-1).setForm(true);
				}
				//Remove redirect call
				if(i < listUrlRecord.size() && listUrlRecord.get(i+1).getType().equalsIgnoreCase("get")) {
					i++;
				}
				/* replace with runtime formDate */
				url = url.replaceFirst("_"+record.getPortletId()+"_formDate=.+?&", "_"+record.getPortletId()+"_formDate=\\${formDatePortlet}&");

			}
			listNameUrlType.add(new NameUrlType(nameVariable+i, beginningUrl+url, URLrecord.getType().toLowerCase(), record.getPortletId()));
		}
		this.recorderGet.add(new RecorderGet(nameVariable, listNameUrlType));
	}	

}
