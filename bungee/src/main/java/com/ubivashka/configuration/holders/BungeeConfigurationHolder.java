package com.ubivashka.configuration.holders;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;

import com.ubivashka.configuration.holder.ConfigurationSectionHolder;

import net.md_5.bungee.config.Configuration;

public class BungeeConfigurationHolder implements ConfigurationSectionHolder {
    private final Configuration configuration;
    private ConfigurationSectionHolder parent;
    private String currentPathKey;

    public BungeeConfigurationHolder(Configuration configuration) {
        Objects.requireNonNull(configuration);
        this.configuration = configuration;
    }

    public BungeeConfigurationHolder(Configuration configuration, ConfigurationSectionHolder parent, String key) {
        Objects.requireNonNull(configuration);
        this.configuration = configuration;
        this.parent = parent;
        this.currentPathKey = key;
    }

    @Override
    public Object get(String... keys) {
        if (keys.length == 0)
            throw new IllegalArgumentException("Cannot process empty keys");
        return getConfigurationElement(Configuration::get, keys);
    }

    @Override
    public ConfigurationSectionHolder section(String... keys) {
        BungeeConfigurationHolder currentSection = this;
        for(String key:keys)
            currentSection = currentSection.getConfigurationSection(key);
        return currentSection;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <L> List<L> getList(String... keys) {
        return (List<L>) getConfigurationElement(Configuration::getList, keys);
    }

    @Override
    public boolean contains(String... key) {
        return getConfigurationElement(Configuration::contains);
    }

    @Override
    public Set<String> keys() {
        return new HashSet<>(configuration.getKeys());
    }

    @Override
    public boolean isSection(String... key) {
        return get(key) instanceof Configuration;
    }

    @Override
    public boolean isList(String... keys) {
        return get(keys) instanceof List;
    }

    @Override
    public ConfigurationSectionHolder parent() {
        return parent;
    }

    @Override
    public String key() {
        return currentPathKey;
    }

    @Override
    public Object getOriginalHolder() {
        return getSection();
    }

    public Configuration getSection() {
        return configuration;
    }

    private <T> T getConfigurationElement(BiFunction<Configuration, String, T> lastElementFunction, String... keys) {
        Configuration currentConfiguration = configuration;
        for (int i = 0; i < keys.length - 1; i++) { // Iterate all elements except last element
            String key = keys[i];
            Configuration findedConfiguration = currentConfiguration.getSection(key);
            currentConfiguration = findedConfiguration == null ? new Configuration() : findedConfiguration;
        }
        return lastElementFunction.apply(currentConfiguration, keys[keys.length - 1]);
    }

    private BungeeConfigurationHolder getConfigurationSection(String key) {
        return new BungeeConfigurationHolder(configuration.getSection(key), this, key);
    }
}