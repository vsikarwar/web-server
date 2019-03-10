package com.viks.http.response;

import com.viks.http.version.HttpVersion;

public class ResponseStatusLine {
	private HttpStatus status;
	private HttpVersion version;
	
	public ResponseStatusLine() {
		version = new HttpVersion();
	}
	
	public ResponseStatusLine(HttpStatus status, HttpVersion version) {
		this.status = status;
		this.version = version;
	}
	
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public HttpVersion getVersion() {
		return version;
	}
	public void setVersion(HttpVersion version) {
		this.version = version;
	}
	
	
	public String toString() {
		return version.toString() + " " + status.toString();
	}
	

}
