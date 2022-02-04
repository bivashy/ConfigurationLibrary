package com.ubivashka.config.converters;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.ubivashka.config.contexts.BungeeConfigurationContext;
import com.ubivashka.config.holders.IConfigurationSectionHolder;
import com.ubivashka.config.processors.BungeeConfigurationContextProcessor;
import com.ubivashka.config.processors.BungeeConfigurationHolder;
import com.ubivashka.config.processors.utils.ReflectionUtil;

import net.md_5.bungee.config.Configuration;

public class BungeeConfigurationHolderConverter extends BungeeConfigurationContextProcessor {

	@Override
	public void process(BungeeConfigurationContext context) {
		IConfigurationSectionHolder<Configuration> configurationHolder = context.getConfigurationSectionHolder()
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
			e.printStackTrace	();
		}
	}

	@Override
	public boolean isValidContext(BungeeConfigurationContext context) {
		return BungeeConfigurationHolder.class.isAssignableFrom(context.getField().getType())
				&& context.getConfigurationSectionHolder().isConfigurationSection(context.getConfigurationPath());
	}

	@Override
	public byte priority() {
		return -128;
	}

}
