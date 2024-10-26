package com.electronics.store.exceptions;

public class RessourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RessourceNotFoundException() {
		super("Resource Not Found");
	}

	public RessourceNotFoundException(String Msg) {
		super(Msg);
	}
}
