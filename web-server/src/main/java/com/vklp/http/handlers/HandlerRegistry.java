package com.vklp.http.handlers;

public class HandlerRegistry {
	
	private static final HandlerRegistry registry = new HandlerRegistry();
	
	private final RequestHandler handler;
	
	private HandlerRegistry() {
		handler = register();
	}
	
	public static HandlerRegistry getInstance() {
		return registry;
	}
	
	private RequestHandler register() {
		RequestHandlerBuilder builder = new RequestHandlerBuilder();
		//customer handler can be registered
		builder.addHandler("/form/result", new FormHandler());
		return builder.build();
	}
	
	public RequestHandler getHandler() {
		return handler;
	}

}
