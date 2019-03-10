package com.viks.http.response;

public class HttpResponseBody {
	
	String body;
	
	public HttpResponseBody(String body) {
		this.body = body;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "HttpResponseBody [body=" + body + "]";
	}
	
	

}
