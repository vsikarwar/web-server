package com.viks.http.processor;

import java.util.HashMap;
import java.util.Map;

public class ProcessorFactory {
	
	private static final Map<ProcessorType, Processor> processors = new HashMap<ProcessorType, Processor>();
	
	static {
		processors.put(ProcessorType.VERSION_PROCESSOR, new VersionProcessor());
		processors.put(ProcessorType.METHOD_PROCESSOR, new MethodProcessor());
		processors.put(ProcessorType.PATH_PROCESSOR, new PathProcessor());
		processors.put(ProcessorType.FILE_PROCESSOR, new FileProcessor());
		processors.put(ProcessorType.KEEP_ALIVE_PROCESSOR, new KeepAliveProcessor());
	}
	
	public static Processor getProcessor(ProcessorType type) {
		if(!processors.containsKey(type)) {
			throw new IllegalArgumentException("Invalid process type requested : " + type);
		}
		return processors.get(type);
	}

}
