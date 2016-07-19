package com.triaubaral.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;

public class PropertiesReaderTest {

	private Property newMockedProperty(String propertyKey, String classpath) {

		Property property = Mockito.mock(Property.class);
		Mockito.when(property.getClasspath()).thenReturn(classpath);
		Mockito.when(property.getCode()).thenReturn(propertyKey);

		return property;
	}

	@Test
	public void shouldFindValueForProperty() {

		List<Property> properties = new ArrayList<Property>();
		
		final String propertiesFilePath = "/env-dir-path.properties";

		properties.add(newMockedProperty("working.dir",propertiesFilePath));
		properties.add(newMockedProperty("archive.dir",propertiesFilePath));
		properties.add(newMockedProperty("input.dir", propertiesFilePath));

		for (Property property : properties) {

			Assertions
					.assertThat(PropertiesReader.INSTANCE.readQuietly(property))
					.isEqualTo("/path/to/"+property.getCode().replace(".", "/"));

		}

	}

	@Test(expected=RuntimeException.class)
	public void shouldThrowRuntimeExceptionWhenClasspathIsEmpty() {

		Property property = newMockedProperty("dummycode","");		
		PropertiesReader.INSTANCE.readQuietly(property);
	}
	
	@Test(expected=RuntimeException.class)
	public void shouldThrowRuntimeExceptionWhenClasspathIsNull() {

		Property property = newMockedProperty("dummycode", null);		
		PropertiesReader.INSTANCE.readQuietly(property);

	}
	
	@Test(expected=NullPointerException.class)
	public void shouldThrowRuntimeException() throws IOException{
		
		// "/fake-path.properties" does not exists so it is a null pointer exception
		
		Property property = newMockedProperty("dummycode","/fake-path.properties");		
		PropertiesReader.INSTANCE.read(property);		
		
	}

}
