package com.ubivashka.configuration.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ubivashka.configuration.ConfigurationProcessor;
import com.ubivashka.configuration.resolver.field.ConfigurationFieldResolver;

/**
 * <pre>
 * If field has this annotation. Values of list will not be affected by
 * {@link ConfigurationProcessor} converters.
 *	Works only for objects that uses default {@link ConfigurationFieldResolver}
 * </pre>
 */
@Deprecated
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConverterIgnore {
}
