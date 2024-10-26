package com.electronics.store.utilities;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageValidatorClass implements ConstraintValidator<ImageNameValidate, String> {
	
	private Logger logger = LoggerFactory.getLogger(ImageValidatorClass.class);
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		logger.info("massage from invalid");
		
		//logic 
		if (value.isBlank()) {
			return false;

		}
		return true;
	}

}
