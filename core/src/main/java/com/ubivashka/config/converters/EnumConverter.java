package com.ubivashka.config.converters;

import java.util.List;

import com.ubivashka.config.processors.context.IConfigurationContext;

@SuppressWarnings("rawtypes")
public class EnumConverter<T, C extends IConfigurationContext<T>>
		extends ConfigurationListConverter<T, C, String, Enum> {
	public EnumConverter() {
		super(Enum.class);
	}

	@Override
	protected Enum<?> valueToEntity(C context, String valueObject) {
		return createEnum(context.getEntityClass(), valueObject);
	}

	@Override
	protected String getConfigurationValue(C context) {
		return context.getConfigurationSectionHolder().getString(context.getConfigurationPath());
	}

	@Override
	protected List<String> getConfigurationValues(C context) {
		return context.getConfigurationSectionHolder().getList(context.getConfigurationPath());
	}

	private <E extends Enum<E>> Enum<E> createEnum(Class enumType, String string) {
		@SuppressWarnings("unchecked")
		Enum<E> newEnum = Enum.valueOf(enumType, string);
		return newEnum;
	}

	@Override
	public byte priority() {
		return -128;
	}

	
}
