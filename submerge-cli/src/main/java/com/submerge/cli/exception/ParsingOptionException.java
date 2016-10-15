package com.submerge.cli.exception;

public class ParsingOptionException extends Exception {

	private static final long serialVersionUID = 1443755777726198578L;

	public ParsingOptionException() {
		super();
	}

	public ParsingOptionException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public ParsingOptionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ParsingOptionException(String arg0) {
		super(arg0);
	}

	public ParsingOptionException(Throwable arg0) {
		super(arg0);
	}

}
