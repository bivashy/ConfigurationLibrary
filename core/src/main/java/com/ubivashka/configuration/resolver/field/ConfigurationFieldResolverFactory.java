package com.ubivashka.configuration.resolver.field;

import com.ubivashka.configuration.context.ConfigurationFieldContext;

public interface ConfigurationFieldResolverFactory {
	ConfigurationFieldResolver<?> createResolver(ConfigurationFieldContext context);

	default boolean shouldResolveCollection() {
		return true;
	}
}
