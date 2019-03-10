package com.viks.http.version;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HttpVersionTest {
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		HttpVersion version = new HttpVersion(0, 1);
		assertEquals("HTTP/1.0", version.toString());
		
		version = new HttpVersion(1, 1);
		assertEquals("HTTP/1.1", version.toString());
		
		version = new HttpVersion(1, 0);
		assertEquals("HTTP/0.1", version.toString());
	}

}
