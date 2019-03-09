package com.viks.http.socket;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;

public class HTTPServer implements Runnable{
	
	static final File WEB_ROOT = new File(".");
	static final String DEFAULT_FILE = "index.html";
	static final String FILE_NOT_FOUND = "404.html";
	static final String METHOD_NOT_SUPPORTED = "not_supported.html";
	
	//port to listen connection
	static final int PORT = 8080;
	
	//verbose mode
	static final boolean verbose = true;
	
	//SOCKET CLASS
	private Socket connect; 
	
	public HTTPServer(Socket c) {
		this.connect = c;
	}
	
	public static void startServer() {
		try {
			ServerSocket serverConnect = new ServerSocket(PORT);
			System.out.println("Server started.");
			
			while(true) {
				HTTPServer server = new HTTPServer(serverConnect.accept());
				
				if (verbose) {
					System.out.println("Connecton opened. (" + new Date() + ")");
				}
				
				Thread thread = new Thread(server);
				thread.start();
			}
		}catch(IOException e) {
			System.err.println("Server Connection error : " + e.getMessage());
		}
	}

	public void run() {
		BufferedReader in = null;
		PrintWriter out = null;
		BufferedOutputStream dataOut = null;
		String fileRequested = null;
		
		
		try {
			in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
			
			out = new PrintWriter(connect.getOutputStream());
			
			dataOut = new BufferedOutputStream(connect.getOutputStream());
			
			String input = in.readLine();
			
			StringTokenizer parse = new StringTokenizer(input);
			
			String method = parse.nextToken().toUpperCase();
			
			fileRequested = parse.nextToken().toLowerCase();
			
			System.out.println(input);
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

}
