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
        ArrayList<Person> peopleWithTransactions = new ArrayList<Person>();
        for (Person person : people) {
            if (person.madeTransaction()) {
                peopleWithTransactions.add(person);
            }
        }
        PersonTransactionComparator transactionComparator = new PersonTransactionComparator();
        Collections.sort(peopleWithTransactions, transactionComparator);
        return peopleWithTransactions;
    }

    public ArrayList<Person> sortByBalance() {
        PersonBalanceComparator balanceComparator = new PersonBalanceComparator();
        Collections.sort(people, balanceComparator);
        return people;
    }

    public ArrayList<Person> sortByTimeDiff() {
        ArrayList<Person> peopleWithTimeDiff = new ArrayList<Person>();
        for (Person person : people) {
            if (person.minimumTimeExists()) {
                peopleWithTimeDiff.add(person);
            }
        }
        PersonTimeComparator timeComparator = new PersonTimeComparator();
        Collections.sort(peopleWithTimeDiff, timeComparator);
        return peopleWithTimeDiff;
    }

    public void printFirstFiveTransactions() {
        ArrayList<Person> peopleWithTransactions = sortByHighestAverageTransactions();
        int peopleSize = peopleWithTransactions.size() < 5 ? peopleWithTransactions.size() : 5;
        for (int i = 0; i < peopleSize; i++) {
            System.out.println(peopleWithTransactions.get(i).getName() + ": " + Math.round(peopleWithTransactions.get(i).averageTransactionAmount()));
        }
    }

    public void printFirstFiveBalances() {
        int peopleSize = people.size() < 5 ? people.size() : 5;
        for (int i = 0; i < peopleSize; i++) {
            System.out.println(people.get(i).getName() + ": " + Math.round(people.get(i).showBalance()));
        }
    }

    public void printFirstFiveTimeDiff() {
        ArrayList<Person> peopleWithTimeDiff = sortByTimeDiff();
        int peopleSize = peopleWithTimeDiff.size() < 5 ? peopleWithTimeDiff.size() : 5;
        for (int i = 0; i < peopleSize; i++) {
            System.out.println(peopleWithTimeDiff.get(i).getName());
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

    public class PersonTimeComparator implements Comparator<Person> {

        @Override
        public int compare(Person firstPerson, Person secondPerson) {
            return Float.compare(firstPerson.getMinimumTime(), secondPerson.getMinimumTime());
        }

    }

}
