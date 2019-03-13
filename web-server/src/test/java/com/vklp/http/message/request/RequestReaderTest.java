package com.vklp.http.message.request;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.SocketTimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.vklp.http.message.request.HttpRequest;
import com.vklp.http.message.request.RequestReader;

public class RequestReaderTest {
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGet() throws SocketTimeoutException {
		String httpRequest = "GET /index.html HTTP/1.1\n" + 
				"Host: localhost:8080\n" + 
				"Connection: keep-alive\n" + 
				"Cache-Control: max-age=0\n" + 
				"Upgrade-Insecure-Requests: 1\n" + 
				"User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36\n" + 
				"Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\n" + 
				"Accept-Encoding: gzip, deflate, br\n" + 
				"Accept-Language: en-US,en;q=0.9\n" + 
				"";
		InputStream in = new ByteArrayInputStream(httpRequest.getBytes());
		RequestReader reader = new RequestReader(in);
		HttpRequest request = reader.readRequest();
		assertEquals(request.getMethod(), "GET");
		assertEquals(request.getUri(), "/index.html");
		assertEquals(request.getVersion().toString(), "HTTP/1.1");
		
		String expectedHeaders = "Cache-Control: max-age=0\n" + 
				"Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\n" + 
				"Upgrade-Insecure-Requests: 1\n" + 
				"Connection: keep-alive\n" + 
				"User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36\n" + 
				"Host: localhost8080\n" + 
				"Accept-Encoding: gzip, deflate, br\n" + 
				"Accept-Language: en-US,en;q=0.9";
		
		assertEquals(request.getHeaders().toString(), expectedHeaders);
		
	}
	
	@Test
	public void testPOST() throws SocketTimeoutException {
		String httpRequest = "POST /index.html HTTP/1.0\n" + 
				"From: viks@adobe.com\n" + 
				"User-Agent: Webserver/1.0\n" + 
				"Content-Type: application/x-www-form-urlencoded\n" + 
				"Content-Length: 32\n" + 
				"\n" + 
				"home=Cosby&favorite+flavor=flies";
		
		InputStream in = new ByteArrayInputStream(httpRequest.getBytes());
		RequestReader reader = new RequestReader(in);
		HttpRequest request = reader.readRequest();
		assertEquals(request.getMethod(), "POST");
		assertEquals(request.getUri(), "/index.html");
		assertEquals(request.getVersion().toString(), "HTTP/1.0");
		
		String expectedHeaders = "User-Agent: Webserver/1.0\n" + 
				"From: viks@adobe.com\n" + 
				"Content-Length: 32\n" + 
				"Content-Type: application/x-www-form-urlencoded";
		System.out.println(request.getHeaders().toString());
		assertEquals(request.getHeaders().toString(), expectedHeaders);
		
		String expectedPayload = "home=Cosby&favorite+flavor=flies";
		assertEquals(request.getContent().toString(), expectedPayload);
		
		
		assertEquals(request.getContent().length, 32);
		
		
	}

}
