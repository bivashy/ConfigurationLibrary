package com.ubivashka.config.holders;

import java.util.List;
import java.util.Set;

public interface IConfigurationSectionHolder<T> {
	Object get(String key);

	String getString(String key);

	Boolean getBoolean(String key);

	Integer getInteger(String key);

	Double getDouble(String key);

	Float getFloat(String key);

	Short getShort(String key);

	Byte getByte(String key);

	IConfigurationSectionHolder<T> getConfigurationSection(String key);

	<L> List<L> getList(String key);

	boolean contains(String key);

	boolean isConfigurationSection(String key);

	boolean isCollection(String key);

	boolean isString(String key);

	boolean isBoolean(String key);

	boolean isInteger(String key);

	boolean isDouble(String key);

	boolean isFloat(String key);

	boolean isShort(String key);

	boolean isByte(String key);

	T getOriginalConfigurationSection();

	Set<String> getKeys();
}
