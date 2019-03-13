package com.vklp.http.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class TestUtils {
	
	public static void write(String hostname, int port, String path, OutputStream output) {
		PrintWriter writer = new PrintWriter(output, true);
 
		writer.println("GET " + path + " HTTP/1.1");
		writer.println("Host: " + hostname + ":" + port);
		writer.println("User-Agent: Simple Http Client");
		writer.println("Accept: text/html");
		writer.println("Accept-Language: en-US");
		writer.println("Connection: keep-alive");
		writer.println();
	}
	
	public static void write(String str, OutputStream output) {
		PrintWriter writer = new PrintWriter(output, true);
		writer.println(str);
	}
	
	public static void writeHTTP1_1_Keep_alive(String hostname, String path, OutputStream output) {
		PrintWriter writer = new PrintWriter(output, true);
		 
		writer.println("GET " + path + " HTTP/1.1");
		writer.println("Host: " + hostname);
		writer.println("User-Agent: Simple Http Client");
		writer.println("Accept: text/html");
		writer.println("Accept-Language: en-US");
		writer.println("Connection: keep-alive");
		writer.println();
	}
	
	public static void writeHTTP1_1_Connection_Close(String hostname, String path, OutputStream output) {
		PrintWriter writer = new PrintWriter(output, true);
		 
		writer.println("GET " + path + " HTTP/1.1");
		writer.println("Host: " + hostname);
		writer.println("User-Agent: Simple Http Client");
		writer.println("Accept: text/html");
		writer.println("Accept-Language: en-US");
		writer.println("Connection: close");
		writer.println();
	}
	
	public static String getResponse(BufferedReader reader) throws IOException {
		String status = read(reader);
		int length = readHeaders(status);
		String payload = readPayload(reader, length);
		
		String response = status + payload;
		return response;
	}
	
	private static String read(BufferedReader reader) throws IOException {
		StringBuilder builder = new StringBuilder();
		String requestLine=reader.readLine();
		builder.append(requestLine + "\n");
		while(requestLine != null && !requestLine.trim().isEmpty()) {
			requestLine = reader.readLine();
			builder.append(requestLine + "\n");
		}
		
		return builder.toString();
	}
	
	private static int readHeaders(String header) {
		int length = 0;
		
		String[] lines = header.split("\n");
		for(String line : lines) {
			if (line.startsWith("Content-Length: ")) { // get the
	            // content-length
				int index = line.indexOf(':') + 1;
				String len = line.substring(index).trim();
				length = Integer.parseInt(len);
			}
		}
		
		return length;
	}
	
	private static String readPayload(BufferedReader in, int contentLength) {
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
        return body.toString();
	}

}
