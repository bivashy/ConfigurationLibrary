package com.ubivashka.config.converters;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.configuration.ConfigurationSection;

import com.ubivashka.config.contexts.SpigotConfigurationContext;
import com.ubivashka.config.holders.IConfigurationSectionHolder;
import com.ubivashka.config.processors.SpigotConfigurationContextProcessor;
import com.ubivashka.config.processors.SpigotConfigurationHolder;
import com.ubivashka.config.processors.utils.ReflectionUtil;

public class SpigotConfigurationHolderConverter extends SpigotConfigurationContextProcessor {

	@Override
	public void process(SpigotConfigurationContext context) {
		IConfigurationSectionHolder<ConfigurationSection> configurationHolder = context.getConfigurationSectionHolder()
				.getConfigurationSection(context.getConfigurationPath());
		Class<?> fieldClass = context.getField().getType();
		Constructor<?> constructor = ReflectionUtil
				.getContructor(fieldClass, configurationHolder.getOriginalConfigurationSection().getClass())
				.orElse(null);
		if (constructor == null)
			return;
		try {
			context.setCurrentObject(constructor.newInstance(configurationHolder.getOriginalConfigurationSection()));
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isValidContext(SpigotConfigurationContext context) {
		return SpigotConfigurationHolder.class.isAssignableFrom(context.getField().getType())
				&& context.getConfigurationSectionHolder().isConfigurationSection(context.getConfigurationPath());
	}

	@Override
	public byte priority() {
		return -128;
	}

}
