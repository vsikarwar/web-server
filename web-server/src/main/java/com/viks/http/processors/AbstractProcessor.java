package com.viks.http.processors;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import org.apache.commons.io.FileUtils;

import com.viks.http.config.ConfigService;
import com.viks.http.config.ConfigService.Configs;
import com.viks.http.message.HttpHeaders.Headers;
import com.viks.http.message.request.HttpRequest;
import com.viks.http.message.response.HttpResponse;
import com.viks.http.message.response.HttpStatus;
import com.viks.http.redirects.Redirect;
import com.viks.http.redirects.RedirectService;

public class AbstractProcessor implements Processor{
	
	protected ConfigService config = ConfigService.getInstance();
	protected RedirectService redirects = RedirectService.getInstance();

	public void process(HttpRequest req, HttpResponse res) {
		if(!req.getVersion().isValid()) {
			invalidVersionError(res);
			return;
		}
		res.setVersion(req.getVersion());
		
		if(req.getMethod() == null) {
			unsupportedMethodError(res);
			return;
		}
		
		System.out.println("PATH : " + req.getUri());
		
		if(redirects.hasRedirects() && redirects.contains(req.getUri())) {
			Redirect redirect = redirects.getRedirect(req.getUri());
			if(redirect.getType().equals("301")) {
				res.setStatus(HttpStatus.MOVED_PERMANENTLY);
			}else {
				res.setStatus(HttpStatus.MOVED_TEMPORARILY);
			}
			res.getHeaders().add(Headers.LOCATION.getName(), redirect.getDestination());
			return;
		}
		
		if(req.getMethod().equals("GET")) {
			doGet(req, res);
		}else if(req.getMethod().equals("POST")) {
			doPost(req, res);
		}else {
			unsupportedMethodError(res);
		}
		
		
		res.getHeaders().add(Headers.DATE.getName(), new Date().toString());
		res.getHeaders().add(Headers.SERVER.getName(), config.getStr(Configs.SERVER_NAME.config()));
		
		connectionHeader(req, res);
	}
	
	protected void doGet(HttpRequest req, HttpResponse res) {
		//
		
	}
	
	protected void doPost(HttpRequest req, HttpResponse res) {
		//unhandled request
	}
	
	private void connectionHeader(HttpRequest req, HttpResponse res) {
		
		String connection = req.getHeaders().get(Headers.CONNECTION.getName());
		String version = req.getVersion().toString();
		
		if((version.equals("HTTP/1.0") && null != connection && connection.equals("keep-alive"))||
				version.equals("HTTP/1.1") && null != connection && !connection.equals("close")) {
			res.getHeaders().add(Headers.CONNECTION.getName(), "keep-alive");
			String timeout = "timeout=" + config.getStr(Configs.KEEP_ALIVE_TIMEOUT.config());
			res.getHeaders().add(Headers.KEEP_ALIVE.getName(), timeout);
		}else {
			res.getHeaders().add(Headers.CONNECTION.getName(), "close");
		}
		
	}
	
	private void invalidVersionError(HttpResponse res) {
		res.setStatus(HttpStatus.HTTP_VERSION_NOT_SUPPORTED);
		fileContent(res, config.getStr(Configs.ERROR_500.config()));
	}
	
	private void unsupportedMethodError(HttpResponse res) {
		res.setStatus(HttpStatus.METHOD_NOT_ALLOWED);
		fileContent(res, config.getStr(Configs.ERROR_400.config()));
	}
	
	protected void fileNotFound(HttpResponse res) {
		res.setStatus(HttpStatus.NOT_FOUND);
		fileContent(res, config.getStr(Configs.ERROR_400.config()));
	}

	protected void fileContent(HttpResponse res, String filePath) {
		try {
			String docRoot = config.getStr(Configs.DOC_ROOT.config());
			byte[] content = FileUtils.readFileToByteArray(new File(docRoot, filePath));
			res.getContent().setContent(content);
			res.getHeaders().add(Headers.CONTENT_TYPE.getName(), Files.probeContentType(Paths.get(filePath)));
			res.getHeaders().add(Headers.CONTENT_LENGTH.getName(), String.valueOf(content.length));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
