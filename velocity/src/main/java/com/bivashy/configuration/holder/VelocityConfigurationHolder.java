package com.bivashy.configuration.holder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.common.reflect.TypeToken;
import com.bivashy.configuration.function.MemoizingSupplier;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class VelocityConfigurationHolder implements ConfigurationSectionHolder {
    private final ConfigurationNode configurationNode;
    private final Supplier<VelocityConfigurationHolder> lazyParent;
    private String key;

    public VelocityConfigurationHolder(ConfigurationNode configurationNode) {
        Objects.requireNonNull(configurationNode);
        this.configurationNode = configurationNode;
        this.lazyParent = MemoizingSupplier.memoize(() -> {
            if (configurationNode.getParent() != null)
                return new VelocityConfigurationHolder(configurationNode.getParent());
            return null;
        });
        if (configurationNode.getKey() != null)
            this.key = configurationNode.getKey().toString();
    }

    @Override
    public Object get(String... key) {
        return getNode(key).getValue();
    }

    @Override
    public ConfigurationSectionHolder section(String... key) {
        return new VelocityConfigurationHolder(getNode(key));
    }

    @Override
    public <L> List<L> getList(String... key) {
        try {
            return (List<L>) getNode(key).getList(TypeToken.of(Object.class));
        } catch(ObjectMappingException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public boolean contains(String... key) {
        return getNode(key).isVirtual();
    }

    @Override
    public boolean isSection(String... key) {
        ConfigurationNode node = getNode(key);
        return node.isMap() || !node.getChildrenMap().isEmpty();
    }

    @Override
    public boolean isList(String... key) {
        return getNode(key).isList();
    }

    @Override
    public Set<String> keys() {
        return configurationNode.getChildrenMap().keySet().stream().map(String::valueOf)
                .collect(Collectors.toSet());
    }

    @Override
    public ConfigurationSectionHolder parent() {
        return lazyParent.get();
    }

    @Override
    public String key() {
        return key;
    }

    @Override
    public Object getOriginalHolder() {
        return getConfigurationNode();
    }

    public ConfigurationNode getConfigurationNode() {
        return configurationNode;
    }

    private ConfigurationNode getNode(String... key) {
        return configurationNode.getNode((Object[]) key);
    }
}
