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

	private static String etag = "2d5532a1155828c732487d7eda85ec86";
	
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
			
			
			String expectedResponseHeader = "HTTP/1.1 304 Not Modified\n" + 
					"Keep-Alive: timeout=3000,max=1000\n" + 
					"Server: WS-0.1\n" + 
					"Cache-Control: max-age=120\n" + 
					"Etag: 2d5532a1155828c732487d7eda85ec86\n" + 
					"Connection: keep-alive\n" + 
					"Date: (.*)";
			
			
			System.out.println("Response : " + response.toString());
			System.out.println("content : " + new String(response.getContent()));
			
			assertTrue(response.toString().trim().matches(expectedResponseHeader));
			
			assertEquals("HTTP/1.1", response.getVersion().toString());
			assertEquals("304 Not Modified", response.getStatus().toString());
			
			assertEquals(6, response.getHeaderSize());
			assertTrue(response.getHeaderNameSet().contains(Headers.SERVER.getName()));
			assertTrue(response.getHeaderNameSet().contains(Headers.CONNECTION.getName()));
			assertTrue(response.getHeaderNameSet().contains(Headers.CACHE_CONTROL.getName()));
			assertTrue(response.getHeaderNameSet().contains(Headers.DATE.getName()));
			
			assertEquals("WS-0.1", response.getHeader(Headers.SERVER));
			assertEquals("keep-alive", response.getHeader(Headers.CONNECTION));
			assertEquals(etag, response.getHeader(Headers.ETAG));
			
			
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		}
	}

}
