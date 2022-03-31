package com.ubivashka.configuration.util;

import java.util.HashMap;
import java.util.Map;

import com.ubivashka.configuration.map.StrongMapValue;

public class ClassMap<V> extends HashMap<Class<?>, V> {
	public ClassMap(Map<Class<?>, V> map) {
		super(map);
	}

	public ClassMap() {
		super();
	}

	/**
	 * Puts value to the map without overriding old value if key already exists
	 * 
	 * @param clazz that will be put to the map. Primitive types will be wrapped.
	 *              For example: {@link int.class} will be {@link Integer.class}
	 * @param value
	 */
	public void putWrapped(Class<?> clazz, V value) {
		Class<?> wrappedClass = PrimitiveWrapper.unwrapClass(clazz);
		if (containsKey(wrappedClass))
			return;
		put(wrappedClass, value);
	}

	/**
	 * Get value that assignable from class,
	 * 
	 * @param clazz
	 * @return value that have key
	 */
	public V getAssignable(Class<?> clazz) {
		return getAssignable(clazz, null);
	}

	public V getAssignable(Class<?> clazz, V def) {
		Class<?> findedKey = keySet().stream().filter(keyClass -> keyClass.isAssignableFrom(clazz))
				.filter(keyClass -> !StrongMapValue.class.isAssignableFrom(get(keyClass).getClass())).findFirst()
				.orElse(null);
		if (findedKey == null)
			return def;
		return get(findedKey);
	}

}
