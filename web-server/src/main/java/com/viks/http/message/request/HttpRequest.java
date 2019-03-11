package com.viks.http.message.request;


import com.viks.http.message.HttpMessage;

public class HttpRequest extends HttpMessage{
	
	private String method;
	private String uri;
	private HttpQueryParams params;
	
	public HttpRequest() {
		params = new HttpQueryParams();
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
	public HttpQueryParams getParams() {
		return params;
	}
	public void setParams(HttpQueryParams params) {
		this.params = params;
	}

}
