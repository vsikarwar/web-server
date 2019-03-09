package com.viks.http.processor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.viks.http.request.Request;
import com.viks.http.response.Response;

public class FileProcessor implements Processor{

	public ProcessorType process(Request request, Response response) {
		System.out.println("File processor");
		File file = new File("./content", request.getPath());
		int fileLength = (int)file.length();
		byte[] fileData = readFileData(file, fileLength);
		response.setBody(fileData);
		
		return ProcessorType.END_OF_CHAIN;
	}
	
	private byte[] readFileData(File file, int fileLength) {
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