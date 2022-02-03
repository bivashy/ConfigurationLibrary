package com.ubivashka.config.contexts;

import java.lang.reflect.Field;

import org.bukkit.configuration.ConfigurationSection;

import com.ubivashka.config.holders.IConfigurationSectionHolder;
import com.ubivashka.config.processors.context.AbstractConfigurationContext;

public class SpigotConfigurationContext extends AbstractConfigurationContext<ConfigurationSection> {
	public SpigotConfigurationContext(Class<?> clazz,
			IConfigurationSectionHolder<ConfigurationSection> configurationSectionHolder, String configurationPath,
			Field field) {
		super(clazz, configurationSectionHolder, configurationPath, field);
	}

}
