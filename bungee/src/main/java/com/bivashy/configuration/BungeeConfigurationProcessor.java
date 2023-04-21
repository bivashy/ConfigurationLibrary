package com.bivashy.configuration;

import com.bivashy.configuration.holders.BungeeConfigurationHolder;
import com.bivashy.configuration.processor.DefaultConfigurationProcessor;

import net.md_5.bungee.config.Configuration;

public class BungeeConfigurationProcessor extends DefaultConfigurationProcessor {
	public BungeeConfigurationProcessor() {
		registerConfigurationHolderWrapper(Configuration.class,
				BungeeConfigurationHolder::new);
	}
}
