package com.excilys.liferay.gatling.recorder.records;

//TODO: Comment Me!
//TODO: Split data from the url!!!

public class PostURL extends RecordURL {
	
	protected String formData = "";
	protected String formType = "NORMAL";
	
	public PostURL(String requestURL, String params) {
		super("POST", requestURL, params);
	}

	@Override
	public String toString() {
		return "RecordURL [method=" + method + ", url=" + url + ", params="
				+ params + ", formType=" + formType + ", formData=" + formData + "]";
	}
	
}
