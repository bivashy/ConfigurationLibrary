package com.bivashy.configuration.resolver.field.base;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.bivashy.configuration.processor.DefaultConfigurationProcessor;
import com.bivashy.configuration.ConfigurationHolder;
import com.bivashy.configuration.ConfigurationProcessor;
import com.bivashy.configuration.context.ConfigurationFieldFactoryContext;
import com.bivashy.configuration.context.ConfigurationFieldResolverContext;
import com.bivashy.configuration.context.base.ConfigurationFieldFactoryContextWrapper;
import com.bivashy.configuration.context.base.ConfigurationFieldResolverContextWrapper;
import com.bivashy.configuration.holder.ConfigurationSectionHolder;
import com.bivashy.configuration.resolver.field.ConfigurationFieldResolver;
import com.bivashy.configuration.resolver.field.ConfigurationFieldResolverFactory;
import com.bivashy.configuration.util.ClassMap;

public class ConfigurationCollectionFieldFactory implements ConfigurationFieldResolverFactory {

    @Override
    public ConfigurationFieldResolver<?> createResolver(ConfigurationFieldFactoryContext context) {
        if (!context.isValueCollection())
            return DefaultConfigurationProcessor.FIELD_RESOLVER_FACTORY.createResolver(context);
        if (context.getConfigurationObject() == null)
            return resolverContext -> null;
        Class<?> collectionType = context.getGeneric(0);

        ConfigurationProcessor processor = context.processor();

        ClassMap<ConfigurationFieldResolverFactory> fieldFactories = new ClassMap<>(processor.getFieldResolverFactories());

        ConfigurationFieldResolverFactory factory = fieldFactories.getOrDefault(collectionType, fieldFactories.getAssignable(collectionType, null));

        List<Object> configurationObjects;
        if (context.isList()) {
            configurationObjects = context.getList();
        } else if (ConfigurationHolder.class.isAssignableFrom(collectionType) && context.isSection()) {
            ConfigurationSectionHolder sectionHolder = context.getSection();
            configurationObjects = sectionHolder.keys().stream().filter(sectionHolder::isSection).map(sectionHolder::section).collect(Collectors.toList());
        } else {
            configurationObjects = Collections.singletonList(context.getConfigurationObject());
        }

        if (factory != null && !factory.equals(this)) {
            if (factory.canInteract(getClass()))
                return resolverContext -> configurationObjects.stream()
                        .map(object -> getFactoryContext(context, object))
                        .map(factoryContext -> factory.createResolver(factoryContext)
                                .resolveField(getResolverContext(factoryContext, factoryContext.getConfigurationObject())))
                        .collect(Collectors.toList());
            return factory.createResolver(context);
        }

        ClassMap<ConfigurationFieldResolver<?>> fieldResolvers = new ClassMap<>(processor.getFieldResolvers());
        ConfigurationFieldResolver<?> findedResolver = fieldResolvers.getOrDefault(context.getGeneric(0), null);

        if (findedResolver != null) {
            if (findedResolver.canInteract(getClass()))
                return resolverContext -> configurationObjects.stream()
                        .map(object -> getResolverContext(context, object))
                        .map(findedResolver::resolveField)
                        .collect(Collectors.toList());
            return findedResolver;
        }
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
