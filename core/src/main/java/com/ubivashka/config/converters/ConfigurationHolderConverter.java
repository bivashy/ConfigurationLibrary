package com.ubivashka.config.converters;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

import com.ubivashka.config.holders.IConfigurationSectionHolder;
import com.ubivashka.config.processors.ConfigurationHolder;
import com.ubivashka.config.processors.context.IConfigurationContext;
import com.ubivashka.config.processors.utils.ReflectionUtil;

public class ConfigurationHolderConverter<T, C extends IConfigurationContext<T>, E extends ConfigurationHolder<T, ? extends IConfigurationSectionHolder<T>, C>>
		extends ConfigurationListConverter<T, C, IConfigurationSectionHolder<T>, E> {
	private final Class<T> configurationClass;

	public ConfigurationHolderConverter(Class<E> entityClass, Class<T> configurationClass) {
		super(entityClass);
		this.configurationClass = configurationClass;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected E valueToEntity(C context, IConfigurationSectionHolder<T> configurationHolder) {
		Class<?> fieldClass = ReflectionUtil.getRealType(context.getField());
		Constructor<?> constructor = ReflectionUtil.getContructor(fieldClass, configurationClass).orElse(null);
		if (constructor == null)
			return null;

		try {
			Object entity = constructor.newInstance(configurationHolder.getOriginalConfigurationSection());
			return (E) entity;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected IConfigurationSectionHolder<T> getConfigurationValue(C context) {
		if (!context.getConfigurationSectionHolder().isConfigurationSection(context.getConfigurationPath()))
			return null;
		IConfigurationSectionHolder<T> sectionHolder = context.getConfigurationSectionHolder()
				.getConfigurationSection(context.getConfigurationPath());
		return sectionHolder;
	}

	@Override
	protected List<IConfigurationSectionHolder<T>> getConfigurationValues(C context) {
		IConfigurationSectionHolder<T> configurationSectionHolder = context.getConfigurationSectionHolder();
		if (!configurationSectionHolder.isConfigurationSection(context.getConfigurationPath()))
			return null;
		IConfigurationSectionHolder<T> subConfigurationSectionHolder = configurationSectionHolder
				.getConfigurationSection(context.getConfigurationPath());
		return subConfigurationSectionHolder.getKeys().stream()
				.filter(key -> subConfigurationSectionHolder.isConfigurationSection(key))
				.map(key -> subConfigurationSectionHolder.getConfigurationSection(key)).collect(Collectors.toList());
	}

	@Override
	public byte priority() {
		return -128;
	}

}
