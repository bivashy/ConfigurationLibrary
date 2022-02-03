package com.ubivashka.config.converters;

import com.ubivashka.config.contexts.BungeeConfigurationContext;
import com.ubivashka.config.processors.BungeeConfigurationContextProcessor;

public class DefaultConverter extends BungeeConfigurationContextProcessor {

	@Override
	public void process(BungeeConfigurationContext context) {
		context.setCurrentObject(context.getConfigurationSectionHolder().get(context.getConfigurationPath()));
	}

	@Override
	public boolean isValidContext(BungeeConfigurationContext context) {
		return context.getCurrentObject() == null;
	}

	@Override
	public byte priority() {
		return 127;
	}

}
