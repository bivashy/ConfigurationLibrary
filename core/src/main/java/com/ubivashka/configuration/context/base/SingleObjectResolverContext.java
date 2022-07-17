package com.ubivashka.configuration.context.base;

import com.ubivashka.configuration.context.ConfigurationFieldResolverContext;

@Deprecated
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
