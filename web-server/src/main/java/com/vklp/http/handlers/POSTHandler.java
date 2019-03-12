package com.vklp.http.handlers;

import com.vklp.http.message.request.HttpRequest;
import com.vklp.http.message.response.HttpResponse;

public class POSTHandler extends AbstractHandler{

	protected void doPost(HttpRequest req, HttpResponse res)  throws Exception{
		// TODO Auto-generated method stub
		System.out.println("POST is not implemented");
		System.out.println(req.getContent().toString());
	}

	@Override
	void doHead(HttpRequest req, HttpResponse res) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void doGet(HttpRequest req, HttpResponse res) {
		// TODO Auto-generated method stub
		
	}

}
