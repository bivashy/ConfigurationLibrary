package com.ubivashka.config.converters;

import org.bukkit.configuration.ConfigurationSection;

import com.ubivashka.config.contexts.SpigotConfigurationContext;

public abstract class SpigotConfigurationListConverter<V, E>
		extends ConfigurationListConverter<ConfigurationSection, SpigotConfigurationContext, V, E> {

	public SpigotConfigurationListConverter(Class<E> valueClass) {
		super(valueClass);
	}

}
