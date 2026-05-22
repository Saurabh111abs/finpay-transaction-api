package com.finpay.transaction_api.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransactionRequest {
	
	@NotBlank(message =  "Idempotency key is required")
	private String idempotencyKey;
	
	@NotBlank(message = "Sender Id is required")
    private String senderId;
	
	@NotBlank(message = "Receiver Id is required")
    private String receiverId;
	
	@NotNull(message = "Amount is required")
	@DecimalMin(value = "0.01" , message = "Amount must be greater than 0")
    private BigDecimal amount;
	
	@NotBlank(message = "Currency is required")
    private String currency;


	

}
