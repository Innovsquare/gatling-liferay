<%@include file="/html/gatling/header.jsp"%>

<%--
	session errors
--%>
<liferay-ui:error key="simulation-name-required" message="simulation-name-required" />
<liferay-ui:error key="simulation-name-already-used" message="simulation-name-already-used" />
<liferay-ui:error key="simulation-variable-required" message="simulation-variable-required" />
<liferay-ui:error key="simulation-variable-syntaxe" message="simulation-variable-syntaxe" />
<%--
	Header
 --%>

<liferay-ui:header title="simulation-list-header"></liferay-ui:header>

<%--
	FAQ link
--%> 
<portlet:renderURL var="helpURL">
	<portlet:param name="page" value="/html/gatling/help.jsp" />
</portlet:renderURL>
<div class="well well-small">
	<a target="blank" href='<%= PortletProps.get("gatling-faq") %>'>
		<span class="label label-warning"><liferay-ui:message key="help-gatling-wiki" /></span>
	</a> 
	<a href="${helpURL}">
		<span class="label"><liferay-ui:message key="help-how-to-use-portlet" /></span>
	</a> 
	<a href="#" class="toggle" data-content="help-simulation">
		<span class="label label-info"><liferay-ui:message key="help-what-simulation" /></span>

	</a>
</div>

<div id="help-simulation" class="alert alert-info help-text help-content-hidden">
	<liferay-ui:message key="simulation-explanation" />
</div>
<%-- If user has permission --%>
<%--NavBar --%>
<div class="navbar">
	<div class="navbar-inner">
		<ul class="nav">
			<li><a id="newSimulation" href="#"> <i class="icon-plus"></i> <liferay-ui:message key="simulation-list-btn-add-simulation" />
			</a></li>
		</ul>
	</div>
</div>

<portlet:resourceURL var="resourceUrl" />
<aui:form action="${resourceUrl}" method="post">
	<h5><liferay-ui:message key="simulation-list-export" /></h5>
	<aui:select label="simulation-list-version-choice" name="gatlingVersion" inlineField="true">
		<aui:option value="2">Gatling 2.0 M3</aui:option>
		<aui:option value="1">Gatling 1.5</aui:option>
	</aui:select>
	<liferay-util:buffer var="textExport">
		<i class="icon-print"></i>
		<liferay-ui:message key="simulation-list-export-btn" />
	</liferay-util:buffer>
	<aui:button cssClass="inline-button" type="submit" value="${textExport }" />
	<%--
		Search container (table) 
	--%>
	<liferay-ui:search-container emptyResultsMessage="simulation-list-empty">
		<%--List of data to display --%>
		<liferay-ui:search-container-results results="${listSimulation }" total="${listSimulation.size() }" />
		<%--for each  column --%>
		<liferay-ui:search-container-row className="com.excilys.liferay.gatling.model.Simulation" keyProperty="simulation_id" modelVar="simulation">
			<liferay-ui:search-container-column-text>
				<c:if test="${MapSimulation.get(simulation)[1] == 2}">
					<input type="checkbox" class="checkLine"
						name="<portlet:namespace/>export" id="<portlet:namespace/>checkAll"
						value="${simulation.simulation_id}" />
				</c:if>
			</liferay-ui:search-container-column-text>
			<portlet:renderURL var="editSimulationURL">
				<portlet:param name="page" value="/html/gatling/editSimulation.jsp" />
				<portlet:param name="simulationId" value="${simulation.simulation_id }" />
			</portlet:renderURL>
			<liferay-ui:search-container-column-text name="simulation-list-table-header-name" value="${simulation.name }" href="${editSimulationURL}" />
			<liferay-ui:search-container-column-text name="simulation-list-table-header-scenarionb" value="${MapSimulation.get(simulation)[0] }" />
			<liferay-ui:search-container-column-text name="simulation-list-table-header-state">
				<c:choose>
					<c:when test="${MapSimulation.get(simulation)[1] == 2}">
						<span class="badge badge-success"><i class=" icon-ok"></i></span>
						<liferay-ui:icon-help message="message-help-info-state-simulation-success"/>
					</c:when>
					<c:when test="${MapSimulation.get(simulation)[1] == 1}">
						<span class="badge badge-warning"><i class="icon-pencil"></i></span>
						<liferay-ui:icon-help message="message-help-info-state-simulation-warning"/>
					</c:when>
					<c:otherwise>
						<span class="badge badge-important"><i class="icon-ban-circle"></i></span>
						<liferay-ui:icon-help message="message-help-info-state-simulation-important"/>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>
	
			<%--delete button --%>
			<liferay-ui:search-container-column-text name="delete" >
			<portlet:actionURL var="deleteSimulationURL" name="removeSimulation">
				<portlet:param name="simulationId" value="${simulation.simulation_id }" />
			</portlet:actionURL>
	
			<liferay-ui:icon-delete url="${deleteSimulationURL}" />
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>
		<%--iterate and display the list --%>
		<liferay-ui:search-iterator paginate="false" />
	</liferay-ui:search-container>
