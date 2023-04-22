package com.bivashy.configuration.holder;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import com.bivashy.configuration.resolver.scalar.ScalarObjectResolver;
import com.bivashy.configuration.function.Castable;

public interface ConfigurationSectionHolder extends Castable<ConfigurationSectionHolder> {
    /**
     * Returns plain object that was not affected by any resolver or unwrapped. May return null if not contains in configuration
     *
     * @param key to the object.
     * @return plain value in configuration.
     */
    Object get(String... key);

    /**
     * Returns string value of the object. It will return null if object in configuration value was null or not exists.
     *
     * <p>Gets the value using resolver from {@link ScalarObjectResolver}</p>
     *
     * @param key to the object
     * @return string representation of object.
     * @see #get(String...)
     */
    default String getString(String... key) {
        return ScalarObjectResolver.STRING.resolveNullable(get(key));
    }

    /**
     * Returns boolean value of the object.
     *
     * <p>Gets the value using resolver from {@link ScalarObjectResolver}</p>
     *
     * <p>Accept values:</p>
     * <p>true, t, yes, y, 1 - returns true</p>
     * <p>false, f, no, n, 0 - returns false</p>
     *
     * @param key to the object
     * @return boolean representation of object
     * @see #get(String...)
     */
    default boolean getBoolean(String... key) {
        return getBoolean(false, key);
    }

    /**
     * Returns boolean value of the object.
     *
     * <p>Gets the value using resolver from {@link ScalarObjectResolver}</p>
     *
     * <p>Accept values:</p>
     * <p>true, t, yes, y, 1 - returns true</p>
     * <p>false, f, no, n, 0 - returns false</p>
     *
     * @param default value if no
     * @param key     to the object
     * @return boolean representation of object
     * @see #get(String...)
     */
    default boolean getBoolean(boolean def, String... key) {
        return ScalarObjectResolver.BOOLEAN.resolvedOrDefault(get(key), def);
    }

    /**
     * Returns float representation of the object
     *
     * <p>Gets the value using resolver from {@link ScalarObjectResolver}</p>
     *
     * @param key to the object
     * @return float value
     * @see #get(String...)
     */
    default float getFloat(String... key) {
        return getFloat(0f, key);
    }

    /**
     * Returns float representation of the object
     *
     * <p>Gets the value using resolver from {@link ScalarObjectResolver}</p>
     *
     * @param default value
     * @param key     to the object
     * @return float value
     * @see #get(String...)
     */
    default float getFloat(float def, String... key) {
        return ScalarObjectResolver.FLOAT.resolvedOrDefault(get(key), def);
    }

    /**
     * Returns double representation of the object
     *
     * <p>Gets the value using resolver from {@link ScalarObjectResolver}</p>
     *
     * @param key to the object
     * @return double value
     * @see #get(String...)
     */
    default double getDouble(String... key) {
        return getDouble(0d, key);
    }

    /**
     * Returns double representation of the object
     *
     * <p>Gets the value using resolver from {@link ScalarObjectResolver}</p>
     *
     * @param default value
     * @param key     to the object
     * @return double value
     * @see #get(String...)
     */
    default double getDouble(double def, String... key) {
        return ScalarObjectResolver.DOUBLE.resolvedOrDefault(get(key), def);
    }

    /**
     * Returns int representation of the object
     *
     * <p>Gets the value using resolver from {@link ScalarObjectResolver}</p>
     *
     * @param key to the object
     * @return int value
     * @see #get(String...)
     */
    default int getInt(String... key) {
        return getInt(0, key);
    }

    /**
     * Returns int representation of the object
     *
     * <p>Gets the value using resolver from {@link ScalarObjectResolver}</p>
     *
     * @param default value
     * @param key     to the object
     * @return int value
     * @see #get(String...)
     */
    default int getInt(int def, String... key) {
        return ScalarObjectResolver.INTEGER.resolvedOrDefault(get(key), def);
    }

    /**
     * Returns long representation of the object
     *
     * <p>Gets the value using resolver from {@link ScalarObjectResolver}</p>
     *
     * @param key to the object
     * @return long value
     * @see #get(String...)
     */
    default long getLong(String... key) {
        return getLong(0, key);
    }

    /**
     * Returns long representation of the object
     *
     * <p>Gets the value using resolver from {@link ScalarObjectResolver}</p>
     *
     * @param default value
     * @param key     to the object
     * @return long value
     * @see #get(String...)
     */
    default long getLong(long def, String... key) {
        return ScalarObjectResolver.LONG.resolvedOrDefault(get(key), def);
    }

    /**
     * Returns if object is assignable from class
     *
     * @param clazz checks if is assignable
     * @param key   to the object
     * @return is configuration object assignable from class.
     */
    default boolean is(Class<?> clazz, String... key) {
        return is(obj -> clazz.isAssignableFrom(obj.getClass()), key);
    }

    /**
     * Returns if object predicate test passes
     *
     * @param test test of the object
     * @param key  to the object
     * @return is configuration object assignable from class.
     */
    default boolean is(Predicate<Object> test, String... key) {
        return test.test(get(key).getClass());
    }

    <L> List<L> getList(String... key);

    /**
     * Resolves section from the key without resolver.
     *
     * <p><bold>Does not uses {@link ScalarObjectResolver}</bold></p>
     *
     * @param key to the object
     * @return section from key
     */
    ConfigurationSectionHolder section(String... key);

    /**
     * Returns result of checking if configuration path is section
     * @param key  to the object
     * @return is path can be represented as section
     */
    boolean isSection(String... key);

    /**
     * Returns result of checking if configuration path is list
     * @param key  to the object
     * @return is path can be represented as list
     */
    boolean isList(String... key);

    /**
     * Returns result of existing specific path in ConfigurationSectionHolder
     *
     * @param key to the object
     * @return contains specific path
     */
    boolean contains(String... key);

    /**
     * Returns keys that exists in this configuration section holder.
     *
     * @return keys in this configuration section
     */
    Set<String> keys();

    /**
     * Returns parent of the current ConfigurationSectionHolder. Result may be inaccurate.
     *
     * @return parent of this section
     */
    ConfigurationSectionHolder parent();

    /**
     * Returns single key of the current section.
     *
     * @return single key of this section
     */
    String key();

    /**
     * Returns original section holder. Useful for section null check
     *
     * @return original section holder
     */
    Object getOriginalHolder();
}
