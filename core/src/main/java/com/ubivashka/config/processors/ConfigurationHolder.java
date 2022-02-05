package com.ubivashka.config.processors;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import com.ubivashka.config.annotations.ConfigField;
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
			try {
				setupField(f, clazz);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
	}

	private void setupField(Field field, Class<? extends ConfigurationHolder<T, S, C>> clazz)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (!field.isAnnotationPresent(ConfigField.class))
			return;

		boolean fieldAccesible = field.isAccessible();
		field.setAccessible(true);

		ConfigField configurationFieldAnnotation = field.getAnnotation(ConfigField.class);
		String configurationPath = getConfigurationPath(configurationFieldAnnotation, field.getName());
		C configurationFieldContext = createDefaultConfigurationContext(clazz, configurationSectionHolder,
				configurationPath, field);
		getConfigurationFieldProcessorsDealership().process(configurationFieldContext);

		if (configurationFieldContext.getCurrentObject() == null)
			return;
		if (!field.getType().isAssignableFrom(configurationFieldContext.getCurrentObject().getClass())) 
			return;
		
		field.set(this, configurationFieldContext.getCurrentObject());
		field.setAccessible(fieldAccesible);
	}

	private String getConfigurationPath(ConfigField configurationFieldAnnotation, String defaultValue) {
		String configPath = configurationFieldAnnotation.path();

		if (configPath == null || configPath.isEmpty())
			configPath = defaultValue;

		return configPath;
	}
}
