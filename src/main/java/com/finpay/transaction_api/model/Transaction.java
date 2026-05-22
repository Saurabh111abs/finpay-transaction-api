package com.finpay.transaction_api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.finpay.transaction_api.enums.TransactionStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	@Column(unique = true , nullable = false)
	private String idempotencyKey;
	
	private String senderId;
	
	private String receiverId;
	
	private BigDecimal amount;
	
	 private String currency;
	
	@Enumerated(EnumType.STRING)
	private TransactionStatus status;
	
	private String failureReason;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	@PrePersist
	public void prePersist() {
		
		 createdAt = LocalDateTime.now();
	        updatedAt = LocalDateTime.now();
	}
	
	   @PreUpdate
	    public void preUpdate() {
	        updatedAt = LocalDateTime.now();
	    }
	   
	
	   
	   
	

}
