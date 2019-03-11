package com.viks.http.redirects;

public class Redirect {
	
	private String source;
	private String destination;
	private String type;
	
	public Redirect(String source, String dest, String type) {
		this.source = source;
		this.destination = dest;
		this.type = type;
	}
	
	public String getSource() {
		return source;
	}
	public String getDestination() {
		return destination;
	}
	public String getType() {
		return type;
	}

}
