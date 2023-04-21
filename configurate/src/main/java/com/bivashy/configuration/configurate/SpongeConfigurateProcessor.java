package com.bivashy.configuration.configurate;

import com.bivashy.configuration.configurate.holder.ConfigurationNodeHolder;
import com.bivashy.configuration.processor.DefaultConfigurationProcessor;

import org.spongepowered.configurate.ConfigurationNode;

public class SpongeConfigurateProcessor extends DefaultConfigurationProcessor {
    public SpongeConfigurateProcessor() {
        registerConfigurationHolderWrapper(ConfigurationNode.class, ConfigurationNodeHolder::new);
    }
}
