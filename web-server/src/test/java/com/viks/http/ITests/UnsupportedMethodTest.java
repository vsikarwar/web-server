package com.viks.http.ITests;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpVersion;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Test;

import com.viks.http.config.ConfigService;
import com.viks.http.socket.SocketService;

public class UnsupportedMethodTest {
	private SocketService service;
	
	@Before
	public void setUp() {
		try {
			service = new SocketService(ConfigService.getInstance());
			service.start();
		}catch(Exception e) {
			
		}
	}

	@Test
		public void testUnsupportedMethod() throws IOException{
			
			String getUrl = "http://localhost:8000/index.html";
			String USER_AGENT = "Mozilla/5.0";
			
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpDelete httpDelete = new HttpDelete(getUrl);
			httpDelete.addHeader("User-Agent", USER_AGENT);
			httpDelete.setProtocolVersion(HttpVersion.HTTP_1_0);
			
			CloseableHttpResponse httpResponse = httpClient.execute(httpDelete);
			
			assertEquals(405, httpResponse.getStatusLine().getStatusCode());
			
			assertEquals("Method Not Allowed", httpResponse.getStatusLine().getReasonPhrase());
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent()));
	
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = reader.readLine()) != null) {
				response.append(inputLine);
			}
			reader.close();
	
			httpClient.close();
		}
	
	
}
