package com.ubivashka.configuration.context;

import java.util.List;
import java.util.Set;

import com.ubivashka.configuration.annotation.ConfigField;
import com.ubivashka.configuration.holder.ConfigurationSectionHolder;
import com.ubivashka.configuration.resolver.scalar.ScalarObjectResolver;

public interface ConfigurationContext {
    /**
     * Returns current configuration holder.
     *
     * @return configuration. Current configuration holder
     */
    ConfigurationSectionHolder configuration();

    /**
     * Returns path that assigned with {@link ConfigField} annotation.
     * Do not use for retrieving objects from configuration because {@link com.ubivashka.configuration.resolver.field.base.ConfigurationCollectionFieldFactory} may create this context, and execute
     * ConfigurationFieldResolver
     *
     * @return path in configuration
     */
    String[] path();

    default boolean is(Class<?> clazz) {
        return configuration().is(clazz, path());
    }

    default boolean isList() {
        return configuration().isList(path());
    }

    default boolean isSection() {
        if (getConfigurationObject() instanceof ConfigurationSectionHolder)
            return true;
        return configuration().isSection(path());
    }

    default Object getConfigurationObject() {
        return configuration().get(path());
    }

    default String getString() {
        return getString(null);
    }

    default String getString(String def) {
        return ScalarObjectResolver.STRING.resolvedOrDefault(getConfigurationObject(), def);
    }

    default boolean getBoolean() {
        return getBoolean(false);
    }

    default boolean getBoolean(boolean def) {
        return ScalarObjectResolver.BOOLEAN.resolvedOrDefault(getConfigurationObject(), def);
    }

    default float getFloat() {
        return getFloat(0f);
    }

    default float getFloat(float def) {
        return ScalarObjectResolver.FLOAT.resolvedOrDefault(getConfigurationObject(), def);
    }

    default double getDouble() {
        return getDouble(0d);
    }


    default double getDouble(double def) {
        return ScalarObjectResolver.DOUBLE.resolvedOrDefault(getConfigurationObject(), def);
    }

    default int getInt() {
        return getInt(0);
    }

    default int getInt(int def) {
        return ScalarObjectResolver.INTEGER.resolvedOrDefault(getConfigurationObject(), def);
    }

    default long getLong() {
        return getLong(0);
    }

    default long getLong(long def) {
        return ScalarObjectResolver.LONG.resolvedOrDefault(getConfigurationObject(), def);
    }

    default Set<String> keys() {
        return getSection().keys();
    }

    default ConfigurationSectionHolder getSection() {
        Object configurationObject = getConfigurationObject();
        if (configurationObject instanceof ConfigurationSectionHolder)
            return (ConfigurationSectionHolder) configurationObject;
        return configuration().section(path());
    }

    default <L> List<L> getList() {
        Object configurationObject = getConfigurationObject();
        if (configurationObject instanceof List)
            return (List<L>) configurationObject;
        return getSection().getList(path());
    }
}
