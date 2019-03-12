package com.vklp.http.storage;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface StorageService {
	
	String getContentAsString(String key) throws FileNotFoundException, IOException;
	
	byte[] getContentAsByte(String key) throws FileNotFoundException, IOException;
	
	String getContentType(String key) throws FileNotFoundException, IOException;
	
	Long getContentLength(String key) throws FileNotFoundException, IOException;
	
	String getChecksum(String key) throws FileNotFoundException, IOException;
	
}
