package com.ubivashka.config.processors.configfield.context;


import com.ubivashka.config.annotations.ConfigField;
import com.ubivashka.config.processors.context.IConfigurationContext;

public interface IConfigFieldContext<T> extends IConfigurationContext<T>{
	ConfigField getConfigField();
}
