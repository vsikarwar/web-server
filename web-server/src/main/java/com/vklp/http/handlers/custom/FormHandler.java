package com.vklp.http.handlers.custom;

import com.vklp.http.handlers.core.POSTHandler;
import com.vklp.http.message.HttpHeaders.Headers;
import com.vklp.http.message.request.HttpRequest;
import com.vklp.http.message.response.HttpResponse;
import com.vklp.http.message.response.HttpStatus;

public class FormHandler extends POSTHandler{

	public void doPost(HttpRequest req, HttpResponse res) throws Exception{
		String firstName = req.getParam("first_name");
		String lastName = req.getParam("last_name");
		
		String path = "form-result.html";
		
		String content = fileService.getContentAsString(path);
			
		content = content.replace("{{first_name}}", firstName);
		content = content.replace("{{last_name}}", lastName);
		
		res.setContent(content.getBytes());
		res.addHeader(Headers.CONTENT_TYPE.getName(), fileService.getContentType(path));
		res.addHeader(Headers.CONTENT_LENGTH.getName(), String.valueOf(content.length()));
		
		res.setStatus(HttpStatus.OK);
	}
	
}
