package com.ubivashka.configuration.util;

import java.util.HashMap;
import java.util.Map;

public class ClassMap<V> extends HashMap<Class<?>, V> {
    public ClassMap(Map<Class<?>, V> map) {
        super(map);
    }

    public ClassMap() {
        super();
    }

    /**
     * Puts value to the map, will override old value if exists.
     *
     * @param clazz that will be put to the map. Primitive types will be wrapped.
     *              For example: {@link int.class} will be {@link Integer}
     * @param value that will be added to the map
     */
    public void putWrapped(Class<?> clazz, V value) {
        Class<?> wrappedClass = PrimitiveWrapper.tryWrap(clazz);
        put(wrappedClass, value);
    }

    /**
     * Get value that assignable from class,
     *
     * @param clazz that will be compared
     * @return value that have key
     */
    public V getAssignable(Class<?> clazz) {
        return getAssignable(clazz, null);
    }

    public V getAssignable(Class<?> clazz, V def) {
        Class<?> findedKey = keySet().stream().filter(keyClass -> {
            if (StrongMapKey.class.isAssignableFrom(keyClass))
                return keyClass.equals(clazz);
            if (StrongMapValue.class.isAssignableFrom(get(keyClass).getClass()))
                return keyClass.equals(clazz);
            return keyClass.isAssignableFrom(clazz);
        }).findFirst().orElse(null);
        if (findedKey == null)
            return def;
        return get(findedKey);
    }

    /**
     * Marker interface for {@linkplain ClassMap#getAssignable(Class, Object)}
     * method. If classmap <bold>value</bold> object has this interface it will not included when searching
     * value in method {@linkplain ClassMap#getAssignable(Class, Object)}
     */
    public interface StrongMapValue {
    }

    /**
     * Marker interface for {@linkplain ClassMap#getAssignable(Class, Object)}
     * method. If classmap key has this interface it will not included when searching
     * value in method {@linkplain ClassMap#getAssignable(Class, Object)}
     */
    public interface StrongMapKey {
    }
}
