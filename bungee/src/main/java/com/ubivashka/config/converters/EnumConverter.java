package com.ubivashka.config.converters;

public class EnumConverter<T extends Enum<T>> implements IConverter<String, Enum<T>> {
	private final Class<T> enumClass;

	public EnumConverter(Class<T> enumClass) {
		this.enumClass = enumClass;
	}

	@Override
	public Enum<T> convertFromDto(String dto) {
		return Enum.valueOf(enumClass, dto.toUpperCase());
	}

	@Override
	public String convertFromEntity(Enum<T> entity) {
		return entity.name();
	}

	@Override
	public Class<String> getDtoClass() {
		return String.class;
	}

	@Override
	public Class<T> getEntityClass() {
		return enumClass;
	}

}
