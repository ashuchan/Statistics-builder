package com.n26.statistics_builder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.n26.statistics_builder.model.Transaction;
import com.n26.statistics_builder.util.ExpiredTransactionException;
import com.n26.statistics_builder.util.TransactionValidationService;

@RequestMapping("/transactions")
@RestController
public class TransactionController {

	private final TransactionValidationService transactionsService;

    @Autowired
    public TransactionController(TransactionValidationService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> registerTransaction(@RequestBody Transaction transaction) {
        try {
			transactionsService.addValid(transaction);
			return new ResponseEntity(HttpStatus.CREATED);
		} catch (ExpiredTransactionException | NullPointerException e) {
			System.out.println("Expired transaction found in request:- "+transaction);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
    }
}
