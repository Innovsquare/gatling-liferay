package com.excilys.liferay.gatling;

import com.excilys.liferay.gatling.model.Request;
import com.excilys.liferay.gatling.model.Scenario;
import com.excilys.liferay.gatling.service.RequestLocalServiceUtil;
import com.excilys.liferay.gatling.service.ScenarioLocalServiceUtil;
import com.excilys.liferay.gatling.service.SimulationLocalServiceUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Date;
import java.util.List;

public class ScriptGenerator {

	
	private static Log log = LogFactoryUtil.getLog(GatlingPortlet.class);

	/**
	 * 
	 * @param sb
	 */
	static public void generateImports(StringBuilder sb){

		sb.append("import com.excilys.ebi.gatling.core.Predef._\n")
		.append("import com.excilys.ebi.gatling.http.Predef._\n")
		.append("import com.excilys.ebi.gatling.jdbc.Predef._\n")
		.append("import Headers._\n")
		.append("import akka.util.duration._\n")
		.append("import bootstrap._\n")
		.append("import assertions._\n\n");
	}

	/**
	 * 
	 * @param sb
	 * @param simulationId
	 * @throws Exception
	 */
	static public void generateClass(StringBuilder sb, Long simulationId) throws Exception {

		//list of scenario
		List<Scenario> listScenario = ScenarioLocalServiceUtil.findBySimulationId(simulationId);

		generateVar(sb, listScenario);
		generateSetUp(sb, listScenario);
	}

	/**
	 * 
	 * @param sb
	 * @param listScenario
	 */
	private static void generateSetUp(StringBuilder sb, List<Scenario> listScenario) {

		sb.append("\tsetUp(");

		for (Scenario scenario : listScenario) {
			sb.append("\n\t\t")
			.append(scenario.getVariableName())
			.append(".users(")
			.append(scenario.getUsers_per_seconds())
			.append(").ramp(")
			.append(scenario.getDuration())
			.append(")");
		}

		sb.append(")\n\n");

	}

	/**
	 * 
	 * @param sb
	 * @param listScenario
	 * @throws Exception
	 */
	static private void generateVar(StringBuilder sb, List<Scenario> listScenario) throws Exception {
		for (Scenario scenario : listScenario) {
			//declare new scenario
			sb.append("\tval ")
			.append(scenario.getVariableName())
			.append(" = scenario(\"")
			.append(scenario.getVariableName())
			.append("\")\n\t\t.randomSwitch( ");
			double totalWeight = getTotalWeight(scenario);
			List<Request> listRequest = RequestLocalServiceUtil.findByScenarioId(scenario.getScenario_id());
			for (Request request : listRequest) {
				//if checked in the jsp 
				if(request.getChecked() == true ){
					generateRequest(sb, request, scenario, totalWeight);
				}
			}
			//remove at the last line the coma
			sb.deleteCharAt(sb.length()-1);
			sb.append(")\n\n");
		}

	}
	
	/**
	 * 
	 * @param scenario
	 * @return
	 * @throws SystemException
	 */
	static private int getTotalWeight(Scenario scenario) throws SystemException {
		List<Request> listRequest = RequestLocalServiceUtil.findByScenarioId(scenario.getScenario_id());
		int weight = 0;
		for (Request request : listRequest) {
			if(request.isChecked()) weight += request.getWeight();
		}
		return weight;
	}

	/**
	 * 
	 * @param sb
	 * @param request
	 * @param scenario
	 * @param totalWeight
	 * @throws Exception
	 */
	static private void generateRequest(StringBuilder sb, Request request, Scenario scenario, double totalWeight) throws Exception {
		
		String site = scenario.getUrl_site();
		if(request.isPrivatePage()){
			//String is a f****** immutable classe ;(
			site = site.replace("/web/", "/group/");
		}
		int weight = (int) (request.getWeight()*100/totalWeight );
		System.out.println(weight);
		sb.append("\n\t\t")
		.append(weight)
		.append(" -> exec( http(\"")
		.append(request.getName())
		.append("\").get(\"")
		.append(site)
		.append(request.getUrl())
		.append("\")),");

	}
	
	/**
	 * 
	 * @param simulationId
	 * @return
	 * @throws Exception
	 */
	static public String generateSimulation2(Long simulationId)
			throws Exception { 
		Date date =new Date();
		
		StringBuilder sb = new StringBuilder();

		generateImports2(sb);
		sb.append("\nclass " + SimulationLocalServiceUtil.getSimulation(simulationId).getVariableName() + " extends Simulation {\n");
		generateClass2(sb, simulationId);
		sb.append("\n}");
		
		return sb.toString();
	}

	/**
	 * 
	 * @param sb
	 * @param simulationId
	 * @throws Exception
	 */
	static public void generateClass2(StringBuilder sb, Long simulationId) throws Exception {

		//list of scenario
		List<Scenario> listScenario = ScenarioLocalServiceUtil.findBySimulationId(simulationId);

		generateVar(sb, listScenario);
		generateSetUp2(sb, listScenario);
	}
	
	static public void generateImports2(StringBuilder sb){

		sb.append("import io.gatling.core.Predef._\n")
		.append("import io.gatling.http.Predef._\n")
		.append("import io.gatling.jdbc.Predef._\n")
		.append("import io.gatling.http.Headers.Names._\n")
		.append("import scala.concurrent.duration._\n")
		.append("import bootstrap._\n")
		.append("import assertions._\n\n");
	}
	
	private static void generateSetUp2(StringBuilder sb, List<Scenario> listScenario) {

		sb.append("\tsetUp(");

		for (Scenario scenario : listScenario) {
			sb.append("\n\t\t")
			.append(scenario.getVariableName())
			.append(".inject(ramp(")
			.append(scenario.getUsers_per_seconds())
			.append(" users ) over (")
			.append(scenario.getDuration())
			.append(" seconds))");
		}

		sb.append(")\n\n");

	}
}
