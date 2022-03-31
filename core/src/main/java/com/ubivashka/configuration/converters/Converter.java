package com.ubivashka.configuration.converters;

public interface Converter<T> {
	T convert(Object value);

	@SuppressWarnings("unchecked")
	static <T> Converter<T> identity() {
		return (object) -> (T) object;
	}
}
