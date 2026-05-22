package com.finpay.transaction_api.exceptions;

public class InsufficientFundsException extends RuntimeException {
	
	public InsufficientFundsException(String message){
		
		super(message);
	}

}
