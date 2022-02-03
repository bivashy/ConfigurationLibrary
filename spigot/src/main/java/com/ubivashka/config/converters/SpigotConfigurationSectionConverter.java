package com.ubivashka.config.converters;

import java.lang.reflect.ParameterizedType;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.configuration.ConfigurationSection;

import com.ubivashka.config.configuration.SpigotConfigurationValue;
import com.ubivashka.config.contexts.SpigotConfigurationContext;
import com.ubivashka.config.holders.AbstractConfigurationValue;
import com.ubivashka.config.holders.IConfigurationSectionHolder;
import com.ubivashka.config.processors.SpigotConfigurationContextProcessor;
import com.ubivashka.config.processors.utils.ReflectionUtil;

public class SpigotConfigurationSectionConverter extends SpigotConfigurationContextProcessor {

	@Override
	public void process(SpigotConfigurationContext context) {
		List<Class<?>> generics = ReflectionUtil
				.getParameterizedTypes((ParameterizedType) context.getField().getGenericType());
		Class<?> classType = generics.get(1);

		List<SpigotConfigurationValue> rawValues = getValues(context.getConfigurationSectionHolder());
		Map<String, Object> configurationValues = rawValues.stream()
				.map(value -> new AbstractMap.SimpleEntry<String, Object>(value.getKey(),
						AbstractConfigurationValue.getRealType(value, classType)))
				.filter(entry -> entry.getKey() != null && entry.getValue() != null)
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
		context.setCurrentObject(configurationValues);
	}

	@Override
	public boolean isValidContext(SpigotConfigurationContext context) {
		Class<?> entityClass = context.getEntityClass();
		if (!Map.class.isAssignableFrom(entityClass))
			return false;
		if (!(context.getField().getGenericType() instanceof ParameterizedType))
			return false;
		List<Class<?>> generics = ReflectionUtil
				.getParameterizedTypes((ParameterizedType) context.getField().getGenericType());

		Class<?> mapFirstArgument = generics.get(0);
		if (!String.class.isAssignableFrom(mapFirstArgument))
			return false;
		return true;
	}

	public List<SpigotConfigurationValue> getValues(IConfigurationSectionHolder<ConfigurationSection> sectionHolder) {
		List<SpigotConfigurationValue> values = new ArrayList<>();

		Set<String> keys = sectionHolder.getKeys();
		keys.forEach((key) -> {
			SpigotConfigurationValue newValue = new SpigotConfigurationValue(sectionHolder, key);
			values.add(newValue);
		});
		return values;
	}

}
