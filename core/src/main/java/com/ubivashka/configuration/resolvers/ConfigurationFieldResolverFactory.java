package com.ubivashka.configuration.resolvers;

import com.ubivashka.configuration.contexts.ConfigurationFieldContext;

public interface ConfigurationFieldResolverFactory {
	ConfigurationFieldResolver<?> createResolver(ConfigurationFieldContext context);

	default boolean shouldResolveCollection() {
		return true;
	}
}
