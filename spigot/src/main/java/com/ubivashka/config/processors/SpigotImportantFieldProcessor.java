package com.ubivashka.config.processors;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import com.ubivashka.config.processors.context.IConfigurationContext;

public class SpigotImportantFieldProcessor extends AbstractImportantFieldProcessor<ConfigurationSection> {

	@Override
	public void onImportantFieldError(IConfigurationContext<ConfigurationSection> context) {
		Bukkit.getLogger().log(Level.SEVERE, "Important field with name " + context.getField().getName()
				+ " and with configuration path " + context.getConfigurationPath() + " not exists");
	}

}
