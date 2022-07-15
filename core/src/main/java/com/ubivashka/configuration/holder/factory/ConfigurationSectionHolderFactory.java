package com.ubivashka.configuration.holder.factory;

import com.ubivashka.configuration.holder.ConfigurationSectionHolder;

public interface ConfigurationSectionHolderFactory<T> {
	ConfigurationSectionHolder wrap(T object);
}
