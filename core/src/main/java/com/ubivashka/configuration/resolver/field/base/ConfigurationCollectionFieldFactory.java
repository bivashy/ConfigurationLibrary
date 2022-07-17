package com.ubivashka.configuration.resolver.field.base;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.ubivashka.configuration.ConfigurationHolder;
import com.ubivashka.configuration.ConfigurationProcessor;
import com.ubivashka.configuration.context.ConfigurationFieldFactoryContext;
import com.ubivashka.configuration.context.ConfigurationFieldResolverContext;
import com.ubivashka.configuration.context.base.ConfigurationFieldFactoryContextWrapper;
import com.ubivashka.configuration.context.base.ConfigurationFieldResolverContextWrapper;
import com.ubivashka.configuration.processor.DefaultConfigurationProcessor;
import com.ubivashka.configuration.resolver.field.ConfigurationFieldResolver;
import com.ubivashka.configuration.resolver.field.ConfigurationFieldResolverFactory;
import com.ubivashka.configuration.util.ClassMap;

public class ConfigurationCollectionFieldFactory implements ConfigurationFieldResolverFactory {

    @Override
    public ConfigurationFieldResolver<?> createResolver(ConfigurationFieldFactoryContext context) {
        if (!context.isValueCollection())
            return DefaultConfigurationProcessor.FIELD_RESOLVER_FACTORY.createResolver(context);
        ConfigurationProcessor processor = context.processor();

        ClassMap<ConfigurationFieldResolver<?>> fieldResolvers = new ClassMap<>(processor.getFieldResolvers());
        ClassMap<ConfigurationFieldResolverFactory> configurationFieldFactories = new ClassMap<>(
                processor.getFieldResolverFactories());

        ConfigurationFieldResolverFactory factory = configurationFieldFactories.getOrDefault(context.getGeneric(0),
                configurationFieldFactories.getAssignable(context.getGeneric(0), null));

        List<Object> configurationObjects;
        if (context.isList()) {
            configurationObjects = context.getList();
        } else if (ConfigurationHolder.class.isAssignableFrom(context.valueType())) {
            configurationObjects = context.keys().stream().filter(context.configuration()::isSection).map(key -> context.configuration().section(key)).collect(Collectors.toList());
        } else {
            configurationObjects = Collections.singletonList(context.getConfigurationObject());
        }

        if (factory != null && !factory.equals(this) && factory.shouldResolveCollection()) {
            return resolverContext -> configurationObjects.stream().map(object -> getFactoryContext(context, object)).map(
                    factoryContext -> factory.createResolver(factoryContext).resolveField(getResolverContext(factoryContext, factoryContext.getConfigurationObject()))).collect(Collectors.toList());
        }

        ConfigurationFieldResolver<?> findedResolver = fieldResolvers.getOrDefault(context.getGeneric(0), null);

        if (findedResolver != null && findedResolver.shouldResolveCollection())
            return resolverContext ->
                    configurationObjects.stream().map(object -> getResolverContext(context, object)).map(findedResolver::resolveField).collect(Collectors.toList());

        if (findedResolver != null)
            return findedResolver;

        return DefaultConfigurationProcessor.FIELD_RESOLVER_FACTORY.createResolver(context);
    }

    private ConfigurationFieldResolverContext getResolverContext(ConfigurationFieldFactoryContext context, Object configurationObject) {
        return new ConfigurationFieldResolverContextWrapper(context.asResolverContext()) {
            @Override
            public Object getConfigurationObject() {
                return configurationObject;
            }

            @Override
            public Class<?> valueType() {
                return context.getGeneric(0);
            }
        };
    }

    private ConfigurationFieldFactoryContext getFactoryContext(ConfigurationFieldFactoryContext context, Object configurationObject) {
        return new ConfigurationFieldFactoryContextWrapper(context) {
            @Override
            public Object getConfigurationObject() {
                return configurationObject;
            }

            @Override
            public Class<?> valueType() {
                return context.getGeneric(0);
            }
        };
    }
}
