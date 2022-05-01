package com.ubivashka.configuration.configurate;

import org.spongepowered.configurate.ConfigurationNode;

import com.ubivashka.configuration.configurate.holder.ConfigurationHolder;
import com.ubivashka.configuration.processors.DefaultConfigurationProcessor;

public class ConfigurationProcessor extends DefaultConfigurationProcessor {
	public ConfigurationProcessor() {
		registerConfigurationHolderWrapper(ConfigurationNode.class, node -> new ConfigurationHolder(node));
	}
}
