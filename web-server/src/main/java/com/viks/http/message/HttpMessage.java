package com.viks.http.message;

public class HttpMessage {
	
	private HttpContent content;
	private HttpHeaders headers;
	private HttpVersion version;
	
	public HttpMessage() {
		content = new HttpContent();
		headers = new HttpHeaders();
		version = new HttpVersion();
		
	}
	
	public HttpContent getContent() {
		return content;
	}
	public void setContent(HttpContent content) {
		this.content = content;
	}
	public HttpHeaders getHeaders() {
		return headers;
	}
	public void setHeaders(HttpHeaders headers) {
		this.headers = headers;
	}

	public HttpVersion getVersion() {
		return version;
	}

	public void setVersion(HttpVersion version) {
		this.version = version;
	}
	

}
