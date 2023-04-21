package com.bivashy.configuration;

import java.util.Map;

import com.bivashy.configuration.holder.ConfigurationSectionHolder;
import com.bivashy.configuration.holder.factory.ConfigurationSectionHolderFactory;
import com.bivashy.configuration.resolver.field.ConfigurationFieldResolver;
import com.bivashy.configuration.resolver.field.ConfigurationFieldResolverFactory;
import com.bivashy.function.Castable;

public interface ConfigurationProcessor extends Castable<ConfigurationProcessor> {
	/**
	 * Resolves objects with fields
	 * 
	 * @param Object with fields that needs to be resolved from configuration
	 * @return this {@link ConfigurationProcessor}
	 */
	ConfigurationProcessor resolve(ConfigurationSectionHolder sectionHolder, Object... fieldHolders);

	/**
	 * Resolves objects with fields
	 * 
	 * @param Object with fields that needs to be resolved from configuration
	 * @return this {@link ConfigurationProcessor}
	 */
	<T> ConfigurationProcessor resolve(T sectionHolder, Object... fieldHolders);

	/**
	 * 
	 * @param type    Type that will be wrapped
	 * @param wrapper Wrapper for the type
	 * @return this {@link ConfigurationProcessor}
	 */
	<T> ConfigurationProcessor registerConfigurationHolderWrapper(Class<T> type, ConfigurationSectionHolderFactory<T> wrapper);

	/**
	 * Register field resolver for specific class
	 * 
	 * @param type          Type that need to be resolved
	 * @param fieldResolver The field resolver
	 * @return this {@link ConfigurationProcessor}
	 */
	<T> ConfigurationProcessor registerFieldResolver(Class<T> type, ConfigurationFieldResolver<T> fieldResolver);

	/**
	 * 
	 * Register field resolver factory for specific class, that can be used for
	 * interfaces. Or fields with generics
	 * 
	 * @param type                 Type that need to be resolved
	 * @param fieldResolverFactory field resolver factory
	 * @return this {@link ConfigurationProcessor}
	 */
	<T> ConfigurationProcessor registerFieldResolverFactory(Class<T> type,
			ConfigurationFieldResolverFactory fieldResolverFactory);

	/**
	 * Returns fields resolvers map. Map is unmodifiable, and can be used for
	 * factories
	 * 
	 * @return unmodifiable field resolvers map
	 */
	Map<Class<?>, ConfigurationFieldResolver<?>> getFieldResolvers();

	/**
	 * Returns field resolver factories. Map is unmodifiable, and can be used for
	 * debugging
	 * 
	 * @return unmodifiable field resolver factories map
	 */
	Map<Class<?>, ConfigurationFieldResolverFactory> getFieldResolverFactories();
}
