package com.ubivashka.config.processors;

import java.lang.reflect.Field;

import com.ubivashka.config.annotations.ConfigField;
import com.ubivashka.config.annotations.ConverterType;
import com.ubivashka.config.holders.IConfigurationSectionHolder;
import com.ubivashka.config.processors.context.IConfigurationContext;

public abstract class ConfigurationHolder<T, S extends IConfigurationSectionHolder<T>, C extends IConfigurationContext<T>> {
	private S configurationSectionHolder;

	public void init(S configurationSectionHolder) {
		this.configurationSectionHolder = configurationSectionHolder;
		setupFields();
	}

	protected abstract ConfigurationContextProcessorsDealership<T, C, ? extends IConfigurationContextProcessor<T, C>> getConfigurationFieldProcessorsDealership();

	protected abstract C createDefaultConfigurationContext(Class<?> clazz, S configurationSectionHolder,
			String configurationPath, Field field);

	protected void handleError(Field field, C context, Exception e) {
	}

	@SuppressWarnings("unchecked")
	private void setupFields() {
		Class<?> clazz = getClass();
		while (clazz != null && ConfigurationHolder.class.isAssignableFrom(clazz)) {

			setupFields((Class<? extends ConfigurationHolder<T, S, C>>) getClass());

			Class<?> superClazz = clazz.getSuperclass();
			while (superClazz != null && ConfigurationHolder.class.isAssignableFrom(superClazz)) {

				setupFields((Class<? extends ConfigurationHolder<T, S, C>>) superClazz);

				superClazz = superClazz.getSuperclass();
			}

			clazz = clazz.getEnclosingClass();
		}
	}

	private void setupFields(Class<? extends ConfigurationHolder<T, S, C>> clazz) {
		for (Field f : clazz.getDeclaredFields())
			setupField(f, clazz);
	}

	private void setupField(Field field, Class<? extends ConfigurationHolder<T, S, C>> clazz) {
		if (!field.isAnnotationPresent(ConfigField.class))
			return;

		boolean fieldAccesible = field.isAccessible();
		field.setAccessible(true);

		ConfigField configurationFieldAnnotation = field.getAnnotation(ConfigField.class);
		String configurationPath = getConfigurationPath(configurationFieldAnnotation, field.getName());
		C configurationFieldContext = createDefaultConfigurationContext(clazz, configurationSectionHolder,
				configurationPath, field);
		IConfigurationContextProcessor<T, C> processor = getConfigurationFieldProcessorsDealership();

		if (field.isAnnotationPresent(ConverterType.class)) {

			ConverterType converterType = field.getAnnotation(ConverterType.class);

			if (getConfigurationFieldProcessorsDealership().containsKey(converterType.value()))
				processor = getConfigurationFieldProcessorsDealership()
						.getOrDefault(field.getAnnotation(ConverterType.class).value(), null);

		}

		processor.process(configurationFieldContext);

		if (configurationFieldContext.getCurrentObject() == null)
			return;

		try {
			field.set(this, configurationFieldContext.getCurrentObject());
		} catch (IllegalArgumentException | IllegalAccessException e) {
			handleError(field, configurationFieldContext, e);
		}

		field.setAccessible(fieldAccesible);
	}

	private String getConfigurationPath(ConfigField configurationFieldAnnotation, String defaultValue) {
		String configPath = configurationFieldAnnotation.path();

		if (configPath == null || configPath.isEmpty())
			configPath = defaultValue;

		return configPath;
	}
}
