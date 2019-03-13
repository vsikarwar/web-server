package com.vklp.http.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

import com.vklp.http.config.Config;
import com.vklp.http.config.Config.Configs;

public class FileStorageService implements StorageService{
	
	private static final String DOC_ROOT = Config.getInstance().getStr(Configs.DOC_ROOT.config());

	public String getContentAsString(String key) throws FileNotFoundException, IOException {
		File file = new File(DOC_ROOT, key);
		if(!file.exists()) {
			throw new FileNotFoundException(key);
		}
		
		String content = FileUtils.readFileToString(file);
		return content;
	}
	
	public byte[] getContentAsByte(String key) throws FileNotFoundException, IOException {
		File file = new File(DOC_ROOT, key);
		if(!file.exists()) {
			throw new FileNotFoundException(key);
		}
		byte[] content = FileUtils.readFileToByteArray(file);
		return content;
	}
	
	public String getContentType(String key) throws FileNotFoundException, IOException {
		File file = new File(DOC_ROOT, key);
		String type="";
		if(file.exists()) {
			type = Files.probeContentType(file.toPath());
		}else {
			throw new FileNotFoundException(key);
		}
		return type;
	}
	
	public Long getContentLength(String key) throws FileNotFoundException {
		File file = new File(DOC_ROOT, key);
		if(file.exists()) {
			return file.length();
		}else {
			throw new FileNotFoundException(key);
		}
	}
	
	public String getChecksum(String key) throws FileNotFoundException, IOException{
		File file = new File(DOC_ROOT, key);
		if(!file.exists()) {
			throw new FileNotFoundException(key);
		}
		
		InputStream is = Files.newInputStream(file.toPath());
		String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(is);
		
		return md5;
		
	}
	
	
	
}
