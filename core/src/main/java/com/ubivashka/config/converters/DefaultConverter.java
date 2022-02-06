package com.ubivashka.config.converters;

import com.ubivashka.config.holders.IConfigurationSectionHolder;
import com.ubivashka.config.processors.IConfigurationContextProcessor;
import com.ubivashka.config.processors.context.IConfigurationContext;

public class DefaultConverter<T, C extends IConfigurationContext<T>> implements IConfigurationContextProcessor<T, C> {

	@Override
	public void process(C context) {
		if (!isValidContext(context))
			return;
		Object contextObject = getContextObject(context);
		if (contextObject == null)
			return;
		context.setCurrentObject(contextObject);
	}

	@Override
	public boolean isValidContext(C context) {
		return context.getCurrentObject() == null;
	}

	@Override
	public byte priority() {
		return -127;
	}

	private Object getContextObject(C context) {
		Class<?> fieldClass = context.getField().getType();
		String key = context.getConfigurationPath();
		IConfigurationSectionHolder<T> sectionHolder = context.getConfigurationSectionHolder();
		if (!fieldClass.isPrimitive() && sectionHolder.get(key).getClass().isAssignableFrom(fieldClass))
			return sectionHolder.get(key);
		if (fieldClass.equals(Boolean.TYPE) && sectionHolder.isBoolean(key))
			return sectionHolder.getBoolean(key);
		if (fieldClass.equals(Byte.TYPE) && sectionHolder.isByte(key))
			return sectionHolder.getByte(key);
		if (fieldClass.equals(Character.TYPE) && sectionHolder.isString(key))
			return Character.valueOf(sectionHolder.getString(key).charAt(0));
		if (fieldClass.equals(Short.TYPE) && sectionHolder.isShort(key))
			return sectionHolder.getShort(key);
		if (fieldClass.equals(Integer.TYPE) && sectionHolder.isInteger(key))
			return sectionHolder.getInteger(key);
		if (fieldClass.equals(Long.TYPE) && sectionHolder.isDouble(key))
			return sectionHolder.getDouble(key).longValue();
		if (fieldClass.equals(Float.TYPE) && sectionHolder.isFloat(key))
			return sectionHolder.getFloat(key);
		if (fieldClass.equals(Double.TYPE) && sectionHolder.isDouble(key))
			return sectionHolder.getDouble(key);
		return null;
	}
}
