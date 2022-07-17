package com.ubivashka.configuration.resolver.scalar;

import java.util.Optional;

public interface ScalarObjectResolver<T> {
    ScalarObjectResolver<String> STRING = (object) -> object == null ? null : object.toString();
    ScalarObjectResolver<Boolean> BOOLEAN = new BooleanResolver();
    ScalarObjectResolver<Float> FLOAT = NumberResolvers.FLOAT;
    ScalarObjectResolver<Double> DOUBLE = NumberResolvers.DOUBLE;
    ScalarObjectResolver<Integer> INTEGER = NumberResolvers.INTEGER;
    ScalarObjectResolver<Long> LONG = NumberResolvers.LONG;

    T resolveNullable(Object object);

    default T resolvedOrDefault(Object object, T def) {
        T resolved = resolveNullable(object);
        if (resolved == null)
            return def;
        return resolved;
    }

    default Optional<T> resolve(Object object) {
        return Optional.ofNullable(resolveNullable(object));
    }
}
