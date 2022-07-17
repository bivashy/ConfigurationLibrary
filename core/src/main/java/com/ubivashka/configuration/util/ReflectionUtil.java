package com.ubivashka.configuration.util;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtil {
	public static List<Class<?>> getParameterizedTypes(ParameterizedType parameterizedType) {
		List<Class<?>> list = new ArrayList<>();

		Type[] typeParameters = parameterizedType.getActualTypeArguments();
		for (Type parameter : typeParameters)
			list.add(typeToClass(parameter));
		return list;
	}

	public static Class<?> typeToClass(Type type) {
		if (type instanceof Class) {
			return (Class<?>) type;
		} else if (type instanceof GenericArrayType) {
			return Array.newInstance(typeToClass(((GenericArrayType) type).getGenericComponentType()), 0).getClass();
		} else if (type instanceof ParameterizedType) {
			return typeToClass(((ParameterizedType) type).getRawType());
		} else if (type instanceof TypeVariable) {
			Type[] bounds = ((TypeVariable<?>) type).getBounds();
			return bounds.length == 0 ? Object.class : typeToClass(bounds[0]);
		} else if (type instanceof WildcardType) {
			Type[] bounds = ((WildcardType) type).getUpperBounds();
			return bounds.length == 0 ? Object.class : typeToClass(bounds[0]);
		} else {
			throw new UnsupportedOperationException("cannot handle type class: " + type.getClass());
		}
	}

}
