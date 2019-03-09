package com.viks.http.request;

import com.viks.http.header.HttpHeaders;
import com.viks.http.version.HttpVersion;

public class Request {
	
	private HttpVersion version;
	private HttpMethod method;
	private HttpUri uri;
	private HttpHeaders headers;
	private HttpCookies cookies;
	
	public Request() {
	}

	public HttpVersion getVersion() {
		return version;
	}

	public void setVersion(HttpVersion version) {
		this.version = version;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public HttpUri getUri() {
		return uri;
	}

	public void setUri(HttpUri uri) {
		this.uri = uri;
	}

	public HttpHeaders getHeaders() {
		return headers;
	}

	public void setHeaders(HttpHeaders headers) {
		this.headers = headers;
	}

	public HttpCookies getCookies() {
		return cookies;
	}

	public void setCookies(HttpCookies cookies) {
		this.cookies = cookies;
	}

	
	
}
