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
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	

}
