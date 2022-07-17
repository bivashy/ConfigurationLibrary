package com.ubivashka.configuration.context.base;

import java.lang.annotation.Annotation;

import com.ubivashka.configuration.ConfigurationProcessor;
import com.ubivashka.configuration.context.ConfigurationFieldResolverContext;
import com.ubivashka.configuration.holder.ConfigurationSectionHolder;

public abstract class ConfigurationFieldResolverContextWrapper implements ConfigurationFieldResolverContext {

    public ConfigurationFieldResolverContext wrapped;

    public ConfigurationFieldResolverContextWrapper(ConfigurationFieldResolverContext wrapper) {
        this.wrapped = wrapper;
    }

    @Override
    public ConfigurationProcessor processor() {
        return wrapped.processor();
    }

    @Override
    public ConfigurationSectionHolder configuration() {
        return wrapped.configuration();
    }

    @Override
    public String[] path() {
        return wrapped.path();
    }

    @Override
    public Class<?> valueType() {
        return wrapped.valueType();
    }

    @Override
    public Class<?> getGeneric(int index) {
        return wrapped.getGeneric(index);
    }

    @Override
    public Object fieldHolder() {
        return wrapped.fieldHolder();
    }

    @Override
    public Annotation getAnnotation(Class<? extends Annotation> annotationType) {
        return wrapped.getAnnotation(annotationType);
    }

    @Override
    public boolean hasAnnotation(Class<? extends Annotation> annotationType) {
        return wrapped.hasAnnotation(annotationType);
    }

}
