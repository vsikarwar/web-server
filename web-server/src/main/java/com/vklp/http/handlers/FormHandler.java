package com.vklp.http.handlers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

import com.vklp.http.config.Config.Configs;
import com.vklp.http.message.HttpHeaders.Headers;
import com.vklp.http.message.request.HttpRequest;
import com.vklp.http.message.response.HttpResponse;
import com.vklp.http.message.response.HttpStatus;

public class FormHandler extends AbstractHandler{

	public void doPost(HttpRequest req, HttpResponse res) {
		String firstName = req.getParams().getValue("first_name");
		String lastName = req.getParams().getValue("last_name");
		
		System.out.println("first name " + firstName);
		System.out.println("last name " + lastName);
		
		String path = "form-result.html";
		
		File file = new File(config.getStr(Configs.DOC_ROOT.config()), path);
		try {
			String content = FileUtils.readFileToString(file);
			
			content = content.replace("{{first_name}}", firstName);
			content = content.replace("{{last_name}}", lastName);
			
			res.getContent().setContent(content.getBytes());
			res.getHeaders().add(Headers.CONTENT_TYPE.getName(), Files.probeContentType(Paths.get(path)));
			res.getHeaders().add(Headers.CONTENT_LENGTH.getName(), String.valueOf(content.getBytes().length));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		res.setStatus(HttpStatus.OK);
	}
	
}
