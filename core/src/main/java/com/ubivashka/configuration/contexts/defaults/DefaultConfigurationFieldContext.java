package com.ubivashka.configuration.contexts.defaults;

import java.lang.reflect.Field;

import com.ubivashka.configuration.ConfigurationProcessor;
import com.ubivashka.configuration.annotations.ConfigField;
import com.ubivashka.configuration.contexts.ConfigurationFieldContext;
import com.ubivashka.configuration.holders.ConfigurationSectionHolder;

public class DefaultConfigurationFieldContext implements ConfigurationFieldContext {

	private final ConfigurationProcessor configurationProcessor;
	private final ConfigurationSectionHolder sectionHolder;
	private final Object fieldHolder;
	private final Field field;

	private final String pathValue;

	public DefaultConfigurationFieldContext(ConfigurationProcessor configurationProcessor,
			ConfigurationSectionHolder sectionHolder, Object fieldHolder, Field field) {
		this.configurationProcessor = configurationProcessor;
		this.sectionHolder = sectionHolder;
		this.fieldHolder = fieldHolder;
		this.field = field;

		this.pathValue = field.getAnnotation(ConfigField.class).value().isEmpty() ? field.getName()
				: field.getAnnotation(ConfigField.class).value();
	}

	@Override
	public ConfigurationProcessor processor() {
		return configurationProcessor;
	}

	@Override
	public ConfigurationSectionHolder configuration() {
		return sectionHolder;
	}

	@Override
	public Object fieldHolder() {
		return fieldHolder;
	}

	@Override
	public Field field() {
		return field;
	}

	@Override
	public String path() {
		return pathValue;
	}

}
