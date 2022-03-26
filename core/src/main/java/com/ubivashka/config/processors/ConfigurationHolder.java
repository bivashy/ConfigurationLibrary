package com.ubivashka.config.processors;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.ubivashka.config.annotations.ConfigField;
import com.ubivashka.config.annotations.ConverterType;
import com.ubivashka.config.holders.IConfigurationSectionHolder;
import com.ubivashka.config.processors.context.IConfigurationContext;

public abstract class ConfigurationHolder<T, S extends IConfigurationSectionHolder<T>, C extends IConfigurationContext<T>> {
	private S configurationSectionHolder;

	public void init(S configurationSectionHolder) {
		init(configurationSectionHolder, this);
	}

	public void init(S configurationSectionHolder, Object... objects) {
		this.configurationSectionHolder = configurationSectionHolder;
		initFields(Arrays.stream(objects).collect(Collectors.toMap(object -> object.getClass(), Function.identity())));
	}

	protected abstract ConfigurationContextProcessorsDealership<T, C, ? extends IConfigurationContextProcessor<T, C>> getConfigurationFieldProcessorsDealership();

	protected abstract C createDefaultConfigurationContext(Class<?> clazz, S configurationSectionHolder,
			String configurationPath, Field field);

	protected void handleError(Field field, C context, Exception e) {
	}

	private void initFields(Map<Class<?>, Object> classMap) {
		classMap.keySet().forEach(clazz -> {

			while (clazz != null && clazz != Object.class) {

				setupFields(clazz, classMap.get(clazz));

				clazz = clazz.getSuperclass();
			}

		});
	}

	private void setupFields(Class<?> clazz, Object fieldHolder) {
		for (Field field : clazz.getDeclaredFields())
			setupField(field, clazz, fieldHolder);
	}

	private void setupField(Field field, Class<?> clazz, Object fieldHolder) {
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
			field.set(fieldHolder, configurationFieldContext.getCurrentObject());
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
