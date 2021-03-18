package com.tauro.featurespace;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class FeatureSpaceTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void testCustomersHighestAverageTransactionAmount() throws Exception {
        FeatureSpace featureSpace = new FeatureSpace("src/main/resources/mockFile.json");
        featureSpace.parseFile();
        featureSpace.showCustomersHighestAverageTransactions();
        assertEquals("Cust1: 10", outputStreamCaptor.toString().trim());
    }

    @Test
    void testCustomersHighestAverageTransactionAmountInOrder() throws Exception {
        FeatureSpace featureSpace = new FeatureSpace("src/main/resources/mockFileTwo.json");
        featureSpace.parseFile();
        featureSpace.showCustomersHighestAverageTransactions();
        assertEquals("Cust1: 100\nCust2: 60", outputStreamCaptor.toString().trim());
    }

    @Test
    void testMerchantsHighestAverageTransactionAmountInOrder() throws Exception {
        FeatureSpace featureSpace = new FeatureSpace("src/main/resources/mockFileThree.json");
        featureSpace.parseFile();
        featureSpace.showMerchantsHighestAverageTransactions();
        assertEquals("Merch2: 90\nMerch1: 46", outputStreamCaptor.toString().trim());
    }

    @Test
    void testCustomersHighestRemainingBalanceInOrder() throws Exception {
        FeatureSpace featureSpace = new FeatureSpace("src/main/resources/mockFileFour.json");
        featureSpace.parseFile();
        featureSpace.showCustomersHighestRemainingBalance();
        assertEquals("Cust2: 85\nCust1: 70", outputStreamCaptor.toString().trim());
    }



}