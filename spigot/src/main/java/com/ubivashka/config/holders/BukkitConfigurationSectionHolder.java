package com.ubivashka.config.holders;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;

public class BukkitConfigurationSectionHolder implements IConfigurationSectionHolder<ConfigurationSection> {
	private final ConfigurationSection configuration;

	public BukkitConfigurationSectionHolder(ConfigurationSection configuration) {
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
	public Float getFloat(String key) {
		return getDouble(key).floatValue();
	}

	@Override
	public Short getShort(String key) {
		return getInteger(key).shortValue();
	}

	@Override
	public Byte getByte(String key) {
		return getInteger(key).byteValue();
	}

	@Override
	public IConfigurationSectionHolder<ConfigurationSection> getConfigurationSection(String key) {
		return new BukkitConfigurationSectionHolder(configuration.getConfigurationSection(key));
	}

	@SuppressWarnings("unchecked")
	public <L> List<L> getList(String key) {
		return (List<L>) configuration.getList(key);
	}

	@Override
	public boolean contains(String key) {
		return configuration.contains(key);
	}

	@Override
	public boolean isConfigurationSection(String key) {
		return configuration.isConfigurationSection(key);
	}

	@Override
	public boolean isCollection(String key) {
		return configuration.isList(key);
	}

	@Override
	public boolean isString(String key) {
		return configuration.isString(key);
	}

	@Override
	public boolean isBoolean(String key) {
		return configuration.isBoolean(key);
	}

	@Override
	public boolean isInteger(String key) {
		return configuration.isInt(key);
	}

	@Override
	public boolean isDouble(String key) {
		return configuration.isDouble(key);
	}

	@Override
	public boolean isFloat(String key) {
		return isDouble(key);
	}

	@Override
	public boolean isShort(String key) {
		return isInteger(key);
	}

	@Override
	public boolean isByte(String key) {
		return isInteger(key);
	}

	@Override
	public ConfigurationSection getOriginalConfigurationSection() {
		return configuration;
	}

	@Override
	public Set<String> getKeys() {
		return new HashSet<>(configuration.getKeys(false));
	}

}
