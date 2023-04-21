package com.bivashy.configuration.holders;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;

import com.bivashy.configuration.holder.ConfigurationSectionHolder;
import com.bivashy.function.MemoizingSupplier;

public class BukkitConfigurationHolder implements ConfigurationSectionHolder {
    private final ConfigurationSection section;
    private final String key;
    private final Supplier<BukkitConfigurationHolder> lazyParent;

    public BukkitConfigurationHolder(ConfigurationSection section) {
        Objects.requireNonNull(section);
        this.section = section;
        this.key = section.getName();
        this.lazyParent = MemoizingSupplier.memoize(() -> {
            if (section.getParent() != null)
                return new BukkitConfigurationHolder(section.getParent());
            return null;
        });
    }

    @Override
    public Object get(String... keys) {
        return getConfigurationElement(ConfigurationSection::get, keys);
    }

    @Override
    public ConfigurationSectionHolder section(String... keys) {
        return new BukkitConfigurationHolder(getConfigurationElement(ConfigurationSection::getConfigurationSection, keys));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <L> List<L> getList(String... keys) {
        return (List<L>) getConfigurationElement(ConfigurationSection::getList, keys);
    }

    @Override
    public boolean contains(String... keys) {
        return getConfigurationElement(ConfigurationSection::contains, keys);
    }

    @Override
    public boolean isSection(String... keys) {
        return getConfigurationElement(ConfigurationSection::isConfigurationSection, keys);
    }

    @Override
    public boolean isList(String... keys) {
        return getConfigurationElement(ConfigurationSection::isList, keys);
    }

    @Override
    public Set<String> keys() {
        return section.getKeys(false);
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
        return getSection();
    }

    public ConfigurationSection getSection() {
        return section;
    }

    private <T> T getConfigurationElement(BiFunction<ConfigurationSection, String, T> lastElementFunction, String... keys) {
        ConfigurationSection currentSection = section;
        for (int i = 0; i < keys.length - 1; i++) { // Iterate all elements except last element
            String key = keys[i];
            ConfigurationSection findedConfiguration = currentSection.getConfigurationSection(key);
            currentSection = findedConfiguration == null ? new MemoryConfiguration() : findedConfiguration;
        }
        return lastElementFunction.apply(currentSection, keys[keys.length - 1]);
    }
}
