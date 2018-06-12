package com.n26.statistics_builder.model;

import java.time.Instant;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import com.n26.statistics_builder.service.TransactionService;

public class Transaction implements Delayed{

	private double amount;
	
	private long timestamp;
	
	private long delayTime;
	
	public Transaction() {
	}

	public Transaction(double amount, long timestamp) {
		this.amount = amount;
		this.timestamp = timestamp;
		delayTime = timestamp+TransactionService.MAX_VALID_AGE_IN_MILISECONDS;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
		delayTime = timestamp+TransactionService.MAX_VALID_AGE_IN_MILISECONDS;
	}

	public int compareTo(Delayed o) {
		Transaction other = (Transaction) o;
		if(timestamp>other.timestamp){
			return 1;
		}
		if(timestamp==other.timestamp){
			return 0;
		}
		return -1;
	}

	/* This method return the time remaining for a transaction to be valid for consideration in statistics.
	 * If the value returned is <=0 then the transaction has expired.
	 * @see java.util.concurrent.Delayed#getDelay(java.util.concurrent.TimeUnit)
	 */
	public long getDelay(TimeUnit unit) {
		return unit.convert(delayTime-Instant.now().toEpochMilli(), TimeUnit.MILLISECONDS);
	}

	@Override
	public String toString() {
		return "Transaction [amount=" + amount + ", timestamp=" + timestamp + ", delayTime=" + delayTime + "]";
	}
}
