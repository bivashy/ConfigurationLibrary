package com.ubivashka.config;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;

import com.ubivashka.config.annotations.ConfigField;
import com.ubivashka.config.processors.BukkitConfigurationHolder;

import net.md_5.bungee.api.ChatColor;

public class Messages extends BukkitConfigurationHolder {
	@ConfigField
	private Map<String, String> messages = new HashMap<>();
	@ConfigField
	private Map<String, Messages> subMessages = new HashMap<>();

	public Messages(ConfigurationSection section) {
		init(section);
	}

	public String getMessage(String key) {
		return messages.get(key);
	}

	public Messages getSubMessages(String key) {
		return subMessages.get(key);
	}

	protected void addSubMessages(String key, ConfigurationSection section) {
		subMessages.put(key, new Messages(section.getConfigurationSection(key)));
	}

	protected void addMessage(String key, String message) {
		messages.put(key, color(message));
	}

	protected String color(String text) {
		if (text == null)
			throw new IllegalArgumentException("Cannot color null text: " + text);
		return ChatColor.translateAlternateColorCodes('&', text);
	}
}
