package com.ubivashka.configuration.resolver.field;

import com.ubivashka.configuration.context.ConfigurationFieldFactoryContext;

public interface ConfigurationFieldResolverFactory {
	ConfigurationFieldResolver<?> createResolver(ConfigurationFieldFactoryContext context);

	default boolean shouldResolveCollection() {
		return true;
	}
}