</aui:form>
<%--submit to addSimulation --%>
<portlet:actionURL name="addSimulation" var="addSimulationURL" windowState="normal" />
<%-- add simulation form --%>
<div id="newFormSimulation" hidden="true">
	<aui:form action="${addSimulationURL}" name="fm">
		<div class="well well-small">
			<p>
				<liferay-ui:icon-help message="About this page">
					<liferay-ui:message key="create-simulation-help" />
				</liferay-ui:icon-help>
			</p>
		</div>
		<aui:input label="simulation-list-form-name-simulation" name="simulationName">
			<aui:validator name="required" />
			<aui:validator name="alphanum" />
			<aui:validator name="custom" errorMessage="simulation-name-already-used">
			 		function (val, fieldNode, ruleValue) {
					var result = false;
					var list = ${listOfSimulationName};
					if (list.indexOf(val) == -1) {
						result = true;
					}
					return result;
				}
			</aui:validator>
		</aui:input>
		<aui:button type="submit" />
	</aui:form>
</div>

<script type="text/javascript">
	AUI().use('aui-modal', 'aui-base', 'event', function(A) {
		var modal = new A.Modal({
			bodyContent : A.one("#newFormSimulation").html(),
			centered : true,
			headerContent : '<h3><liferay-ui:message key="simulation-list-form-header" /></h3>',
			modal : true,
			resizable : false,
			visible : false,
			zIndex : 100,
		}).render();
		//Remove the form template (no duplication otherwise it will never validate the pop-up)
		A.one("#newFormSimulation").empty();

		A.one('#newSimulation').on('click', function() {
			modal.show();
		});

		A.all(".toggle").each(function() {
			this.on('click', function(event) {
				var contentId = this.getData("content");
				var texts = A.all(".help-content-display");
				texts.replaceClass("help-content-display", "help-content-hidden");
				var helpText = A.one("#" + contentId);
				if (this.hasClass('help-content-selected')) {
					helpText.replaceClass("help-content-display", "help-content-hidden");
					this.removeClass("help-content-selected");
				} else {
					helpText.replaceClass("help-content-hidden", "help-content-display");
					this.addClass("help-content-selected");
				}
			});
		});
		/*
		 * Multi select
		 */
		var lastChecked = null;
        var checkboxes = A.all(".checkLine");
        checkboxes.each(function() {
        		this.on('click', function(event) {
	                if(!lastChecked) {
	                    lastChecked = this;
	                    return;
	                }
	
	                if(event.shiftKey) {
	                    var start = checkboxes.indexOf(this);
	                    var end = checkboxes.indexOf(lastChecked);
	
	                    checkboxes.slice(Math.min(start,end), Math.max(start,end)+ 1).attr('checked', lastChecked.get("checked"));
	
	                }
	                lastChecked = this;
        		});
        });
	});
</script>
