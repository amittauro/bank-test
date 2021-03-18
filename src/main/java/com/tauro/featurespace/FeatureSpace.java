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
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    file));
            String line = "reading file line by line";
            while (line != null) {
                line = reader.readLine();
                if (line == null) { break; }
                JSONObject event = new JSONObject(line);
                if (getEventType(event).equals("transaction")) {
                    addTransaction(getMerchantId(event), getAmount(event), merchantsHash, "merchant");
                }
                customer = addTransaction(getCustomerId(event), getAmount(event),
                        customersHash, "customer");
                customer.updateBalance(getAmount(event), getEventType(event));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        customers = new People(createPeopleArray(customersHash), "customers");
        merchants = new People(createPeopleArray(merchantsHash), "merchants");
    }

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

    private Person addTransaction(String personId, float amount,
                                        HashMap<String, Person> personHash, String personType) {
        Person person = personHash.get(personId);
        if (person == null) {
            person = new Person(personId, personType);
            personHash.put(personId, person);
            person.addTransaction(amount);
        } else {
            person.addTransaction(amount);
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
