package com.ubivashka.configuration.holder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.reflect.TypeToken;
import com.ubivashka.configuration.holder.ConfigurationSectionHolder;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.Types;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class VelocityConfigurationHolder implements ConfigurationSectionHolder {
	private final ConfigurationNode configurationNode;

	public VelocityConfigurationHolder(ConfigurationNode configurationNode) {
		Objects.requireNonNull(configurationNode);
		this.configurationNode = configurationNode;
	}

	@Override
	public Object get(String key) {
		return configurationNode.getNode(key).getValue();
	}

	@Override
	public String getString(String key) {
		return configurationNode.getNode(key).getString();
	}

	@Override
	public Boolean getBoolean(String key) {
		return configurationNode.getNode(key).getBoolean();
	}

	@Override
	public Integer getInteger(String key) {
		return configurationNode.getNode(key).getInt();
	}

	@Override
	public Double getDouble(String key) {
		return configurationNode.getNode(key).getDouble();
	}

	@Override
	public ConfigurationSectionHolder getSection(String key) {
		return new VelocityConfigurationHolder(configurationNode.getNode(key));
	}

	@Override
	public <L> List<L> getList(String key) {
		try {
			return (List<L>) configurationNode.getNode(key).getList(TypeToken.of(Object.class));
		} catch (ObjectMappingException e) {
			return new ArrayList<>();
		}
	}

	@Override
	public boolean contains(String key) {
		return getKeys().stream().anyMatch(configurationKey -> configurationKey.equals(key));
	}

	@Override
	public boolean isConfigurationSection(String key) {
		return configurationNode.isList();
	}

	@Override
	public boolean isCollection(String key) {
		return configurationNode.isList();
	}

	@Override
	public boolean isString(String key) {
		return Types.asString(configurationNode.getValue()) != null;
	}

	@Override
	public boolean isBoolean(String key) {
		return Types.asBoolean(configurationNode.getValue()) != null;
	}

	@Override
	public boolean isInteger(String key) {
		return Types.asInt(configurationNode.getValue()) != null;
	}

	@Override
	public boolean isDouble(String key) {
		return Types.asDouble(configurationNode.getValue()) != null;
	}

	@Override
	public Set<String> getKeys() {
		return configurationNode.getChildrenMap().keySet().stream().map(String::valueOf)
				.collect(Collectors.toSet());
	}

	@Override
	public Object getOriginalHolder() {
		return getConfigurationNode();
	}

	public ConfigurationNode getConfigurationNode() {
		return configurationNode;
	}

}
