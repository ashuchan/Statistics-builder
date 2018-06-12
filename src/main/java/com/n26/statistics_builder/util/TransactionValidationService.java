package com.n26.statistics_builder.util;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.n26.statistics_builder.model.Transaction;
import com.n26.statistics_builder.service.TransactionService;

@Service
public class TransactionValidationService {
	
	private final TransactionService transactionsService;

    @Autowired
    public TransactionValidationService(TransactionService transactionsService) {
        this.transactionsService = transactionsService;
    }
	
	public void addValid(Transaction transaction) throws ExpiredTransactionException {
		if(transaction == null){
			throw new NullPointerException();
		}
        Instant instantOfTransaction = Instant.ofEpochMilli(transaction.getTimestamp());
        Instant oldestValidInstant = Instant.now().minusMillis(TransactionService.MAX_VALID_AGE_IN_MILISECONDS);
        if(instantOfTransaction.isBefore(oldestValidInstant)){
        	throw new ExpiredTransactionException();
        }
        transactionsService.saveTransaction(transaction);
    }
}
