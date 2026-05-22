package com.finpay.transaction_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.finpay.transaction_api.dto.TransactionRequest;
import com.finpay.transaction_api.dto.TransactionResponse;
import com.finpay.transaction_api.service.TransactionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
	
	private final TransactionService service;

    @PostMapping("/createTransaction")
    public ResponseEntity<TransactionResponse> createTransaction(
            @Valid @RequestBody TransactionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(service.processTransaction(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransaction(
            @PathVariable String id) {
        return ResponseEntity.ok(service.getTransaction(id));
    }

    @GetMapping("/getTransactions")
    public ResponseEntity<List<TransactionResponse>> getAllTransactions() {
        return ResponseEntity.ok(service.getAllTransactions());
    }
	
	

}
