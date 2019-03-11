package com.vklp.http.message.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.util.StringTokenizer;

import com.vklp.http.message.HttpHeaders;
import com.vklp.http.message.HttpVersion;
import com.vklp.http.message.HttpHeaders.Headers;
import com.vklp.http.version.VersionHandler;

public class RequestReader {
	
	private InputStream inputStream;
	private HttpRequest request;
	
	public RequestReader(InputStream inputStream) {
		this.inputStream = inputStream;
		this.request = new HttpRequest();
	}
	
	public HttpRequest readRequest() throws SocketTimeoutException {
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));
		
		boolean isRequestLine = readRequestLine(inputReader);
		if(!isRequestLine) {
			return null;
		}
		readHeaders(inputReader);

		if(request.getMethod().equals("POST")) {
			String contentLength = request.getHeaders().get(Headers.CONTENT_LENGTH.getName());
			if(null!= contentLength) {
				Integer cl = Integer.valueOf(contentLength);
				request.getContent().setContentLength(cl);
				readPayload(inputReader, cl);
			}
		}
		return request;
		
	}
	
	private void readPayload(BufferedReader in, int contentLength) {
		StringBuilder body = new StringBuilder();
        int c = 0;
        for (int i = 0; i < contentLength; i++) {
            try {
				c = in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
            body.append((char) c);
        }
        request.getContent().setContent(body.toString().getBytes());
	}
	
	private boolean readRequestLine(BufferedReader in) throws SocketTimeoutException {
		String requestLine=null;
		try {
			requestLine = in.readLine();
		} catch(SocketTimeoutException ste) {
			throw ste;
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		if(requestLine == null) {
			return false;
		}
		
		StringTokenizer st = new StringTokenizer(requestLine);
		
		if(!st.hasMoreTokens()) {
			System.out.println("BAD request");
		}
		
		request.setMethod(st.nextToken().toUpperCase());
		
		if(!st.hasMoreTokens()) {
			System.out.println("BAD request");
		}
		
		uriHandler(st.nextToken());
		
		if(!st.hasMoreTokens()) {
			System.out.println("No version");
		}
		
		String version = st.nextToken();
		VersionHandler versionHandler = new VersionHandler();
		HttpVersion httpVersion = versionHandler.handle(version);
		request.setVersion(httpVersion);
		
		return true;
	}
	
	public void uriHandler(String pathStr) {
		String path = pathStr;
		if(pathStr.contains("?")) {
			StringTokenizer tokenizer = new StringTokenizer(pathStr, "?");
			path = tokenizer.nextToken();
			request.getParams().putParamLine(tokenizer.nextToken());
		}
		request.setUri(path);
	}
	
	private void readHeaders(BufferedReader in) {
		HttpHeaders headers = request.getHeaders();
		
		try {
			String line = in.readLine();
			while(line != null && !line.trim().isEmpty()) {
				headers.put(line);
				line = in.readLine();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}
