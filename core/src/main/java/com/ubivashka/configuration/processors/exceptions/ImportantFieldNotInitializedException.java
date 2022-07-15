package com.ubivashka.configuration.processors.exceptions;

import com.ubivashka.configuration.context.ConfigurationFieldContext;

public class ImportantFieldNotInitializedException extends Exception {

	private final ConfigurationFieldContext fieldContext;

	public ImportantFieldNotInitializedException(ConfigurationFieldContext fieldContext) {
		this.fieldContext = fieldContext;
	}

	public ConfigurationFieldContext getFieldContext() {
		return fieldContext;
	}

}
