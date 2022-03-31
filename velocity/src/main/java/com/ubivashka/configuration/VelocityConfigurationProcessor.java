package com.ubivashka.configuration;

import com.ubivashka.configuration.holder.VelocityConfigurationHolder;
import com.ubivashka.configuration.processors.DefaultConfigurationProcessor;

import ninja.leaping.configurate.ConfigurationNode;

public class VelocityConfigurationProcessor extends DefaultConfigurationProcessor {
	public VelocityConfigurationProcessor() {
		registerConfigurationHolderWrapper(ConfigurationNode.class, node -> new VelocityConfigurationHolder(node));
	}
}
