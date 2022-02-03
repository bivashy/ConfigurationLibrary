package com.ubivashka.config.processors;

import java.util.logging.Level;

import com.ubivashka.config.processors.context.IConfigurationContext;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.config.Configuration;

public class BungeeImportantFieldProcessor extends AbstractImportantFieldProcessor<Configuration> {

	@Override
	public void onImportantFieldError(IConfigurationContext<Configuration> context) {
		ProxyServer.getInstance().getLogger().log(Level.SEVERE,
				"Important field with name " + context.getField().getName() + " and with configuration path "
						+ context.getConfigurationPath() + " not exists");
	}

}
