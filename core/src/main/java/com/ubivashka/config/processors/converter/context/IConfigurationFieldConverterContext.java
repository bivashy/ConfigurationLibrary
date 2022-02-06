package com.ubivashka.config.processors.converter.context;

import com.ubivashka.config.processors.context.IConfigurationContext;

public interface IConfigurationFieldConverterContext<T> extends IConfigurationContext<T> {
	Class<?> getEntityClass();

	Class<?> getDtoClass();
}
