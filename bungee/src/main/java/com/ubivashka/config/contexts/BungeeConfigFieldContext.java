package com.ubivashka.config.contexts;

import net.md_5.bungee.config.Configuration;

import java.lang.reflect.Field;

import com.ubivashka.config.holders.IConfigurationSectionHolder;
import com.ubivashka.config.processors.ConfigurationHolder;
import com.ubivashka.config.processors.configfield.context.AbstractConfigFieldContext;
import com.ubivashka.config.processors.context.IConfigurationContext;

public class BungeeConfigFieldContext extends AbstractConfigFieldContext<Configuration> {

	public BungeeConfigFieldContext(
			Class<? extends ConfigurationHolder<Configuration, ? extends IConfigurationSectionHolder<Configuration>, ? extends IConfigurationContext<Configuration>>> clazz,
			IConfigurationSectionHolder<Configuration> configurationSectionHolder, String configurationPath,
			Field field) {
		super(clazz, configurationSectionHolder, configurationPath, field);
	}

}
