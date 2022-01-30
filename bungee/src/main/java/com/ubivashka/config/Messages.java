package com.ubivashka.config;

import java.util.HashMap;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;

public class Messages {
	private HashMap<String, String> messages = new HashMap<>();
	private HashMap<String, Messages> subMessages = new HashMap<>();

	public Messages(Configuration section) {
		for (String key : section.getKeys()) {
			if (section.getSection(key) != null) {
				addSubMessages(key, section);
				continue;
			}
			addMessage(key, section.getString(key));
		}
	}

	public String getMessage(String key) {
		return messages.get(key);
	}

	public Messages getSubMessages(String key) {
		return subMessages.get(key);
	}

	private void addSubMessages(String key, Configuration section) {
		subMessages.put(key, new Messages(section.getSection(key)));
	}

	private void addMessage(String key, String message) {
		messages.put(key, color(message));
	}

	private String color(String text) {
		if (text == null)
			throw new IllegalArgumentException("Cannot color null text: " + text);
		return ChatColor.translateAlternateColorCodes('&', text);
	}
}
