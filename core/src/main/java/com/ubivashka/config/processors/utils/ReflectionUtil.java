package com.ubivashka.config.processors.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class ReflectionUtil {
	private ReflectionUtil() {
	}

	public static <T> Optional<Constructor<?>> getContructor(Class<T> clazz, Class<?>... constructorArguments) {
		return Arrays.stream(clazz.getDeclaredConstructors())
				.filter(constructor -> constructor.getParameterCount() == constructorArguments.length
						&& Arrays.equals(constructorArguments, constructor.getParameterTypes()))
				.findFirst();
	}

	public static Class<?> getRealType(Field field) {
		if (isCollection(field.getType()))
			return getParameterizedTypes((ParameterizedType) field.getGenericType()).get(0);
		return field.getType();
	}

	public static boolean isCollection(Class<?> clazz) {
		return Collection.class.isAssignableFrom(clazz);
	}

	public static Class<?>[] getSuperClassGenerics(Class<?> clazz) {
		Type[] types = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments();
		return Arrays.stream(types).map(type -> typeToClass(type)).toArray(Class<?>[]::new);
	}

	public static Class<?>[] getInterfaceGenerics(Class<?> clazz) {
		Type[] types = clazz.getGenericInterfaces();
		return Arrays.stream(types).map(type -> getParameterizedTypes((ParameterizedType) type))
				.flatMap(Collection::stream).toArray(Class<?>[]::new);
	}

	public static <T> Class<?>[] getGenericInterfaceParameters(Class<?> clazz, Class<T> interfaceType) {
		Class<?>[] result = new Class<?>[0];
		if (!interfaceType.isInterface() || interfaceType.getTypeParameters().length < 1)
			return result;
		Type[] interfaceTypes = clazz.getGenericInterfaces();
		for (Type it : interfaceTypes) {
			if (!(it instanceof ParameterizedType))
				continue;
			ParameterizedType parameterizedType = (ParameterizedType) it;
			if (!parameterizedType.getRawType().equals(interfaceType))
				continue;
			return getParameterizedTypes(parameterizedType).toArray(new Class<?>[0]);
		}
		return result;
	}

	public static List<Class<?>> getParameterizedTypes(ParameterizedType parameterizedType) {
		List<Class<?>> list = new ArrayList<>();

		Type[] typeParameters = parameterizedType.getActualTypeArguments();
		for (int j = 0; j < typeParameters.length; j++) {
			Type parameter = typeParameters[j];
			list.add(typeToClass(parameter));
		}
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
