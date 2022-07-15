package com.ubivashka.configuration;

import java.util.Map;

import com.ubivashka.configuration.converter.Converter;
import com.ubivashka.configuration.holder.ConfigurationSectionHolder;
import com.ubivashka.configuration.resolver.field.ConfigurationFieldResolver;
import com.ubivashka.configuration.resolver.field.ConfigurationFieldResolverFactory;
import com.ubivashka.configuration.wrapper.ConfigurationHolderWrapper;
import com.ubivashka.function.CastableInterface;

public interface ConfigurationProcessor extends CastableInterface<ConfigurationProcessor> {
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
	<T> ConfigurationProcessor registerConfigurationHolderWrapper(Class<T> type, ConfigurationHolderWrapper<T> wrapper);

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
	 * Register converter that will be used in list
	 * 
	 * @param type.      Type that need to convert
	 * @param converter. Converter that converts object (for example string or
	 *                   integer) to type
	 * @return this {@link ConfigurationProcessor}
	 */
	<T> ConfigurationProcessor registerConverter(Class<T> type, Converter<T> converter);

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

	/**
	 * Returns converters, usually used for converting list values. For example
	 * converting Integer to string
	 * 
	 * @return Object converters
	 */
	Map<Class<?>, Converter<?>> getConverters();

}
