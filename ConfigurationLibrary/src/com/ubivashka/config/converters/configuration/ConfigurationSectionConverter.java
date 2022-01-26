package com.ubivashka.config.converters.configuration;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.bukkit.configuration.ConfigurationSection;

import com.ubivashka.config.converters.Converter;

public class ConfigurationSectionConverter extends Converter<ConfigurationSection, Map<String, ConfigurationValue<?>>> {

	public ConfigurationSectionConverter() {
		super(ConfigurationSectionConverter::fromConfigurationSection, ConfigurationSectionConverter::fromMap);
	}

	private static Map<String, ConfigurationValue<?>> fromConfigurationSection(
			ConfigurationSection configurationSection) {
		return configurationSection.getKeys(false).stream().collect(Collectors.toMap(Function.identity(),
				key -> ConfigurationValueTypes.getValidConfigurationValueType(configurationSection, key)));
	}

	private static ConfigurationSection fromMap(Map<String, ConfigurationValue<?>> map) {
		throw new UnsupportedOperationException();
	}

}
