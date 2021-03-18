package com.tauro.featurespace;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;

public class Person {

    private String id;
    private String type;
    private ArrayList<Float> transactions = new ArrayList<Float>();
    private Float balance = 0f;
    private ArrayList<LocalDateTime> transactionTimes = new ArrayList<LocalDateTime>();

    public Person(String customerId, String personType) {
        id = customerId;
        type = personType;
    }

    public void addTransaction(float amount, String time) {
        transactions.add(amount);
        int timeLength = time.length();
        time = time.substring(0, timeLength - 1);
        LocalDateTime localDateTime = LocalDateTime.parse(time);
        transactionTimes.add(localDateTime);
    }

    public float averageTransactionAmount() {
        float arraySize = transactions.size();
        float totalTransactionAmount = 0;
        for (int i = 0; i < arraySize; i++) {
            totalTransactionAmount += transactions.get(i);
        }
        return totalTransactionAmount / arraySize;
    }

    public String getName() {
        return id;
    }

    public void updateBalance(Float amount, String type) {
        if (type.equals("deposit")) {
            balance += amount;
        } else {
            balance -= amount;
        }
    }

    public Float showBalance() {
        return balance;
    }

    public ArrayList<Long> getTimesBetweenFiveTransactions() {
        int j = 4;
        int i = 0;
        int n = transactionTimes.size();
        ArrayList<Long> timeDifferences = new ArrayList<Long>();
        while (j < n) {
            long diff = ChronoUnit.SECONDS.between(transactionTimes.get(i), transactionTimes.get(j));
            timeDifferences.add(diff);
            i += 1;
            j += 1;
        }
        return timeDifferences;
    }

    public Long getMinimumTime() {
        ArrayList<Long> timeDifferences = getTimesBetweenFiveTransactions();
        Long min = Collections.min(timeDifferences);
        return min;
    }

}
