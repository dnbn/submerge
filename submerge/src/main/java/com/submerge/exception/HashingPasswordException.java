package com.submerge.exception;

public class HashingPasswordException extends RuntimeException {

	private static final long serialVersionUID = -502401312046361097L;

	public HashingPasswordException(String arg0) {
		super(arg0);
	}

	public HashingPasswordException(Throwable arg0) {
		super(arg0);
	}

	public HashingPasswordException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public HashingPasswordException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
