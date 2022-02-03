package com.ubivashka.config.processors;

import com.ubivashka.config.processors.context.IConfigurationContext;

public interface IConfigurationContextProcessor<T,C extends IConfigurationContext<T>> {
	void process(C context);
	
	boolean isValidContext(C context);
	
	default byte priority() {
		return 0;
	}
}
