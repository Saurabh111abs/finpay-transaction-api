package com.finpay.transaction_api.exceptions;

public class DuplicateTransactionException extends RuntimeException {
	
	 
	public DuplicateTransactionException(String message) {
		
		super(message);
	}
}
