package com.ubivashka.configuration;

import org.bukkit.configuration.ConfigurationSection;

import com.ubivashka.configuration.holders.BukkitConfigurationHolder;
import com.ubivashka.configuration.processor.DefaultConfigurationProcessor;

public class BukkitConfigurationProcessor extends DefaultConfigurationProcessor {
	public BukkitConfigurationProcessor() {
		registerConfigurationHolderWrapper(ConfigurationSection.class,
				section -> new BukkitConfigurationHolder(section));
	}
}
