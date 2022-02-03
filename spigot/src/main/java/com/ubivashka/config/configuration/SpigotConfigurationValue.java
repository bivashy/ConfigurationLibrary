package com.ubivashka.config.configuration;

import org.bukkit.configuration.ConfigurationSection;

import com.ubivashka.config.holders.AbstractConfigurationValue;
import com.ubivashka.config.holders.IConfigurationSectionHolder;

public class SpigotConfigurationValue extends AbstractConfigurationValue<ConfigurationSection> {
	public SpigotConfigurationValue(IConfigurationSectionHolder<ConfigurationSection> sectionHolder, String key) {
		super(sectionHolder, key);
	}
}
