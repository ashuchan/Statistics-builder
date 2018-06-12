package com.n26.statistics_builder.service.impl;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.n26.statistics_builder.model.Statistics;
import com.n26.statistics_builder.model.Transaction;
import com.n26.statistics_builder.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService{
	
	private Statistics cachedStatistics = new Statistics(0, 0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0);
	
	private DelayQueue<Transaction> currentTransactions = new DelayQueue<Transaction>();

	public Statistics getCurrentStatistics() {
		return cachedStatistics;
	}

	public synchronized void saveTransaction(Transaction toAdd) {
		currentTransactions.add(toAdd);
		cachedStatistics.setSum(cachedStatistics.getSum()+toAdd.getAmount());
		cachedStatistics.setCount(currentTransactions.size());
		cachedStatistics.setAvg(cachedStatistics.getSum()/cachedStatistics.getCount());
		if(cachedStatistics.getMax()<toAdd.getAmount()){
			cachedStatistics.setMax(toAdd.getAmount());
		}
		if (cachedStatistics.getMin()>toAdd.getAmount()){
			cachedStatistics.setMin(toAdd.getAmount());
		}
	}
	
	@Scheduled(fixedRate=1000)
	public synchronized void updateStatistics(){
		//Removing all expired transactions
		while(true){
			Transaction t = currentTransactions.poll();
			if(t==null){
				break;
			}
			System.out.println("Removed expired transaction t "+t+" with delay "+t.getDelay(TimeUnit.MILLISECONDS));
		}
		Statistics temp = new Statistics(0, 0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0);
		//Recalculating Statistics
		for(Transaction t:currentTransactions){
			temp.setSum(temp.getSum()+t.getAmount());
			if(temp.getMax()<t.getAmount()){
				temp.setMax(t.getAmount());
			}
			if (temp.getMin()>t.getAmount()){
				temp.setMin(t.getAmount());
			}
		}
		temp.setCount(currentTransactions.size());
		if(currentTransactions.size()>0){
			temp.setAvg(temp.getSum()/temp.getCount());
		}
		cachedStatistics = temp;
	}
	
}
