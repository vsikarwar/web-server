package com.vklp.http.handlers;

import java.io.File;

import com.vklp.http.config.Config.Configs;
import com.vklp.http.message.request.HttpRequest;
import com.vklp.http.message.response.HttpResponse;
import com.vklp.http.message.response.HttpStatus;

public class HEADHandler extends AbstractHandler{

	public void doHead(HttpRequest req, HttpResponse res) {
		String path = req.getUri();
		
		File file = new File(config.getStr(Configs.DOC_ROOT.config()), path);
		if(file.exists()) {
			//fileContent(res, path);
			res.setStatus(HttpStatus.OK);
		}else {
			fileNotFound(res);
		}
	}

}
