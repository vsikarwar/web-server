package com.vklp.http.ITests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.vklp.http.config.Config;
import com.vklp.http.handlers.RequestHandler;
import com.vklp.http.utils.TestUtils;
import com.vklp.ws.services.SocketService;

public class OpenConnectionTest {
	

	
	private static Config config = Config.getInstance();
	private static RequestHandler handler = new RequestHandler();
	private static SocketService service;
	
	@BeforeClass
	public static void setUp() {
			service = new SocketService(config, handler);
			service.start();
	}
	
	@AfterClass
	public static void tearDown() {
		System.out.println("INSIDE TEAR DOWN!!!");
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
	public void openConnections() {
		final String request ="GET /index.html HTTP/1.1\n" +
				"Host: localhost:8000\n" +
				"User-Agent: Simple Http Client\n" +
				"Accept: text/html\n" + 
				"Accept-Language: en-US\n" +
				"Connection: close\n";
		
		final String expectedResponse = "HTTP/1.1 200 OK\n" + 
	    		"Server: WS-0.1\n" + 
	    		"Connection: close\n" + 
	    		"Content-Length: 74\n" + 
	    		"Date: (.*)\n" + 
	    		"Content-Type: text/html\n" + 
	    		"\n" + 
	    		"<html><title>viks</title><body><h1> Hi! FROM WEB SERVER</h1></body></html>";
		
		ExecutorService executor = Executors.newFixedThreadPool(20);
		
		for(int i = 0; i<20; i++) {
			/*Thread thread = new Thread(new Runnable() {
				public void run() {
					process(request, expectedResponse, i);
				}
			}, "Thread-" + i);*/
			
			WorkerThread thread = new WorkerThread(i, request, expectedResponse);
			
			executor.execute(thread);
		}
		
		
		executor.shutdown();
		
		
		while (!executor.isTerminated()) {
        }
        System.out.println("Finished all threads");
	}
	
	private class WorkerThread implements Runnable{
		String name;
		String request;
		String response;
		WorkerThread(int name, String request, String response){
			this.name = "Worker-" + name;
			this.request = request;
			this.response = response;
		}

		public void run() {
			process(request, response);
			
		}
		
		private void process(String request, String expectedResponse) {
			Socket socket = getSocket();
			InputStream input = getInputStream(socket);
			OutputStream output = getOutputStream(socket);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			
			int requestProcessed = 0;
	        
        	TestUtils.write(request, output);
        	String response = null;
			try {
				response = TestUtils.getResponse(reader);
			} catch (IOException e) {
				//break;
			}
			if(response == null || response.trim().isEmpty() || response.trim().equals("null")) {
				//break;
			}else {
				//assertTrue(response.matches(expectedResponse));
	    		requestProcessed++;
			}
    		
	       // assertEquals(requestProcessed, 1);
	        
	        try {
	        	reader.close();
	            output.close();
	            input.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        System.out.println("**DONE : " + this.name);
	        counter.getAndIncrement();
		}
		
	}
	
	private AtomicInteger counter = new AtomicInteger();
	
	
	



}
