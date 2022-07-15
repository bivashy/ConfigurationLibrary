package com.ubivashka.configuration.configurate;

import com.ubivashka.configuration.configurate.holder.ConfigurationNodeHolder;
import com.ubivashka.configuration.processor.DefaultConfigurationProcessor;

import org.spongepowered.configurate.ConfigurationNode;

public class SpongeConfigurateProcessor extends DefaultConfigurationProcessor {
    public SpongeConfigurateProcessor() {
        registerConfigurationHolderWrapper(ConfigurationNode.class, ConfigurationNodeHolder::new);
    }
}
