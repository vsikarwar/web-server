package com.viks.http.response;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;

public class ResponseHandler {
	
	private static final String NEW_LINE = "\n";

	private static final ResponseHandler INSTANCE = new ResponseHandler();
	
	private static final String SPACE = " ";
	
	private ResponseHandler() {
		
	}
	
	public static ResponseHandler getInstance() {
		return INSTANCE;
	}
	
	private String generateResponseHeader(Response res) {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(res.getVersion());
		sb.append(SPACE);
		sb.append(res.getStatus());
		sb.append(NEW_LINE);
		
		sb.append("Server: " + res.getServer());
		sb.append(NEW_LINE);
		
		sb.append("Date: " + res.getDate());
		sb.append(NEW_LINE);
		
		sb.append("Content-type: " + "text/html");
		sb.append(NEW_LINE);
		
		sb.append("Content-Length: " + res.getContentLength());
		sb.append(NEW_LINE);
		
		for(String key : res.getHeaders().keySet()) {
			sb.append(key + ": " + res.getHeaders().get(key));
			sb.append(NEW_LINE);
		}
		
		sb.append("\r\n");
		
		System.out.println("Response : \n" + sb.toString());
		return sb.toString();
	}
	
	
	
	public void handle(Response res, OutputStream out) {
		
		res.setServer("Java HTTP Server : 1.0");
		res.setDate(new Date());
		
		if(res.getStatus() == null) {
			//error condition; status can not be null 
		}
		
		try {
			
			PrintWriter pw = new PrintWriter(out);
			
			BufferedOutputStream dataOut = new BufferedOutputStream(out);
			
			pw.println(generateResponseHeader(res));
			pw.flush();
			
			dataOut.write(res.getBody(), 0, res.getContentLength());
			dataOut.flush();
			
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
