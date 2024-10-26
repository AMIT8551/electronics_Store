package com.electronics.store.exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.electronics.store.dtos.ApiResponceMassage;
import com.electronics.store.dtos.ApiResponses;

@RestControllerAdvice
public class GlobaleExceptionHandler {

	@ExceptionHandler(value = RessourceNotFoundException.class)
	public ResponseEntity<ApiResponceMassage> resourceNotFoundExceptionHandler(RessourceNotFoundException ex) {

		ApiResponceMassage responceMassage = ApiResponceMassage.builder().message(ex.getMessage()).success(true)
				.status(HttpStatus.NOT_FOUND).build();

		return new ResponseEntity<ApiResponceMassage>(responceMassage, HttpStatus.NOT_FOUND);
	}

//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<List<ApiResponceMassage>> methodArgNotFoundExceptionHandler(
//			MethodArgumentNotValidException ex) {
//
//		List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
//
//		List<ApiResponceMassage> responseMessages = new ArrayList<>();
//
//		for (ObjectError error : allErrors) {
//
//			String field = ((FieldError) error).getField();
//
////	    	String objectName = error.getObjectName();
//
//			String errorMessage = error.getDefaultMessage();
//
//			// Build ApiResponseMessage and add to the list
//			ApiResponceMassage apiResponseMessage = ApiResponceMassage.builder().message(field + ": " + errorMessage)
//					.status(HttpStatus.BAD_REQUEST).success(false).build();
//
//			responseMessages.add(apiResponseMessage);
//		}
//
//		return new ResponseEntity<>(responseMessages, HttpStatus.BAD_REQUEST);
//	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,ApiResponses>> methodArgNotFoundExceptionHandler(MethodArgumentNotValidException ex){
		
		  
	    List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();

	    
	    Map<String,ApiResponses> responseMessages =  new HashMap<String, ApiResponses>();

	  
	    for (ObjectError error : allErrors) {
	         
	    	String field = ((FieldError)error).getField();

	        String errorMessage = error.getDefaultMessage(); 
	        String customStatus;
	        
	        switch (error.getCode()) {
	        case "Size":
	        	customStatus = "TOO_LONG";  
	            break;
	        case "Pattern":
	            customStatus = "INVALID_PATTERN";  
	            break;
	        case "NotBlank":
	            customStatus = "BLANK_FIELD";  
	            break;
	        case "Min":
	            customStatus = "TOO_SMALL";  
	            break;
	        case "Max":
	            customStatus = "TOO_LARGE";  
	            break;
	        default:
	            customStatus = "INVALID";  
	    }

	        
	        ApiResponses apiResponseMessage = ApiResponses.builder()
	                .message(errorMessage)
	                .customStatus(customStatus)
	                .status(HttpStatus.BAD_REQUEST)
	                .success(false)
	                .build();

	        responseMessages.put(field, apiResponseMessage);
	    }

	    // Return the list as the response entity
	    return new ResponseEntity<>(responseMessages, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = ImageFormatNotSupportException.class)
	public ResponseEntity<ApiResponceMassage> imageFormatNotSupportExceptionHandler(ImageFormatNotSupportException ex) {

		ApiResponceMassage responceMassage = ApiResponceMassage.builder().message(ex.getMessage()).success(true)
				.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
		

		return new ResponseEntity<ApiResponceMassage>(responceMassage, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}
	
	@ExceptionHandler(value = ImageNotFoundException.class)
	public ResponseEntity<ApiResponceMassage> imageNotFoundExceptionHandler(ImageNotFoundException ex) {

		ApiResponceMassage responceMassage = ApiResponceMassage.builder().message(ex.getMessage()).success(false)
				.status(HttpStatus.NOT_FOUND).build();
		

		return new ResponseEntity<ApiResponceMassage>(responceMassage, HttpStatus.NOT_FOUND);
	}

}
