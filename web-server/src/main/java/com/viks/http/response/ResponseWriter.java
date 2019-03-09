package com.viks.http.response;

import java.io.OutputStream;

public class ResponseWriter {
	
	private OutputStream outputStream;
	
	public ResponseWriter(OutputStream outputStream) {
		this.outputStream = outputStream;
	}
	
	public void write(Response res) {
		writeStatus(res);
		writeHeaders(res);
		writeBody(res);
	}

	private void writeStatus(Response res) {
		outputStream.
	}
}
