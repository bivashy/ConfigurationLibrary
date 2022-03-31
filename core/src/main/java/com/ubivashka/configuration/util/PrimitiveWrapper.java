package com.ubivashka.configuration.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * A utility for wrapping and unwrapping primitive types.<br>
 * <br>
 * The default primitive value for a wrapper can be obtained using
 * {@link #defaultValue(Class)}.
 * 
 * @author Elliot Ford
 * 
 * @see <a href="https://gist.github.com/Ellzord/678737f13c6d65db3263">Original
 *      source in github</a>
 * @see Integer
 * @see Boolean
 * @see Short
 * @see Long
 * @see Character
 * @see Byte
 * @see Double
 * @see Float
 *
 */
public final class PrimitiveWrapper {

	private static final Map<Class<?>, Class<?>> PRIMITIVES_WRAPPERS = new HashMap<>();

	private static final Map<Class<?>, Object> WRAPPER_DEFAULTS = new HashMap<>();

	static {
		/*
		 * Primitives to wrappers.
		 */
		PRIMITIVES_WRAPPERS.put(int.class, Integer.class);
		PRIMITIVES_WRAPPERS.put(boolean.class, Boolean.class);
		PRIMITIVES_WRAPPERS.put(short.class, Short.class);
		PRIMITIVES_WRAPPERS.put(long.class, Long.class);
		PRIMITIVES_WRAPPERS.put(char.class, Character.class);
		PRIMITIVES_WRAPPERS.put(byte.class, Byte.class);
		PRIMITIVES_WRAPPERS.put(double.class, Double.class);
		PRIMITIVES_WRAPPERS.put(float.class, Float.class);

		/*
		 * Default primitive values.
		 */
		WRAPPER_DEFAULTS.put(Integer.class, 0);
		WRAPPER_DEFAULTS.put(Boolean.class, false);
		WRAPPER_DEFAULTS.put(Short.class, (short) 0);
		WRAPPER_DEFAULTS.put(Long.class, 0L);
		WRAPPER_DEFAULTS.put(Character.class, '\0');
		WRAPPER_DEFAULTS.put(Byte.class, (byte) 0);
		WRAPPER_DEFAULTS.put(Double.class, 0d);
		WRAPPER_DEFAULTS.put(Float.class, 0f);
	}

	@SuppressWarnings("unchecked")
	public static <T> T defaultValue(Class<?> type) {
		Object value = WRAPPER_DEFAULTS.get(Objects.requireNonNull(type));
		if (value == null)
			throw new IllegalArgumentException("Not primitive wrapper");
		return (T) value;
	}

	/**
	 * Checks to see if the specified type is a primitive wrapper.
	 * 
	 * @param type Type to check.
	 * @return Whether the type is a primitive wrapper.
	 */
	public static boolean isWrapper(Class<?> type) {
		return unwrap(type).isPresent();
	}

	/**
	 * Unwraps the primitive wrapper to the primitive type.
	 * 
	 * @param type Wrapper type to unwrap.
	 * @return The unwrapped primitive type.
	 * @throws IllegalArgumentException If the type is not a primitive wrapper.
	 */
	public static Class<?> unwrapClass(Class<?> type) {
		return unwrap(type).orElse(type);
	}

	private static Optional<Class<?>> unwrap(Class<?> type) {
		Objects.requireNonNull(type);
		return PRIMITIVES_WRAPPERS.keySet().stream().filter(key -> PRIMITIVES_WRAPPERS.get(key).equals(type))
				.findFirst();
	}

	/**
	 * Gets the wrapper type for the primitive type.
	 * 
	 * @param type Primitive type to wrap.
	 * @return The primitive wrapper type.
	 * @throws IllegalArgumentException If the type is not primitive.
	 */
	public static Class<?> wrap(Class<?> type) {
		if (!type.isPrimitive()) {
			throw new IllegalArgumentException("Not primitive");
		}
		return PRIMITIVES_WRAPPERS.get(type);
	}
}
