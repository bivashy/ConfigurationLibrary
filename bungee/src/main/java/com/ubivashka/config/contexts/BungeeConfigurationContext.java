package com.ubivashka.config.contexts;

import java.lang.reflect.Field;

import com.ubivashka.config.holders.IConfigurationSectionHolder;
import com.ubivashka.config.processors.context.AbstractConfigurationContext;

import net.md_5.bungee.config.Configuration;

public class BungeeConfigurationContext extends AbstractConfigurationContext<Configuration> {
	public BungeeConfigurationContext(Class<?> clazz,
			IConfigurationSectionHolder<Configuration> configurationSectionHolder, String configurationPath,
			Field field) {
		super(clazz, configurationSectionHolder, configurationPath, field);
	}

}
