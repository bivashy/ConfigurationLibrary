package com.ubivashka.config.processors.configfield.context;

import java.lang.reflect.Field;

import com.ubivashka.config.annotations.ConfigField;
import com.ubivashka.config.holders.IConfigurationSectionHolder;
import com.ubivashka.config.processors.ConfigurationHolder;
import com.ubivashka.config.processors.context.AbstractConfigurationContext;
import com.ubivashka.config.processors.context.IConfigurationContext;

public abstract class AbstractConfigFieldContext<T>
		extends AbstractConfigurationContext<T> implements IConfigFieldContext<T> {

	private final ConfigField configField;

	public AbstractConfigFieldContext(Class<? extends ConfigurationHolder<T, ? extends IConfigurationSectionHolder<T>, ? extends IConfigurationContext<T>>> clazz,
			IConfigurationSectionHolder<T> configurationSectionHolder, String configurationPath, Field field) {
		super(clazz, configurationSectionHolder, configurationPath, field);
		this.configField = field.getAnnotation(ConfigField.class);
	}

	@Override
	public ConfigField getConfigField() {
		return configField;
	}

}
