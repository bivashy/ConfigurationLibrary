package com.ubivashka.configuration.resolvers;

import com.ubivashka.configuration.contexts.ConfigurationFieldContext;

public interface ConfigurationFieldResolverFactory<T> {
	ConfigurationFieldResolver<T> createResolver(ConfigurationFieldContext context);
}
