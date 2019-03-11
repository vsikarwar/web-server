package com.vklp.http.processors;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.vklp.http.message.HttpHeaders.Headers;
import com.vklp.http.message.request.HttpRequest;
import com.vklp.http.message.request.RequestReader;
import com.vklp.http.message.response.HttpResponse;
import com.vklp.http.processors.GenericProcessor;
import com.vklp.http.processors.Processor;

public class GenericProcessorTest {

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
		
		HttpResponse response = new HttpResponse();
		
		Processor processor = new GenericProcessor();
		processor.process(request, response);
		
		/*** Response
		 
				"HTTP/1.1 200 OK\n" + 
				"Keep-Alive: timeout=3000\n" + 
				"Content-type: text/html\n" + 
				"Server: WS-0.1\n" + 
				"Connection: keep-alive\n" + 
				"Content-Length: 74\n" + 
				"Date: Mon Mar 11 01:38:21 IST 2019\n" + 
				"";
				
		***/
		
		assertEquals("HTTP/1.1", response.getVersion().toString());
		assertEquals("200 OK", response.getStatus().toString());
		assertEquals("text/html", response.getHeaders().get(Headers.CONTENT_TYPE.getName()));
		assertEquals("WS-0.1", response.getHeaders().get(Headers.SERVER.getName()));
		assertEquals("keep-alive", response.getHeaders().get(Headers.CONNECTION.getName()));
		assertEquals("74", response.getHeaders().get(Headers.CONTENT_LENGTH.getName()));
		
		try {
			String content = FileUtils.readFileToString(new File("./content/index.html"));
			assertEquals(content, response.getContent().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(response.toString());
		System.out.println(response.getContent().toString());
	}

}
