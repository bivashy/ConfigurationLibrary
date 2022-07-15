package com.ubivashka.configuration.processors.post.context;

import java.lang.reflect.Field;

import com.ubivashka.configuration.context.ConfigurationFieldContext;

public interface PostProcessorContext {
	Object getResolvedObject();
	
	Field getField();

	ConfigurationFieldContext getConfigurationFieldContext();
}
