package com.vklp.http.message.response;

import com.vklp.http.message.HttpMessage;

public class HttpResponse extends HttpMessage{
	
	private HttpStatus status;

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	
//	public String toString() {
//		StringBuffer sb = new StringBuffer();
//		sb.append(this.getVersion().toString() + " " + this.getStatus().toString() + "\n");
//		sb.append(this.getHeaders() + "\n");
//		sb.append(this.getContent().toString());
//		return sb.toString();
//	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.getVersion().toString() + " " + this.getStatus().toString() + "\n");
		sb.append(this.getHeaders() + "\n");
		sb.append("\r\n");
		//sb.append(this.getContent().toString());
		return sb.toString();
	}



}
