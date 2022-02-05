package com.ubivashka.config.converters;

import com.ubivashka.config.processors.IConfigurationContextProcessor;
import com.ubivashka.config.processors.context.IConfigurationContext;

public class DefaultConverter<T, C extends IConfigurationContext<T>> implements IConfigurationContextProcessor<T, C> {

	@Override
	public void process(C context) {
		if (!isValidContext(context))
			return;
		context.setCurrentObject(context.getConfigurationSectionHolder().get(context.getConfigurationPath()));
	}

	@Override
	public boolean isValidContext(C context) {
		return context.getCurrentObject() == null;
	}

	@Override
	public byte priority() {
		return -128;
	}

}
