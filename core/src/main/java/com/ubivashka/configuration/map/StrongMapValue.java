package com.ubivashka.configuration.map;

import com.ubivashka.configuration.util.ClassMap;

/**
 * Marker interface for {@linkplain ClassMap#getAssignable(Class, Object)}
 * method. If object has this interface it will not included when searching
 * value in method {@linkplain ClassMap#getAssignable(Class, Object)}
 */
public interface StrongMapValue {

}
