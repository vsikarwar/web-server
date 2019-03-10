package com.viks.http.socket;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.viks.http.server.ServerConfig;

public class SocketServiceTest {
	
	SocketService service;

	@Before
	public void setUp() throws Exception {
		service = new SocketService(ServerConfig.getInstance());
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
