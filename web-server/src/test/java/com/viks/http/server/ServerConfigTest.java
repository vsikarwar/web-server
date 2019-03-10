package com.viks.http.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.viks.http.server.ServerConfig.ServerConfigs;

public class ServerConfigTest {
	
	ServerConfig config;

	@Before
	public void setUp() throws Exception {
		config = ServerConfig.getInstance();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGet() {
		Integer port = config.getInt(ServerConfigs.PORT.config());
		assertNotNull(port);
		
		String serverName = config.getStr(ServerConfigs.SERVER_NAME.config());
		assertEquals(serverName, "WS-0.1");
	}

}
