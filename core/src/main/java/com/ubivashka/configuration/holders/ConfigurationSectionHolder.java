package com.ubivashka.configuration.holders;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.ubivashka.function.CastableInterface;

public interface ConfigurationSectionHolder extends CastableInterface<ConfigurationSectionHolder> {
	static ConfigurationSectionHolder empty() {
		return new ConfigurationSectionHolder() {
			@Override
			public Object get(String key) {
				return null;
			}

			@Override
			public String getString(String key) {
				return null;
			}

			@Override
			public Boolean getBoolean(String key) {
				return null;
			}

			@Override
			public Integer getInteger(String key) {
				return null;
			}

			@Override
			public Double getDouble(String key) {
				return null;
			}

			@Override
			public ConfigurationSectionHolder getSection(String key) {
				return null;
			}

			@Override
			public <L> List<L> getList(String key) {
				return Collections.emptyList();
			}

			@Override
			public boolean contains(String key) {
				return false;
			}

			@Override
			public boolean isConfigurationSection(String key) {
				return false;
			}

			@Override
			public boolean isCollection(String key) {
				return false;
			}

			@Override
			public boolean isString(String key) {
				return false;
			}

			@Override
			public boolean isBoolean(String key) {
				return false;
			}

			@Override
			public boolean isInteger(String key) {
				return false;
			}

			@Override
			public boolean isDouble(String key) {
				return false;
			}

			@Override
			public Set<String> getKeys() {
				return Collections.emptySet();
			}

			@Override
			public Object getOriginalHolder() {
				return null;
			}
		};
	}

    /**
	 * Returns object if configuration field serialized
	 *
	 * @param key for
	 * @return object in configuration that can be anyting.
	 */
	Object get(String key);

	String getString(String key);

	Boolean getBoolean(String key);

	Integer getInteger(String key);

	Double getDouble(String key);

	default Float getFloat(String key) {
		return getDouble(key).floatValue();
	}

	default Short getShort(String key) {
		return getInteger(key).shortValue();
	}

	default Byte getByte(String key) {
		return getInteger(key).byteValue();
	}

	ConfigurationSectionHolder getSection(String key);

	<L> List<L> getList(String key);

	boolean contains(String key);

	boolean isConfigurationSection(String key);

	boolean isCollection(String key);

	boolean isString(String key);

	boolean isBoolean(String key);

	boolean isInteger(String key);

	boolean isDouble(String key);

	default boolean isFloat(String key) {
		return isDouble(key);
	}

	default boolean isShort(String key) {
		return isInteger(key);
	}

	default boolean isByte(String key) {
		return isInteger(key);
	}

	/**
	 * Returns keys that exists in this configuration section holder.
	 *
	 * @return keys in this configuration section
	 */
	Set<String> getKeys();

	/**
	 * Returns original section holder. Useful for section null check
	 *
	 * @return original section holder
	 */
	Object getOriginalHolder();
}
