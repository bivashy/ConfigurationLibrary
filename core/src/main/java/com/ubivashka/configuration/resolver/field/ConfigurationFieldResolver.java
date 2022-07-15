package com.ubivashka.configuration.resolver.field;

import com.ubivashka.configuration.context.ConfigurationFieldResolverContext;

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
