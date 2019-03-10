package com.viks.http.processors;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import com.viks.http.header.Headers;
import com.viks.http.request.Request;
import com.viks.http.response.HttpStatus;
import com.viks.http.response.Response;
import com.viks.http.server.ServerConfig;
import com.viks.http.server.ServerConfig.ServerConfigs;
import com.viks.http.version.VersionHandler;

public class RequestHandler {
	
	private ServerConfig config = ServerConfig.getInstance();
	
	private FileProcessor processor = new FileProcessor();
	
	
	public void handle(Request req, Response res) {
		if(isVersionValid(req, res) && isMethodSupported(req, res)) {
			processContentTypeHeader(req, res);
			processor.process(req, res);
			contentLengthHeader(req, res);
		}
		processDefaultHeaders(req, res);
		processKeepAliveHeader(req, res);

	}
	
	private boolean isMethodSupported(Request req, Response res) {
		//if method is supported or not
		String method = req.getMethod().getMethod();
		if(!method.equals("GET")) {
			res.getStatusLine().setStatus(HttpStatus.METHOD_NOT_ALLOWED);
			return false;
		}
		return true;
	}
	
	private boolean isVersionValid(Request req, Response res) {
		VersionHandler vHandler = new VersionHandler();
		boolean isVersionValid = vHandler.isVersionValid(req.getVersion());
		if(!isVersionValid) {
			res.getStatusLine().setStatus(HttpStatus.HTTP_VERSION_NOT_SUPPORTED);
			return false;
		}
		res.getStatusLine().setVersion(req.getVersion());
		return true;
		
	}
	
	protected void processDefaultHeaders(Request req, Response res) {
		//is keep alive
		res.getHeaders().add(Headers.DATE.getName(), String.valueOf(new Date()));
		res.getHeaders().add(Headers.SERVER.getName(), config.getStr(ServerConfigs.SERVER_NAME.config()));
		
	}

	private void processContentTypeHeader(Request req, Response res) {
		try {
			res.getHeaders().add(Headers.CONTENT_TYPE.getName(), Files.probeContentType(Paths.get(req.getUri().getPath())));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processKeepAliveHeader(Request req, Response res) {
		boolean isKeepAlive = req.getHeaders().get(Headers.CONNECTION.getName()).equals("keep-alive");
		System.out.println("IS Keep alive : " + isKeepAlive);
		if(isKeepAlive) {
			res.getHeaders().add(Headers.CONNECTION.getName(), "keep-alive");
			String timeout = "timeout=" + config.getStr(ServerConfigs.KEEP_ALIVE_TIMEOUT.config());
			res.getHeaders().add(Headers.KEEP_ALIVE.getName(), timeout);
		}else {
			res.getHeaders().add(Headers.CONNECTION.getName(), "close");
		}
	}
	
	private void contentLengthHeader(Request req, Response res) {
		res.getHeaders().add(Headers.CONTENT_LENGTH.getName(), String.valueOf(res.getBody().getBytes().length));
	}

}
