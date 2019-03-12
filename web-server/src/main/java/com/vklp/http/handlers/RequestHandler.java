package com.vklp.http.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vklp.http.message.request.HttpRequest;
import com.vklp.http.message.response.HttpResponse;

public class RequestHandler{
	
	private final Map<String, Handler> defaultHandlers;
	private final Map<String, Handler> customHandlers;
	private final List<Handler> requestHandlers;
	private final ErrorHandler errorHandler;
	
	public RequestHandler() {
		defaultHandlers = new HashMap<String, Handler>();
		customHandlers = new HashMap<String, Handler>();
		requestHandlers = new ArrayList<Handler>();
		
		errorHandler = new ErrorHandler();
		
		//request handlers
		requestHandlers.add(new RedirectHandler());
		requestHandlers.add(new ETagHandler());
		
		//default handlers
		defaultHandlers.put("GET", new GETHandler());
		defaultHandlers.put("POST", new POSTHandler());
		defaultHandlers.put("HEAD", new HEADHandler());
	}
	
	public void put(String path, Handler handler) {
		customHandlers.put(path, handler);
	}
	
	public void setDefaultGetHandler(Handler handler) {
		defaultHandlers.put("GET", handler);
	}
	
	public void setDefaultPostHandler(Handler handler) {
		defaultHandlers.put("POST", handler);
	}
	
	public void setDefaultHeadHandler(Handler handler) {
		defaultHandlers.put("HEAD", handler);
	}

	public void handle(HttpRequest req, HttpResponse res) {
		
		boolean isRequestHandled = false;
		
		for(Handler handler : requestHandlers) {
			if(handler.canHandler(req, res)) {
				handler.handle(req, res);
				isRequestHandled = true;
				break;
			}
		}
		
		//see if custom handler is registered for this path
		if(!isRequestHandled && customHandlers.containsKey(req.getUri())) {
			Handler handler = customHandlers.get(req.getUri());
			handler.handle(req, res);
			isRequestHandled = true;
		}
		
		//use default handlers to handle the request
		if(!isRequestHandled && defaultHandlers.containsKey(req.getMethod())) {
			defaultHandlers.get(req.getMethod()).handle(req, res);
			isRequestHandled=true;
		}
		
		//error condition, can not handle the request
		if(!isRequestHandled) {
			errorHandler.handle(req, res);
		}
		
	}
	
}
