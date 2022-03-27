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

import com.ubivashka.config.configuration.BukkitConfigurationValue;
import com.ubivashka.config.contexts.BukkitConfigurationContext;
import com.ubivashka.config.holders.AbstractConfigurationValue;
import com.ubivashka.config.holders.IConfigurationSectionHolder;
import com.ubivashka.config.processors.BukkitConfigurationContextProcessor;
import com.ubivashka.config.processors.utils.ReflectionUtil;

public class BukkitConfigurationSectionConverter extends BukkitConfigurationContextProcessor {

	@Override
	public void process(BukkitConfigurationContext context) {
		List<Class<?>> generics = ReflectionUtil
				.getParameterizedTypes((ParameterizedType) context.getField().getGenericType());
		Class<?> classType = generics.get(1);

		List<BukkitConfigurationValue> rawValues = getValues(context.getConfigurationSectionHolder());
		Map<String, Object> configurationValues = rawValues.stream()
				.map(value -> new AbstractMap.SimpleEntry<String, Object>(value.getKey(),
						AbstractConfigurationValue.getRealType(value, classType)))
				.filter(entry -> entry.getKey() != null && entry.getValue() != null)
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
		context.setCurrentObject(configurationValues);
	}

	@Override
	public boolean isValidContext(BukkitConfigurationContext context) {
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

	public List<BukkitConfigurationValue> getValues(IConfigurationSectionHolder<ConfigurationSection> sectionHolder) {
		List<BukkitConfigurationValue> values = new ArrayList<>();

		Set<String> keys = sectionHolder.getKeys();
		keys.forEach((key) -> {
			BukkitConfigurationValue newValue = new BukkitConfigurationValue(sectionHolder, key);
			values.add(newValue);
		});
		return values;
	}

}
