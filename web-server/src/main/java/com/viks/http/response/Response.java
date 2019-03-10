package com.viks.http.response;

import com.viks.http.header.HttpHeaders;

public class Response {
	
	private ResponseStatusLine statusLine;
	private HttpHeaders headers;
	private String body = "";
	
	public Response() {
		statusLine = new ResponseStatusLine();
		headers = new HttpHeaders();
	}

	public ResponseStatusLine getStatusLine() {
		return statusLine;
	}

	public void setStatusLine(ResponseStatusLine statusLine) {
		this.statusLine = statusLine;
	}

	public HttpHeaders getHeaders() {
		return headers;
	}

	public void setHeaders(HttpHeaders headers) {
		this.headers = headers;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.statusLine + "\n");
		sb.append(this.headers + "\n");
		sb.append(body);
		return sb.toString();
	}
	
	
}
