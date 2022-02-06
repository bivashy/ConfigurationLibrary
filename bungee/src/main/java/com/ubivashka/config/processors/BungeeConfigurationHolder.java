package com.ubivashka.config.processors;

import java.lang.reflect.Field;

import com.ubivashka.config.contexts.BungeeConfigurationContext;
import com.ubivashka.config.converters.BungeeConfigurationSectionConverter;
import com.ubivashka.config.converters.ConfigurationHolderConverter;
import com.ubivashka.config.converters.DefaultConverter;
import com.ubivashka.config.converters.EnumConverter;
import com.ubivashka.config.dealerships.BungeeConfigurationContextProcessorsDealership;
import com.ubivashka.config.holders.BungeeConfigurationSectionHolder;

import net.md_5.bungee.config.Configuration;

public abstract class BungeeConfigurationHolder
		extends ConfigurationHolder<Configuration, BungeeConfigurationSectionHolder, BungeeConfigurationContext> {

	private static final BungeeConfigurationContextProcessorsDealership PROCESSORS_DEALERSHIP = new BungeeConfigurationContextProcessorsDealership();

	static {
		PROCESSORS_DEALERSHIP.put("default", new DefaultConverter<Configuration, BungeeConfigurationContext>());
		PROCESSORS_DEALERSHIP.put("enum", new EnumConverter<Configuration, BungeeConfigurationContext>());
		PROCESSORS_DEALERSHIP.put("holders",
				new ConfigurationHolderConverter<Configuration, BungeeConfigurationContext, BungeeConfigurationHolder>(
						BungeeConfigurationHolder.class));
		PROCESSORS_DEALERSHIP.put("section_converter", new BungeeConfigurationSectionConverter());
	}

	protected void init(Configuration configuration) {
		init(new BungeeConfigurationSectionHolder(configuration));
	}

	@Override
	protected ConfigurationContextProcessorsDealership<Configuration, BungeeConfigurationContext, ? extends IConfigurationContextProcessor<Configuration, BungeeConfigurationContext>> getConfigurationFieldProcessorsDealership() {
		return PROCESSORS_DEALERSHIP;
	}

	@Override
	protected BungeeConfigurationContext createDefaultConfigurationContext(Class<?> clazz,
			BungeeConfigurationSectionHolder configurationSectionHolder, String configurationPath, Field field) {
		return new BungeeConfigurationContext(clazz, configurationSectionHolder, configurationPath, field);
	}

	public static BungeeConfigurationContextProcessorsDealership getContextProcessorsDealership() {
		return PROCESSORS_DEALERSHIP;
	}
}
