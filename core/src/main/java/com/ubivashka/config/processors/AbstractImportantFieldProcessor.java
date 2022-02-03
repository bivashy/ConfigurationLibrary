package com.ubivashka.config.processors;

import com.ubivashka.config.annotations.ImportantField;
import com.ubivashka.config.processors.context.IConfigurationContext;

public abstract class AbstractImportantFieldProcessor<T>
		implements IConfigurationContextProcessor<T, IConfigurationContext<T>> {

	@Override
	public void process(IConfigurationContext<T> context) {
		if (!isValidContext(context))
			return;
		if (context.getConfigurationSectionHolder().contains(context.getConfigurationPath()))
			return;
		onImportantFieldError(context);
	}

	@Override
	public boolean isValidContext(IConfigurationContext<T> context) {
		return context.getField().isAnnotationPresent(ImportantField.class);
	}

	@Override
	public byte priority() {
		return 127;
	}
	
	public abstract void onImportantFieldError(IConfigurationContext<T> context);
}
