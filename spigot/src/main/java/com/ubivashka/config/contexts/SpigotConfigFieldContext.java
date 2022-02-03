package com.ubivashka.config.contexts;

import java.lang.reflect.Field;

import org.bukkit.configuration.ConfigurationSection;

import com.ubivashka.config.holders.IConfigurationSectionHolder;
import com.ubivashka.config.processors.ConfigurationHolder;
import com.ubivashka.config.processors.configfield.context.AbstractConfigFieldContext;
import com.ubivashka.config.processors.context.IConfigurationContext;

public class SpigotConfigFieldContext extends AbstractConfigFieldContext<ConfigurationSection> {

	public SpigotConfigFieldContext(
			Class<? extends ConfigurationHolder<ConfigurationSection, ? extends IConfigurationSectionHolder<ConfigurationSection>, ? extends IConfigurationContext<ConfigurationSection>>> clazz,
			IConfigurationSectionHolder<ConfigurationSection> configurationSectionHolder, String configurationPath,
			Field field) {
		super(clazz, configurationSectionHolder, configurationPath, field);
	}

}
