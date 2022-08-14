package com.ubivashka.configuration.context;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import com.ubivashka.configuration.ConfigurationProcessor;
import com.ubivashka.configuration.context.base.DefaultConfigurationFieldFactoryContext;
import com.ubivashka.configuration.holder.ConfigurationSectionHolder;
import com.ubivashka.configuration.util.PrimitiveWrapper;
import com.ubivashka.configuration.util.ReflectionUtil;
import com.ubivashka.function.Castable;

public interface ConfigurationFieldFactoryContext extends Castable<ConfigurationFieldFactoryContext>, ConfigurationContext {
    /**
     * Creates {@link ConfigurationFieldFactoryContext} using default class
     *
     * @param configurationProcessor
     * @param fieldHolder
     * @param field
     * @return default class
     */
    static ConfigurationFieldFactoryContext of(ConfigurationProcessor configurationProcessor,
                                               ConfigurationSectionHolder sectionHolder, Object fieldHolder, Field field) {
        return new DefaultConfigurationFieldFactoryContext(configurationProcessor, sectionHolder, fieldHolder, field);
    }

    /**
     * Returns processor of field
     *
     * @return processor that controls processing of this field
     */
    ConfigurationProcessor processor();

    /**
     * Returns object that holds field
     *
     * @return field holder object
     */
    Object fieldHolder();

    default <T extends Annotation> T getAnnotation(Class<T> annotationType) {
        return field().getAnnotation(annotationType);
    }

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
     * undefined, it will return Object class because of type erasure.
     * May return null if generic not present
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
        return PrimitiveWrapper.tryWrap(field().getType());
    }

    default boolean isValueCollection() {
        return Collection.class.isAssignableFrom(valueType());
    }

    /**
     * Field of resolving class.
     *
     * @return resolving field.
     */
    Field field();

    /**
     * Converts {@link ConfigurationFieldFactoryContext} to
     * {@link ConfigurationFieldResolverContext}. This can be used for resolver
     * factories
     *
     * @return downcasted context
     */
    default ConfigurationFieldResolverContext asResolverContext() {
        return new ConfigurationFieldResolverContext() {

            @Override
            public ConfigurationProcessor processor() {
                return ConfigurationFieldFactoryContext.this.processor();
            }

            @Override
            public String[] path() {
                return ConfigurationFieldFactoryContext.this.path();
            }

            @Override
            public ConfigurationSectionHolder configuration() {
                return ConfigurationFieldFactoryContext.this.configuration();
            }

            @Override
            public Class<?> valueType() {
                return ConfigurationFieldFactoryContext.this.valueType();
            }

            @Override
            public Class<?> getGeneric(int index) {
                return ConfigurationFieldFactoryContext.this.getGeneric(index);
            }

            @Override
            public Object fieldHolder() {
                return ConfigurationFieldFactoryContext.this.fieldHolder();
            }

            @Override
            public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
                return ConfigurationFieldFactoryContext.this.getAnnotation(annotationType);
            }

            @Override
            public boolean hasAnnotation(Class<? extends Annotation> annotationType) {
                return ConfigurationFieldFactoryContext.this.hasAnnotation(annotationType);
            }
        };
    }
}
