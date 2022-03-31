package com.ubivashka.configuration.wrappers;

import com.ubivashka.configuration.holders.ConfigurationSectionHolder;

public interface ConfigurationHolderWrapper<T> {
	ConfigurationSectionHolder wrap(T object);
}
