package com.ubivashka.config.holders;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.ubivashka.config.processors.ConfigurationHolder;
import com.ubivashka.config.processors.utils.ReflectionUtil;

public abstract class AbstractConfigurationValue<T> implements IConfigurationValue<T> {
	private final IConfigurationSectionHolder<T> sectionHolder;
	private final String key;

	public AbstractConfigurationValue(IConfigurationSectionHolder<T> sectionHolder, String key) {
		this.sectionHolder = sectionHolder;
		this.key = key;
	}

	public static Object getRealType(@SuppressWarnings("rawtypes") AbstractConfigurationValue configurationValue,
			Class<?> valueClass) {
		if (configurationValue == null)
			return null;
		if (ConfigurationHolder.class.isAssignableFrom(valueClass)) {
			if (!configurationValue.isConfigurationSection())
				return null;
			Constructor<?> constructor = ReflectionUtil
					.getContructor(valueClass,
							configurationValue.getAsConfigurationSection().getOriginalConfigurationSection().getClass())
					.orElse(null);
			if (constructor == null) 
				return null;
			
			try {
				return constructor.newInstance(configurationValue.getAsConfigurationSection().getOriginalConfigurationSection());
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		if (configurationValue.isString() && String.class.isAssignableFrom(valueClass))
			return configurationValue.getAsString();
		if (configurationValue.isConfigurationSection()
				&& IConfigurationSectionHolder.class.isAssignableFrom(valueClass))
			return configurationValue.getAsConfigurationSection();
		if (configurationValue.isCollection() && List.class.isAssignableFrom(valueClass))
			return configurationValue.getAsList();
		if (configurationValue.isInteger() && Integer.class.isAssignableFrom(valueClass))
			return configurationValue.getAsInteger();
		if (configurationValue.isDouble() && Double.class.isAssignableFrom(valueClass))
			return configurationValue.getAsDouble();
		if (configurationValue.isFloat() && Float.class.isAssignableFrom(valueClass))
			return configurationValue.getAsFloat();
		if (configurationValue.isShort() && Short.class.isAssignableFrom(valueClass))
			return configurationValue.getAsShort();
		if (configurationValue.isByte() && Byte.class.isAssignableFrom(valueClass))
			return configurationValue.getAsByte();
		return null;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public IConfigurationSectionHolder<T> getRootConfigurationSection() {
		return sectionHolder;
	}

	@Override
	public Object getConfigurationObject() {
		return sectionHolder.get(key);
	}

	@Override
	public IConfigurationSectionHolder<T> getAsConfigurationSection() {
		return sectionHolder.getConfigurationSection(key);
	}

	@Override
	public <L> List<L> getAsList() {
		return sectionHolder.getList(key);
	}

	@Override
	public String getAsString() {
		return sectionHolder.getString(key);
	}

	@Override
	public Integer getAsInteger() {
		return sectionHolder.getInteger(key);
	}

	@Override
	public Double getAsDouble() {
		return sectionHolder.getDouble(key);
	}

	@Override
	public Float getAsFloat() {
		return sectionHolder.getFloat(key);
	}

	@Override
	public Short getAsShort() {
		return sectionHolder.getShort(key);
	}

	@Override
	public Byte getAsByte() {
		return sectionHolder.getByte(key);
	}

	@Override
	public boolean isConfigurationSection() {
		return sectionHolder.isConfigurationSection(key);
	}

	@Override
	public boolean isCollection() {
		return sectionHolder.isCollection(key);
	}

	@Override
	public boolean isString() {
		return sectionHolder.isString(key);
	}

	@Override
	public boolean isInteger() {
		return sectionHolder.isInteger(key);
	}

	@Override
	public boolean isDouble() {
		return sectionHolder.isDouble(key);
	}

	@Override
	public boolean isFloat() {
		return sectionHolder.isFloat(key);
	}

	@Override
	public boolean isShort() {
		return sectionHolder.isShort(key);
	}

	@Override
	public boolean isByte() {
		return sectionHolder.isByte(key);
	}

}
