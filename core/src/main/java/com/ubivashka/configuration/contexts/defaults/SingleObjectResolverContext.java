package com.ubivashka.configuration.contexts.defaults;

import com.ubivashka.configuration.context.ConfigurationFieldResolverContext;

public class SingleObjectResolverContext extends ConfigurationFieldResolverContextWrapper {
	private final Object configurationValue;

	public SingleObjectResolverContext(ConfigurationFieldResolverContext wrapper, Object configurationValue) {
		super(wrapper);
		this.configurationValue = configurationValue;
	}

	@SuppressWarnings("unchecked")
	public <T> T getConfigurationValue() {
		return (T) configurationValue;
	}
}
