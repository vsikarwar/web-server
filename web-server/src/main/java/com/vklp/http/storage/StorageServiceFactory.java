package com.vklp.http.storage;

import java.util.HashMap;
import java.util.Map;

public class StorageServiceFactory {
	
	private static final StorageServiceFactory INSTANCE = new StorageServiceFactory();
	private final Map<String, StorageService> map = new HashMap<String, StorageService>();
	
	private StorageServiceFactory() {
		init();
	}
	
	public static StorageServiceFactory getInstance() {
		return INSTANCE;
	}
	
	public StorageService getStorageService(StorageServices serviceName) {
		return map.get(serviceName.getName());
	}
	
	public enum StorageServices{
		FILE_STORAGE("file-storage"),
		IN_MEMORY_STORAGE("in-memory-storage");
		
		private String name;
		
		private StorageServices(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}
	
	private void init() {
		map.put(StorageServices.FILE_STORAGE.getName(), new FileStorageService());
	}

}
