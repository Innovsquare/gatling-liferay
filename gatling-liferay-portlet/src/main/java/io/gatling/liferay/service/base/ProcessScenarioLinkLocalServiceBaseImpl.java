package io.gatling.liferay.service.base;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.PersistedModelLocalServiceRegistryUtil;
import com.liferay.portal.service.persistence.UserPersistence;

import io.gatling.liferay.model.ProcessScenarioLink;
import io.gatling.liferay.service.ProcessScenarioLinkLocalService;
import io.gatling.liferay.service.persistence.FormParamPersistence;
import io.gatling.liferay.service.persistence.LoginPersistence;
import io.gatling.liferay.service.persistence.ProcessPersistence;
import io.gatling.liferay.service.persistence.ProcessScenarioLinkPersistence;
import io.gatling.liferay.service.persistence.RecordPersistence;
import io.gatling.liferay.service.persistence.ScenarioPersistence;
import io.gatling.liferay.service.persistence.SimulationPersistence;
import io.gatling.liferay.service.persistence.SiteMapPersistence;
import io.gatling.liferay.service.persistence.UrlRecordPersistence;
import io.gatling.liferay.service.persistence.UrlSiteMapPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the process scenario link local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link io.gatling.liferay.service.impl.ProcessScenarioLinkLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see io.gatling.liferay.service.impl.ProcessScenarioLinkLocalServiceImpl
 * @see io.gatling.liferay.service.ProcessScenarioLinkLocalServiceUtil
 * @generated
 */
