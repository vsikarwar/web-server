package com.viks.http.processors;

import java.io.File;

import com.viks.http.config.ConfigService.Configs;
import com.viks.http.message.request.HttpRequest;
import com.viks.http.message.response.HttpResponse;
import com.viks.http.message.response.HttpStatus;

public class GenericProcessor extends AbstractProcessor{
	
	public void doGet(HttpRequest req, HttpResponse res) {
		String path = req.getUri();
		
		File file = new File(config.getStr(Configs.DOC_ROOT.config()), path);
		if(file.exists()) {
			fileContent(res, path);
			res.setStatus(HttpStatus.OK);
		}else {
			fileNotFound(res);
		}
	}

}
