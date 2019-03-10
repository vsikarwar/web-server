package com.viks.http.server;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.viks.http.server.ServerConfig.ServerConfigs;

public class ServerConfigsTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		assertEquals("port", ServerConfigs.PORT.config());
		assertEquals("default.threads", ServerConfigs.DEFAULT_THREADS.config());
		assertEquals("max.threads", ServerConfigs.MAX_THREADS.config());
		assertEquals("socket.buff.size", ServerConfigs.SOCKET_BUFFER_SIZE.config());
		assertEquals("server.name", ServerConfigs.SERVER_NAME.config());
		
	}

}
