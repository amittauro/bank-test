package com.tauro.featurespace;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.*;


public class FeatureSpace {

    private String file;
    private HashMap<String, Customer> customersHash = new HashMap<String, Customer>();

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
            String line = "reading file line by line";
            while (line != null) {
                line = reader.readLine();
                if (line == null) { break; }
                JSONObject event = new JSONObject(line);
                addTransactionForCustomer(getCustomerId(event), getAmount(event));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<Customer> sortedCustomers = sortCustomersHighestAverageTransactions();
        printFirstFive(sortedCustomers);
    }

    private String getCustomerId(JSONObject event) {
        return event.getString("customerId");
    }

    private float getAmount(JSONObject event) {
        return event.getFloat("amount");
    }

    private void printFirstFive(ArrayList<Customer> customers) {
        int customersSize = customers.size() < 5 ? customers.size() : 5;
        for (int i = 0; i < customersSize; i++) {
            System.out.println(customers.get(i).id);
        }
    }

    private void addTransactionForCustomer(String customerId, float amount) {
        Customer customer = customersHash.get(customerId);
        if (customer == null) {
            customer = new Customer(customerId);
            customersHash.put(customerId, customer);
            customer.addTransaction(amount);
        } else {
            customer.addTransaction(amount);
        }
    }

    private ArrayList<Customer> sortCustomersHighestAverageTransactions() {
        ArrayList<Customer> customers = createCustomersArray();
        CustomerTransactionComparator customerComparator = new CustomerTransactionComparator();
        Collections.sort(customers, customerComparator);
        return customers;
    }

    private ArrayList<Customer> createCustomersArray() {
        ArrayList<Customer> customers = new ArrayList<Customer>();
        for (Customer i : customersHash.values()) {
            customers.add(i);
        }
        return customers;
    }

    public class CustomerTransactionComparator implements Comparator<Customer> {

        @Override
        public int compare(Customer firstCustomer, Customer secondCustomer) {
            return Float.compare(secondCustomer.averageTransactionAmount(), firstCustomer.averageTransactionAmount());
        }

    }
 }
