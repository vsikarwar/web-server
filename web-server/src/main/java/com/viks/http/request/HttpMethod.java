package com.viks.http.request;

public class HttpMethod {
	
	private String method;
	
	public HttpMethod(String method) {
		this.method = method;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public String toString() {
		return "HTTPMethod [method=" + method + "]";
	}
	
	

}
