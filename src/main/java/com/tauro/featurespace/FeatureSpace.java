package com.tauro.featurespace;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.json.JSONObject;

import java.util.*;


public class FeatureSpace {

    private String file;
    private People customers;
    private People merchants;

    public FeatureSpace(String uploadedFile) {

        file = uploadedFile;
    }

    public void parseFile() {
        HashMap<String, Person> customersHash = new HashMap<String, Person>();
        HashMap<String, Person> merchantsHash = new HashMap<String, Person>();
        Person customer;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    file));
            String line = "reading file line by line";
            while (line != null) {
                line = reader.readLine();
                if (line == null) { break; }
                JSONObject event = new JSONObject(line);
                customer = getPerson(getCustomerId(event), customersHash, "customer");
                if (getEventType(event).equals("transaction")) {
                    updateCustomerTransactionsAndBalance(customer, getAmount(event), getTransactionTime(event));
                    updateMerchantTransactions(getMerchantId(event), merchantsHash,
                            getAmount(event), getTransactionTime(event));
                } else {
                    customer.updateBalance(getAmount(event), "deposit");
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        customers = new People(createPeopleArray(customersHash), "customers");
        merchants = new People(createPeopleArray(merchantsHash), "merchants");
    }

    private void updateCustomerTransactionsAndBalance(Person customer, float amount, String time) {
        customer.addTransaction(amount, time);
        customer.updateBalance(amount, "transaction");
    }

    private void updateMerchantTransactions(String merchantId, HashMap<String, Person> merchantsHash,
                                            float amount, String time) {
        Person merchant = getPerson(merchantId, merchantsHash, "merchant");
        merchant.addTransaction(amount, time);
    }

    public void showMerchantsShortestTimeBetweenTransactions() {
        merchants.sortByTimeDiff();
        merchants.printFirstFiveTimeDiff();
    }

    public void showCustomersHighestAverageTransactions() {
        customers.sortByHighestAverageTransactions();
        customers.printFirstFiveTransactions();
    }

    public void showMerchantsHighestAverageTransactions() {
        merchants.sortByHighestAverageTransactions();
        merchants.printFirstFiveTransactions();
    }

    public void showCustomersHighestRemainingBalance() {
        customers.sortByBalance();
        customers.printFirstFiveBalances();
    }

    private String getTransactionTime(JSONObject event) {
        return event.getString("time");
    }

    private String getCustomerId(JSONObject event) {
        return event.getString("customerId");
    }

    private String getEventType(JSONObject event) {
        return event.getString("eventType");
    }

    private String getMerchantId(JSONObject event) {
        return event.getString("merchantId");
    }

    private float getAmount(JSONObject event) {
        return event.getFloat("amount");
    }

    private Person getPerson(String personId, HashMap<String, Person> personHash, String personType) {
        Person person = personHash.get(personId);
        if (person == null) {
            person = new Person(personId, personType);
            personHash.put(personId, person);
        }
        return person;
    }

    private ArrayList<Person> createPeopleArray(HashMap<String, Person> personHash) {
        ArrayList<Person> persons = new ArrayList<Person>();
        for (Person i : personHash.values()) {
            persons.add(i);
        }
        return persons;
    }

 }
