package com.ubivashka.configuration.context.base;

import java.lang.reflect.Field;

import com.ubivashka.configuration.ConfigurationProcessor;
import com.ubivashka.configuration.annotation.ConfigField;
import com.ubivashka.configuration.context.ConfigurationFieldFactoryContext;
import com.ubivashka.configuration.holder.ConfigurationSectionHolder;

public class DefaultConfigurationFieldFactoryContext implements ConfigurationFieldFactoryContext {

    private final ConfigurationProcessor configurationProcessor;
    private final ConfigurationSectionHolder sectionHolder;
    private final Object fieldHolder;
    private final Field field;

    private final String[] pathValue;

    public DefaultConfigurationFieldFactoryContext(ConfigurationProcessor configurationProcessor,
                                                   ConfigurationSectionHolder sectionHolder, Object fieldHolder, Field field) {
        this.configurationProcessor = configurationProcessor;
        this.sectionHolder = sectionHolder;
        this.fieldHolder = fieldHolder;
        this.field = field;
        this.pathValue = field.getAnnotation(ConfigField.class).value().length == 0 ? new String[]{field.getName()}
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
    public String[] path() {
        return pathValue;
    }

}
