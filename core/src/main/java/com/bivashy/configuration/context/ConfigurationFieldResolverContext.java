package com.bivashy.configuration.context;

import java.lang.annotation.Annotation;

import com.bivashy.configuration.ConfigurationProcessor;
import com.bivashy.function.Castable;

public interface ConfigurationFieldResolverContext extends Castable<ConfigurationFieldResolverContext>, ConfigurationContext {

    /**
     * Returns processor of field
     *
     * @return processor that controls processing of this field
     */
    ConfigurationProcessor processor();

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

    <T extends Annotation> T getAnnotation(Class<T> annotationType);

    /**
     * Checks if field has specific annotation
     *
     * @param annotationType - Annotation that need to check
     * @return result of annotation check
     */
    boolean hasAnnotation(Class<? extends Annotation> annotationType);
}
