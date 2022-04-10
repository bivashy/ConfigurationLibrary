package com.ubivashka.configuration.resolvers;

import com.ubivashka.configuration.contexts.ConfigurationFieldResolverContext;

public interface ConfigurationFieldResolver<T> {
	T resolveField(ConfigurationFieldResolverContext resolverContext);

	/**
	 * Defines should resolver process collection in configuration.
	 * Will not work if the <b>custom</b> factory itself does not handle it.
	 * 
	 * @return Should process configuration list.
	 */
	default boolean shouldResolveCollection() {
		return true;
	}
}
