package com.tauro.featurespace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class People {
    private ArrayList<Person> people;
    private String type;

    public People(ArrayList<Person> peopleArray, String peopleType) {
        people = peopleArray;
        type = peopleType;
    }

    public ArrayList<Person> sortByHighestAverageTransactions() {
        PersonTransactionComparator transactionComparator = new PersonTransactionComparator();
        Collections.sort(people, transactionComparator);
        return people;
    }

    public ArrayList<Person> sortByBalance() {
        PersonBalanceComparator balanceComparator = new PersonBalanceComparator();
        Collections.sort(people, balanceComparator);
        return people;
    }

    public void printFirstFive(String type) {
        int peopleSize = people.size() < 5 ? people.size() : 5;
        for (int i = 0; i < peopleSize; i++) {
            if (type.equals("transactions")) {
                System.out.println(people.get(i).getName() + ": " + Math.round(people.get(i).averageTransactionAmount()));
            } else {
                System.out.println(people.get(i).getName() + ": " + Math.round(people.get(i).showBalance()));
            }
        }
    }

    public class PersonTransactionComparator implements Comparator<Person> {

        @Override
        public int compare(Person firstPerson, Person secondPerson) {
            return Float.compare(secondPerson.averageTransactionAmount(), firstPerson.averageTransactionAmount());
        }

    }

    public class PersonBalanceComparator implements Comparator<Person> {

        @Override
        public int compare(Person firstPerson, Person secondPerson) {
            return Float.compare(secondPerson.showBalance(), firstPerson.showBalance());
        }

    }

}
