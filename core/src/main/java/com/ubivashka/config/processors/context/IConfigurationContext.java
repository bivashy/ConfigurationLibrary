package com.ubivashka.config.processors.context;

import java.lang.reflect.Field;

import com.ubivashka.config.holders.IConfigurationSectionHolder;

public interface IConfigurationContext<T> {
	Class<?> getConfigurationHolderClass();

	Class<?> getEntityClass();

	Class<?> getDtoClass();

	IConfigurationSectionHolder<T> getConfigurationSectionHolder();

	String getConfigurationPath();

	Field getField();

	void setCurrentObject(Object object);

	Object getCurrentObject();
}
