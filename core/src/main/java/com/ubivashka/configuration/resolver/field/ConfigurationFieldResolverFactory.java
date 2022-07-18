package com.ubivashka.configuration.resolver.field;

import com.ubivashka.configuration.context.ConfigurationFieldFactoryContext;

public interface ConfigurationFieldResolverFactory {
	ConfigurationFieldResolver<?> createResolver(ConfigurationFieldFactoryContext context);

	/**
	 * Resolves can field resolver factory interact with another resolver factory.
	 * For example {@link com.ubivashka.configuration.resolver.field.base.ConfigurationCollectionFieldFactory} will call this method providing ConfigurationCollectionFieldFactory class
	 * and you can return false if you prefer resolve collection yourself.
	 */
	default boolean canInteract(Class<?> clazz) {
		return true;
	}
}
