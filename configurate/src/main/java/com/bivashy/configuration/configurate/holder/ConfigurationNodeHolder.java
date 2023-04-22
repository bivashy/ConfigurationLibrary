package com.bivashy.configuration.configurate.holder;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.spongepowered.configurate.ConfigurationNode;

import com.bivashy.configuration.holder.ConfigurationSectionHolder;
import com.bivashy.configuration.function.MemoizingSupplier;

public class ConfigurationNodeHolder implements ConfigurationSectionHolder {
    private final ConfigurationNode configurationNode;
    private final Supplier<ConfigurationNodeHolder> lazyParent;
    private String key;

    public ConfigurationNodeHolder(ConfigurationNode configurationNode) {
        Objects.requireNonNull(configurationNode);
        this.configurationNode = configurationNode;
        if (configurationNode.key() != null)
            this.key = configurationNode.key().toString();
        this.lazyParent = MemoizingSupplier.memoize(() -> {
            if (configurationNode.parent() != null)
                return new ConfigurationNodeHolder(configurationNode.parent());
            return null;
        });
    }

    @Override
    public Object get(String... key) {
        return getConfigurationNode(key).raw();
    }

    @Override
    public ConfigurationSectionHolder section(String... key) {
        return new ConfigurationNodeHolder(getConfigurationNode(key));
    }

    @Override
    public boolean isSection(String... key) {
        ConfigurationNode node = getConfigurationNode(key);
        return node.isMap() || !node.childrenMap().isEmpty();
    }

    @Override
    public boolean isList(String... key) {
        return getConfigurationNode(key).isList();
    }

    @Override
    public boolean contains(String... key) {
        return !getConfigurationNode(key).virtual();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <L> List<L> getList(String... key) {
        return (List<L>) getConfigurationNode(key).childrenList().stream().map(ConfigurationNode::raw)
                .collect(Collectors.toList());
    }


    @Override
    public Set<String> keys() {
        return configurationNode.childrenMap().keySet().stream().map(String::valueOf)
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

    private ConfigurationNode getConfigurationNode(String... key) {
        return configurationNode.node((Object[]) key);
    }
}