public abstract class ProcessScenarioLinkLocalServiceBaseImpl
    extends BaseLocalServiceImpl implements ProcessScenarioLinkLocalService,
        IdentifiableBean {
    @BeanReference(type = io.gatling.liferay.service.FormParamLocalService.class)
    protected io.gatling.liferay.service.FormParamLocalService formParamLocalService;
    @BeanReference(type = FormParamPersistence.class)
    protected FormParamPersistence formParamPersistence;
    @BeanReference(type = io.gatling.liferay.service.LoginLocalService.class)
    protected io.gatling.liferay.service.LoginLocalService loginLocalService;
    @BeanReference(type = LoginPersistence.class)
    protected LoginPersistence loginPersistence;
    @BeanReference(type = io.gatling.liferay.service.ProcessLocalService.class)
    protected io.gatling.liferay.service.ProcessLocalService processLocalService;
    @BeanReference(type = ProcessPersistence.class)
    protected ProcessPersistence processPersistence;
    @BeanReference(type = io.gatling.liferay.service.ProcessScenarioLinkLocalService.class)
    protected io.gatling.liferay.service.ProcessScenarioLinkLocalService processScenarioLinkLocalService;
    @BeanReference(type = ProcessScenarioLinkPersistence.class)
    protected ProcessScenarioLinkPersistence processScenarioLinkPersistence;
    @BeanReference(type = io.gatling.liferay.service.RecordLocalService.class)
    protected io.gatling.liferay.service.RecordLocalService recordLocalService;
    @BeanReference(type = RecordPersistence.class)
    protected RecordPersistence recordPersistence;
    @BeanReference(type = io.gatling.liferay.service.ScenarioLocalService.class)
    protected io.gatling.liferay.service.ScenarioLocalService scenarioLocalService;
    @BeanReference(type = ScenarioPersistence.class)
    protected ScenarioPersistence scenarioPersistence;
    @BeanReference(type = io.gatling.liferay.service.SimulationLocalService.class)
    protected io.gatling.liferay.service.SimulationLocalService simulationLocalService;
    @BeanReference(type = SimulationPersistence.class)
    protected SimulationPersistence simulationPersistence;
    @BeanReference(type = io.gatling.liferay.service.SiteMapLocalService.class)
    protected io.gatling.liferay.service.SiteMapLocalService siteMapLocalService;
    @BeanReference(type = SiteMapPersistence.class)
    protected SiteMapPersistence siteMapPersistence;
    @BeanReference(type = io.gatling.liferay.service.UrlRecordLocalService.class)
    protected io.gatling.liferay.service.UrlRecordLocalService urlRecordLocalService;
    @BeanReference(type = UrlRecordPersistence.class)
    protected UrlRecordPersistence urlRecordPersistence;
    @BeanReference(type = io.gatling.liferay.service.UrlSiteMapLocalService.class)
    protected io.gatling.liferay.service.UrlSiteMapLocalService urlSiteMapLocalService;
    @BeanReference(type = UrlSiteMapPersistence.class)
    protected UrlSiteMapPersistence urlSiteMapPersistence;
    @BeanReference(type = com.liferay.counter.service.CounterLocalService.class)
    protected com.liferay.counter.service.CounterLocalService counterLocalService;
    @BeanReference(type = com.liferay.portal.service.ResourceLocalService.class)
    protected com.liferay.portal.service.ResourceLocalService resourceLocalService;
    @BeanReference(type = com.liferay.portal.service.UserLocalService.class)
    protected com.liferay.portal.service.UserLocalService userLocalService;
    @BeanReference(type = com.liferay.portal.service.UserService.class)
    protected com.liferay.portal.service.UserService userService;
    @BeanReference(type = UserPersistence.class)
    protected UserPersistence userPersistence;
    private String _beanIdentifier;
    private ClassLoader _classLoader;
    private ProcessScenarioLinkLocalServiceClpInvoker _clpInvoker = new ProcessScenarioLinkLocalServiceClpInvoker();

    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never modify or reference this class directly. Always use {@link io.gatling.liferay.service.ProcessScenarioLinkLocalServiceUtil} to access the process scenario link local service.
     */

    /**
     * Adds the process scenario link to the database. Also notifies the appropriate model listeners.
     *
     * @param processScenarioLink the process scenario link
     * @return the process scenario link that was added
     * @throws SystemException if a system exception occurred
     */
    @Indexable(type = IndexableType.REINDEX)
    @Override
    public ProcessScenarioLink addProcessScenarioLink(
        ProcessScenarioLink processScenarioLink) throws SystemException {
        processScenarioLink.setNew(true);

        return processScenarioLinkPersistence.update(processScenarioLink);
    }

    /**
     * Creates a new process scenario link with the primary key. Does not add the process scenario link to the database.
     *
     * @param psl_id the primary key for the new process scenario link
     * @return the new process scenario link
     */
    @Override
    public ProcessScenarioLink createProcessScenarioLink(long psl_id) {
        return processScenarioLinkPersistence.create(psl_id);
    }

    /**
     * Deletes the process scenario link with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param psl_id the primary key of the process scenario link
     * @return the process scenario link that was removed
     * @throws PortalException if a process scenario link with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    @Indexable(type = IndexableType.DELETE)
    @Override
    public ProcessScenarioLink deleteProcessScenarioLink(long psl_id)
        throws PortalException, SystemException {
        return processScenarioLinkPersistence.remove(psl_id);
    }

    /**
     * Deletes the process scenario link from the database. Also notifies the appropriate model listeners.
     *
     * @param processScenarioLink the process scenario link
     * @return the process scenario link that was removed
     * @throws SystemException if a system exception occurred
     */
    @Indexable(type = IndexableType.DELETE)
    @Override
    public ProcessScenarioLink deleteProcessScenarioLink(
        ProcessScenarioLink processScenarioLink) throws SystemException {
        return processScenarioLinkPersistence.remove(processScenarioLink);
    }

    @Override
    public DynamicQuery dynamicQuery() {
        Class<?> clazz = getClass();

        return DynamicQueryFactoryUtil.forClass(ProcessScenarioLink.class,
            clazz.getClassLoader());
    }

    /**
     * Performs a dynamic query on the database and returns the matching rows.
     *
     * @param dynamicQuery the dynamic query
     * @return the matching rows
     * @throws SystemException if a system exception occurred
     */
    @Override
    @SuppressWarnings("rawtypes")
    public List dynamicQuery(DynamicQuery dynamicQuery)
        throws SystemException {
        return processScenarioLinkPersistence.findWithDynamicQuery(dynamicQuery);
    }

    /**
     * Performs a dynamic query on the database and returns a range of the matching rows.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link io.gatling.liferay.model.impl.ProcessScenarioLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
     * </p>
     *
     * @param dynamicQuery the dynamic query
     * @param start the lower bound of the range of model instances
     * @param end the upper bound of the range of model instances (not inclusive)
     * @return the range of matching rows
     * @throws SystemException if a system exception occurred
     */
    @Override
    @SuppressWarnings("rawtypes")
    public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end)
        throws SystemException {
        return processScenarioLinkPersistence.findWithDynamicQuery(dynamicQuery,
            start, end);
    }

    /**
     * Performs a dynamic query on the database and returns an ordered range of the matching rows.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link io.gatling.liferay.model.impl.ProcessScenarioLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
     * </p>
     *
     * @param dynamicQuery the dynamic query
     * @param start the lower bound of the range of model instances
     * @param end the upper bound of the range of model instances (not inclusive)
     * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
     * @return the ordered range of matching rows
     * @throws SystemException if a system exception occurred
     */
    @Override
    @SuppressWarnings("rawtypes")
    public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        return processScenarioLinkPersistence.findWithDynamicQuery(dynamicQuery,
            start, end, orderByComparator);
    }

    /**
     * Returns the number of rows that match the dynamic query.
     *
     * @param dynamicQuery the dynamic query
     * @return the number of rows that match the dynamic query
     * @throws SystemException if a system exception occurred
     */
    @Override
    public long dynamicQueryCount(DynamicQuery dynamicQuery)
        throws SystemException {
        return processScenarioLinkPersistence.countWithDynamicQuery(dynamicQuery);
    }

    /**
     * Returns the number of rows that match the dynamic query.
     *
     * @param dynamicQuery the dynamic query
     * @param projection the projection to apply to the query
     * @return the number of rows that match the dynamic query
     * @throws SystemException if a system exception occurred
     */
    @Override
    public long dynamicQueryCount(DynamicQuery dynamicQuery,
        Projection projection) throws SystemException {
        return processScenarioLinkPersistence.countWithDynamicQuery(dynamicQuery,
            projection);
    }

    @Override
    public ProcessScenarioLink fetchProcessScenarioLink(long psl_id)
        throws SystemException {
        return processScenarioLinkPersistence.fetchByPrimaryKey(psl_id);
    }

    /**
     * Returns the process scenario link with the primary key.
     *
     * @param psl_id the primary key of the process scenario link
     * @return the process scenario link
     * @throws PortalException if a process scenario link with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public ProcessScenarioLink getProcessScenarioLink(long psl_id)
        throws PortalException, SystemException {
        return processScenarioLinkPersistence.findByPrimaryKey(psl_id);
    }

    @Override
    public PersistedModel getPersistedModel(Serializable primaryKeyObj)
        throws PortalException, SystemException {
        return processScenarioLinkPersistence.findByPrimaryKey(primaryKeyObj);
    }

    /**
     * Returns a range of all the process scenario links.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link io.gatling.liferay.model.impl.ProcessScenarioLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
     * </p>
     *
     * @param start the lower bound of the range of process scenario links
     * @param end the upper bound of the range of process scenario links (not inclusive)
     * @return the range of process scenario links
     * @throws SystemException if a system exception occurred
     */
    @Override
    public List<ProcessScenarioLink> getProcessScenarioLinks(int start, int end)
        throws SystemException {
        return processScenarioLinkPersistence.findAll(start, end);
    }

    /**
     * Returns the number of process scenario links.
     *
     * @return the number of process scenario links
     * @throws SystemException if a system exception occurred
     */
    @Override
    public int getProcessScenarioLinksCount() throws SystemException {
        return processScenarioLinkPersistence.countAll();
    }

    /**
     * Updates the process scenario link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
     *
     * @param processScenarioLink the process scenario link
     * @return the process scenario link that was updated
     * @throws SystemException if a system exception occurred
     */
    @Indexable(type = IndexableType.REINDEX)
    @Override
    public ProcessScenarioLink updateProcessScenarioLink(
        ProcessScenarioLink processScenarioLink) throws SystemException {
        return processScenarioLinkPersistence.update(processScenarioLink);
    }

    /**
     * Returns the form param local service.
     *
     * @return the form param local service
     */
    public io.gatling.liferay.service.FormParamLocalService getFormParamLocalService() {
        return formParamLocalService;
    }

    /**
     * Sets the form param local service.
     *
     * @param formParamLocalService the form param local service
     */
    public void setFormParamLocalService(
        io.gatling.liferay.service.FormParamLocalService formParamLocalService) {
        this.formParamLocalService = formParamLocalService;
    }

    /**
     * Returns the form param persistence.
     *
     * @return the form param persistence
     */
    public FormParamPersistence getFormParamPersistence() {
        return formParamPersistence;
    }

    /**
     * Sets the form param persistence.
     *
     * @param formParamPersistence the form param persistence
     */
    public void setFormParamPersistence(
        FormParamPersistence formParamPersistence) {
        this.formParamPersistence = formParamPersistence;
    }

    /**
     * Returns the login local service.
     *
     * @return the login local service
     */
    public io.gatling.liferay.service.LoginLocalService getLoginLocalService() {
        return loginLocalService;
    }

    /**
     * Sets the login local service.
     *
     * @param loginLocalService the login local service
     */
    public void setLoginLocalService(
        io.gatling.liferay.service.LoginLocalService loginLocalService) {
        this.loginLocalService = loginLocalService;
    }

    /**
     * Returns the login persistence.
     *
     * @return the login persistence
     */
    public LoginPersistence getLoginPersistence() {
        return loginPersistence;
    }

    /**
     * Sets the login persistence.
     *
     * @param loginPersistence the login persistence
     */
    public void setLoginPersistence(LoginPersistence loginPersistence) {
        this.loginPersistence = loginPersistence;
    }

    /**
     * Returns the process local service.
     *
     * @return the process local service
     */
    public io.gatling.liferay.service.ProcessLocalService getProcessLocalService() {
        return processLocalService;
    }

    /**
     * Sets the process local service.
     *
     * @param processLocalService the process local service
     */
    public void setProcessLocalService(
        io.gatling.liferay.service.ProcessLocalService processLocalService) {
        this.processLocalService = processLocalService;
    }

    /**
     * Returns the process persistence.
     *
     * @return the process persistence
     */
    public ProcessPersistence getProcessPersistence() {
        return processPersistence;
    }

    /**
     * Sets the process persistence.
     *
     * @param processPersistence the process persistence
     */
    public void setProcessPersistence(ProcessPersistence processPersistence) {
        this.processPersistence = processPersistence;
    }

    /**
     * Returns the process scenario link local service.
     *
     * @return the process scenario link local service
     */
    public io.gatling.liferay.service.ProcessScenarioLinkLocalService getProcessScenarioLinkLocalService() {
        return processScenarioLinkLocalService;
    }

    /**
     * Sets the process scenario link local service.
     *
     * @param processScenarioLinkLocalService the process scenario link local service
     */
    public void setProcessScenarioLinkLocalService(
        io.gatling.liferay.service.ProcessScenarioLinkLocalService processScenarioLinkLocalService) {
        this.processScenarioLinkLocalService = processScenarioLinkLocalService;
    }

    /**
     * Returns the process scenario link persistence.
     *
     * @return the process scenario link persistence
     */
    public ProcessScenarioLinkPersistence getProcessScenarioLinkPersistence() {
        return processScenarioLinkPersistence;
    }

    /**
     * Sets the process scenario link persistence.
     *
     * @param processScenarioLinkPersistence the process scenario link persistence
     */
    public void setProcessScenarioLinkPersistence(
        ProcessScenarioLinkPersistence processScenarioLinkPersistence) {
        this.processScenarioLinkPersistence = processScenarioLinkPersistence;
    }

    /**
     * Returns the record local service.
     *
     * @return the record local service
     */
    public io.gatling.liferay.service.RecordLocalService getRecordLocalService() {
        return recordLocalService;
    }

    /**
     * Sets the record local service.
     *
     * @param recordLocalService the record local service
     */
    public void setRecordLocalService(
        io.gatling.liferay.service.RecordLocalService recordLocalService) {
        this.recordLocalService = recordLocalService;
    }

    /**
     * Returns the record persistence.
     *
     * @return the record persistence
     */
    public RecordPersistence getRecordPersistence() {
        return recordPersistence;
    }

    /**
     * Sets the record persistence.
     *
     * @param recordPersistence the record persistence
     */
    public void setRecordPersistence(RecordPersistence recordPersistence) {
        this.recordPersistence = recordPersistence;
    }

    /**
     * Returns the scenario local service.
     *
     * @return the scenario local service
     */
    public io.gatling.liferay.service.ScenarioLocalService getScenarioLocalService() {
        return scenarioLocalService;
    }

    /**
     * Sets the scenario local service.
     *
     * @param scenarioLocalService the scenario local service
     */
    public void setScenarioLocalService(
        io.gatling.liferay.service.ScenarioLocalService scenarioLocalService) {
        this.scenarioLocalService = scenarioLocalService;
    }

    /**
     * Returns the scenario persistence.
     *
     * @return the scenario persistence
     */
    public ScenarioPersistence getScenarioPersistence() {
        return scenarioPersistence;
    }

    /**
     * Sets the scenario persistence.
     *
     * @param scenarioPersistence the scenario persistence
     */
    public void setScenarioPersistence(ScenarioPersistence scenarioPersistence) {
        this.scenarioPersistence = scenarioPersistence;
    }

    /**
     * Returns the simulation local service.
     *
     * @return the simulation local service
     */
    public io.gatling.liferay.service.SimulationLocalService getSimulationLocalService() {
        return simulationLocalService;
    }

    /**
     * Sets the simulation local service.
     *
     * @param simulationLocalService the simulation local service
     */
    public void setSimulationLocalService(
        io.gatling.liferay.service.SimulationLocalService simulationLocalService) {
        this.simulationLocalService = simulationLocalService;
    }

    /**
     * Returns the simulation persistence.
     *
     * @return the simulation persistence
     */
    public SimulationPersistence getSimulationPersistence() {
        return simulationPersistence;
    }

    /**
     * Sets the simulation persistence.
     *
     * @param simulationPersistence the simulation persistence
     */
    public void setSimulationPersistence(
        SimulationPersistence simulationPersistence) {
        this.simulationPersistence = simulationPersistence;
    }

    /**
     * Returns the site map local service.
     *
     * @return the site map local service
     */
    public io.gatling.liferay.service.SiteMapLocalService getSiteMapLocalService() {
        return siteMapLocalService;
    }

    /**
     * Sets the site map local service.
     *
     * @param siteMapLocalService the site map local service
     */
    public void setSiteMapLocalService(
        io.gatling.liferay.service.SiteMapLocalService siteMapLocalService) {
        this.siteMapLocalService = siteMapLocalService;
    }

    /**
     * Returns the site map persistence.
     *
     * @return the site map persistence
     */
    public SiteMapPersistence getSiteMapPersistence() {
        return siteMapPersistence;
    }

    /**
     * Sets the site map persistence.
     *
     * @param siteMapPersistence the site map persistence
     */
    public void setSiteMapPersistence(SiteMapPersistence siteMapPersistence) {
        this.siteMapPersistence = siteMapPersistence;
    }

    /**
     * Returns the url record local service.
     *
     * @return the url record local service
     */
    public io.gatling.liferay.service.UrlRecordLocalService getUrlRecordLocalService() {
        return urlRecordLocalService;
    }

    /**
     * Sets the url record local service.
     *
     * @param urlRecordLocalService the url record local service
     */
    public void setUrlRecordLocalService(
        io.gatling.liferay.service.UrlRecordLocalService urlRecordLocalService) {
        this.urlRecordLocalService = urlRecordLocalService;
    }

    /**
     * Returns the url record persistence.
     *
     * @return the url record persistence
     */
    public UrlRecordPersistence getUrlRecordPersistence() {
        return urlRecordPersistence;
    }

    /**
     * Sets the url record persistence.
     *
     * @param urlRecordPersistence the url record persistence
     */
    public void setUrlRecordPersistence(
        UrlRecordPersistence urlRecordPersistence) {
        this.urlRecordPersistence = urlRecordPersistence;
    }

    /**
     * Returns the url site map local service.
     *
     * @return the url site map local service
     */
    public io.gatling.liferay.service.UrlSiteMapLocalService getUrlSiteMapLocalService() {
        return urlSiteMapLocalService;
    }

    /**
     * Sets the url site map local service.
     *
     * @param urlSiteMapLocalService the url site map local service
     */
    public void setUrlSiteMapLocalService(
        io.gatling.liferay.service.UrlSiteMapLocalService urlSiteMapLocalService) {
        this.urlSiteMapLocalService = urlSiteMapLocalService;
    }

    /**
     * Returns the url site map persistence.
     *
     * @return the url site map persistence
     */
    public UrlSiteMapPersistence getUrlSiteMapPersistence() {
        return urlSiteMapPersistence;
    }

    /**
     * Sets the url site map persistence.
     *
     * @param urlSiteMapPersistence the url site map persistence
     */
    public void setUrlSiteMapPersistence(
        UrlSiteMapPersistence urlSiteMapPersistence) {
        this.urlSiteMapPersistence = urlSiteMapPersistence;
    }

    /**
     * Returns the counter local service.
     *
     * @return the counter local service
     */
    public com.liferay.counter.service.CounterLocalService getCounterLocalService() {
        return counterLocalService;
    }

    /**
     * Sets the counter local service.
     *
     * @param counterLocalService the counter local service
     */
    public void setCounterLocalService(
        com.liferay.counter.service.CounterLocalService counterLocalService) {
        this.counterLocalService = counterLocalService;
    }

    /**
     * Returns the resource local service.
     *
     * @return the resource local service
     */
    public com.liferay.portal.service.ResourceLocalService getResourceLocalService() {
        return resourceLocalService;
    }

    /**
     * Sets the resource local service.
     *
     * @param resourceLocalService the resource local service
     */
    public void setResourceLocalService(
        com.liferay.portal.service.ResourceLocalService resourceLocalService) {
        this.resourceLocalService = resourceLocalService;
    }

    /**
     * Returns the user local service.
     *
     * @return the user local service
     */
    public com.liferay.portal.service.UserLocalService getUserLocalService() {
        return userLocalService;
    }

    /**
     * Sets the user local service.
     *
     * @param userLocalService the user local service
     */
    public void setUserLocalService(
        com.liferay.portal.service.UserLocalService userLocalService) {
        this.userLocalService = userLocalService;
    }

    /**
     * Returns the user remote service.
     *
     * @return the user remote service
     */
    public com.liferay.portal.service.UserService getUserService() {
        return userService;
    }

    /**
     * Sets the user remote service.
     *
     * @param userService the user remote service
     */
    public void setUserService(
        com.liferay.portal.service.UserService userService) {
        this.userService = userService;
    }

    /**
     * Returns the user persistence.
     *
     * @return the user persistence
     */
    public UserPersistence getUserPersistence() {
        return userPersistence;
    }

    /**
     * Sets the user persistence.
     *
     * @param userPersistence the user persistence
     */
    public void setUserPersistence(UserPersistence userPersistence) {
        this.userPersistence = userPersistence;
    }

    public void afterPropertiesSet() {
        Class<?> clazz = getClass();

        _classLoader = clazz.getClassLoader();

        PersistedModelLocalServiceRegistryUtil.register("io.gatling.liferay.model.ProcessScenarioLink",
            processScenarioLinkLocalService);
    }

    public void destroy() {
        PersistedModelLocalServiceRegistryUtil.unregister(
            "io.gatling.liferay.model.ProcessScenarioLink");
    }

    /**
     * Returns the Spring bean ID for this bean.
     *
     * @return the Spring bean ID for this bean
     */
    @Override
    public String getBeanIdentifier() {
        return _beanIdentifier;
    }

    /**
     * Sets the Spring bean ID for this bean.
     *
     * @param beanIdentifier the Spring bean ID for this bean
     */
    @Override
    public void setBeanIdentifier(String beanIdentifier) {
        _beanIdentifier = beanIdentifier;
    }

    @Override
    public Object invokeMethod(String name, String[] parameterTypes,
        Object[] arguments) throws Throwable {
        Thread currentThread = Thread.currentThread();

        ClassLoader contextClassLoader = currentThread.getContextClassLoader();

        if (contextClassLoader != _classLoader) {
            currentThread.setContextClassLoader(_classLoader);
        }

        try {
            return _clpInvoker.invokeMethod(name, parameterTypes, arguments);
        } finally {
            if (contextClassLoader != _classLoader) {
                currentThread.setContextClassLoader(contextClassLoader);
            }
        }
    }

    protected Class<?> getModelClass() {
        return ProcessScenarioLink.class;
    }

    protected String getModelClassName() {
        return ProcessScenarioLink.class.getName();
    }

    /**
     * Performs an SQL query.
     *
     * @param sql the sql query
     */
    protected void runSQL(String sql) throws SystemException {
        try {
            DataSource dataSource = processScenarioLinkPersistence.getDataSource();

            SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
                    sql, new int[0]);

            sqlUpdate.update();
        } catch (Exception e) {
            throw new SystemException(e);
        }
    }
}
