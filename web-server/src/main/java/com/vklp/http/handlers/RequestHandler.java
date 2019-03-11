package com.vklp.http.handlers;

import java.util.HashMap;
import java.util.Map;

import com.vklp.http.message.request.HttpRequest;
import com.vklp.http.message.response.HttpResponse;

public class RequestHandler{
	
	private final Map<String, Handler> methodHandler;
	private final Map<String, Handler> pathHandler;
	private final RedirectHandler redirectHandler;
	
	public RequestHandler() {
		methodHandler = new HashMap<String, Handler>();
		pathHandler = new HashMap<String, Handler>();
		redirectHandler = new RedirectHandler();
		
		//default handlers
		methodHandler.put("GET", new GETHandler());
		methodHandler.put("POST", new POSTHandler());
		methodHandler.put("HEAD", new HEADHandler());
	}
	
	public void put(String path, Handler handler) {
		pathHandler.put(path, handler);
	}
	
	public void setDefaultGetHandler(Handler handler) {
		methodHandler.put("GET", handler);
	}
	
	public void setDefaultPostHandler(Handler handler) {
		methodHandler.put("POST", handler);
	}
	
	public void setDefaultHeadHandler(Handler handler) {
		methodHandler.put("HEAD", handler);
	}

	public void handle(HttpRequest req, HttpResponse res) {
		
		if(redirectHandler.hasRedirects() && redirectHandler.contains(req.getUri())) {
			redirectHandler.handle(req, res);
		}else if(pathHandler.containsKey(req.getUri())) {
			Handler handler = pathHandler.get(req.getUri());
			handler.handle(req, res);
		}else if(methodHandler.containsKey(req.getMethod())) {
			methodHandler.get(req.getMethod()).handle(req, res);
		}else {
			//can not handle
		}
	}

}
