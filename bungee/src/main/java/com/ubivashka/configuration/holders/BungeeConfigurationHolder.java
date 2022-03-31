package com.ubivashka.configuration.holders;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ubivashka.configuration.ConfigurationProcessor;

import net.md_5.bungee.config.Configuration;

public class BungeeConfigurationHolder implements ConfigurationSectionHolder {
	private final Configuration configuration;

	public BungeeConfigurationHolder(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public Object get(String key) {
		return configuration.get(key);
	}

	@Override
	public String getString(String key) {
		return configuration.getString(key);
	}

	@Override
	public Boolean getBoolean(String key) {
		return configuration.getBoolean(key);
	}

	@Override
	public Integer getInteger(String key) {
		return configuration.getInt(key);
	}

	@Override
	public Double getDouble(String key) {
		return configuration.getDouble(key);
	}

	@Override
	public ConfigurationSectionHolder getSection(String key) {
		return new BungeeConfigurationHolder(configuration.getSection(key));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <L> List<L> getList(String key) {
		return (List<L>) configuration.getList(key);
	}

	@Override
	public boolean contains(String key) {
		return configuration.contains(key);
	}

	@Override
	public boolean isConfigurationSection(String key) {
		return get(key) instanceof Configuration;
	}

	@Override
	public boolean isCollection(String key) {
		return get(key) instanceof List<?>;
	}

	@Override
	public boolean isString(String key) {
		return get(key) instanceof String;
	}

	@Override
	public boolean isBoolean(String key) {
		return get(key) instanceof Boolean;
	}

	@Override
	public boolean isInteger(String key) {
		return get(key) instanceof Number;
	}

	@Override
	public boolean isDouble(String key) {
		return get(key) instanceof Number;
	}

	@Override
	public Set<String> getKeys() {
		return new HashSet<>(configuration.getKeys());
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

	public Configuration getSection() {
		return configuration;
	}
}
