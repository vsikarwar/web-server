package com.vklp.http.handlers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.vklp.http.config.Config;
import com.vklp.http.config.Config.Configs;
import com.vklp.http.message.HttpHeaders.Headers;
import com.vklp.http.message.request.HttpRequest;
import com.vklp.http.message.response.HttpResponse;
import com.vklp.http.message.response.HttpStatus;
import com.vklp.http.storage.StorageService;
import com.vklp.http.storage.StorageServiceFactory;
import com.vklp.http.storage.StorageServiceFactory.StorageServices;

public abstract class AbstractHandler implements Handler{

	public boolean canHandler(HttpRequest req, HttpResponse res) {
		return false;
	}

	protected static Config config = Config.getInstance();
	protected static StorageService fileService = StorageServiceFactory.getInstance().getStorageService(StorageServices.FILE_STORAGE);
	
	private static final Logger logger = Logger.getLogger(AbstractHandler.class);
	
	public void handle(HttpRequest req, HttpResponse res) {
		if(!req.getVersion().isValid()) {
			handleError(res, HttpStatus.HTTP_VERSION_NOT_SUPPORTED);
			return;
		}
		res.setVersion(req.getVersion());
		
		if(req.getMethod() == null) {
			handleError(res, HttpStatus.METHOD_NOT_ALLOWED);
			return;
		}
		
		logger.debug("Request PATH : " + req.getUri());
		
		connectionHeader(req, res);
		cacheControl(req, res);
		
		res.addHeader(Headers.DATE, new Date().toString());
		res.addHeader(Headers.SERVER, config.getStr(Configs.SERVER_NAME.config()));
		
		try {
			if(req.getMethod().equals("GET")) {
				doGet(req, res);
			}else if(req.getMethod().equals("POST")) {
				doPost(req, res);
			}else if(req.getMethod().equals("HEAD")){
				doHead(req, res);
			}else {
				handleError(res, HttpStatus.METHOD_NOT_ALLOWED);
			}
		}catch(FileNotFoundException e) {
			e.printStackTrace();
			handleError(res, HttpStatus.NOT_FOUND);
		}catch(Exception e) {
			e.printStackTrace();
			handleError(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	public abstract void doHead(HttpRequest req, HttpResponse res)  throws Exception;
	
	public abstract void doGet(HttpRequest req, HttpResponse res)  throws Exception;
	
	public abstract void doPost(HttpRequest req, HttpResponse res)  throws Exception;
	
	private void connectionHeader(HttpRequest req, HttpResponse res) {
		
		String connection = req.getHeader(Headers.CONNECTION.getName());
		String version = req.getVersion().toString();
		
		if((version.equals("HTTP/1.0") && null != connection && connection.equals("keep-alive"))||
				version.equals("HTTP/1.1") && null != connection && !connection.equals("close") ||
				version.equals("HTTP/1.1") && null == connection) {
			res.addHeader(Headers.CONNECTION.getName(), "keep-alive");
			String timeout = "timeout=" + config.getStr(Configs.KEEP_ALIVE_TIMEOUT.config());
			String max = "max=" + config.getStr(Configs.KEEP_ALIVE_MAX.config());
			res.addHeader(Headers.KEEP_ALIVE.getName(), timeout + "," + max);
		}else {
			res.addHeader(Headers.CONNECTION.getName(), "close");
		}
		
	}
	
	private void cacheControl(HttpRequest req, HttpResponse res) {
		if(req.getUri().startsWith("/assets") && req.getMethod().equals("GET")) {
			try {
				String checksum = fileService.getChecksum(req.getUri());
				res.addHeader(Headers.CACHE_CONTROL, "max-age=120");
				res.addHeader(Headers.ETAG, checksum);
			} catch (FileNotFoundException e) {
				handleError(res, HttpStatus.NOT_FOUND);
				e.printStackTrace();
			} catch (IOException e) {
				handleError(res, HttpStatus.INTERNAL_SERVER_ERROR);
				e.printStackTrace();
			}
		}
	}
	
	protected void handleError(HttpResponse res, HttpStatus status) {
		
		String content = "<html><title>" + 
							status.code() +
							"</title><body><h1>" +
							status.description( )+
							"</h1></body></html>";
		
		
		
		res.clearHeaders();
		
		res.setContent(content.getBytes());
		res.setContentType("text/html");
		res.setContentLength(content.length());
		res.addHeader(Headers.DATE, new Date().toString());
		res.addHeader(Headers.SERVER, config.getStr(Configs.SERVER_NAME.config()));
		res.addHeader(Headers.CONNECTION, "close");
		
		res.setStatus(status);
		
	}
	
}
