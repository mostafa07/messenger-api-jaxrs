package com.example.jaxrsTut.messenger.exception;

public class DataNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -7836521914071020789L;

	public DataNotFoundException(String message) {
		super(message);
	}
}
