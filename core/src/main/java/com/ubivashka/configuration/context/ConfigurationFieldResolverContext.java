package com.ubivashka.configuration.context;

import java.lang.annotation.Annotation;

import com.ubivashka.configuration.ConfigurationProcessor;
import com.ubivashka.configuration.annotation.ConfigField;
import com.ubivashka.configuration.holder.ConfigurationSectionHolder;
import com.ubivashka.function.CastableInterface;

public interface ConfigurationFieldResolverContext extends CastableInterface<ConfigurationFieldResolverContext> {

	/**
	 * Returns processor of field
	 * 
	 * @return processor that controls processing of this field
	 */
	ConfigurationProcessor processor();

	/**
	 * @return configuration. Current configuration holder
	 */
	ConfigurationSectionHolder configuration();

	/**
	 * Returns path that assigned with {@link ConfigField} annotation
	 * 
	 * @return path in configuration
	 */
	String path();

	/**
	 * Returns unwrapped field`s class. It only unwraps primitives, or list generic
	 * 
	 * @return value that need to be resolved
	 */
	Class<?> valueType();

	Class<?> getGeneric(int index);

	/**
	 * Returns object that holds field
	 * 
	 * @return field holder object
	 */
	Object fieldHolder();

	/**
	 * Checks if field has specific annotation
	 * 
	 * @param annotationType - Annotation that need to check
	 * @return result of annotation check
	 */
	boolean hasAnnotation(Class<? extends Annotation> annotationType);
}
