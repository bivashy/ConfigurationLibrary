package com.ubivashka.config.processors;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.configuration.ConfigurationSection;

import com.ubivashka.config.converters.EnumConverter;
import com.ubivashka.config.converters.IConverter;
import com.ubivashka.config.processors.utils.ReflectionUtil;

public abstract class ConfigurationHolder {
	private static final List<IConverter<?, ?>> CONVERTERS = new ArrayList<>();

	private ConfigurationSection configurationSection;

	public void init(ConfigurationSection configurationSection) {
		this.configurationSection = configurationSection;
		setupFields();
	}

	@SuppressWarnings("unchecked")
	private void setupFields() {
		Class<?> clazz = getClass();

		setupFields(getClass());

		// Init all super classes
		Class<?> superClazz = clazz.getSuperclass();
		while (!superClazz.equals(Object.class) && superClazz != null) {
			setupFields((Class<? extends ConfigurationHolder>) superClazz);
			superClazz = superClazz.getSuperclass();
		}

	}

	private void setupFields(Class<? extends ConfigurationHolder> clazz) {
		for (Field f : clazz.getDeclaredFields())
			setupField(f, clazz);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean setupField(Field field, Class<? extends ConfigurationHolder> clazz) {
		boolean fieldAccesible = field.isAccessible();
		field.setAccessible(true);

		if (!field.isAnnotationPresent(ConfigField.class)) {
			field.setAccessible(fieldAccesible);
			return false;
		}

		ConfigField configurationFieldAnnotation = field.getAnnotation(ConfigField.class);
		String configPath = getConfigurationPath(configurationFieldAnnotation, field.getName());

		if (!configurationSection.contains(configPath)) {
			if (configurationFieldAnnotation.important())
				configurationFieldAnnotation.importantAction().doAction(configPath, configurationSection);
			field.setAccessible(fieldAccesible);
			return false;
		}

		Object value = configurationSection.get(configPath);
		Class<?> valueClass = configurationSection.isList(configPath) ? String.class
				: ReflectionUtil.getRealType(value.getClass());
		IConverter converter = getConverter(valueClass, ReflectionUtil.getRealType(field));
		if (converter != null) {
			boolean isFieldCollecton = ReflectionUtil.isCollection(field.getType());
			boolean isValueCollection = value instanceof Collection;
			if (isFieldCollecton && isValueCollection)
				value = converter.createFromDtos((Collection) value);
			if (isFieldCollecton && !isValueCollection)
				value = converter.createFromDtos(Arrays.asList(value));
			if (!isFieldCollecton && !isValueCollection)
				value = converter.convertFromDto(value);
		}

		try {
			field.set(this, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		field.setAccessible(fieldAccesible);
		return true;
	}

	private String getConfigurationPath(ConfigField configurationFieldAnnotation, String defaultValue) {
		String configPath = configurationFieldAnnotation.path();
		if (configPath == null || configPath.isEmpty())
			configPath = defaultValue;

		return configPath;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <T, U> IConverter<T, U> getConverter(Class<T> dtoClass, Class<U> entityClass) {
		if(entityClass.isEnum())
			return new EnumConverter(entityClass);
		List<IConverter<?, ?>> findedConverters = CONVERTERS.stream().filter((converter) -> {

			Class<?> converterDtoClass = getGenericClass(converter, 0);

			Class<?> converterEntityClass = getGenericClass(converter, 1);
			return converterDtoClass.isAssignableFrom(dtoClass) && converterEntityClass.isAssignableFrom(entityClass);
		}).collect(Collectors.toList());

		return (IConverter<T, U>) findedConverters.stream().findFirst().orElse(null);
	}

	private Class<?> getGenericClass(IConverter<?, ?> converter, int index) {
		if (index == 0 && converter.getDtoClass() != null)
			return converter.getDtoClass();
		if (index == 1 && converter.getEntityClass() != null)
			return converter.getEntityClass();

		Class<?>[] interfaceGenericClasses = ReflectionUtil.getGenericInterfaceParameters(converter.getClass(),
				IConverter.class);
		if (interfaceGenericClasses.length != 0)
			return interfaceGenericClasses[index];

		Class<?>[] superClassGenericClasses = ReflectionUtil.getSuperClassGenerics(converter.getClass());
		if (superClassGenericClasses.length != 0)
			return superClassGenericClasses[index];

		throw new IllegalArgumentException("Generic classes not found!");
	}

	public static void addConverter(IConverter<?, ?> converter) {
		if (converter == null)
			return;
		if (CONVERTERS.contains(converter))
			return;
		CONVERTERS.add(converter);
	}

	public static void registerDefaultConverters() {
		
	}
}
