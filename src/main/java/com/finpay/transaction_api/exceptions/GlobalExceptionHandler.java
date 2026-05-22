package com.finpay.transaction_api.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.finpay.transaction_api.controller")
public class GlobalExceptionHandler {
	
	  @ExceptionHandler(DuplicateTransactionException.class)
	    public ResponseEntity<Map<String, String>> handleDuplicate(
	            DuplicateTransactionException ex) {
	        return ResponseEntity.status(HttpStatus.CONFLICT)
	            .body(Map.of("error", ex.getMessage()));
	    }
	  
	  @ExceptionHandler(InsufficientFundsException.class)
	    public ResponseEntity<Map<String, String>> handleInsufficientFunds(
	            InsufficientFundsException ex) {
	        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
	            .body(Map.of("error", ex.getMessage()));
	    }
	  
	   @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<Map<String, String>> handleValidation(
	            MethodArgumentNotValidException ex) {
	        Map<String, String> errors = new HashMap<>();
	        ex.getBindingResult().getFieldErrors()
	            .forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));
	        return ResponseEntity.badRequest().body(errors);
	    }
	   
	   @ExceptionHandler(Exception.class)
	    public ResponseEntity<Map<String, String>> handleGeneral(Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body(Map.of("error", "An unexpected error occurred"));
	    }

}
