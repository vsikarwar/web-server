package com.vklp.http.message;

public class HttpContent {
	
	private byte[] content;
	private int contentLength;
	private String contentType;
	
	public HttpContent() {
		this.content = "".getBytes();
	}

	public byte[] getContent() {
		return content;
	}
	
	public void setEmptyContent() {
		this.content = "".getBytes();
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public int getContentLength() {
		return contentLength;
	}

	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public String toString() {
		String str = new String(this.content);
		return str;
	}
	
}
