package com.bivashy.configuration;

import com.bivashy.configuration.holder.VelocityConfigurationHolder;
import com.bivashy.configuration.processor.DefaultConfigurationProcessor;

import ninja.leaping.configurate.ConfigurationNode;

public class VelocityConfigurationProcessor extends DefaultConfigurationProcessor {
	public VelocityConfigurationProcessor() {
		registerConfigurationHolderWrapper(ConfigurationNode.class, VelocityConfigurationHolder::new);
	}
}
