package com.vklp.http.handlers;

public class RequestHandlerBuilder {
	
	private RequestHandler handler;
	
	public RequestHandlerBuilder() {
		handler = new RequestHandler();
	}
	
	public RequestHandler build() {
		return handler;
	}
	
	public RequestHandlerBuilder addHandler(String path, Handler handler) {
		this.handler.put(path, handler);
		return this;
	}
	
	public RequestHandlerBuilder defaultGet(Handler handler) {
		this.handler.setDefaultGetHandler(handler);
		return this;
	}
	
	public RequestHandlerBuilder defaultPost(Handler handler) {
		this.handler.setDefaultPostHandler(handler);
		return this;
	}
	
	public RequestHandlerBuilder defaultHead(Handler handler) {
		this.handler.setDefaultHeadHandler(handler);
		return this;
	}

}
