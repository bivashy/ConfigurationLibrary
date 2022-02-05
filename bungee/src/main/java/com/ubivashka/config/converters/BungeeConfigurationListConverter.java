package com.ubivashka.config.converters;

import com.ubivashka.config.contexts.BungeeConfigurationContext;

import net.md_5.bungee.config.Configuration;

public abstract class BungeeConfigurationListConverter<V, E>
		extends ConfigurationListConverter<Configuration, BungeeConfigurationContext, V, E> {

	public BungeeConfigurationListConverter(Class<E> valueClass) {
		super(valueClass);
	}

}
