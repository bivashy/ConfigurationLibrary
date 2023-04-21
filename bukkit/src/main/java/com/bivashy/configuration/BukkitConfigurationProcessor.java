package com.bivashy.configuration;

import org.bukkit.configuration.ConfigurationSection;

import com.bivashy.configuration.holders.BukkitConfigurationHolder;
import com.bivashy.configuration.processor.DefaultConfigurationProcessor;

public class BukkitConfigurationProcessor extends DefaultConfigurationProcessor {
	public BukkitConfigurationProcessor() {
		registerConfigurationHolderWrapper(ConfigurationSection.class,
				BukkitConfigurationHolder::new);
	}
}
