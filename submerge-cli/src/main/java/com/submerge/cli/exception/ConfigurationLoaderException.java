package com.submerge.cli.exception;

public class ConfigurationLoaderException extends RuntimeException {

	private static final long serialVersionUID = 6425462682026412833L;

	public ConfigurationLoaderException() {
		super();
	}

	public ConfigurationLoaderException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ConfigurationLoaderException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigurationLoaderException(String message) {
		super(message);
	}

	public ConfigurationLoaderException(Throwable cause) {
		super(cause);
	}

}
