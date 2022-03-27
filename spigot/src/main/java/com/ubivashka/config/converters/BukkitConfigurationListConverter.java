package com.ubivashka.config.converters;

import org.bukkit.configuration.ConfigurationSection;

import com.ubivashka.config.contexts.BukkitConfigurationContext;

public abstract class BukkitConfigurationListConverter<V, E>
		extends ConfigurationListConverter<ConfigurationSection, BukkitConfigurationContext, V, E> {

	public BukkitConfigurationListConverter(Class<E> valueClass) {
		super(valueClass);
	}

}
