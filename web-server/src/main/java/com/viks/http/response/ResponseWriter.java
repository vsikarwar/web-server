package com.viks.http.response;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class ResponseWriter {
	
	private OutputStream outputStream;
	private BufferedOutputStream os;
	private OutputStreamWriter osw;
	
	public ResponseWriter(OutputStream outputStream) {
		this.outputStream = outputStream;
		os = new BufferedOutputStream(this.outputStream);
		try {
			osw = new OutputStreamWriter(os, "US-ASCII");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public void write(Response res) {
		try {
			System.out.println("Response : " + res.toString());
			osw.write(res.toString());
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		try {
			osw.flush();
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
