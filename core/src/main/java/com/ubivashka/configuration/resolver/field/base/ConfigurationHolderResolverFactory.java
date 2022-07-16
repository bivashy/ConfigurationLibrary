package com.ubivashka.configuration.resolver.field.base;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.ubivashka.configuration.context.ConfigurationFieldContext;
import com.ubivashka.configuration.holder.ConfigurationSectionHolder;
import com.ubivashka.configuration.resolver.field.ConfigurationFieldResolver;
import com.ubivashka.configuration.resolver.field.ConfigurationFieldResolverFactory;

public class ConfigurationHolderResolverFactory implements ConfigurationFieldResolverFactory {
	@Override
	public ConfigurationFieldResolver<?> createResolver(ConfigurationFieldContext factoryContext) {
		if (factoryContext.isValueCollection())
			throw new UnsupportedOperationException("Collection unsupported for ConfigurationHolder");
		ConfigurationSectionHolder sectionHolder = getSectionHolder(factoryContext.configuration(), factoryContext.path());
		Class<?> fieldClass = factoryContext.valueType();
		try {
			for (Constructor<?> constructor : fieldClass.getDeclaredConstructors()) {
				if (constructor.getParameterCount() != 1)
					continue;
				if (ConfigurationSectionHolder.class.isAssignableFrom(constructor.getParameterTypes()[0]))
					return (context) -> {
						try {
							return constructor.newInstance(sectionHolder);
						} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							e.printStackTrace();
						}
						return null;
					};
				if (constructor.getParameterTypes()[0].isAssignableFrom(factoryContext.configuration().getOriginalHolder().getClass()))
					return (context) -> {
						try {
							return constructor.newInstance(sectionHolder.getOriginalHolder());
						} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							e.printStackTrace();
						}
						return null;
					};
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ConfigurationSectionHolder getSectionHolder(ConfigurationSectionHolder root, String path) {
		String[] fullPath = path.split("\\."); // Rewrite this, for example use String[] in @ConfigField annotation in the future.
		ConfigurationSectionHolder sectionHolder = root;
		for (String pathPiece : fullPath)
			sectionHolder = sectionHolder.getSection(pathPiece);
		return sectionHolder;
	}
}
