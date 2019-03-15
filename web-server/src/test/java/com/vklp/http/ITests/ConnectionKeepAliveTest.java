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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.vklp.http.config.Config;
import com.vklp.http.handlers.RequestHandler;
import com.vklp.http.services.SocketService;
import com.vklp.http.utils.TestUtils;

public class ConnectionKeepAliveTest {
	
	private static Config config = Config.getInstance();
	private static RequestHandler handler = new RequestHandler();
	private static SocketService service;
	
	private Socket socket;
	private InputStream input;
	private OutputStream output;
	private BufferedReader reader;
	
	@BeforeClass
	public static void setUp() {
			service = new SocketService(config, handler);
			service.start();
	}
	
	@AfterClass
	public static void tearDown() {
		service.stop();
	}
	
	@Before
	public void setUpTestData() {
		this.socket = getSocket();
		this.input = getInputStream(socket);
		this.output = getOutputStream(socket);
		this.reader = new BufferedReader(new InputStreamReader(input));
	}
	
	@After
	public void tearDownTestData() {
		try {
			this.reader.close();
			this.output.close();
			this.input.close();
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	
	@Test
	@Ignore
	public void testHTTP1_0() {
		String request ="GET /index.html HTTP/1.0\n" +
				"Host: localhost:8000\n" +
				"User-Agent: Simple Http Client\n" +
				"Accept: text/html\n" + 
				"Accept-Language: en-US\n";
		
		String expectedResponse = "HTTP/1.0 200 OK\n" + 
	    		"Server: WS-0.1\n" + 
	    		"Connection: close\n" + 
	    		"Content-Length: 74\n" + 
	    		"Date: (.*)\n" + 
	    		"Content-Type: text/html\n" + 
	    		"\n" + 
	    		"<html><title>viks</title><body><h1> Hi! FROM WEB SERVER</h1></body></html>";
		
        int requestProcessed = 0;
        
        for(int i = 1; i< 3; i++) {
        	TestUtils.write(request, output);
        	String response = null;
			try {
				response = TestUtils.getResponse(reader);
				if(response == null || response.trim().isEmpty() || response.trim().equals("null")) {
					break;
				}
			} catch (IOException e) {
				break;
			}
    		assertTrue(response.matches(expectedResponse));
    		requestProcessed++;
        }
        assertEquals(requestProcessed, 1);
	}
	
	@Test
	@Ignore
	public void testHTTP1_0ConnectionKeepAlive() {
		String request ="GET /index.html HTTP/1.0\n" +
				"Host: localhost:8000\n" +
				"User-Agent: Simple Http Client\n" +
				"Accept: text/html\n" + 
				"Accept-Language: en-US\n"
				+ "Connection: keep-alive\n";
		
		String expectedResponse = "HTTP/1.0 200 OK\n" + 
				"Keep-Alive: timeout=3000,max=1000\n" +
	    		"Server: WS-0.1\n" + 
	    		"Connection: keep-alive\n" + 
	    		"Content-Length: 74\n" + 
	    		"Date: (.*)\n" + 
	    		"Content-Type: text/html\n" + 
	    		"\n" + 
	    		"<html><title>viks</title><body><h1> Hi! FROM WEB SERVER</h1></body></html>";
		
        int requestProcessed = 0;
        
        for(int i = 1; i< 3; i++) {
        	TestUtils.write(request, output);
        	String response = null;
			try {
				response = TestUtils.getResponse(reader);
				if(response == null || response.trim().isEmpty() || response.trim().equals("null")) {
					break;
				}
			} catch (IOException e) {
				break;
			}
    		assertTrue(response.matches(expectedResponse));
    		requestProcessed++;
        }
        assertEquals(requestProcessed, 2);
	}
	
	@Test
	@Ignore
	public void testHTTP1_1() {
		String request ="GET /index.html HTTP/1.1\n" +
				"Host: localhost:8000\n" +
				"User-Agent: Simple Http Client\n" +
				"Accept: text/html\n" + 
				"Accept-Language: en-US\n";
		
		String expectedResponse = "HTTP/1.1 200 OK\n" + 
	    		"Keep-Alive: timeout=3000,max=1000\n" + 
	    		"Server: WS-0.1\n" + 
	    		"Connection: keep-alive\n" + 
	    		"Content-Length: 74\n" + 
	    		"Date: (.*)\n" + 
	    		"Content-Type: text/html\n" + 
	    		"\n" + 
	    		"<html><title>viks</title><body><h1> Hi! FROM WEB SERVER</h1></body></html>";
		
        int requestProcessed = 0;
        
        for(int i = 1; i< 10; i++) {
        	TestUtils.write(request, output);
        	String response = null;
			try {
				response = TestUtils.getResponse(reader);
				if(response == null || response.trim().isEmpty() || response.trim().equals("null")) {
					break;
				}
			} catch (IOException e) {
				break;
			}
    		assertTrue(response.matches(expectedResponse));
    		requestProcessed++;
        }
        assertEquals(requestProcessed, 9);
	}
	
	@Test
	@Ignore
	public void testHTTP1_1KeepAlive() {
		String request ="GET /index.html HTTP/1.1\n" +
				"Host: localhost:8000\n" +
				"User-Agent: Simple Http Client\n" +
				"Accept: text/html\n" + 
				"Accept-Language: en-US\n" +
				"Connection: keep-alive\n";
		
		String expectedResponse = "HTTP/1.1 200 OK\n" + 
	    		"Keep-Alive: timeout=3000,max=1000\n" + 
	    		"Server: WS-0.1\n" + 
	    		"Connection: keep-alive\n" + 
	    		"Content-Length: 74\n" + 
	    		"Date: (.*)\n" + 
	    		"Content-Type: text/html\n" + 
	    		"\n" + 
	    		"<html><title>viks</title><body><h1> Hi! FROM WEB SERVER</h1></body></html>";
		
        int requestProcessed = 0;
        
        for(int i = 1; i< 10; i++) {
        	TestUtils.write(request, output);
        	String response = null;
			try {
				response = TestUtils.getResponse(reader);
				if(response == null || response.trim().isEmpty() || response.trim().equals("null")) {
					break;
				}
			} catch (IOException e) {
				break;
			}
    		assertTrue(response.matches(expectedResponse));
    		requestProcessed++;
        }
        assertEquals(requestProcessed, 9);
	}
	
	@Test
	@Ignore
	public void testHTTP1_1_Connection_Close() {
		String request ="GET /index.html HTTP/1.1\n" +
				"Host: localhost:8000\n" +
				"User-Agent: Simple Http Client\n" +
				"Accept: text/html\n" + 
				"Accept-Language: en-US\n" +
				"Connection: close\n";
		
		String expectedResponse = "HTTP/1.1 200 OK\n" + 
				"Server: WS-0.1\n" + 
				"Connection: close\n" + 
				"Content-Length: 74\n" + 
				"Date: (.*)\n" + 
				"Content-Type: text/html\n" + 
				"\n" + 
				"<html><title>viks</title><body><h1> Hi! FROM WEB SERVER</h1></body></html>";
		
        int requestProcessed = 0;
        for(int i = 1; i< 3; i++) {
        	TestUtils.write(request, output);
        	String response=null;
			try {
				response = TestUtils.getResponse(reader);
				if(response == null || response.trim().isEmpty() || response.trim().equals("null")) {
					break;
				}
			} catch (IOException e) {
				break;
			}
			
    		assertTrue(response.matches(expectedResponse));
    		requestProcessed++;
        }
        assertEquals(requestProcessed, 1);
	}

	

}
