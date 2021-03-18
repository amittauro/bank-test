package com.tauro.featurespace;

import java.util.ArrayList;

public class Person {

        private String id;
        private String type;
        private ArrayList<Float> transactions = new ArrayList<Float>();
        private Float balance = 0f;

        public Person(String customerId, String personType) {
            id = customerId;
            type = personType;
        }

        public void addTransaction(float amount) {
            transactions.add(amount);
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


}
