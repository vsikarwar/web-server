package com.vklp.http.ITests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class HTTPClient {
	
	public void getRequest() {
 
        String hostname = "localhost";
        int port = 8000;
        String path = "/index.html";
 
        try {
        	Socket socket = getSocket(hostname, port);
        	
            OutputStream output = socket.getOutputStream();
            InputStream input = socket.getInputStream();
            
            write(hostname, port, path, output);
            read(input);
        } catch (UnknownHostException ex) {
 
            System.out.println("Server not found: " + ex.getMessage());
 
        } catch (IOException ex) {
 
            System.out.println("I/O error: " + ex.getMessage());
        }
	}

	private void read(InputStream input) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
		String line;
 
		while ((line = reader.readLine()) != null) {
		    System.out.println(line);
		}
	}

	private void write(String hostname, int port, String path, OutputStream output) {
		PrintWriter writer = new PrintWriter(output, true);
 
		writer.println("GET " + path + " HTTP/1.1");
		writer.println("Host: " + hostname + ":" + port);
		writer.println("User-Agent: Simple Http Client");
		writer.println("Accept: text/html");
		writer.println("Accept-Language: en-US");
		writer.println("Connection: close");
		writer.println();
	}

	private Socket getSocket(String hostname, int port) throws UnknownHostException, IOException {
		Socket socket = new Socket(hostname, port);
		return socket;
	}

}
