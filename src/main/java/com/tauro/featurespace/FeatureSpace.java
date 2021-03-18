package com.tauro.featurespace;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.*;


public class FeatureSpace {

    private String file;
    private HashMap<String, Person> customersHash = new HashMap<String, Person>();

    public FeatureSpace(String uploadedFile) {

        file = uploadedFile;
    }

    static class Person {

        private String id;
        private String type;
        private ArrayList<Float> transactions = new ArrayList<Float>();

        public Person(String customerId, String personType) {
            id = customerId;
            type = personType;
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
                addPersonTransaction(getPersonId(event), getAmount(event));
//                addMerchantTransaction(getMerchantId(event), getAmount(event));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<Person> sortedPersons = sortCustomersHighestAverageTransactions();
        printFirstFive(sortedPersons);
    }

    private String getPersonId(JSONObject event) {
        return event.getString("customerId");
    }

    private float getAmount(JSONObject event) {
        return event.getFloat("amount");
    }

    private void printFirstFive(ArrayList<Person> customers) {
        int customersSize = customers.size() < 5 ? customers.size() : 5;
        for (int i = 0; i < customersSize; i++) {
            System.out.println(customers.get(i).id);
        }
    }

    private void addPersonTransaction(String customerId, float amount) {
        Person customer = customersHash.get(customerId);
        if (customer == null) {
            customer = new Person(customerId, "customer");
            customersHash.put(customerId, customer);
            customer.addTransaction(amount);
        } else {
            customer.addTransaction(amount);
        }
    }

    private ArrayList<Person> sortCustomersHighestAverageTransactions() {
        ArrayList<Person> customers = createCustomersArray();
        PersonTransactionComparator personComparator = new PersonTransactionComparator();
        Collections.sort(customers, personComparator);
        return customers;
    }

    private ArrayList<Person> createCustomersArray() {
        ArrayList<Person> customers = new ArrayList<Person>();
        for (Person i : customersHash.values()) {
            customers.add(i);
        }
        return customers;
    }

    public class PersonTransactionComparator implements Comparator<Person> {

        @Override
        public int compare(Person firstPerson, Person secondPerson) {
            return Float.compare(secondPerson.averageTransactionAmount(), firstPerson.averageTransactionAmount());
        }

    }
 }
