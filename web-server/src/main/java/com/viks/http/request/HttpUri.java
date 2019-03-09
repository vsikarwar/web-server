package com.viks.http.request;

import java.util.HashMap;
import java.util.Map;

public class HttpUri {
	
	private String path;
	private Map<String, String> queryParams;
	private boolean hasParams;
	
	public boolean isHasParams() {
		return hasParams;
	}

	public void setHasParams(boolean hasParams) {
		this.hasParams = hasParams;
	}

	public HttpUri() {
		queryParams = new HashMap<String, String>();
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Map<String, String> getQueryParams() {
		return queryParams;
	}
	public void setQueryParams(Map<String, String> queryParams) {
		this.queryParams = queryParams;
	}
	
	

}
