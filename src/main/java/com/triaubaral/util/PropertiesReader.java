package com.triaubaral.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

public enum PropertiesReader {

	INSTANCE;
	
	private static Map<String, Properties> loadedProperties = new HashMap<String, Properties>();	
	
	private Properties load(String resourcePath) throws IOException{
		
		boolean isPropertiesLoaded = loadedProperties.containsKey(resourcePath);
		
		if(isPropertiesLoaded){
			return loadedProperties.get(resourcePath);
		}
		
		return createProperties(resourcePath);		
		
	}
	
	private Properties createProperties(String resourcePath) throws IOException{
		
		Properties propFile = new Properties();		
			
		StringBuffer buffer = new StringBuffer();
			
		buffer.append(loadResourceContentFromClasspath(resourcePath));

		StringReader configContentReader = new StringReader(buffer.toString());

		propFile.load(configContentReader);
		
		loadedProperties.put(resourcePath, propFile);
		
		return propFile;
		
	}	
	
	private String loadResourceContentFromClasspath(String resourcePath) throws IOException{
			
		InputStream inputStream = this.getClass().getResourceAsStream(resourcePath);	
		
		return IOUtils.toString(inputStream,"ISO-8859-1");
		
	}
	
	public String readQuietly(Property property, String... params) {
		
		try {
			
			return read(property, params);
			
		} catch (IOException e) {
			
			throw new RuntimeException("Impossible de lire le fichier : "+property.getClasspath());
		}
		
	}
	
	public String read(Property property, String... params) throws IOException{
		
		 String propertyValue = load(property.getClasspath()).getProperty(property.getCode());
		 	
	        if (params != null) {
	
	            for (int i = 0; i < params.length; i++) {
	
	                propertyValue = propertyValue.replace("${" + i + "}", params[i]);
	            }
	           
	        }
	
	     return propertyValue;
	}
}
