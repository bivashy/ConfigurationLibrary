package com.ubivashka.config.configuration;

import com.ubivashka.config.holders.AbstractConfigurationValue;
import com.ubivashka.config.holders.IConfigurationSectionHolder;

import net.md_5.bungee.config.Configuration;

public class BungeeConfigurationValue extends AbstractConfigurationValue<Configuration> {
	public BungeeConfigurationValue(IConfigurationSectionHolder<Configuration> sectionHolder, String key) {
		super(sectionHolder, key);
	}
}
