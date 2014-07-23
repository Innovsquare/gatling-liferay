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
	lien vers la FAQ 
--%>
<portlet:renderURL var="helpURL">
	<portlet:param name="page" value="/html/gatling/help.jsp" />
</portlet:renderURL>
<div class="well well-small">
	<a target="blank" href="https://github.com/excilys/gatling/wiki/Getting-Started">
		<span class="label label-warning"><liferay-ui:message key="help-faq-gatling"/></span>
	</a>
	<a href="${helpURL}">
		<span class="label"><liferay-ui:message key="help-how-to-use-portlet"/></span>
	</a>
	<a href="#" class="toggle" data-content="help-simulation">
		<span class="label label-info"><liferay-ui:message key="help-what-simulation"/></span>
	</a>
	<p id="help-simulation" class="help-text help-content-hidden text-info" >
		What is a simulation help text ....
	</p>
</div>


<%--
	Search container (tableau) 
--%>
<liferay-ui:search-container emptyResultsMessage="simulation-list-empty" >
	<%--Liste sur laquelle on travail --%>
	<liferay-ui:search-container-results results="${listSimulation }" total="${listSimulation.size() }" />
	<%--it�ration des colonnes --%>
	<liferay-ui:search-container-row
		className="com.liferay.sample.model.Simulation"
		keyProperty="simulation_id" modelVar="simulation">
		<portlet:renderURL var="editSimulationURL">
			<portlet:param name="page" value="/html/gatling/editSimulation.jsp" />
			<portlet:param name="simulationId"
				value="${simulation.simulation_id }" />
		</portlet:renderURL>
		<%--un champs texte --%>
		<liferay-ui:search-container-column-text
			name="simulation-list-table-header-name" value="${simulation.name }"
			href="${editSimulationURL}" />
		<%--menu action --%>
		<liferay-ui:search-container-column-jsp align="right"
			path="/html/gatling/simulation_actions.jsp" />
	</liferay-ui:search-container-row>
	<%--itere et affiche la liste --%>
	<liferay-ui:search-iterator paginate="false" />
</liferay-ui:search-container>

<aui:button id="newSimulation"
	value="simulation-list-btn-add-simulation"></aui:button>

<%--submit to addSimulation --%>
<portlet:actionURL name="addSimulation" var="addSimulationURL"
	windowState="normal" />
<%--Formulaire d'ajout --%>
<div id="newFormSimulation" hidden="true">
	<div class="well well-small">
		<p><liferay-ui:icon-help message="About this page" ><liferay-ui:message key="create-simulation-help" /></liferay-ui:icon-help></p>
	</div>
	<aui:form action="${addSimulationURL}" name="simulation_fm"
		id="simulation_fm">
		<aui:input label="simulation-list-form-nom-simulation" name="simulationName" id="simulationName">
			<aui:validator name="required"></aui:validator>
			<aui:validator name="alphanum"></aui:validator>
			
		</aui:input>
		<aui:input label="simulation-list-form-variable-name" name="variableName" id="variableName" prefix="simulation" readonly="readonly">
			<aui:validator name="required"></aui:validator>
		</aui:input>
		<aui:button type="submit"></aui:button>
	</aui:form>
</div>

<script type="text/javascript">
	YUI().use(
	  'aui-modal',
	  function(Y) {
	    var modal = new Y.Modal(
	      {
	        bodyContent: AUI().one("#newFormSimulation").html(),
	        centered: true,
	        headerContent: '<h3><liferay-ui:message key="simulation-list-form-header" /></h3>',
	        modal: true,
	        resizable: false,
	        visible: false,
	        zIndex: 100,
	        width: 450
	      }
	    ).render();
	    
	    Y.one('#newSimulation').on(
	      'click',
	      function() {
	        modal.show();
	      }
	    );
	  }
	);
	
	AUI().use(
		'aui-base',
		'event',
		function(A) {
			A.one("#<portlet:namespace />simulationName").on("keyup", function(e) {
				A.one("#<portlet:namespace />variableName").val(this.val().replace(/\W/g, ''));
			});
			
			A.all(".toggle").each(function() {
				this.on('click',function(event) {
					var contentId = this.getData("content");
					console.log('#'+contentId);
					var texts = A.all(".help-content-display");
					texts.replaceClass("help-content-display","help-content-hidden");
					var helpText = A.one("#"+contentId);
					if(this.hasClass('help-content-selected')) {
						helpText.replaceClass("help-content-display","help-content-hidden");
						this.removeClass("help-content-selected");
					} else {
						console.log('display');
						helpText.replaceClass("help-content-hidden","help-content-display");
						this.addClass("help-content-selected");
					}
				});
			});
		}
	);
	
</script>
