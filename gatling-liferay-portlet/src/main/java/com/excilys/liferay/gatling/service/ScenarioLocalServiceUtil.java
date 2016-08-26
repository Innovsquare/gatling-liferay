package com.excilys.liferay.gatling.service;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.InvokableLocalService;

/**
 * Provides the local service utility for Scenario. This utility wraps
 * {@link com.excilys.liferay.gatling.service.impl.ScenarioLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ScenarioLocalService
 * @see com.excilys.liferay.gatling.service.base.ScenarioLocalServiceBaseImpl
 * @see com.excilys.liferay.gatling.service.impl.ScenarioLocalServiceImpl
 * @generated
 */
public class ScenarioLocalServiceUtil {
    private static ScenarioLocalService _service;

    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never modify this class directly. Add custom service methods to {@link com.excilys.liferay.gatling.service.impl.ScenarioLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
     */

    /**
    * Adds the scenario to the database. Also notifies the appropriate model listeners.
    *
    * @param scenario the scenario
    * @return the scenario that was added
    * @throws SystemException if a system exception occurred
    */
    public static com.excilys.liferay.gatling.model.Scenario addScenario(
        com.excilys.liferay.gatling.model.Scenario scenario)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getService().addScenario(scenario);
    }

    /**
    * Creates a new scenario with the primary key. Does not add the scenario to the database.
    *
    * @param scenario_id the primary key for the new scenario
    * @return the new scenario
    */
    public static com.excilys.liferay.gatling.model.Scenario createScenario(
        long scenario_id) {
        return getService().createScenario(scenario_id);
    }

