package com.vklp.http.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

public class Utils {
	
	public static byte[] createChecksum(String key) throws Exception {
	       InputStream fis =  new FileInputStream(key);

	       byte[] buffer = new byte[1024];
	       MessageDigest complete = MessageDigest.getInstance("MD5");
	       int numRead;

	       do {
	           numRead = fis.read(buffer);
	           if (numRead > 0) {
	               complete.update(buffer, 0, numRead);
	           }
	       } while (numRead != -1);

	       fis.close();
	       return complete.digest();
	   }

}
