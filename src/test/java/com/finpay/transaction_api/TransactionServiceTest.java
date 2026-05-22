package com.finpay.transaction_api;


import com.finpay.transaction_api.dto.TransactionRequest;
import com.finpay.transaction_api.dto.TransactionResponse;

import com.finpay.transaction_api.enums.TransactionStatus;
import com.finpay.transaction_api.exceptions.DuplicateTransactionException;
import com.finpay.transaction_api.exceptions.InsufficientFundsException;
import com.finpay.transaction_api.model.Transaction;
import com.finpay.transaction_api.repository.TransactionRepository;
import com.finpay.transaction_api.service.TransactionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository repository;

    @InjectMocks
    private TransactionService service;

    private TransactionRequest request;

    @BeforeEach
    void setUp() {
        request = new TransactionRequest();
        request.setIdempotencyKey("txn-test-001");
        request.setSenderId("user-1");
        request.setReceiverId("user-2");
        request.setAmount(new BigDecimal("500.00"));
        request.setCurrency("INR");
    }

    // Test 1 — Successful transaction
    @Test
    void shouldProcessTransactionSuccessfully() {
        Transaction saved = new Transaction();
        saved.setId("uuid-123");
        saved.setStatus(TransactionStatus.SUCCESS);
        saved.setAmount(request.getAmount());
        saved.setCurrency(request.getCurrency());
        saved.setSenderId(request.getSenderId());
        saved.setReceiverId(request.getReceiverId());

        when(repository.findByIdempotencyKey("txn-test-001"))
            .thenReturn(Optional.empty());
        when(repository.save(any(Transaction.class)))
            .thenReturn(saved);

        TransactionResponse response = service.processTransaction(request);

        assertEquals(TransactionStatus.SUCCESS, response.getStatus());
        assertEquals(new BigDecimal("500.00"), response.getAmount());
        verify(repository, times(1)).save(any(Transaction.class));
    }

    // Test 2 — Duplicate transaction
    @Test
    void shouldThrowExceptionForDuplicateTransaction() {
        when(repository.findByIdempotencyKey("txn-test-001"))
            .thenReturn(Optional.of(new Transaction()));

        assertThrows(DuplicateTransactionException.class, () -> {
            service.processTransaction(request);
        });

        verify(repository, never()).save(any(Transaction.class));
    }

    // Test 3 — Insufficient funds
    @Test
    void shouldThrowExceptionForInsufficientFunds() {
        request.setAmount(new BigDecimal("99999.00"));

        when(repository.findByIdempotencyKey("txn-test-001"))
            .thenReturn(Optional.empty());

        assertThrows(InsufficientFundsException.class, () -> {
            service.processTransaction(request);
        });

        verify(repository, never()).save(any(Transaction.class));
    }

    // Test 4 — Transaction not found
    @Test
    void shouldThrowExceptionWhenTransactionNotFound() {
        when(repository.findById("invalid-id"))
            .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.getTransaction("invalid-id");
        });
    }
}
