package com.ubivashka.configuration.resolvers.defaults;

import java.util.List;
import java.util.stream.Collectors;

import com.ubivashka.configuration.ConfigurationProcessor;
import com.ubivashka.configuration.annotations.SectionObject;
import com.ubivashka.configuration.annotations.SingleObject;
import com.ubivashka.configuration.contexts.ConfigurationFieldContext;
import com.ubivashka.configuration.contexts.ConfigurationFieldResolverContext;
import com.ubivashka.configuration.contexts.defaults.ConfigurationFieldResolverContextWrapper;
import com.ubivashka.configuration.contexts.defaults.SingleObjectResolverContext;
import com.ubivashka.configuration.holders.ConfigurationSectionHolder;
import com.ubivashka.configuration.processors.DefaultConfigurationProcessor;
import com.ubivashka.configuration.resolvers.ConfigurationFieldResolver;
import com.ubivashka.configuration.resolvers.ConfigurationFieldResolverFactory;
import com.ubivashka.configuration.util.ClassMap;

public class ConfigurationCollectionFieldFactory<T> implements ConfigurationFieldResolverFactory<T> {

	@SuppressWarnings("unchecked")
	@Override
	public ConfigurationFieldResolver<T> createResolver(ConfigurationFieldContext context) {
		if (!context.isValueCollection())
			return (ConfigurationFieldResolver<T>) DefaultConfigurationProcessor.FIELD_RESOLVER_FACTORY
					.createResolver(context);
		ConfigurationProcessor processor = context.processor();

		ClassMap<ConfigurationFieldResolver<?>> fieldResolvers = new ClassMap<>(processor.getFieldResolvers());

		ConfigurationFieldResolver<T> findedResolver = (ConfigurationFieldResolver<T>) fieldResolvers
				.getOrDefault(context.getGeneric(0), null);

		if (findedResolver != null && findedResolver.shouldResolveCollection())
			if (context.hasAnnotation(SectionObject.class))
				return (ConfigurationFieldResolver<T>) new ConfigurationFieldResolver<List<T>>() {
					@Override
					public List<T> resolveField(ConfigurationFieldResolverContext resolverContext) {
						ConfigurationSectionHolder sectionHolder = resolverContext.configuration()
								.getSection(resolverContext.path());

						return sectionHolder.getKeys().stream().map(key -> {
							ConfigurationFieldResolverContext context = new ConfigurationFieldResolverContextWrapper(
									resolverContext) {
								@Override
								public String path() {
									return key;
								}

								@Override
								public ConfigurationSectionHolder configuration() {
									return sectionHolder;
								}
							};

							return findedResolver.resolveField(context);
						}).collect(Collectors.toList());
					}
				};
		if (context.hasAnnotation(SingleObject.class))
			return (ConfigurationFieldResolver<T>) new ConfigurationFieldResolver<List<T>>() {
				@Override
				public List<T> resolveField(ConfigurationFieldResolverContext resolverContext) {
					return resolverContext.configuration().getList(resolverContext.path()).stream().map(object -> {
						SingleObjectResolverContext context = new SingleObjectResolverContext(resolverContext, object);
						return findedResolver.resolveField(context);
					}).collect(Collectors.toList());
				}
			};

		if (findedResolver != null)
			return findedResolver;

		return (ConfigurationFieldResolver<T>) DefaultConfigurationProcessor.FIELD_RESOLVER_FACTORY
				.createResolver(context);
	}

}
