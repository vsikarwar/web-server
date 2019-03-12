package com.vklp.http.message;

import java.util.List;

import com.vklp.http.message.HttpHeaders.Headers;

public class HttpMessage {
	
	private HttpContent content;
	private HttpHeaders headers;
	private HttpVersion version;
	
	public HttpMessage() {
		content = new HttpContent();
		headers = new HttpHeaders();
		version = new HttpVersion();
		
	}
	
	public byte[] getContent() {
		return this.content.getContent();
	}
	
	public void setContent(byte[] content) {
		this.content.setContent(content);
	}
	
	public void clearHeaders() {
		this.headers.clear();
	}
	
	public void setContent(String content) {
		this.content.setContent(content.getBytes());
	}
	
	public void setContentType(String type) {
		this.content.setContentType(type);
		this.headers.add(Headers.CONTENT_TYPE.getName(), type);
	}
	
	public void setContentLength(long length) {
		this.content.setContentLength(length);
		this.headers.add(Headers.CONTENT_LENGTH.getName(), String.valueOf(length));
	}
	
	public String getHeader(String name) {
		return this.headers.get(name);
	}
	
	public String getHeader(Headers header) {
		return this.headers.get(header.getName());
	}
	
	public List<String> getHeaders(String name) {
		return this.headers.getAll(name);
	}
	
	protected HttpHeaders getHeaders() {
		return this.headers;
	}
	
	public void addHeader(String line) {
		this.headers.put(line);
	}
	
	public void addHeader(String name, String value) {
		this.headers.add(name, value);
	}
	
	public void addHeader(Headers header, String value) {
		this.headers.add(header.getName(), value);
	}
	
	public HttpVersion getVersion() {
		return version;
	}

	public void setVersion(int major, int minor) {
		this.version = new HttpVersion(minor, major);
	}
	
	public void setVersion(HttpVersion version) {
		this.version = version;
	}
	

}
