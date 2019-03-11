package com.viks.http.server;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.viks.http.config.ConfigService.Configs;

public class ServerConfigsTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		assertEquals("port", Configs.PORT.config());
		assertEquals("default.threads", Configs.DEFAULT_THREADS.config());
		assertEquals("max.threads", Configs.MAX_THREADS.config());
		assertEquals("socket.buff.size", Configs.SOCKET_BUFFER_SIZE.config());
		assertEquals("server.name", Configs.SERVER_NAME.config());
		
	}

}
