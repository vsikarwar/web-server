package com.viks.http.redirects;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RedirectServiceTest {
	
	private RedirectService service;

	@Before
	public void setUp() throws Exception {
		service = new RedirectService();
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testReadRedirects() {
		service.readRedirects();
	}

}
