/**
 * Copyright 2015 Altendis, Groupe Excilys (www.altendis.fr)
 */
package com.excilys.liferay.gatling.controller;

import com.excilys.liferay.gatling.NoSuchScenarioException;
import com.excilys.liferay.gatling.model.Login;
import com.excilys.liferay.gatling.model.Process;
import com.excilys.liferay.gatling.model.ProcessType;
import com.excilys.liferay.gatling.model.Scenario;
import com.excilys.liferay.gatling.model.Simulation;
import com.excilys.liferay.gatling.model.SiteMap;
import com.excilys.liferay.gatling.model.AST.SimulationAST;
import com.excilys.liferay.gatling.service.ASTService;
import com.excilys.liferay.gatling.service.LoginLocalServiceUtil;
import com.excilys.liferay.gatling.service.ProcessLocalServiceUtil;
import com.excilys.liferay.gatling.service.ScenarioLocalServiceUtil;
import com.excilys.liferay.gatling.service.SimulationLocalServiceUtil;
import com.excilys.liferay.gatling.service.SiteMapLocalServiceUtil;
import com.excilys.liferay.gatling.service.persistence.LoginUtil;
import com.excilys.liferay.gatling.service.persistence.ProcessUtil;
import com.excilys.liferay.gatling.service.persistence.ScenarioUtil;
import com.excilys.liferay.gatling.service.persistence.SimulationUtil;
import com.excilys.liferay.gatling.util.GatlingUtil;
import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.ReadOnlyException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ValidatorException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
/**
 * Controller linked to the default view
 */
@Controller(value = "ViewController")
@RequestMapping("VIEW")
public class ViewController {

	private static final Log LOG = LogFactoryUtil.getLog(ViewController.class);

	@RenderMapping(params = "render=renderView")
	public String renderRequest(final RenderRequest renderRequest,
			final RenderResponse renderResponse, final Model model) throws SystemException {
		LOG.debug("render View");
		
		/* Record the sites list */
		
		List<Group> listGroups = GatlingUtil.getListOfSites();
		renderRequest.setAttribute("listGroup", listGroups);
		
		/* Initialize the simulation and the scenario if not existant */
		Simulation defaultSimulation = SimulationLocalServiceUtil.createDefaultSimulation();
		Scenario defaultScenario = ScenarioLocalServiceUtil.createDefaultScenario(defaultSimulation);
		
		Login defaultLogin = LoginLocalServiceUtil.createDefaultLogin();
		
		final ThemeDisplay themeDisplay =	(ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		SiteMap defaultSiteMap = SiteMapLocalServiceUtil.siteMapCreation(themeDisplay, defaultScenario.getGroup_id());
		
		Process login = ProcessLocalServiceUtil.createProcess("Login", ProcessType.LOGIN,
				defaultLogin.getPrimaryKey(), 2, 0, defaultScenario.getScenario_id());
		
		Process random = ProcessLocalServiceUtil.createProcess("Random Page", ProcessType.RANDOMPAGE,
				defaultSiteMap.getPrimaryKey(), 3, 1, defaultScenario.getScenario_id());
		
		Process logout = ProcessLocalServiceUtil.createProcess("Logout", ProcessType.LOGOUT,
				null, 1, 2,
				defaultScenario.getScenario_id());
		
		List<Process> processes = new ArrayList<>(3);
		processes.add(login);
		processes.add(random);
		processes.add(logout);
		
		/* Record the simulation and scenario data */
		renderRequest.setAttribute("simulationId", defaultSimulation.getSimulation_id());
		renderRequest.setAttribute("scenarioGroupId", defaultScenario.getGroup_id());
		renderRequest.setAttribute("processes", processes);
		renderRequest.setAttribute("numberOfUsers", defaultScenario.getNumberOfUsers());
		renderRequest.setAttribute("rampUp", defaultScenario.getDuration());
		renderRequest.setAttribute("feederContent", defaultSimulation.getFeederContent());
		return "view";
	}
	

	public void editFeederAction(final ResourceRequest request) throws SystemException, PortalException{
		LOG.debug("Action Triggered : Save Default Simulation");
		
		long simulationId = ParamUtil.getLong(request, "simulationId");
		long scenarioGroupId = ParamUtil.getLong(request, "scenarioGroupId");
		long numberOfUsers = ParamUtil.getLong(request, "numberOfUsers");
		long rampUp = ParamUtil.getLong(request, "rampUp");
		
		String feederContent = ParamUtil.getString(request, "feederContent");
		
		Simulation simulation = SimulationLocalServiceUtil.getSimulation(simulationId);
		List<Scenario> scenarios = ScenarioLocalServiceUtil.findBySimulationId(simulation.getSimulation_id());
		if(scenarios == null || scenarios.isEmpty()) {
			throw new NoSuchScenarioException();
		}
		Scenario scenario = scenarios.get(0);
		
		scenario.setGroup_id(scenarioGroupId);
		scenario.setNumberOfUsers(numberOfUsers);
		scenario.setDuration(rampUp);
		simulation.setFeederContent(feederContent);
	
		List<Process> processes = ProcessLocalServiceUtil.findProcessFromScenarioId(scenario.getScenario_id());
		for (Process process : processes) {
			int time = ParamUtil.getInteger(request, process.getProcess_id()+"");
			if(time != process.getPause()) {
				process.setPause(time);
				ProcessLocalServiceUtil.updateProcess(process);
			}
		}
		
		SimulationLocalServiceUtil.updateSimulation(simulation);
		ScenarioLocalServiceUtil.updateScenario(scenario);
		
	}

	
	@ResourceMapping(value="generateZip")	
	public void exportZippedEnvironment(final ResourceRequest request, final ResourceResponse response) throws ValidatorException, ReadOnlyException, IOException, SystemException, PortalException, Exception {
		LOG.debug("Generating zip file...");

		// Saving datas
		editFeederAction(request);
		
		//long[] simulationsIds = ParamUtil.getLongValues(request, "export");
		Simulation simulation = SimulationLocalServiceUtil.getByName("_default_simulation_");

		// Retreives the defaut scenario frim the simulation id (expected single result)
		List<Scenario> scenarios = ScenarioLocalServiceUtil.findBySimulationId(simulation.getSimulation_id());
		if(scenarios == null || scenarios.isEmpty()) {
			throw new NoSuchScenarioException();
		}
		
		long[] simulationsIds = new long[]{simulation.getSimulation_id()};

		response.setContentType("application/zip");
		response.addProperty("Content-Disposition", "attachment; filename = GatlingSimulations.zip");
		
		List<SimulationAST> scriptASTs = new ArrayList<>();
		for( long simulationId : simulationsIds) {
			scriptASTs.add(ASTService.computesSimulationAST(simulationId, PortalUtil.getPortalURL(request)));
		}
		
		GatlingUtil.zipMyEnvironment(response.getPortletOutputStream(), getClass().getClassLoader(), request, scriptASTs);

		response.addProperty(HttpHeaders.CACHE_CONTROL, "max-age=3600, must-revalidate");
		LOG.debug("Zip generated ...");
	}
	
	
	/**
	 * Takes all the renders without param.
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws SystemException 
	 */
	@RenderMapping
	public String handleRenderRequest(final RenderRequest request,
			final RenderResponse response, final Model model) throws SystemException {
		return renderRequest(request, response, model);
	}

	
}
