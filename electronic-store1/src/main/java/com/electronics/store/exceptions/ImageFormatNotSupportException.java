package com.electronics.store.exceptions;

public class ImageFormatNotSupportException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ImageFormatNotSupportException() {
		super("Format Not Supported");
	}
	
	public ImageFormatNotSupportException(String message) {
		super(message);
	}

}
