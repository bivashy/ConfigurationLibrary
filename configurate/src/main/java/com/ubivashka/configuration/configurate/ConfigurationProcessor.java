package com.ubivashka.configuration.configurate;

import com.ubivashka.configuration.configurate.holder.ConfigurationHolder;
import com.ubivashka.configuration.processors.DefaultConfigurationProcessor;
import org.spongepowered.configurate.ConfigurationNode;

@Deprecated
public class ConfigurationProcessor extends DefaultConfigurationProcessor {
	public ConfigurationProcessor() {
		registerConfigurationHolderWrapper(ConfigurationNode.class, node -> new ConfigurationHolder(node));
	}
}
