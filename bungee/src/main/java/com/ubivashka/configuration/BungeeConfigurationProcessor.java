package com.ubivashka.configuration;

import com.ubivashka.configuration.holders.BungeeConfigurationHolder;
import com.ubivashka.configuration.processors.DefaultConfigurationProcessor;

import net.md_5.bungee.config.Configuration;

public class BungeeConfigurationProcessor extends DefaultConfigurationProcessor {
	public BungeeConfigurationProcessor() {
		registerConfigurationHolderWrapper(Configuration.class,
				configuration -> new BungeeConfigurationHolder(configuration));
	}
}
