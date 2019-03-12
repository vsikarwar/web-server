package com.vklp.http.handlers;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.vklp.http.message.request.HttpRequest;
import com.vklp.http.message.response.HttpResponse;
import com.vklp.http.message.response.HttpStatus;

public class GETHandler extends AbstractHandler{

	public void doGet(HttpRequest req, HttpResponse res) throws FileNotFoundException, IOException {
		String path = req.getUri();
		
		res.setContent(fileService.getContentAsByte(path));
		res.setContentType(fileService.getContentType(path));
		res.setContentLength(fileService.getContentLength(path));
		res.setStatus(HttpStatus.OK);
		
	}

	@Override
	void doHead(HttpRequest req, HttpResponse res) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void doPost(HttpRequest req, HttpResponse res) {
		// TODO Auto-generated method stub
		
	}

}
