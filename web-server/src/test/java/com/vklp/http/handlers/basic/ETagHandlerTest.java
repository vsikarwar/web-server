package com.vklp.http.handlers.basic;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.SocketTimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.vklp.http.message.HttpHeaders.Headers;
import com.vklp.http.message.request.HttpRequest;
import com.vklp.http.message.request.RequestReader;
import com.vklp.http.message.response.HttpResponse;

public class ETagHandlerTest {
	
	private static String httpRequest = "GET /index.html HTTP/1.1\n" + 
			"Host: localhost:8080\n" + 
			"Connection: keep-alive\n" + 
			"Cache-Control: max-age=0\n" + 
			"If-None-Match: 2d5532a1155828c732487d7eda85ec86\n" + 
			"Upgrade-Insecure-Requests: 1\n" + 
			"User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36\n" + 
			"Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\n" + 
			"Accept-Encoding: gzip, deflate, br\n" + 
			"Accept-Language: en-US,en;q=0.9\n" + 
			"";

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		InputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes());
		RequestReader requestReader = new RequestReader(inputStream);
		try {
			HttpRequest request = requestReader.readRequest();
			HttpResponse response = new HttpResponse();
			
			ETagHandler handler = new ETagHandler();
			
			System.out.println("Key set : " + request.getHeaderNameSet());
			
			handler.handle(request, response);
			
			/*
			String expectedResponseHeader = "HTTP/0.0 500 Internal Server Error\n" + 
					"Server: WS-0.1\n" + 
					"Connection: close\n" + 
					"Content-Length: 74\n" + 
					"Date: Tue Mar 12 18:59:02 IST 2019\n" + 
					"Content-Type: text/html\n";
			*/
			
			System.out.println("Response : " + response.toString());
			System.out.println("content : " + new String(response.getContent()));
			
			String expectedContent = "<html><title>500</title><body><h1>Internal Server Error</h1></body></html>";
			
			
			assertEquals(expectedContent, new String(response.getContent()));
			
			assertEquals("HTTP/1.1", response.getVersion().toString());
			assertEquals("500 Internal Server Error", response.getStatus().toString());
			
			assertEquals(5, response.getHeaderSize());
			assertTrue(response.getHeaderNameSet().contains(Headers.SERVER.getName()));
			assertTrue(response.getHeaderNameSet().contains(Headers.CONNECTION.getName()));
			assertTrue(response.getHeaderNameSet().contains(Headers.CONTENT_LENGTH.getName()));
			assertTrue(response.getHeaderNameSet().contains(Headers.DATE.getName()));
			assertTrue(response.getHeaderNameSet().contains(Headers.CONTENT_TYPE.getName()));
			
			assertEquals("WS-0.1", response.getHeader(Headers.SERVER));
			assertEquals("close", response.getHeader(Headers.CONNECTION));
			assertEquals("74", response.getHeader(Headers.CONTENT_LENGTH));
			assertEquals("text/html", response.getHeader(Headers.CONTENT_TYPE));
			
			
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		}
	}

}
