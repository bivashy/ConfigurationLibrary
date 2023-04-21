package com.bivashy.configuration.holder.factory;

import com.bivashy.configuration.holder.ConfigurationSectionHolder;

public interface ConfigurationSectionHolderFactory<T> {
	ConfigurationSectionHolder wrap(T object);
}
