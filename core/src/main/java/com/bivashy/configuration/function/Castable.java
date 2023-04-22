package com.bivashy.configuration.function;

public interface Castable<T> {

	/**
	 * Safely cast current object to another object.
	 * Returns null if argument is null or cast throws {@link ClassCastException}.
	 * 
	 * @param clazz
	 * @return Casted object or null.
	 */
	default <R extends T> R as(Class<R> clazz) {
		if (clazz == null)
			return null;
		try {
			return clazz.cast(this);
		} catch (ClassCastException ignored) {
			return null;
		}
	}
}
