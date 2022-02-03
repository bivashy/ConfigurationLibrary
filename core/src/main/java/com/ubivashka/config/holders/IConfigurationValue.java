package com.ubivashka.config.holders;

import java.util.List;

public interface IConfigurationValue<T> {
	String getKey();
	
	IConfigurationSectionHolder<T> getRootConfigurationSection();
	
	Object getConfigurationObject();
	
	IConfigurationSectionHolder<T> getAsConfigurationSection();

	<L> List<L> getAsList();

	String getAsString();

	Integer getAsInteger();

	Double getAsDouble();

	Float getAsFloat();

	Short getAsShort();

	Byte getAsByte();

	boolean isConfigurationSection();

	boolean isCollection();

	boolean isString();

	boolean isInteger();

	boolean isDouble();

	boolean isFloat();

	boolean isShort();

	boolean isByte();

}
