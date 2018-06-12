# Statistics-builder
Coding Challenge by N26
API to record and retrieve real-time statistics abot transactions in a given timeframe.

# Description
  1. To maintain a 60 second update window for the statistics, I am using DelayQueue to store transaction objects with a ticking delay to mark them as expired. Transaction object has implemented Delayed interface where in it returns the amount of time it has before expiry.
  2. A scheduler task is run every second to clear old transactions and update the statistics.

# How to run

Install Maven locally
Run: mvn spring-boot:run
Alternatively, you can use the included Maven wrapper and run ./mvnw spring-boot:run instead.

# Adding transactions

Run the following in your terminal:

curl -d '{"amount":1, "timestamp":1478192204000}' -H "Content-Type: application/json" -X POST -i localhost:8080/transactions
Make sure you change the timestamp number to the current Unix epoch timestamp.

# Getting statistics

The following will give you statistics about all transactions in the last 60 seconds:

curl -i localhost:8080/statistics
