package com.ubivashka.config.processors;

import java.lang.reflect.Field;

import org.bukkit.configuration.ConfigurationSection;

import com.ubivashka.config.contexts.BukkitConfigurationContext;
import com.ubivashka.config.converters.ConfigurationHolderConverter;
import com.ubivashka.config.converters.DefaultConverter;
import com.ubivashka.config.converters.EnumConverter;
import com.ubivashka.config.converters.BukkitConfigurationSectionConverter;
import com.ubivashka.config.dealerships.BukkitConfigurationContextProcessorsDealership;
import com.ubivashka.config.holders.BukkitConfigurationSectionHolder;

public abstract class BukkitConfigurationHolder extends
		ConfigurationHolder<ConfigurationSection, BukkitConfigurationSectionHolder, BukkitConfigurationContext> {

	private static final BukkitConfigurationContextProcessorsDealership PROCESSORS_DEALERSHIP = new BukkitConfigurationContextProcessorsDealership();

	static {
		PROCESSORS_DEALERSHIP.put("default", new DefaultConverter<ConfigurationSection, BukkitConfigurationContext>());
		PROCESSORS_DEALERSHIP.put("enum", new EnumConverter<ConfigurationSection, BukkitConfigurationContext>());
		PROCESSORS_DEALERSHIP.put("holders",
				new ConfigurationHolderConverter<ConfigurationSection, BukkitConfigurationContext, BukkitConfigurationHolder>(
						BukkitConfigurationHolder.class, ConfigurationSection.class));
		PROCESSORS_DEALERSHIP.put("section_converter", new BukkitConfigurationSectionConverter());
	}

	protected void init(ConfigurationSection configurationSection) {
		init(new BukkitConfigurationSectionHolder(configurationSection));
	}

	@Override
	protected ConfigurationContextProcessorsDealership<ConfigurationSection, BukkitConfigurationContext, ? extends IConfigurationContextProcessor<ConfigurationSection, BukkitConfigurationContext>> getConfigurationFieldProcessorsDealership() {
		return PROCESSORS_DEALERSHIP;
	}

	@Override
	protected BukkitConfigurationContext createDefaultConfigurationContext(Class<?> clazz,
			BukkitConfigurationSectionHolder configurationSectionHolder, String configurationPath, Field field) {
		return new BukkitConfigurationContext(clazz, configurationSectionHolder, configurationPath, field);
	}

	public static BukkitConfigurationContextProcessorsDealership getContextProcessorsDealership() {
		return PROCESSORS_DEALERSHIP;
	}

}
