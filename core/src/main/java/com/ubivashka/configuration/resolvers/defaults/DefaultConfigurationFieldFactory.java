package com.ubivashka.configuration.resolvers.defaults;

import com.ubivashka.configuration.ConfigurationProcessor;
import com.ubivashka.configuration.contexts.ConfigurationFieldContext;
import com.ubivashka.configuration.resolvers.ConfigurationFieldResolver;
import com.ubivashka.configuration.resolvers.ConfigurationFieldResolverFactory;
import com.ubivashka.configuration.util.ClassMap;

public class DefaultConfigurationFieldFactory<T> implements ConfigurationFieldResolverFactory<T> {

	private static final ConfigurationFieldResolver<?> DEFAULT_RESOLVER = new DefaultConfigurationFieldResolver<>();

	@SuppressWarnings("unchecked")
	@Override
	public ConfigurationFieldResolver<T> createResolver(ConfigurationFieldContext context) {
		ConfigurationProcessor processor = context.processor();

		ClassMap<ConfigurationFieldResolver<?>> fieldResolvers = new ClassMap<>(processor.getFieldResolvers());
		ConfigurationFieldResolver<T> findedResolver = (ConfigurationFieldResolver<T>) fieldResolvers
				.getOrDefault(context.valueType(), DEFAULT_RESOLVER);

		return findedResolver;

	}
}
