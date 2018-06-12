package com.n26.statistics_builder;

import java.util.Arrays;

import org.junit.Test;

import com.n26.statistics_builder.model.Statistics;
import com.n26.statistics_builder.model.Transaction;
import com.n26.statistics_builder.service.TransactionService;
import com.n26.statistics_builder.service.impl.TransactionServiceImpl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StatisticsBuilderTest {
	@Test
	public void testAddTransactions() {
		TransactionService service = new TransactionServiceImpl();

		addTransactions(service, 1.5, 2, 2.5);

		Statistics currentStatistics = service.getCurrentStatistics();
		assertThat(currentStatistics.getCount(), is(3));
		assertThat(currentStatistics.getAvg(), is(2.0));
		assertThat(currentStatistics.getMax(), is(2.5));
		assertThat(currentStatistics.getMin(), is(1.5));
		assertThat(currentStatistics.getSum(), is(6.0));
	}

	private void addTransactions(TransactionService service, double... amounts) {
		Arrays.stream(amounts).forEach(amount -> {
			Transaction transaction = new Transaction(amount, System.currentTimeMillis());
			service.saveTransaction(transaction);
		});
	}

	@Test
	public void testPurgeOldTransactions() {
		TransactionService service = new TransactionServiceImpl();

		addTransactions(service, 1.5, 2);
		addInvalidTransactions(service, 777, 888, 999);
		addTransactions(service, 2.5);

		service.updateStatistics();

		Statistics currentStatistics = service.getCurrentStatistics();
		assertThat(currentStatistics.getCount(), is(3));
		assertThat(currentStatistics.getAvg(), is(2.0));
		assertThat(currentStatistics.getMax(), is(2.5));
		assertThat(currentStatistics.getMin(), is(1.5));
		assertThat(currentStatistics.getSum(), is(6.0));
	}
	
	private void addInvalidTransactions(TransactionService service, double... amounts) {
        Arrays.stream(amounts).forEach(amount -> {
            Transaction transaction = new Transaction(amount, 0);
            service.saveTransaction(transaction);
        });
    }
}
