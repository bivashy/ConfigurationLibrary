package com.ubivashka.config.processors;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

public enum ImportantAction {
	NOTHING {
		@Override
		public void doAction(String fieldPath, ConfigurationSection configurationSection) {
		}
	},
	LOG {
		@Override
		public void doAction(String fieldPath, ConfigurationSection configurationSection) {
			Bukkit.getServer().getLogger().log(Level.SEVERE,
					"The important field: " + fieldPath + " not finded in section: " + configurationSection.getCurrentPath());
		}
	};

	public abstract void doAction(String fieldPath, ConfigurationSection configurationSection);
}
