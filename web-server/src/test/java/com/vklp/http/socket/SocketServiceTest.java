package com.vklp.http.socket;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.vklp.http.config.ConfigService;
import com.vklp.ws.services.SocketService;

public class SocketServiceTest {
	
	SocketService service;

	@Before
	public void setUp() throws Exception {
		service = new SocketService();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		service.start();
		assertTrue(service.isStarted());
	}
	
	@Test
	public void testNotStarted() {
		assertFalse(service.isStarted());
	}

}
