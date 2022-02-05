package com.ubivashka.config.converters;

import com.ubivashka.config.contexts.BungeeConfigurationContext;
import com.ubivashka.config.processors.BungeeConfigurationContextProcessor;

public class BungeeConfigurationDefaultConverter extends BungeeConfigurationContextProcessor{
	protected final Class<?> converterEntityClass, converterDtoClass;

	public BungeeConfigurationDefaultConverter(Class<?> converterEntityClass, Class<?> converterDtoClass) {
		this.converterEntityClass = converterEntityClass;
		this.converterDtoClass = converterDtoClass;
	}

	@Override
	public void process(BungeeConfigurationContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValidContext(BungeeConfigurationContext context) {
		// TODO Auto-generated method stub
		return false;
	}

}
