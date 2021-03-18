package com.tauro.featurespace;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.json.JSONObject;

import java.lang.reflect.Array;
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
        Person merchant;
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
                    customer.addTransaction(getAmount(event));
                    customer.updateBalance(getAmount(event), "transaction");
                    merchant = getPerson(getMerchantId(event), merchantsHash, "merchant");
                    merchant.addTransaction(getAmount(event));
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

//    private void updateTransactionsAndBalance(String type, float amount, JSONObject event, HashMap<String, Person> personHash) {
//        Person merchant;
//        Person customer = getPerson(getCustomerId(event), customersHash, "customer");
//        if (type.equals("transaction")) {
//            customer.addTransaction(amount);
//            customer.updateBalance(amount, "transaction");
//            merchant = getPerson(getMerchantId(event), merchantsHash, "merchant");
//            merchant.addTransaction(amount);
//        } else {
//            customer.updateBalance(amount, "deposit");
//        }
//    }

    public void showCustomersHighestAverageTransactions() {
        customers.sortByHighestAverageTransactions();
        customers.printFirstFive("transactions");
    }

    public void showMerchantsHighestAverageTransactions() {
        merchants.sortByHighestAverageTransactions();
        merchants.printFirstFive("transactions");
    }

    public void showCustomersHighestRemainingBalance() {
        customers.sortByBalance();
        customers.printFirstFive("balance");
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
