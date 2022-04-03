package com.ubivashka.configuration.holders;

import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;

import com.ubivashka.configuration.ConfigurationProcessor;

public class BukkitConfigurationHolder implements ConfigurationSectionHolder {
	private final ConfigurationSection section;

	public BukkitConfigurationHolder(ConfigurationSection section) {
		this.section = section;
	}

	@Override
	public Object get(String key) {
		return section.get(key);
	}

	@Override
	public String getString(String key) {
		return section.getString(key);
	}

	@Override
	public Boolean getBoolean(String key) {
		return section.getBoolean(key);
	}

	@Override
	public Integer getInteger(String key) {
		return section.getInt(key);
	}

	@Override
	public Double getDouble(String key) {
		return section.getDouble(key);
	}

	@Override
	public ConfigurationSectionHolder getSection(String key) {
		return new BukkitConfigurationHolder(section.getConfigurationSection(key));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <L> List<L> getList(String key) {
		return (List<L>) section.getList(key);
	}

	@Override
	public boolean contains(String key) {
		return section.contains(key);
	}

	@Override
	public boolean isConfigurationSection(String key) {
		return section.isConfigurationSection(key);
	}

	@Override
	public boolean isCollection(String key) {
		return section.isList(key);
	}

	@Override
	public boolean isString(String key) {
		return section.isString(key);
	}

	@Override
	public boolean isBoolean(String key) {
		return section.isBoolean(key);
	}

	@Override
	public boolean isInteger(String key) {
		return section.isInt(key);
	}

	@Override
	public boolean isDouble(String key) {
		return section.isDouble(key);
	}

	@Override
	public Set<String> getKeys() {
		return section.getKeys(false);
	}

	@Override
	public char getPathSeparator() {
		return '.';
	}

	@Override
	public ConfigurationProcessor setPathSeparator(char pathSeparator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getOriginalHolder() {
		return getSection();
	}

	public ConfigurationSection getSection() {
		return section;
	}

}
