package com.finpay.transaction_api.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.finpay.transaction_api.dto.TransactionRequest;
import com.finpay.transaction_api.dto.TransactionResponse;
import com.finpay.transaction_api.enums.TransactionStatus;
import com.finpay.transaction_api.exceptions.DuplicateTransactionException;
import com.finpay.transaction_api.exceptions.InsufficientFundsException;
import com.finpay.transaction_api.model.Transaction;
import com.finpay.transaction_api.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionService {
	
	private final TransactionRepository repository;
	
	private static final BigDecimal MOCK_BALANCE = new BigDecimal("10000.00");
	
	
    public TransactionResponse processTransaction(TransactionRequest request) {

        // 1. Duplicate check
        repository.findByIdempotencyKey(request.getIdempotencyKey())
            .ifPresent(t -> {
                log.warn("Duplicate transaction. Key: {}", 
                    request.getIdempotencyKey());
                throw new DuplicateTransactionException(
                    "Transaction already processed with key: " 
                    + request.getIdempotencyKey());
            });

        // 2. Balance check
        if (request.getAmount().compareTo(MOCK_BALANCE) > 0) {
            log.warn("Insufficient funds for sender: {}", 
                request.getSenderId());
            throw new InsufficientFundsException(
                "Insufficient funds for sender: " + request.getSenderId());
        }

        // 3. Save transaction
        Transaction tx = new Transaction();
        tx.setIdempotencyKey(request.getIdempotencyKey());
        tx.setSenderId(request.getSenderId());
        tx.setReceiverId(request.getReceiverId());
        tx.setAmount(request.getAmount());
        tx.setCurrency(request.getCurrency());
        tx.setStatus(TransactionStatus.SUCCESS); // ← ye line pehle
        
        Transaction saved = repository.save(tx); // ← ye baad mein
        log.info("Transaction successful. ID: {}", saved.getId());

        return mapToResponse(saved);
    }

    public TransactionResponse getTransaction(String id) {
        Transaction tx = repository.findById(id)
            .orElseThrow(() -> 
                new RuntimeException("Transaction not found: " + id));
        return mapToResponse(tx);
    }

    public List<TransactionResponse> getAllTransactions() {
        return repository.findAll().stream()
            .map(this::mapToResponse)
            .toList();
    }

    private TransactionResponse mapToResponse(Transaction tx) {
        TransactionResponse res = new TransactionResponse();
        res.setTransactionId(tx.getId());
        res.setStatus(tx.getStatus());
        res.setAmount(tx.getAmount());
        res.setCurrency(tx.getCurrency());
        res.setSenderId(tx.getSenderId());
        res.setReceiverId(tx.getReceiverId());
        res.setFailureReason(tx.getFailureReason());
        res.setCreatedAt(tx.getCreatedAt());
        return res;
    }

}
