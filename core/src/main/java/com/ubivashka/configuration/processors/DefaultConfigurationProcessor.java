package com.ubivashka.configuration.processors;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.ubivashka.configuration.ConfigurationProcessor;
import com.ubivashka.configuration.annotations.ConfigField;
import com.ubivashka.configuration.annotations.ImportantField;
import com.ubivashka.configuration.contexts.ConfigurationFieldContext;
import com.ubivashka.configuration.converters.Converter;
import com.ubivashka.configuration.holders.ConfigurationSectionHolder;
import com.ubivashka.configuration.processors.exceptions.CannotParseException;
import com.ubivashka.configuration.processors.exceptions.ImportantFieldNotInitializedException;
import com.ubivashka.configuration.resolvers.ConfigurationFieldResolver;
import com.ubivashka.configuration.resolvers.ConfigurationFieldResolverFactory;
import com.ubivashka.configuration.resolvers.defaults.ConfigurationCollectionFieldFactory;
import com.ubivashka.configuration.resolvers.defaults.DefaultConfigurationFieldFactory;
import com.ubivashka.configuration.util.ClassMap;
import com.ubivashka.configuration.util.PrimitiveWrapper;
import com.ubivashka.configuration.wrappers.ConfigurationHolderWrapper;

public class DefaultConfigurationProcessor implements ConfigurationProcessor {
	public static final ConfigurationFieldResolverFactory<?> FIELD_RESOLVER_FACTORY = new DefaultConfigurationFieldFactory<>();
	public static final ConfigurationFieldResolverFactory<List<?>> COLLECTION_FIELD_RESOLVER_FACTORY = new ConfigurationCollectionFieldFactory<>();

	private final ClassMap<ConfigurationFieldResolverFactory<?>> configurationFieldFactories = new ClassMap<>();
	private final ClassMap<ConfigurationFieldResolver<?>> configurationFieldResolvers = new ClassMap<>();
	private final ClassMap<ConfigurationHolderWrapper<?>> configurationHolderWrappers = new ClassMap<>();
	private final ClassMap<Converter<?>> converters = new ClassMap<>();

	public DefaultConfigurationProcessor() {
		registerConverter(String.class, (object) -> String.valueOf(object));
		registerConverter(Number.class, (object) -> {
			if (object instanceof String)
				return Double.parseDouble(String.valueOf(object));
			if (object instanceof Number)
				return (Number) object;
			throw new IllegalArgumentException(new CannotParseException());
		});
		registerConverter(Pattern.class, (object) -> {
			if (object instanceof String)
				return Pattern.compile((String) object);
			throw new IllegalArgumentException(new CannotParseException());
		});

		registerFieldResolverFactory(List.class, COLLECTION_FIELD_RESOLVER_FACTORY);
		registerFieldResolverFactory(Collection.class, COLLECTION_FIELD_RESOLVER_FACTORY);
	}

	@Override
	public ConfigurationProcessor resolve(ConfigurationSectionHolder sectionHolder, Object... fieldHolders) {
		for (Object object : fieldHolders) {
			Class<?> objectClass = object.getClass();
			Set<Field> fields = deepFields(objectClass).stream()
					.filter(field -> field.isAnnotationPresent(ConfigField.class)).collect(Collectors.toSet());
			fields.forEach(field -> {
				boolean isAccessible = field.isAccessible();
				field.setAccessible(true);
				ConfigurationFieldContext configurationFieldContext = ConfigurationFieldContext.of(this, sectionHolder,
						object, field);
				resolveField(configurationFieldContext);

				field.setAccessible(isAccessible);
			});
		}
		return this;
	}

	@Override
	public <T> ConfigurationProcessor resolve(T sectionHolder, Object... fieldHolders) {
		ConfigurationHolderWrapper<T> wrapper = (ConfigurationHolderWrapper<T>) configurationHolderWrappers
				.getAssignable(PrimitiveWrapper.unwrapClass(sectionHolder.getClass()));
		if (wrapper == null)
			throw new IllegalArgumentException(
					"Cannot unwrap " + sectionHolder.getClass().getSimpleName() + " to ConfigurationSectionHolder");
		resolve(wrapper.wrap(sectionHolder), fieldHolders);
		return this;
	}

	@Override
	public <T> ConfigurationProcessor registerFieldResolver(Class<T> type,
			ConfigurationFieldResolver<T> fieldResolver) {
		configurationFieldResolvers.putWrapped(type, fieldResolver);
		return this;
	}

	@Override
	public <T> ConfigurationProcessor registerConfigurationHolderWrapper(Class<T> type,
			ConfigurationHolderWrapper<T> wrapper) {
		configurationHolderWrappers.putWrapped(type, wrapper);
		return this;
	}

	@Override
	public <T> ConfigurationProcessor registerFieldResolverFactory(Class<T> type,
			ConfigurationFieldResolverFactory<? extends T> fieldResolverFactory) {
		configurationFieldFactories.putWrapped(type, fieldResolverFactory);
		return this;
	}

	@Override
	public <T> ConfigurationProcessor registerConverter(Class<T> type, Converter<T> converter) {
		converters.putWrapped(type, converter);
		return this;
	}

	@Override
	public Map<Class<?>, ConfigurationFieldResolver<?>> getFieldResolvers() {
		return Collections.unmodifiableMap(configurationFieldResolvers);
	}

	@Override
	public Map<Class<?>, ConfigurationFieldResolverFactory<?>> getFieldResolverFactories() {
		return Collections.unmodifiableMap(configurationFieldFactories);
	}

	@Override
	public Map<Class<?>, Converter<?>> getConverters() {
		return Collections.unmodifiableMap(converters);
	}

	protected Set<Field> deepFields(Class<?> currentClass) {
		Set<Field> fields = new HashSet<>();
		while (currentClass != null && currentClass != Object.class) {
			Collections.addAll(fields, currentClass.getDeclaredFields());
			currentClass = currentClass.getSuperclass();
		}
		return fields;
	}

	private void resolveField(ConfigurationFieldContext configurationFieldContext) {
		Class<?> key = configurationFieldContext.valueType();
		ConfigurationFieldResolverFactory<?> factory = configurationFieldFactories.getOrDefault(key,
				configurationFieldFactories.getAssignable(key, FIELD_RESOLVER_FACTORY));

		try {
			ConfigurationFieldResolver<?> resolver = factory.createResolver(configurationFieldContext);

			Field field = configurationFieldContext.field();
			Object resolvedObject = resolver.resolveField(configurationFieldContext.asResolverContext());

			if (resolvedObject == null && field.isAnnotationPresent(ImportantField.class))
				throw new ImportantFieldNotInitializedException(configurationFieldContext);
			field.set(configurationFieldContext.fieldHolder(), resolvedObject);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
