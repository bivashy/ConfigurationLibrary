package com.ubivashka.config.processors.context;

import java.lang.reflect.Field;

import com.ubivashka.config.holders.IConfigurationSectionHolder;
import com.ubivashka.config.processors.utils.ReflectionUtil;

public abstract class AbstractConfigurationContext<T> implements IConfigurationContext<T> {
	protected final Class<?> clazz;
	protected final IConfigurationSectionHolder<T> configurationSectionHolder;
	protected final String configurationPath;
	protected final Field field;
	protected Class<?> entityClass, dtoClass;
	protected Object currentObject = null;

	public AbstractConfigurationContext(Class<?> clazz, IConfigurationSectionHolder<T> configurationSectionHolder,
			String configurationPath, Field field) {
		this.clazz = clazz;
		this.configurationSectionHolder = configurationSectionHolder;
		this.configurationPath = configurationPath;
		this.field = field;

		this.entityClass = ReflectionUtil.getRealType(field);
		if (configurationSectionHolder.contains(configurationPath))
			this.dtoClass = configurationSectionHolder.isCollection(configurationPath) ? String.class
					: configurationSectionHolder.get(configurationPath).getClass();
	}

	@Override
	public Class<?> getConfigurationHolderClass() {
		return clazz;
	}

	@Override
	public Class<?> getEntityClass() {
		return entityClass;
	}

	@Override
	public Class<?> getDtoClass() {
		return dtoClass;
	}

	@Override
	public IConfigurationSectionHolder<T> getConfigurationSectionHolder() {
		return configurationSectionHolder;
	}

	@Override
	public String getConfigurationPath() {
		return configurationPath;
	}

	@Override
	public Field getField() {
		return field;
	}

	@Override
	public Object getCurrentObject() {
		return currentObject;
	}

	@Override
	public void setCurrentObject(Object currentObject) {
		this.currentObject = currentObject;
	}

}
