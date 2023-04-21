package com.bivashy.configuration.resolver.scalar;

public class ResolveException extends RuntimeException{
	public ResolveException() {
		super();
	}

	public ResolveException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResolveException(String message) {
		super(message);
	}

	public ResolveException(Throwable cause) {
		super(cause);
	}
}
