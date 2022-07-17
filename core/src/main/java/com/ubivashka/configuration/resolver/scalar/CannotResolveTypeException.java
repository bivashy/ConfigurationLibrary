package com.ubivashka.configuration.resolver.scalar;

public class CannotResolveTypeException extends ResolveException {
	public CannotResolveTypeException(Object value, String type) {
		super("Cannot resolve type " + type + " from " + value.getClass());
	}
}
