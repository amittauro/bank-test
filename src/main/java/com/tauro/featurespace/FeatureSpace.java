package com.tauro.featurespace;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Comparator;
import java.util.Collections;



public class FeatureSpace {

    private String file;
    private ArrayList<Customer> customers = new ArrayList<Customer>();

    public FeatureSpace(String uploadedFile) {

        file = uploadedFile;
    }

    static class Customer {

        private String id;
        private ArrayList<Float> transactions = new ArrayList<Float>();

        public Customer(String customerId) {
            id = customerId;
        }

        private void addTransaction(float amount) {
            transactions.add(amount);
        }

        public float averageTransactionAmount() {
            float arraySize = transactions.size();
            float totalTransactionAmount = 0;
            for (int i = 0; i < arraySize; i++) {
                totalTransactionAmount += transactions.get(0);
            }
            return totalTransactionAmount / arraySize;
        }

    }

    public void customersHighestAverageTransactionAmount() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    file));
            String line = reader.readLine();
            JSONObject event = new JSONObject(line);
            String customerId = event.getString("customerId");
            String transactionId = event.getString("transactionId");
            float amount = event.getFloat("amount");
            addTransactionForCustomer(customerId, amount);
//            System.out.print(customer);
            while (line != null) {
//                System.out.println(line);
                // read next line
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                event = new JSONObject(line);
                customerId = event.getString("customerId");
                transactionId = event.getString("transactionId");
                amount = event.getFloat("amount");
                addTransactionForCustomer(customerId, amount);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        highestAverageTransactions();
    }

    private boolean customerExists(String customerId) {
        int arraySize = customers.size();
        boolean result = false;
        for (int i = 0; i < arraySize; i++) {
            if (customers.get(i).id.equals(customerId)) {
                result =  true;
                break;
            }
        }
        return result;
    }

    private Customer findCustomer(String customerId) {
        int arraySize = customers.size();
        int index = 0;
        for (int i = 0; i < arraySize; i++) {
            if (customers.get(i).id == customerId) {
                return customers.get(i);
            }
        }
        return new Customer("wrongId");
    }

    private void addTransactionForCustomer(String customerId, float amount) {
        Customer customer;
        if (customerExists(customerId) == true) {
            customer = findCustomer(customerId);
            customer.addTransaction(amount);
        } else {
            customer = new Customer(customerId);
            customer.addTransaction(amount);
            customers.add(customer);
        }
    }

    private void highestAverageTransactions() {
        CustomerTransactionComparator customerComparator = new CustomerTransactionComparator();
        Collections.sort(customers, customerComparator);
        Collections.reverse(customers);
        int customersSize;
        if (customers.size() < 5) {
            customersSize = customers.size();
        } else {
            customersSize = 5;
        }
        for (int i = 0; i < customersSize; i++) {
            String customerId;
            customerId = customers.get(i).id;
            System.out.println(customerId);
        }

    }

    public class CustomerTransactionComparator implements Comparator<Customer> {

        @Override
        public int compare(Customer firstCustomer, Customer secondCustomer) {
            return Float.compare(firstCustomer.averageTransactionAmount(), secondCustomer.averageTransactionAmount());
        }

    }
 }
