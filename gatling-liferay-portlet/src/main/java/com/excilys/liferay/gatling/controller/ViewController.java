/**
 * Copyright 2015 Altendis, Groupe Excilys (www.altendis.fr)
 */
package com.excilys.liferay.gatling.controller;

import com.excilys.liferay.gatling.model.Scenario;
import com.excilys.liferay.gatling.model.Simulation;
import com.excilys.liferay.gatling.service.ScenarioLocalServiceUtil;
import com.excilys.liferay.gatling.service.SimulationLocalServiceUtil;
import com.excilys.liferay.gatling.service.persistence.ScenarioUtil;
import com.excilys.liferay.gatling.service.persistence.SimulationUtil;
import com.excilys.liferay.gatling.util.GatlingUtil;
import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.theme.ThemeDisplay;

import java.io.IOException;
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
		
		Simulation defaultSimulation = SimulationLocalServiceUtil.getByName("_default_simulation_");
		Scenario defaultScenario = null;
		
		if(defaultSimulation != null){
			List<Scenario> scenarios = ScenarioLocalServiceUtil.findBySimulationId(defaultSimulation.getSimulation_id());
			if(scenarios != null && !scenarios.isEmpty()){
				defaultScenario = scenarios.get(0);
			}
			else {
				defaultScenario = createDefaultScenario(defaultSimulation);
			}
		}
		else {
			defaultSimulation = createDefaultSimulation();
			defaultScenario = createDefaultScenario(defaultSimulation);
		}
		
		/* Record the simulation and scenario data */
		
		renderRequest.setAttribute("simulationId", defaultSimulation.getSimulation_id());
		renderRequest.setAttribute("scenarioGroupId", defaultScenario.getGroup_id());
		renderRequest.setAttribute("numberOfUsers", defaultScenario.getNumberOfUsers());
		renderRequest.setAttribute("rampUp", defaultScenario.getDuration());
		renderRequest.setAttribute("feederContent", defaultSimulation.getFeederContent());
		return "view";
	}
	
	/**
	 * Creates the empty default simulation, persists it and returns it.
	 * @return The fresh default simulation
	 * @throws SystemException If an error occures in services
	 */
	public static Simulation createDefaultSimulation() throws SystemException{
		Simulation simulation = SimulationUtil.create(CounterLocalServiceUtil.increment(Simulation.class.getName()));
		simulation.setName("_default_simulation_");
		simulation.setFeederContent("");
		simulation.setIsFeederAFile(false);
		simulation.persist();
		return simulation;
	}
	
	/**
	 * Creates the empty default scenario, persists it and returns it.
	 * @param simulation The simulation that contains the new scenario
	 * @return The fresh default scenario
	 * @throws SystemException If an error occures in services
	 */
	public static Scenario createDefaultScenario(Simulation simulation) throws SystemException {
		Scenario scenario = ScenarioUtil.create(CounterLocalServiceUtil.increment(Scenario.class.getName()));
		scenario.setName("_default_scenario_");
		scenario.setGroup_id(0);
		scenario.setSimulation_id(simulation.getSimulation_id());
		scenario.setNumberOfUsers(10);
		scenario.setDuration(5);
		scenario.persist();
		return scenario;
	}

	
	@ResourceMapping(value="generateZip")	
	public void serveManyScript(final ResourceRequest request, final ResourceResponse response) throws ValidatorException, ReadOnlyException, IOException, SystemException, PortalException, Exception {
		LOG.debug("Generating zip file...");
		String template = "/templateGatling2.X.X.mustache";
		//create and export only one file with scenario script for this simulation id
		Simulation simulation = null;
		//final Date date = new Date();		
		final long[] simulationsIds = ParamUtil.getLongValues(request, "export");
		
		response.setContentType("application/zip");
		response.addProperty("Content-Disposition", "attachment; filename = GatlingSimulations_it_works.zip");
		
		final ThemeDisplay themeDisplay =	(ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		
		GatlingUtil.zipMyEnvironment(response.getPortletOutputStream(), getClass().getClassLoader(), themeDisplay, 20182);
		
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
	
	
	
	/* Test functions : Need to be removed */
	
	@ActionMapping(params="action=debugNico")
	public void debugNico(final ActionRequest request, final ActionResponse response, final Model model) throws SystemException, NoSuchModelException{
		LOG.debug("Debug Nico called");
		response.setRenderParameter("render", "renderView");
	}
	

	@ActionMapping(params="action=debugYann")
	public void debugYann(final ActionRequest request, final ActionResponse response, final Model model) throws SystemException, NoSuchModelException{
		LOG.debug("Debug Yann called");

		response.setRenderParameter("render", "renderView");
	}
	
	/* Test functions : Need to be removed */

}
