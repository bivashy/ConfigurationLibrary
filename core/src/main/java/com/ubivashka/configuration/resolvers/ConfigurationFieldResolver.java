package com.ubivashka.configuration.resolvers;

import com.ubivashka.configuration.contexts.ConfigurationFieldResolverContext;

public interface ConfigurationFieldResolver<T> {
	T resolveField(ConfigurationFieldResolverContext resolverContext);

	default boolean shouldResolveCollection() {
		return true;
	}
}
