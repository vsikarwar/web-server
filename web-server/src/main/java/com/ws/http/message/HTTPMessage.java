package com.ws.http.message;

import com.viks.http.header.HttpHeaders;

public class HTTPMessage {
	
	private HttpHeaders headers;
	private byte[] content;
	
	public HTTPMessage() {
		headers = new HttpHeaders();
		content = null;
	}
	

}
