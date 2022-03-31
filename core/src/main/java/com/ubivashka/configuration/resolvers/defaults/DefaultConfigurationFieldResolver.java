package com.ubivashka.configuration.resolvers.defaults;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.ubivashka.configuration.annotations.ConverterIgnore;
import com.ubivashka.configuration.contexts.ConfigurationFieldResolverContext;
import com.ubivashka.configuration.converters.Converter;
import com.ubivashka.configuration.resolvers.ConfigurationFieldResolver;

public class DefaultConfigurationFieldResolver<T> implements ConfigurationFieldResolver<T> {

	@SuppressWarnings("unchecked")
	@Override
	public T resolveField(ConfigurationFieldResolverContext resolverContext) {

		if (resolverContext.configuration().isCollection(resolverContext.path())
				&& Collection.class.isAssignableFrom(resolverContext.valueType())
				&& !resolverContext.hasAnnotation(ConverterIgnore.class)) {

			List<Object> objectList = resolverContext.configuration().getList(resolverContext.path());

			return (T) objectList.stream().map(object -> {

				if (!resolverContext.getGeneric(0).isAssignableFrom(object.getClass()))
					return resolverContext.processor().getConverters()
							.getOrDefault(resolverContext.valueType(), Converter.identity()).convert(object);
				return object;

			}).collect(Collectors.toList());

		}

		return (T) resolverContext.configuration().get(resolverContext.path());
	}
}
