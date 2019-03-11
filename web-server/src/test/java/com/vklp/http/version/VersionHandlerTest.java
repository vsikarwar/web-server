package com.vklp.http.version;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.vklp.http.message.HttpVersion;
import com.vklp.http.version.VersionHandler;

public class VersionHandlerTest {
	
	VersionHandler handler;

	@Before
	public void setUp() throws Exception {
		handler = new VersionHandler();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testParseVersionString() {
		String version = "HTTP/1.0";
		HttpVersion httpVersion = handler.parseVersionString(version);
		
		assertEquals(version, httpVersion.toString());
		
		assertEquals(1, httpVersion.getMajorVersion());
		
		assertEquals(0, httpVersion.getMinorVersion());
		
	}
	
	@Test
	public void testIsVersionValid() {
		String version = "HTTP/1.1";
		HttpVersion httpVersion = handler.parseVersionString(version);
		assertTrue(handler.isVersionValid(httpVersion));
		
		
		version = "HTTP/1.0";
		httpVersion = handler.parseVersionString(version);
		assertTrue(handler.isVersionValid(httpVersion));

		version = "HTTP/0.1";
		httpVersion = handler.parseVersionString(version);
		assertFalse(handler.isVersionValid(httpVersion));

	}

}
