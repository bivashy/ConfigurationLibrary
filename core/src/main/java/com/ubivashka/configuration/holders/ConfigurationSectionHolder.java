package com.ubivashka.configuration.holders;

import java.util.List;
import java.util.Set;

import com.ubivashka.configuration.ConfigurationProcessor;
import com.ubivashka.function.CastableInterface;

public interface ConfigurationSectionHolder extends CastableInterface<ConfigurationSectionHolder> {
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
	 * Returns current path separator, default value is '.'
	 * 
	 * @return Path separator for keys
	 */
	char getPathSeparator();

	/**
	 * Change path separator, for example if path separator equals to '-' <br>
	 * and path is: foo-bar <br>
	 * it will be something like this: <br>
	 * 
	 * <pre>
	 * <pre>
	 * foo: 
	 *   bar: 'test'
	 * </pre>
	 * </pre>
	 * 
	 * And configuration value will be 'test'
	 * 
	 * @param pathSeparator that will be used for separating path
	 * @return this {@link ConfigurationProcessor}
	 */
	ConfigurationProcessor setPathSeparator(char pathSeparator);

	/**
	 * Returns original section holder. Useful for section null check
	 * 
	 * @return original section holder
	 */
	Object getOriginalHolder();
}
