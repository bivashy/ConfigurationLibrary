package com.ubivashka.config.holders;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.md_5.bungee.config.Configuration;

public class BungeeConfigurationSectionHolder implements IConfigurationSectionHolder<Configuration> {
	private final Configuration configuration;

	public BungeeConfigurationSectionHolder(Configuration configuration) {
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
		return configuration.getFloat(key);
	}

	@Override
	public Short getShort(String key) {
		return configuration.getShort(key);
	}

	@Override
	public Byte getByte(String key) {
		return configuration.getByte(key);
	}

	@Override
	public IConfigurationSectionHolder<Configuration> getConfigurationSection(String key) {
		return new BungeeConfigurationSectionHolder(configuration.getSection(key));
	}
	
	@SuppressWarnings("unchecked")
	public <L> List<L> getList(String key){
		return (List<L>) configuration.getList(key);
	}

	@Override
	public boolean contains(String key) {
		return configuration.contains(key);
	}

	@Override
	public boolean isConfigurationSection(String key) {
		return configuration.get(key) instanceof Configuration;
	}

	@Override
	public boolean isCollection(String key) {
		return configuration.get(key) instanceof List<?>;
	}

	@Override
	public boolean isString(String key) {
		return configuration.get(key) instanceof String;
	}

	@Override
	public boolean isBoolean(String key) {
		return configuration.get(key) instanceof Boolean;
	}

	@Override
	public boolean isInteger(String key) {
		return configuration.get(key) instanceof Number;
	}

	@Override
	public boolean isDouble(String key) {
		return configuration.get(key) instanceof Number;
	}

	@Override
	public boolean isFloat(String key) {
		return configuration.get(key) instanceof Number;
	}

	@Override
	public boolean isShort(String key) {
		return configuration.get(key) instanceof Number;
	}

	@Override
	public boolean isByte(String key) {
		return configuration.get(key) instanceof Number;
	}

	@Override
	public Configuration getOriginalConfigurationSection() {
		return configuration;
	}

	@Override
	public Set<String> getKeys() {
		return new HashSet<>(configuration.getKeys());
	}

}
