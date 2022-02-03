package com.ubivashka.config.processors;

import java.lang.reflect.Field;

import org.bukkit.configuration.ConfigurationSection;

import com.ubivashka.config.contexts.SpigotConfigurationContext;
import com.ubivashka.config.converters.SpigotConfigurationSectionConverter;
import com.ubivashka.config.converters.SpigotEnumConverter;
import com.ubivashka.config.converters.DefaultConverter;
import com.ubivashka.config.dealerships.SpigotConfigurationContextProcessorsDealership;
import com.ubivashka.config.holders.SpigotConfigurationSectionHolder;

public abstract class SpigotConfigurationHolder extends
		ConfigurationHolder<ConfigurationSection, SpigotConfigurationSectionHolder, SpigotConfigurationContext> {

	private static final SpigotConfigurationContextProcessorsDealership PROCESSORS_DEALERSHIP = new SpigotConfigurationContextProcessorsDealership();

	static {
		PROCESSORS_DEALERSHIP.put("default", new DefaultConverter());
		PROCESSORS_DEALERSHIP.put("enum", new SpigotEnumConverter());
		PROCESSORS_DEALERSHIP.put("section_converter", new SpigotConfigurationSectionConverter());
	}

	protected void init(ConfigurationSection configurationSection) {
		init(new SpigotConfigurationSectionHolder(configurationSection));
	}

	@Override
	protected ConfigurationContextProcessorsDealership<ConfigurationSection, SpigotConfigurationContext, ? extends IConfigurationContextProcessor<ConfigurationSection, SpigotConfigurationContext>> getConfigurationFieldProcessorsDealership() {
		return PROCESSORS_DEALERSHIP;
	}

	@Override
	protected SpigotConfigurationContext createDefaultConfigurationContext(Class<?> clazz,
			SpigotConfigurationSectionHolder configurationSectionHolder, String configurationPath, Field field) {
		return new SpigotConfigurationContext(clazz, configurationSectionHolder, configurationPath, field);
	}

}
