package com.ubivashka.configuration.resolver.field.base;

import java.util.List;
import java.util.stream.Collectors;

import com.ubivashka.configuration.ConfigurationProcessor;
import com.ubivashka.configuration.annotation.SectionObject;
import com.ubivashka.configuration.annotation.SingleObject;
import com.ubivashka.configuration.context.ConfigurationFieldContext;
import com.ubivashka.configuration.context.ConfigurationFieldResolverContext;
import com.ubivashka.configuration.context.base.ConfigurationFieldResolverContextWrapper;
import com.ubivashka.configuration.context.base.SingleObjectResolverContext;
import com.ubivashka.configuration.holder.ConfigurationSectionHolder;
import com.ubivashka.configuration.processor.DefaultConfigurationProcessor;
import com.ubivashka.configuration.resolver.field.ConfigurationFieldResolver;
import com.ubivashka.configuration.resolver.field.ConfigurationFieldResolverFactory;
import com.ubivashka.configuration.util.ClassMap;

public class ConfigurationCollectionFieldFactory implements ConfigurationFieldResolverFactory {

    @Override
    public ConfigurationFieldResolver<?> createResolver(ConfigurationFieldContext context) {
        if (!context.isValueCollection())
            return DefaultConfigurationProcessor.FIELD_RESOLVER_FACTORY.createResolver(context);
        ConfigurationProcessor processor = context.processor();

        ClassMap<ConfigurationFieldResolver<?>> fieldResolvers = new ClassMap<>(processor.getFieldResolvers());
        ClassMap<ConfigurationFieldResolverFactory> configurationFieldFactories = new ClassMap<>(
                processor.getFieldResolverFactories());

        ConfigurationFieldResolverFactory factory = configurationFieldFactories.getOrDefault(context.getGeneric(0),
                configurationFieldFactories.getAssignable(context.getGeneric(0), null));

        if (factory != null && !factory.equals(this) && factory.shouldResolveCollection())
            return factory.createResolver(context);

        ConfigurationFieldResolver<?> findedResolver = fieldResolvers.getOrDefault(context.getGeneric(0), null);

        if (findedResolver != null && findedResolver.shouldResolveCollection())
            if (context.hasAnnotation(SectionObject.class))
                return new ConfigurationFieldResolver<List<?>>() {
                    @Override
                    public List<?> resolveField(ConfigurationFieldResolverContext resolverContext) {
                        ConfigurationSectionHolder sectionHolder = resolverContext.configuration()
                                .getSection(resolverContext.path());

                        return sectionHolder.getKeys().stream().map(key -> {
                            ConfigurationFieldResolverContext context = new ConfigurationFieldResolverContextWrapper(
                                    resolverContext) {
                                @Override
                                public String path() {
                                    return key;
                                }

                                @Override
                                public ConfigurationSectionHolder configuration() {
                                    return sectionHolder;
                                }
                            };

                            return findedResolver.resolveField(context);
                        }).collect(Collectors.toList());
                    }
                };
        if (context.hasAnnotation(SingleObject.class) && findedResolver.shouldResolveCollection())
            return new ConfigurationFieldResolver<List<?>>() {
                @Override
                public List<?> resolveField(ConfigurationFieldResolverContext resolverContext) {
                    return resolverContext.configuration().getList(resolverContext.path()).stream().map(object -> {
                        SingleObjectResolverContext context = new SingleObjectResolverContext(resolverContext, object);
                        return findedResolver.resolveField(context);
                    }).collect(Collectors.toList());
                }
            };

        if (findedResolver != null)
            return findedResolver;

        return DefaultConfigurationProcessor.FIELD_RESOLVER_FACTORY.createResolver(context);
    }

}
