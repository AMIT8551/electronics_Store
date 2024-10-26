package com.electronics.store.exceptions;

public class ImageNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;

	public ImageNotFoundException() {
		super("Image Not Found");
	}
	
	public ImageNotFoundException(String message) {
		super(message);
	}
	

}
