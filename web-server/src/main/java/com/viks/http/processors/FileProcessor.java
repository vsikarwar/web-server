package com.viks.http.processors;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.viks.http.request.Request;
import com.viks.http.response.HttpStatus;
import com.viks.http.response.Response;
import com.viks.http.server.ServerConfig;
import com.viks.http.server.ServerConfig.ServerConfigs;

public class FileProcessor {
	
	private final static String DOC_ROOT = ServerConfig.getInstance().getStr(ServerConfigs.DOC_ROOT.config());
	
	public void process(Request req, Response res) {
		String reqPath = DOC_ROOT + req.getUri().getPath();
		
		//path processor
		File file = new File(reqPath);
				
		if(!file.exists()) {
			reqPath = DOC_ROOT + "/errors/400.html";
			res.getStatusLine().setStatus(HttpStatus.NOT_FOUND);
		}else {
			res.getStatusLine().setStatus(HttpStatus.OK);
		}
		
		try {
			String content = FileUtils.readFileToString(new File(reqPath));
			res.setBody(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
