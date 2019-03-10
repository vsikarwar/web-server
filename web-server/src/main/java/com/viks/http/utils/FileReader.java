package com.viks.http.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileReader {
	
	public static boolean isFileExists(String filePath) {
		File file = new File(filePath);
		if(file.exists()) {
			return true;
		}else {
			return false;
		}
	}
	
	public static byte[] readFile(String filePath) {
		File file = new File(filePath);
		int fileLength = (int)file.length();
		return readFileData(file, fileLength);
	}
	
	private static byte[] readFileData(File file, int fileLength) {
		FileInputStream fileIn = null;
		byte[] fileData = new byte[fileLength];
		
		try {
			try {
				fileIn = new FileInputStream(file);
				fileIn.read(fileData);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} finally {
			if (fileIn != null)
				try {
					fileIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		return fileData;
	}

}
