package com.ubivashka.configuration.wrapper;

import com.ubivashka.configuration.holder.ConfigurationSectionHolder;

public interface ConfigurationHolderWrapper<T> {
	ConfigurationSectionHolder wrap(T object);
}
