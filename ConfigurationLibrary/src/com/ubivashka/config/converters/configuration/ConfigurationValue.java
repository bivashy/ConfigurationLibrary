package com.ubivashka.config.converters.configuration;

import org.bukkit.configuration.ConfigurationSection;

import com.ubivashka.config.converters.configuration.ConfigurationValueTypes.ConfigurationTypes;
import com.ubivashka.config.processors.utils.ReflectionUtil;

public interface ConfigurationValue<T> {
	default T castObject(Object object) {
		@SuppressWarnings("unchecked")
		Class<T> genericClazz = (Class<T>) ReflectionUtil.getInterfaceGenerics(getClass())[0];
		return genericClazz.cast(object);
	}

	ConfigurationTypes getType();
	
	boolean isValid(ConfigurationSection section, String key);
}
