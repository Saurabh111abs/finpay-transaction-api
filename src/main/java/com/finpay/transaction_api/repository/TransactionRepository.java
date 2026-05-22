package com.finpay.transaction_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finpay.transaction_api.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, String>{
	
	
	Optional<Transaction>  findByIdempotencyKey(String idempotencyKey);

}
