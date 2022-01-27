package com.ubivashka.config.processors;

import java.util.logging.Level;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.config.Configuration;

public enum ImportantAction {
	NOTHING {
		@Override
		public void doAction(String fieldPath, Configuration configurationSection) {
		}
	},
	LOG {
		@Override
		public void doAction(String fieldPath, Configuration configurationSection) {
			ProxyServer.getInstance().getLogger().log(Level.SEVERE,
					"The important field: " + fieldPath + " not finded in section: " + configurationSection);
		}
	};

	public abstract void doAction(String fieldPath, Configuration configurationSection);
}
