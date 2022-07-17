package com.ubivashka.configuration.resolver.field.base;

import com.ubivashka.configuration.ConfigurationProcessor;
import com.ubivashka.configuration.annotation.SingleObject;
import com.ubivashka.configuration.context.ConfigurationFieldFactoryContext;
import com.ubivashka.configuration.context.base.SingleObjectResolverContext;
import com.ubivashka.configuration.resolver.field.ConfigurationFieldResolver;
import com.ubivashka.configuration.resolver.field.ConfigurationFieldResolverFactory;
import com.ubivashka.configuration.util.ClassMap;

public class DefaultConfigurationFieldFactory implements ConfigurationFieldResolverFactory {

	private static final ConfigurationFieldResolver<?> DEFAULT_RESOLVER = new DefaultConfigurationFieldResolver<>();

	@Override
	public ConfigurationFieldResolver<?> createResolver(ConfigurationFieldFactoryContext context) {
		ConfigurationProcessor processor = context.processor();

		ClassMap<ConfigurationFieldResolver<?>> fieldResolvers = new ClassMap<>(processor.getFieldResolvers());
		ConfigurationFieldResolver<?> findedResolver = fieldResolvers.getOrDefault(context.valueType(),
				DEFAULT_RESOLVER);

		if (context.hasAnnotation(SingleObject.class))
			return (resolverContext) -> {
				SingleObjectResolverContext singleContext = new SingleObjectResolverContext(resolverContext,
						resolverContext.configuration().get(resolverContext.path()));
				return findedResolver.resolveField(singleContext);
			};

		return findedResolver;

	}
}
