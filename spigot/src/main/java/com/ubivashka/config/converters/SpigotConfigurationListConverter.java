package com.ubivashka.config.converters;

import org.bukkit.configuration.ConfigurationSection;

import com.ubivashka.config.processors.context.IConfigurationContext;

public abstract class SpigotConfigurationListConverter<V, E>
		extends ConfigurationListConverter<ConfigurationSection, IConfigurationContext<ConfigurationSection>, V, E> {

	public SpigotConfigurationListConverter(Class<E> valueClass) {
		super(valueClass);
	}

}
