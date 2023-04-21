package com.bivashy.configuration.resolver.field.base;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.bivashy.configuration.context.ConfigurationFieldFactoryContext;
import com.bivashy.configuration.holder.ConfigurationSectionHolder;
import com.bivashy.configuration.resolver.field.ConfigurationFieldResolver;
import com.bivashy.configuration.resolver.field.ConfigurationFieldResolverFactory;

public class ConfigurationHolderResolverFactory implements ConfigurationFieldResolverFactory {
    @Override
    public ConfigurationFieldResolver<?> createResolver(ConfigurationFieldFactoryContext factoryContext) {
        if (!factoryContext.isSection())
            return (context) -> null;
        ConfigurationSectionHolder sectionHolder = factoryContext.getSection();
        Class<?> fieldClass = factoryContext.valueType();
        try {
            for (Constructor<?> constructor : fieldClass.getDeclaredConstructors()) {
                if (constructor.getParameterCount() != 1)
                    continue;
                if (ConfigurationSectionHolder.class.isAssignableFrom(constructor.getParameterTypes()[0]))
                    return (context) -> createNewInstance(constructor, sectionHolder);
                if (constructor.getParameterTypes()[0].isAssignableFrom(factoryContext.configuration().getOriginalHolder().getClass()))
                    return (context) -> createNewInstance(constructor, sectionHolder.getOriginalHolder());
            }
        } catch(SecurityException e) {
            e.printStackTrace();
        }
        return (context) -> null;
    }

    private Object createNewInstance(Constructor<?> constructor, Object... arguments) {
        try {
            return constructor.newInstance(arguments);
        } catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
}
