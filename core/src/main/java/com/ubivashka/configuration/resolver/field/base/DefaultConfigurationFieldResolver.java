package com.ubivashka.configuration.resolver.field.base;

import com.ubivashka.configuration.context.ConfigurationFieldResolverContext;
import com.ubivashka.configuration.resolver.field.ConfigurationFieldResolver;

public class DefaultConfigurationFieldResolver<T> implements ConfigurationFieldResolver<T> {

    @SuppressWarnings("unchecked")
    @Override
    public T resolveField(ConfigurationFieldResolverContext resolverContext) {
        return (T) resolverContext.getConfigurationObject();
    }
}
