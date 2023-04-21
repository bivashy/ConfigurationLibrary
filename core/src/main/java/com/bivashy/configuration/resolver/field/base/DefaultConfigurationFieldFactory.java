package com.bivashy.configuration.resolver.field.base;

import com.bivashy.configuration.ConfigurationProcessor;
import com.bivashy.configuration.context.ConfigurationFieldFactoryContext;
import com.bivashy.configuration.resolver.field.ConfigurationFieldResolver;
import com.bivashy.configuration.resolver.field.ConfigurationFieldResolverFactory;
import com.bivashy.configuration.util.ClassMap;

public class DefaultConfigurationFieldFactory implements ConfigurationFieldResolverFactory {

	private static final ConfigurationFieldResolver<?> DEFAULT_RESOLVER = new DefaultConfigurationFieldResolver<>();

	@Override
	public ConfigurationFieldResolver<?> createResolver(ConfigurationFieldFactoryContext context) {
		ConfigurationProcessor processor = context.processor();
		ClassMap<ConfigurationFieldResolver<?>> fieldResolvers = new ClassMap<>(processor.getFieldResolvers());
		return fieldResolvers.getOrDefault(context.valueType(),
				DEFAULT_RESOLVER);
	}
}
