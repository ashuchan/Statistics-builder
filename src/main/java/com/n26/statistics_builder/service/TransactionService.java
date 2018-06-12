package com.n26.statistics_builder.service;

import com.n26.statistics_builder.model.Statistics;
import com.n26.statistics_builder.model.Transaction;

public interface TransactionService {
	
	public static final long MAX_VALID_AGE_IN_MILISECONDS = 60*1000;
	
	Statistics getCurrentStatistics();

	void saveTransaction(Transaction t);
	
	void updateStatistics();
}
