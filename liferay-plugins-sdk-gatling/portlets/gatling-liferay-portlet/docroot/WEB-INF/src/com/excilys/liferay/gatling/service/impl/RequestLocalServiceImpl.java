/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.excilys.liferay.gatling.service.impl;

import com.excilys.liferay.gatling.model.Request;
import com.excilys.liferay.gatling.service.base.RequestLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.SystemException;

import java.util.List;

/**
 * The implementation of the request local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.sample.service.RequestLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @see com.liferay.sample.service.base.RequestLocalServiceBaseImpl
 * @see com.liferay.sample.service.RequestLocalServiceUtil
 */
public class RequestLocalServiceImpl extends RequestLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.sample.service.RequestLocalServiceUtil} to access the request local service.
	 */
	private static final Log LOG = LogFactoryUtil.getLog(RequestLocalServiceImpl.class.getName());


	@Override
	public List<Request> findByParentPlid(long parentPlid) throws SystemException{
		return requestPersistence.findByParentPlid(parentPlid);
	}	

	@Override
	public int countByParentPlid(long parentPlid) throws SystemException{
		return requestPersistence.countByParentPlid(parentPlid);
	}

	@Override
	public List<Request> findByScenarioId(long scenarioId) throws SystemException{
		return requestPersistence.findByScenarioId(scenarioId);
	}	

	@Override
	public int countByScenarioId(long scenarioId) throws SystemException{
		return requestPersistence.countByScenarioId(scenarioId);
	}
	
	@Override
	public List<Request> findByScenarioIdAndUsed(long scenarioId) throws SystemException{
		return requestPersistence.findByScenarioIdAndUsed(scenarioId, 0);
	}
	
	@Override
	public int countByScenarioIdAndUsed(long scenarioId) throws SystemException{
		return requestPersistence.countByScenarioIdAndUsed(scenarioId, 0);
	}

	@Override
	public void removeByScenarioId(long scenarioId) throws SystemException {
		requestPersistence.removeByScenarioId(scenarioId);
	}
	 
	@Override
	public List<Request> findByScenarioIdAndIsNotPortlet(long scenarioId) throws SystemException{
		return requestPersistence.findByScenarioIdAndIsNotPortlet(scenarioId, false);
	}
	
	@Override
	public int countByScenarioIdAndIsNotPortlet(long scenarioId) throws SystemException{
		return requestPersistence.countByScenarioIdAndIsNotPortlet(scenarioId, false);
	}
	
	@Override
	public List<Request> findByScenarioIdAndUsedAndIsNotPortlet(long scenarioId) throws SystemException{
		return requestPersistence.findByScenarioIdAndUsedAndIsNotPortlet(scenarioId, false, 0);
	}
	
	@Override
	public int countByScenarioIdAndUsedAndIsNotPortlet(long scenarioId) throws SystemException{
		return requestPersistence.countByScenarioIdAndUsedAndIsNotPortlet(scenarioId, false, 0);
	}
	
	
	
}