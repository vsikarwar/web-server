package com.vklp.http.message.request;


import com.vklp.http.message.HttpMessage;

public class HttpRequest extends HttpMessage{
	
	private String method;
	private String uri;
	private HttpParams queryParams;
	private HttpParams params;
	
	public HttpRequest() {
		queryParams = new HttpParams();
		params = new HttpParams();
	}
	
	public HttpParams getParams() {
		return params;
	}
	
	public String getParam(String name) {
		return this.params.getValue(name);
	}
	
	public String getQueryParam(String name) {
		return this.queryParams.getValue(name);
	}

	public void setParams(HttpParams params) {
		this.params = params;
	}

	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public HttpParams getQueryParams() {
		return queryParams;
	}
	public void setQueryParams(HttpParams params) {
		this.queryParams = params;
	}

}
