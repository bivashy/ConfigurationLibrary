package com.ubivashka.configuration.configurate.holder;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.Scalars;
import org.spongepowered.configurate.serialize.SerializationException;

import com.ubivashka.configuration.ConfigurationProcessor;
import com.ubivashka.configuration.holders.ConfigurationSectionHolder;

import io.leangen.geantyref.TypeToken;

public class ConfigurationHolder implements ConfigurationSectionHolder {
	private final ConfigurationNode configurationNode;

	public ConfigurationHolder(ConfigurationNode configurationNode) {
		this.configurationNode = configurationNode;
	}

	@Override
	public Object get(String key) {
		return configurationNode.node(key).raw();
	}

	@Override
	public String getString(String key) {
		return configurationNode.node(key).getString();
	}

	@Override
	public Boolean getBoolean(String key) {
		return configurationNode.node(key).getBoolean();
	}

	@Override
	public Integer getInteger(String key) {
		return configurationNode.node(key).getInt();
	}

	@Override
	public Double getDouble(String key) {
		return configurationNode.node(key).getDouble();
	}

	@Override
	public ConfigurationSectionHolder getSection(String key) {
		return new ConfigurationHolder(configurationNode.node(key));
	}

	@Override
	public <L> List<L> getList(String key) {
		try {
			return (List<L>) configurationNode.node(key).getList(TypeToken.get(Object.class));
		} catch (SerializationException e) {
			e.printStackTrace();
			return Collections.emptyList();
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
		return Scalars.STRING.tryDeserialize(configurationNode.node(key).rawScalar()) != null;
	}

	@Override
	public boolean isBoolean(String key) {
		return Scalars.BOOLEAN.tryDeserialize(configurationNode.node(key).rawScalar()) != null;
	}

	@Override
	public boolean isInteger(String key) {
		return Scalars.INTEGER.tryDeserialize(configurationNode.node(key).rawScalar()) != null;
	}

	@Override
	public boolean isDouble(String key) {
		return Scalars.DOUBLE.tryDeserialize(configurationNode.node(key).rawScalar()) != null;
	}

	@Override
	public Set<String> getKeys() {
		return configurationNode.childrenMap().keySet().stream().map(object -> String.valueOf(object))
				.collect(Collectors.toSet());
	}

	@Override
	public char getPathSeparator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ConfigurationProcessor setPathSeparator(char pathSeparator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getOriginalHolder() {
		return getConfigurationNode();
	}

	public ConfigurationNode getConfigurationNode() {
		return configurationNode;
	}

}
