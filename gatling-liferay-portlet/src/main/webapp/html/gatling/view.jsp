<%-- 
	Copyright 2015 Altendis, Groupe Excilys (www.altendis.fr)
--%>
<%@include file="/html/gatling/header.jsp"%>


<style>

.action {
	display: inline-block;
	background: #ffdd99;
	height: 60px;
	line-height: 60px;
	margin: 15px 20px;
	padding: 0px 20px;
	border-radius: 10px;
	text-align: center;
}

.time-separator-container {
	display: inline-block ;
 	height: 60px;
	vertical-align: middle;
	text-align: center;
}

.time-separator {
	margin-top:5px;
 	display: block ;
	vertical-align: middle;
	text-align: center;
	height: 50px;
}

</style>


<%-- Title --%>
<liferay-ui:header title="javax.portlet.title" />


<%-- Top Menu --%>
<div class="well well-small">

	<%-- Wiki Gatling Link --%>
	<a target="blank" href='<%=PortletProps.get("gatling-wiki")%>'
		class="label label-warning">
		<i class="icon-share"></i> <liferay-ui:message key="help-gatling-wiki" />
	</a>

	<%-- Advanced View Link --%>
	<portlet:renderURL var="renderAdvancedView">
		<portlet:param name="render" value="renderAdvancedView" />
	</portlet:renderURL>
	<a href="${renderAdvancedView}" id="advancedButon" class="label label-info"> <i
		class="icon-wrench"></i> <liferay-ui:message key="advanced-test-btn"/>
	</a>
	
	<%--Take a tour view link --%>
	<a href="#" class="label label-success" onclick="tourDefaultFirstSimu.start();">
		<i class="icon-list-alt"></i> <liferay-ui:message key="take-a-tour" />
	</a> 
	
	
	<%-- Portlet View --%>
	<portlet:renderURL var="renderRecorderView">
		<portlet:param name="render" value="renderRecorderView" />
	</portlet:renderURL>
	<a href="${renderRecorderView}" class="label label-success">
		<i class="icon-heart"></i> Recorder
	</a>

</div>


<%-- NavBar --%>
<div class="navbar">
	<div class="navbar-inner">
		<ul class="nav">
			<li>
				
				<%-- Export Button --%>
				<portlet:resourceURL id="generateZip" var="resourceUrl" />
				<a id="exportToggle" href="${resourceUrl}">
					<i class="icon-print"></i> <liferay-ui:message key="simulation-export"/>
				</a>
				
			</li>
		</ul>
	</div>
</div>


<%-- Simulation Form --%>

<portlet:actionURL var="saveDefaultSimulation">
	<portlet:param name="action" value="saveDefaultSimulation" />
</portlet:actionURL>

<aui:form action="${resourceUrl}" method="post">

	<%-- Scenario FieldSet --%>
	<aui:fieldset class="fieldset">
	
		<aui:input name="simulationId" type="hidden" value="${simulationId}" />
		
		<%-- Scenario Title --%>
		<legend class="fieldset-legend">
			<span class="legend">1.Design your scenario</span>
		</legend>


		<%-- Scenario --%>
		<div class="scenario" >
			<c:forEach items="${processes}" var="process" varStatus="i">
				<div class="action">${process.name}</div>

				<c:if test="${!i.last}" >
				<div class="time-separator-container">
					<div class="time-separator">
						<div class="icon-chevron-right"></div>
						<div class="time">
						<aui:input 	type="number" style="width:2em" label="" inlineLabel="left" name="${process.process_id}" value="${process.pause}" inlineField="true"/>s
						</div>
					</div>
				</div>
				</c:if>
				
			</c:forEach>
		</div>

	</aui:fieldset>


	<%-- Details Fieldset --%>
	<aui:fieldset label="2.Configure your injection profile">
		<aui:select label="simulation-edit-form-sites" name="scenarioGroupId"
			required="true">
			<c:forEach var="group" items="${listGroup}">
				<c:set var="isSelected" scope="request"
					value="${group.groupId eq scenarioGroupId}" />
				<aui:option label="${group.descriptiveName}"
					value="${group.groupId}" selected="isSelected" />
			</c:forEach>
		</aui:select>

		<aui:input label="scenario-edit-nb-users-per-second"
			name="numberOfUsers" value="${numberOfUsers}" inlineField="true"
			helpMessage="nbuser-info-help">
			<aui:validator name="required" />
			<aui:validator name="number" />
			<aui:validator name="min">1</aui:validator>
		</aui:input>

		<aui:input label="ramp-over-duration" name="rampUp" value="${rampUp}"
			inlineField="true" helpMessage="duration-info-help">
			<aui:validator name="required" />
			<aui:validator name="number" />
			<aui:validator name="min">1</aui:validator>
		</aui:input>

	</aui:fieldset>

	
	<%-- Login Feeder Fieldset --%>
	<aui:fieldset label="3.Specify your feeders">
		<aui:input name="feederContent"
			label="write-one-account-and-password-per-line" type="textarea"
			cssClass="textarea-feeder" value="${feederContent}"></aui:input>

	</aui:fieldset>
	
	<%-- Login Feeder Fieldset --%>
	<aui:fieldset label="4.Let's export and run with Gatling">
		<%-- Submit/Save Button --%>
		<aui:button type="submit" value="Export the simulation"
			cssClass="pull-left" />

	</aui:fieldset>	
</aui:form>


<script type="text/javascript">
<%@ include file="/js/defaultTourSimulation.js" %>
</script>
