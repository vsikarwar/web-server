package com.viks.http.ITests;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Test;

import com.viks.http.server.ServerConfig;
import com.viks.http.socket.SocketService;

public class KeepAliveTest {
	
	private SocketService service;
	
	@Before
	public void setUp() {
		try {
			service = new SocketService(ServerConfig.getInstance());
			service.start();
		}catch(Exception e) {
			
		}
	}
	
	@Test
	public void testKeepAlive() throws IOException{
		
		String getUrl = "http://localhost:8000/index.html";
		String USER_AGENT = "Mozilla/5.0";
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(getUrl);
		httpGet.addHeader("User-Agent", USER_AGENT);
		httpGet.setProtocolVersion(HttpVersion.HTTP_1_0);
		
		StringBuffer response = request(httpClient, httpGet);
		assertEquals(readFile("./content/index.html"), response.toString());
		
		response = request(httpClient, httpGet);
		assertEquals(readFile("./content/index.html"), response.toString());
		
		httpClient.close();
	}

	private StringBuffer request(CloseableHttpClient httpClient, HttpGet httpGet)
			throws IOException, ClientProtocolException {
		CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

		assertEquals(200, httpResponse.getStatusLine().getStatusCode());
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				httpResponse.getEntity().getContent()));

		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = reader.readLine()) != null) {
			response.append(inputLine);
		}
		reader.close();
		return response;
	}
	
	private static String readFile(String fileName) {
		String content=null;
		
		try {
			content = FileUtils.readFileToString(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

}