    /**
    * Deletes the scenario with the primary key from the database. Also notifies the appropriate model listeners.
    *
    * @param scenario_id the primary key of the scenario
    * @return the scenario that was removed
    * @throws PortalException if a scenario with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    public static com.excilys.liferay.gatling.model.Scenario deleteScenario(
        long scenario_id)
        throws com.liferay.portal.kernel.exception.PortalException,
            com.liferay.portal.kernel.exception.SystemException {
        return getService().deleteScenario(scenario_id);
    }

    /**
    * Deletes the scenario from the database. Also notifies the appropriate model listeners.
    *
    * @param scenario the scenario
    * @return the scenario that was removed
    * @throws SystemException if a system exception occurred
    */
    public static com.excilys.liferay.gatling.model.Scenario deleteScenario(
        com.excilys.liferay.gatling.model.Scenario scenario)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getService().deleteScenario(scenario);
    }

    public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
        return getService().dynamicQuery();
    }

    /**
    * Performs a dynamic query on the database and returns the matching rows.
    *
    * @param dynamicQuery the dynamic query
    * @return the matching rows
    * @throws SystemException if a system exception occurred
    */
    @SuppressWarnings("rawtypes")
    public static java.util.List dynamicQuery(
        com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getService().dynamicQuery(dynamicQuery);
    }

    /**
    * Performs a dynamic query on the database and returns a range of the matching rows.
    *
    * <p>
    * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.excilys.liferay.gatling.model.impl.ScenarioModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
    * </p>
    *
    * @param dynamicQuery the dynamic query
    * @param start the lower bound of the range of model instances
    * @param end the upper bound of the range of model instances (not inclusive)
    * @return the range of matching rows
    * @throws SystemException if a system exception occurred
    */
    @SuppressWarnings("rawtypes")
    public static java.util.List dynamicQuery(
        com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
        int end) throws com.liferay.portal.kernel.exception.SystemException {
        return getService().dynamicQuery(dynamicQuery, start, end);
    }

    /**
    * Performs a dynamic query on the database and returns an ordered range of the matching rows.
    *
    * <p>
    * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.excilys.liferay.gatling.model.impl.ScenarioModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
    * </p>
    *
    * @param dynamicQuery the dynamic query
    * @param start the lower bound of the range of model instances
    * @param end the upper bound of the range of model instances (not inclusive)
    * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
    * @return the ordered range of matching rows
    * @throws SystemException if a system exception occurred
    */
    @SuppressWarnings("rawtypes")
    public static java.util.List dynamicQuery(
        com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
        int end,
        com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getService()
                   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
    }

    /**
    * Returns the number of rows that match the dynamic query.
    *
    * @param dynamicQuery the dynamic query
    * @return the number of rows that match the dynamic query
    * @throws SystemException if a system exception occurred
    */
    public static long dynamicQueryCount(
        com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getService().dynamicQueryCount(dynamicQuery);
    }

    /**
    * Returns the number of rows that match the dynamic query.
    *
    * @param dynamicQuery the dynamic query
    * @param projection the projection to apply to the query
    * @return the number of rows that match the dynamic query
    * @throws SystemException if a system exception occurred
    */
    public static long dynamicQueryCount(
        com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
        com.liferay.portal.kernel.dao.orm.Projection projection)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getService().dynamicQueryCount(dynamicQuery, projection);
    }

    public static com.excilys.liferay.gatling.model.Scenario fetchScenario(
        long scenario_id)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getService().fetchScenario(scenario_id);
    }

    /**
    * Returns the scenario with the primary key.
    *
    * @param scenario_id the primary key of the scenario
    * @return the scenario
    * @throws PortalException if a scenario with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    public static com.excilys.liferay.gatling.model.Scenario getScenario(
        long scenario_id)
        throws com.liferay.portal.kernel.exception.PortalException,
            com.liferay.portal.kernel.exception.SystemException {
        return getService().getScenario(scenario_id);
    }

    public static com.liferay.portal.model.PersistedModel getPersistedModel(
        java.io.Serializable primaryKeyObj)
        throws com.liferay.portal.kernel.exception.PortalException,
            com.liferay.portal.kernel.exception.SystemException {
        return getService().getPersistedModel(primaryKeyObj);
    }

    /**
    * Returns a range of all the scenarios.
    *
    * <p>
    * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.excilys.liferay.gatling.model.impl.ScenarioModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
    * </p>
    *
    * @param start the lower bound of the range of scenarios
    * @param end the upper bound of the range of scenarios (not inclusive)
    * @return the range of scenarios
    * @throws SystemException if a system exception occurred
    */
    public static java.util.List<com.excilys.liferay.gatling.model.Scenario> getScenarios(
        int start, int end)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getService().getScenarios(start, end);
    }

    /**
    * Returns the number of scenarios.
    *
    * @return the number of scenarios
    * @throws SystemException if a system exception occurred
    */
    public static int getScenariosCount()
        throws com.liferay.portal.kernel.exception.SystemException {
        return getService().getScenariosCount();
    }

    /**
    * Updates the scenario in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
    *
    * @param scenario the scenario
    * @return the scenario that was updated
    * @throws SystemException if a system exception occurred
    */
    public static com.excilys.liferay.gatling.model.Scenario updateScenario(
        com.excilys.liferay.gatling.model.Scenario scenario)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getService().updateScenario(scenario);
    }

    /**
    * Returns the Spring bean ID for this bean.
    *
    * @return the Spring bean ID for this bean
    */
    public static java.lang.String getBeanIdentifier() {
        return getService().getBeanIdentifier();
    }

    /**
    * Sets the Spring bean ID for this bean.
    *
    * @param beanIdentifier the Spring bean ID for this bean
    */
    public static void setBeanIdentifier(java.lang.String beanIdentifier) {
        getService().setBeanIdentifier(beanIdentifier);
    }

    public static java.lang.Object invokeMethod(java.lang.String name,
        java.lang.String[] parameterTypes, java.lang.Object[] arguments)
        throws java.lang.Throwable {
        return getService().invokeMethod(name, parameterTypes, arguments);
    }

    /**
    * Creates the empty default scenario, persists it and returns it.
    *
    * @param simulation The simulation that contains the new scenario
    * @param themeDisplay
    * @return The fresh default scenario
    * @throws SystemException If an error occures in services
    */
    public static com.excilys.liferay.gatling.model.Scenario createDefaultScenario(
        com.excilys.liferay.gatling.model.Simulation simulation)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getService().createDefaultScenario(simulation);
    }

    public static int countBySimulationId(long simulationId)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getService().countBySimulationId(simulationId);
    }

    public static java.util.List<com.excilys.liferay.gatling.model.Scenario> findBySimulationId(
        long simulationId)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getService().findBySimulationId(simulationId);
    }

    /**
    * Remove all {@link Scenario} (and children) for a given simulationId
    */
    public static void removeBySimulationIdCascade(long simulationId)
        throws com.liferay.portal.NoSuchModelException,
            com.liferay.portal.kernel.exception.SystemException {
        getService().removeBySimulationIdCascade(simulationId);
    }

    /**
    * Remove all {@link Request} for a scenarioId
    */
    public static void removeByIdCascade(long scenarioId)
        throws com.liferay.portal.NoSuchModelException,
            com.liferay.portal.kernel.exception.SystemException {
        getService().removeByIdCascade(scenarioId);
    }

    /**
    * Check if name is unique for {@link Scenario}
    */
    public static boolean isNameUnique(java.lang.String name, long idSimulation)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getService().isNameUnique(name, idSimulation);
    }

    /**
    * Count how many {@link Scenario} have this variableName
    */
    public static int countByVariableName(java.lang.String variableName,
        long idSimulation)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getService().countByVariableName(variableName, idSimulation);
    }

    /**
    * get {@link Scenario} have this variableName
    */
    public static java.util.List<com.excilys.liferay.gatling.model.Scenario> findByVariableName(
        java.lang.String variableName, long idSimulation)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getService().findByVariableName(variableName, idSimulation);
    }

    /**
    * Add a {@link Scenario} from an {@link ActionRequest}
    *
    * @param {@link ActionRequest} request
    * @param {@link ActionResponse} response
    * @return {@link Scenario} if added, else null
    * @throws SystemException
    */
    public static com.excilys.liferay.gatling.model.Scenario addScenarioFromRequest(
        javax.portlet.ActionRequest request)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getService().addScenarioFromRequest(request);
    }

    /**
    * Edit a {@link Scenario} from an {@link ActionRequest}
    *
    * @param {@link ActionRequest} request
    * @param {@link ActionResponse} response
    * @return {@link Scenario} if added, else null
    * @throws SystemException
    */
    public static com.excilys.liferay.gatling.model.Scenario editScenarioFromRequest(
        javax.portlet.ActionRequest request)
        throws com.liferay.portal.kernel.exception.PortalException,
            com.liferay.portal.kernel.exception.SystemException {
        return getService().editScenarioFromRequest(request);
    }

    public static void clearService() {
        _service = null;
    }

    public static ScenarioLocalService getService() {
        if (_service == null) {
            InvokableLocalService invokableLocalService = (InvokableLocalService) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
                    ScenarioLocalService.class.getName());

            if (invokableLocalService instanceof ScenarioLocalService) {
                _service = (ScenarioLocalService) invokableLocalService;
            } else {
                _service = new ScenarioLocalServiceClp(invokableLocalService);
            }

            ReferenceRegistry.registerReference(ScenarioLocalServiceUtil.class,
                "_service");
        }

        return _service;
    }

    /**
     * @deprecated As of 6.2.0
     */
    public void setService(ScenarioLocalService service) {
    }
}
