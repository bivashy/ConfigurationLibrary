package com.ubivashka.config.converters;

import com.ubivashka.config.contexts.SpigotConfigurationContext;
import com.ubivashka.config.processors.SpigotConfigurationContextProcessor;

public class DefaultConverter extends SpigotConfigurationContextProcessor {

	@Override
	public void process(SpigotConfigurationContext context) {
		if (!isValidContext(context))
			return;
		context.setCurrentObject(context.getConfigurationSectionHolder().get(context.getConfigurationPath()));
	}

	@Override
	public boolean isValidContext(SpigotConfigurationContext context) {
		return context.getCurrentObject() == null;
	}

	@Override
	public byte priority() {
		return 127;
	}

}
