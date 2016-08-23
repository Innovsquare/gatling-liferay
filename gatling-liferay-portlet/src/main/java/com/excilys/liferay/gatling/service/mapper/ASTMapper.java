package com.excilys.liferay.gatling.service.mapper;

import com.excilys.liferay.gatling.NoSuchFormParamException;
import com.excilys.liferay.gatling.model.FormParam;
import com.excilys.liferay.gatling.model.Process;
import com.excilys.liferay.gatling.model.ProcessType;
import com.excilys.liferay.gatling.model.Record;
import com.excilys.liferay.gatling.model.UrlRecord;
import com.excilys.liferay.gatling.model.UrlRecordType;
import com.excilys.liferay.gatling.model.AST.feeder.HttpBodyFileAST;
import com.excilys.liferay.gatling.model.AST.feeder.RecordFeederFileAST;
import com.excilys.liferay.gatling.model.AST.feeder.ResourceFileAST;
import com.excilys.liferay.gatling.model.AST.feeder.data.RecordDataAST;
import com.excilys.liferay.gatling.model.AST.process.LoginAST;
import com.excilys.liferay.gatling.model.AST.process.LogoutAST;
import com.excilys.liferay.gatling.model.AST.process.ProcessAST;
import com.excilys.liferay.gatling.model.AST.process.RandomPageAST;
import com.excilys.liferay.gatling.model.AST.process.RecorderAST;
import com.excilys.liferay.gatling.service.ASTService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

import java.util.ArrayList;
import java.util.List;

public class ASTMapper {
	

	public static ProcessAST mapProcessToAST(Process process) throws PortalException, SystemException {
		ProcessAST ast = null;
		
		if (process == null)
			return null;
		
		switch(ProcessType.valueOf(process.getType())) {
			case RECORD:
				RecordFeederFileAST feeder = ASTService.computesRecordFeederFileAST(process.getRecordId());
				ast = new RecorderAST(feeder);
				break;
			case LOGIN:
				//TODO handle feeder
				ast = new LoginAST();
				break;
			case RANDOMPAGE:
				//TODO handle feeder
				ast = new RandomPageAST();
				break;
			default:
				ast = new LogoutAST();
				break;
		}
		
		ast.setPause(process.getPause());
		return ast;
	}
	
	public static RecordFeederFileAST mapRecordToAST(Record record) throws SystemException, NoSuchFormParamException{
		String name = record.getName();
		List<RecordDataAST> data = ASTService.computesRecordDataAST(record.getRecordId());
		return new RecordFeederFileAST(name, data);
	}

	public static List<RecordDataAST> mapUrlRecordsToAST(List<UrlRecord> urlRecords) throws NoSuchFormParamException, SystemException {
		List<RecordDataAST> dataList = new ArrayList<>();
		for (UrlRecord urlRecord : urlRecords) {
			
			ResourceFileAST formResource = null;
			switch(UrlRecordType.valueOf(urlRecord.getType())){
				case POST:
					formResource = ASTService.computesFormParamFeederFileAST(urlRecord.getUrlRecordId());
					break;
				case MULTIPART:
					formResource = ASTService.computesHttpBodyFileAST(urlRecord.getUrlRecordId());
					break;
				default:
					break;
			}
			
			RecordDataAST data = new RecordDataAST(urlRecord.getUrl(), urlRecord.getType(), formResource);
			dataList.add(data);
		}
		return dataList;
	}

	public static HttpBodyFileAST mapMultiPartFormParamToAST(FormParam params, String name){
		return new HttpBodyFileAST(name, params.getData());
	}
	
	public static ResourceFileAST mapFormParamToAST(FormParam params, String name) {
		//TODO: Create a FormParamFeederFileAST
		return null;
	}
	
	
	
}
