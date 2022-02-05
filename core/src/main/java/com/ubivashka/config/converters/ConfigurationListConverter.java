package com.ubivashka.config.converters;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.ubivashka.config.processors.IConfigurationContextProcessor;
import com.ubivashka.config.processors.context.IConfigurationContext;
import com.ubivashka.config.processors.utils.ReflectionUtil;

public abstract class ConfigurationListConverter<T, C extends IConfigurationContext<T>, V, E>
		implements IConfigurationContextProcessor<T, C> {
	protected final Class<E> valueClass;

	public ConfigurationListConverter(Class<E> valueClass) {
		this.valueClass = valueClass;
	}

	@Override
	public void process(C context) {
		V configurationValue = getConfigurationValue(context);
		if (configurationValue == null) 
			return;
		if (!ReflectionUtil.isCollection(context.getField().getType())) {
			context.setCurrentObject(valueToEntity(context, configurationValue));
			return;
		}
		List<V> configurationValues = getConfigurationValues(context);
		if (configurationValues == null) {
			context.setCurrentObject(Arrays.asList(valueToEntity(context, configurationValue)));
			return;
		}
		context.setCurrentObject(
				configurationValues.stream().map(value -> valueToEntity(context, value)).collect(Collectors.toList()));
	}

	@Override
	public boolean isValidContext(C context) {
		return valueClass.isAssignableFrom(ReflectionUtil.getRealType(context.getField()));
	}

	protected abstract E valueToEntity(C context, V valueObject);

	protected abstract V getConfigurationValue(C context);

	protected abstract List<V> getConfigurationValues(C context);

}
