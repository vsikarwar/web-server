package com.viks.http.header;


public enum Headers{
	CONNECTION("Connection"),
	CONTENT_LENGTH("Content-Length"),
	KEEP_ALIVE("Keep-Alive"),
	CONTENT_TYPE("Content-type"),
	DATE("Date"),
	SERVER("Server");
	
	private final String name;
	
	Headers(String name) {
		this.name= name;
	}
	
	public String getName() {
		return name;
	}
}
