package com.ubivashka.configuration.contexts;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import com.ubivashka.configuration.ConfigurationProcessor;
import com.ubivashka.configuration.annotations.ConfigField;
import com.ubivashka.configuration.contexts.defaults.DefaultConfigurationFieldContext;
import com.ubivashka.configuration.holders.ConfigurationSectionHolder;
import com.ubivashka.configuration.util.PrimitiveWrapper;
import com.ubivashka.configuration.util.ReflectionUtil;
import com.ubivashka.function.CastableInterface;

public interface ConfigurationFieldContext extends CastableInterface<ConfigurationFieldContext> {
	/**
	 * Creates {@link ConfigurationFieldContext} using default class
	 * 
	 * @param configurationProcessor
	 * @param fieldHolder
	 * @param field
	 * @return default class
	 */
	static ConfigurationFieldContext of(ConfigurationProcessor configurationProcessor,
			ConfigurationSectionHolder sectionHolder, Object fieldHolder, Field field) {
		return new DefaultConfigurationFieldContext(configurationProcessor, sectionHolder, fieldHolder, field);
	}

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
	default boolean hasAnnotation(Class<? extends Annotation> annotationType) {
		return field().isAnnotationPresent(annotationType);
	}

	/**
	 * Returns generic of field if it exists, else returns null If generic is
	 * undefined, it will return Object class because of type erasure
	 * 
	 * @param index Index of generic, starts from 0
	 * @return Generic type
	 */
	default Class<?> getGeneric(int index) {
		if (field().getGenericType() instanceof ParameterizedType)
			return ReflectionUtil.getParameterizedTypes((ParameterizedType) field().getGenericType()).get(index);
		return null;
	}

	/**
	 * Returns unwrapped field`s class. It only unwraps primitives.
	 * 
	 * @return field type
	 */
	default Class<?> valueType() {
		return PrimitiveWrapper.unwrapClass(field().getType());
	}

	default boolean isValueCollection() {
		return Collection.class.isAssignableFrom(field().getType());
	}

	/**
	 * Field of resolving class.
	 * 
	 * @return resolving field.
	 */
	Field field();

	/**
	 * Converts {@link ConfigurationFieldContext} to
	 * {@link ConfigurationFieldResolverContext}. This can be used for resolver
	 * factories
	 * 
	 * @return downcasted context
	 */
	default ConfigurationFieldResolverContext asResolverContext() {
		return new ConfigurationFieldResolverContext() {

			@Override
			public ConfigurationProcessor processor() {
				return ConfigurationFieldContext.this.processor();
			}

			@Override
			public String path() {
				return ConfigurationFieldContext.this.path();
			}

			@Override
			public ConfigurationSectionHolder configuration() {
				return ConfigurationFieldContext.this.configuration();
			}

			@Override
			public Class<?> valueType() {
				return ConfigurationFieldContext.this.valueType();
			}

			@Override
			public Class<?> getGeneric(int index) {
				return ConfigurationFieldContext.this.getGeneric(index);
			}

			@Override
			public Object fieldHolder() {
				return ConfigurationFieldContext.this.fieldHolder();
			}

			@Override
			public boolean hasAnnotation(Class<? extends Annotation> annotationType) {
				return ConfigurationFieldContext.this.hasAnnotation(annotationType);
			}

		};
	}
}
