package com.ubivashka.configuration.resolvers.defaults;

import java.util.stream.Collectors;

import com.ubivashka.configuration.contexts.ConfigurationFieldContext;
import com.ubivashka.configuration.resolvers.ConfigurationFieldResolver;
import com.ubivashka.configuration.resolvers.ConfigurationFieldResolverFactory;

public class ConfigurationEnumFieldFactory implements ConfigurationFieldResolverFactory {
	@Override
	public ConfigurationFieldResolver<?> createResolver(ConfigurationFieldContext factoryContext) {
		if (factoryContext.isValueCollection())
			return (context) -> factoryContext.configuration().getList(factoryContext.path()).stream().map(object -> {
				String stringObject = String.valueOf(object);
				return getEnum(context.getGeneric(0), stringObject);
			}).collect(Collectors.toList());

		return (context) -> getEnum(context.valueType(), context.configuration().getString(context.path()));
	}

	@SuppressWarnings("unchecked")
	private <T extends Enum<T>> Enum<T> getEnum(Class<?> clazz, String enumString) {
		return Enum.valueOf((Class<T>) clazz, enumString);
	}
}
