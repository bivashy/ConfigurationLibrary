package com.ubivashka.config.processors;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.HashMap;

import com.ubivashka.config.annotations.ConverterType;
import com.ubivashka.config.processors.context.IConfigurationContext;

public abstract class ConfigurationContextProcessorsDealership<T, C extends IConfigurationContext<T>, V extends IConfigurationContextProcessor<T, C>>
		extends HashMap<String, V> implements IConfigurationContextProcessor<T, C> {

	private static final long serialVersionUID = 5923042057389682486L;

	@Override
	public void process(C configurationFieldContext) {
		Field field = configurationFieldContext.getField();

		if (field.isAnnotationPresent(ConverterType.class)) {
			ConverterType converterType = field.getAnnotation(ConverterType.class);
			IConfigurationContextProcessor<T, C> processor = getOrDefault(converterType.value(), null);
			if (processor == null)
				throw new IllegalArgumentException("Converter type with name " + converterType.value() + " not exists");
			processor.process(configurationFieldContext);
			return;
		}
		values().stream().filter(processor -> processor.isValidContext(configurationFieldContext))
				.sorted(new Comparator<IConfigurationContextProcessor<T, C>>() {

					@Override
					public int compare(IConfigurationContextProcessor<T, C> firstContext,
							IConfigurationContextProcessor<T, C> secondContext) {
						return Integer.compare(secondContext.priority(), firstContext.priority());
					}

				}).forEach(processor -> processor.process(configurationFieldContext));
		;
	}

	@Override
	public boolean isValidContext(C context) {
		return values().stream().anyMatch(processor -> processor.isValidContext(context));
	}

}
