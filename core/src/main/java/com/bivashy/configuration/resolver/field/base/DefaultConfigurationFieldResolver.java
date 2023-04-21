package com.bivashy.configuration.resolver.field.base;

import com.bivashy.configuration.context.ConfigurationFieldResolverContext;
import com.bivashy.configuration.resolver.field.ConfigurationFieldResolver;

public class DefaultConfigurationFieldResolver<T> implements ConfigurationFieldResolver<T> {

    @SuppressWarnings("unchecked")
    @Override
    public T resolveField(ConfigurationFieldResolverContext resolverContext) {
        return (T) resolverContext.getConfigurationObject();
    }
}
