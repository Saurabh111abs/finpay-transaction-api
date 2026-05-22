package com.finpay.transaction_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.finpay.transaction_api.enums.TransactionStatus;

import lombok.Data;
@Data
public class TransactionResponse {
	
	 private String transactionId;
	    private TransactionStatus status;
	    private BigDecimal amount;
	    private String currency;
	    private String senderId;
	    private String receiverId;
	    private String failureReason;
	    private LocalDateTime createdAt;
	    

	    
	    

}
