package com.ubivashka.config.converters;

import com.ubivashka.config.contexts.SpigotConfigurationContext;
import com.ubivashka.config.processors.SpigotConfigurationContextProcessor;

public class SpigotEnumConverter extends SpigotConfigurationContextProcessor {

	@Override
	public void process(SpigotConfigurationContext context) {
		context.setCurrentObject(createEnum(context.getEntityClass(),
				context.getConfigurationSectionHolder().getString(context.getConfigurationPath())));
	}

	@Override
	public boolean isValidContext(SpigotConfigurationContext context) {
		return context.getEntityClass() != null && context.getEntityClass().isEnum()
				&& context.getConfigurationSectionHolder().isString(context.getConfigurationPath());
	}

	private <E extends Enum<E>> Enum<E> createEnum(@SuppressWarnings("rawtypes") Class enumType, String string) {
		@SuppressWarnings("unchecked")
		Enum<E> newEnum = Enum.valueOf(enumType, string);
		return newEnum;
	}

}
