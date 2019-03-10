package com.viks.http.request;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UriHandlerTest {
	
	UriHandler handler;

	@Before
	public void setUp() throws Exception {
		handler = new UriHandler();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		String path = "/abc/def.html";
		Request req = new Request();
		handler.handle(req, path);
		
		assertEquals(path, req.getUri().getPath());
	}
	
	@Test
	public void testParams() {
		String path = "/abc/def?a=123&b=234";
		Request req = new Request();
		handler.handle(req, path);
		
		assertEquals("/abc/def", req.getUri().getPath());
		assertEquals(2, req.getUri().getQueryParams().size());
		
		assertEquals("123", req.getUri().getQueryParams().get("a"));
		assertEquals("234", req.getUri().getQueryParams().get("b"));
	}

}
