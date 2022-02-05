package com.ubivashka.config.converters;

import com.ubivashka.config.processors.context.IConfigurationContext;

import net.md_5.bungee.config.Configuration;

public abstract class BungeeConfigurationListConverter<V, E>
		extends ConfigurationListConverter<Configuration, IConfigurationContext<Configuration>, V, E> {

	public BungeeConfigurationListConverter(Class<E> valueClass) {
		super(valueClass);
	}

}
