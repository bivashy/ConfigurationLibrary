package com.ubivashka.config.converters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ubivashka.config.holders.IConfigurationSectionHolder;
import com.ubivashka.config.processors.IConfigurationContextProcessor;
import com.ubivashka.config.processors.context.IConfigurationContext;

public class DefaultConverter<T, C extends IConfigurationContext<T>> implements IConfigurationContextProcessor<T, C> {

	private static final List<PrimitiveObjectConverter> PRIMITIVE_OBJECT_CONVERTERS = new ArrayList<>(
			Arrays.asList(new PrimitiveObjectConverter(Boolean.TYPE, Boolean.class) {

				@Override
				public Object getObject(IConfigurationSectionHolder<?> sectionHolder, String key) {
					return sectionHolder.getBoolean(key);
				}

			}, new PrimitiveObjectConverter(Byte.TYPE, Byte.class) {

				@Override
				public Object getObject(IConfigurationSectionHolder<?> sectionHolder, String key) {
					return sectionHolder.getByte(key);
				}

			}, new PrimitiveObjectConverter(Character.TYPE, Character.class) {

				@Override
				public Object getObject(IConfigurationSectionHolder<?> sectionHolder, String key) {
					return Character.valueOf(sectionHolder.getString(key).charAt(0));
				}

			}, new PrimitiveObjectConverter(Short.TYPE, Short.class) {

				@Override
				public Object getObject(IConfigurationSectionHolder<?> sectionHolder, String key) {
					return sectionHolder.getShort(key);
				}

			}, new PrimitiveObjectConverter(Integer.TYPE, Integer.class) {

				@Override
				public Object getObject(IConfigurationSectionHolder<?> sectionHolder, String key) {
					return sectionHolder.getInteger(key);
				}

			}, new PrimitiveObjectConverter(Long.TYPE, Long.class) {

				@Override
				public Object getObject(IConfigurationSectionHolder<?> sectionHolder, String key) {
					return sectionHolder.getDouble(key).longValue();
				}

			}, new PrimitiveObjectConverter(Float.TYPE, Float.class) {

				@Override
				public Object getObject(IConfigurationSectionHolder<?> sectionHolder, String key) {
					return sectionHolder.getFloat(key);
				}

			}, new PrimitiveObjectConverter(Double.TYPE, Double.class) {

				@Override
				public Object getObject(IConfigurationSectionHolder<?> sectionHolder, String key) {
					return sectionHolder.getDouble(key).doubleValue();
				}

			}));

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
		return context.getCurrentObject() == null
				&& context.getConfigurationSectionHolder().contains(context.getConfigurationPath());
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
		PrimitiveObjectConverter primitiveObjectConverter = PRIMITIVE_OBJECT_CONVERTERS.stream()
				.filter(converter -> converter.isValidClass(fieldClass)).findFirst().orElse(null);
		if (primitiveObjectConverter == null)
			return null;
		return primitiveObjectConverter.getObject(sectionHolder, key);
	}

	private abstract static class PrimitiveObjectConverter {
		private final Class<?> typeClass, wrapperClass;

		private PrimitiveObjectConverter(Class<?> typeClass, Class<?> wrapperClass) {
			this.typeClass = typeClass;
			this.wrapperClass = wrapperClass;
		}

		public boolean isValidClass(Class<?> clazz) {
			return clazz.equals(typeClass) || clazz.equals(wrapperClass);
		}

		public abstract Object getObject(IConfigurationSectionHolder<?> sectionHolder, String key);

	}
}
