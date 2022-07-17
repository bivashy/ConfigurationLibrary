package com.ubivashka.configuration.context.base;

import java.lang.reflect.Field;

import com.ubivashka.configuration.ConfigurationProcessor;
import com.ubivashka.configuration.context.ConfigurationFieldFactoryContext;
import com.ubivashka.configuration.holder.ConfigurationSectionHolder;

public abstract class ConfigurationFieldFactoryContextWrapper implements ConfigurationFieldFactoryContext {
    private final ConfigurationFieldFactoryContext wrapped;

    public ConfigurationFieldFactoryContextWrapper(ConfigurationFieldFactoryContext wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ConfigurationProcessor processor() {
        return wrapped.processor();
    }

    @Override
    public Object fieldHolder() {
        return wrapped.fieldHolder();
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
    public Field field() {
        return wrapped.field();
    }
}