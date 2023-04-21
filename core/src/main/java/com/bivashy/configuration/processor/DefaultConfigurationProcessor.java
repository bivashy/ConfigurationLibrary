package com.bivashy.configuration.processor;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.bivashy.configuration.annotation.ConfigField;
import com.bivashy.configuration.annotation.ImportantField;
import com.bivashy.configuration.processor.exception.ImportantFieldNotInitializedException;
import com.bivashy.configuration.ConfigurationHolder;
import com.bivashy.configuration.ConfigurationProcessor;
import com.bivashy.configuration.context.ConfigurationContext;
import com.bivashy.configuration.context.ConfigurationFieldFactoryContext;
import com.bivashy.configuration.holder.ConfigurationSectionHolder;
import com.bivashy.configuration.holder.factory.ConfigurationSectionHolderFactory;
import com.bivashy.configuration.resolver.field.ConfigurationFieldResolver;
import com.bivashy.configuration.resolver.field.ConfigurationFieldResolverFactory;
import com.bivashy.configuration.resolver.field.base.ConfigurationCollectionFieldFactory;
import com.bivashy.configuration.resolver.field.base.ConfigurationEnumFieldFactory;
import com.bivashy.configuration.resolver.field.base.ConfigurationHolderResolverFactory;
import com.bivashy.configuration.resolver.field.base.DefaultConfigurationFieldFactory;
import com.bivashy.configuration.util.ClassMap;
import com.bivashy.configuration.util.PrimitiveWrapper;

public class DefaultConfigurationProcessor implements ConfigurationProcessor {
    public static final ConfigurationFieldResolverFactory FIELD_RESOLVER_FACTORY = new DefaultConfigurationFieldFactory();
    public static final ConfigurationFieldResolverFactory ENUM_FIELD_RESOLVER_FACTORY = new ConfigurationEnumFieldFactory();
    public static final ConfigurationFieldResolverFactory COLLECTION_FIELD_RESOLVER_FACTORY = new ConfigurationCollectionFieldFactory();
    public static final ConfigurationHolderResolverFactory CONFIGURATION_HOLDER_RESOLVER_FACTORY = new ConfigurationHolderResolverFactory();

    private final ClassMap<ConfigurationFieldResolverFactory> configurationFieldFactories = new ClassMap<>();
    private final ClassMap<ConfigurationFieldResolver<?>> configurationFieldResolvers = new ClassMap<>();
    private final ClassMap<ConfigurationSectionHolderFactory<?>> configurationHolderWrappers = new ClassMap<>();

    public DefaultConfigurationProcessor() {
        registerFieldResolver(Pattern.class, (context) -> Pattern.compile(context.getString()));
        registerFieldResolver(String.class, ConfigurationContext::getString);
        registerFieldResolver(Boolean.class, ConfigurationContext::getBoolean);
        registerFieldResolver(Float.class, ConfigurationContext::getFloat);
        registerFieldResolver(Double.class, ConfigurationContext::getDouble);
        registerFieldResolver(Integer.class, ConfigurationContext::getInt);
        registerFieldResolver(Long.class, ConfigurationContext::getLong);

        registerFieldResolverFactory(Enum.class, ENUM_FIELD_RESOLVER_FACTORY);
        registerFieldResolverFactory(Collection.class, COLLECTION_FIELD_RESOLVER_FACTORY);
        registerFieldResolverFactory(ConfigurationHolder.class, CONFIGURATION_HOLDER_RESOLVER_FACTORY);
    }

    @Override
    public ConfigurationProcessor resolve(ConfigurationSectionHolder sectionHolder, Object... fieldHolders) {
        for (Object object : fieldHolders) {
            Class<?> objectClass = object.getClass();
            Set<Field> fields = deepFields(objectClass).stream().filter(field -> field.isAnnotationPresent(ConfigField.class)).collect(Collectors.toSet());
            fields.forEach(field -> {
                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);
                ConfigurationFieldFactoryContext configurationFieldFactoryContext = ConfigurationFieldFactoryContext.of(this, sectionHolder, object, field);
                resolveField(configurationFieldFactoryContext);

                field.setAccessible(isAccessible);
            });
        }
        return this;
    }

    @Override
    public <T> ConfigurationProcessor resolve(T sectionHolder, Object... fieldHolders) {
        @SuppressWarnings("unchecked")
        ConfigurationSectionHolderFactory<T> wrapper = (ConfigurationSectionHolderFactory<T>) configurationHolderWrappers
                .getAssignable(PrimitiveWrapper.tryWrap(sectionHolder.getClass()));
        if (wrapper == null)
            throw new IllegalArgumentException("Cannot unwrap " + sectionHolder.getClass().getSimpleName() + " to ConfigurationSectionHolder");
        resolve(wrapper.wrap(sectionHolder), fieldHolders);
        return this;
    }

    @Override
    public <T> ConfigurationProcessor registerFieldResolver(Class<T> type, ConfigurationFieldResolver<T> fieldResolver) {
        configurationFieldResolvers.putWrapped(type, fieldResolver);
        return this;
    }

    @Override
    public <T> ConfigurationProcessor registerConfigurationHolderWrapper(Class<T> type, ConfigurationSectionHolderFactory<T> wrapper) {
        configurationHolderWrappers.putWrapped(type, wrapper);
        return this;
    }

    @Override
    public <T> ConfigurationProcessor registerFieldResolverFactory(Class<T> type, ConfigurationFieldResolverFactory fieldResolverFactory) {
        configurationFieldFactories.putWrapped(type, fieldResolverFactory);
        return this;
    }

    @Override
    public Map<Class<?>, ConfigurationFieldResolver<?>> getFieldResolvers() {
        return Collections.unmodifiableMap(configurationFieldResolvers);
    }

    @Override
    public Map<Class<?>, ConfigurationFieldResolverFactory> getFieldResolverFactories() {
        return Collections.unmodifiableMap(configurationFieldFactories);
    }

    protected Set<Field> deepFields(Class<?> currentClass) {
        Set<Field> fields = new HashSet<>();
        while(currentClass != null && currentClass != Object.class) {
            Collections.addAll(fields, currentClass.getDeclaredFields());
            currentClass = currentClass.getSuperclass();
        }
        return fields;
    }

    private void resolveField(ConfigurationFieldFactoryContext configurationFieldFactoryContext) {
        Class<?> key = configurationFieldFactoryContext.valueType();
        ConfigurationFieldResolverFactory factory = configurationFieldFactories.getOrDefault(key,
                configurationFieldFactories.getAssignable(key, FIELD_RESOLVER_FACTORY));

        try {
            ConfigurationFieldResolver<?> resolver = factory.createResolver(configurationFieldFactoryContext);

            Field field = configurationFieldFactoryContext.field();
            Object resolvedObject = resolver.resolveField(configurationFieldFactoryContext.asResolverContext());

            if (resolvedObject == null) {
                if (field.isAnnotationPresent(ImportantField.class))
                    throw new ImportantFieldNotInitializedException(configurationFieldFactoryContext);
                return;
            }
            field.set(configurationFieldFactoryContext.fieldHolder(), resolvedObject);
        } catch(Throwable e) {
            System.err.println("Error occurred on path " + Arrays.toString(configurationFieldFactoryContext.path()) + " with field type "
                    + configurationFieldFactoryContext.field().getType().getSimpleName()+" in "+configurationFieldFactoryContext.fieldHolder().getClass().getSimpleName());
            e.printStackTrace();
        }
    }

}
