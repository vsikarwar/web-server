package com.vklp.http.ITests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.vklp.http.config.Config;
import com.vklp.http.handlers.RequestHandler;
import com.vklp.ws.services.SocketService;

public class ConnectionKeepAliveTest {
	
	private Config config = Config.getInstance();
	private RequestHandler handler = new RequestHandler();
	private SocketService service;
	
	@Before
	public void setUp() {
			service = new SocketService(config, handler);
			service.start();
	}
	
	@After
	public void tearDown() {
		service.stop();
	}
	
	private Socket getSocket() {
		Socket socket = null;
		try {
			socket = new Socket("localhost", 8000);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return socket;
	}
	
	private OutputStream getOutputStream(Socket socket) {
		OutputStream out = null;
		
		try {
			out = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return out;
	}
	
	private InputStream getInputStream(Socket socket) {
		InputStream in = null;
		
		try {
			in = socket.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return in;
	}
	
	@Test
	public void testKeepAlive() {
		String hostname = "localhost";
		int port = 8000;
		String path = "/index.html";
		
		Socket socket = getSocket();
		
		OutputStream output = getOutputStream(socket);
        InputStream input = getInputStream(socket);
        
        
        for(int i = 1; i< 10; i++) {
        	BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        	System.out.println("******PROESSING REQUEST : " + i);
        	write(hostname, port, path, output);
        	
        	String status = read(reader);
        	int length = readHeaders(status);
    		String payload = readPayload(reader, length);
        }
		
	}
	
	private void write(String hostname, int port, String path, OutputStream output) {
		PrintWriter writer = new PrintWriter(output, true);
 
		writer.println("GET " + path + " HTTP/1.1");
		writer.println("Host: " + hostname + ":" + port);
		writer.println("User-Agent: Simple Http Client");
		writer.println("Accept: text/html");
		writer.println("Accept-Language: en-US");
		writer.println("Connection: keep-alive");
		writer.println();
	}
	
	private String read(BufferedReader reader) {
		StringBuilder builder = new StringBuilder();
		try {
			String requestLine=reader.readLine();
			builder.append(requestLine + "\n");
			while(requestLine != null && !requestLine.trim().isEmpty()) {
				requestLine = reader.readLine();
				builder.append(requestLine + "\n");
			}
		} catch(SocketTimeoutException ste) {
			ste.printStackTrace();;
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return builder.toString();
	}
	
	private int readHeaders(String header) {
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
	
	private String readPayload(BufferedReader in, int contentLength) {
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
