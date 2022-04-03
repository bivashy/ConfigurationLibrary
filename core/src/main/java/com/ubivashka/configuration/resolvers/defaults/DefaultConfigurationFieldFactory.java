package com.ubivashka.configuration.resolvers.defaults;

import com.ubivashka.configuration.ConfigurationProcessor;
import com.ubivashka.configuration.annotations.SingleObject;
import com.ubivashka.configuration.contexts.ConfigurationFieldContext;
import com.ubivashka.configuration.contexts.defaults.SingleObjectResolverContext;
import com.ubivashka.configuration.resolvers.ConfigurationFieldResolver;
import com.ubivashka.configuration.resolvers.ConfigurationFieldResolverFactory;
import com.ubivashka.configuration.util.ClassMap;

public class DefaultConfigurationFieldFactory implements ConfigurationFieldResolverFactory {

	private static final ConfigurationFieldResolver<?> DEFAULT_RESOLVER = new DefaultConfigurationFieldResolver<>();

	@Override
	public ConfigurationFieldResolver<?> createResolver(ConfigurationFieldContext context) {
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
