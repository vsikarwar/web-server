package com.vklp.http.message.response;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

public class ResponseWriter {
	
	private static final Logger logger = Logger.getLogger(ResponseWriter.class);
	
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
	
	public void write(HttpResponse res) {
		try {
			logger.debug("Response : " + res.toString());
			osw.write(res.toString());
			osw.flush();
			logger.debug(new String(res.getContent()));
			if(res.getContent() != null) {
				os.write(res.getContent());
				os.flush();
			}
			
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		try {
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
