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
        featureSpace.customersHighestAverageTransactionAmount();
        assertEquals("Cust1", outputStreamCaptor.toString().trim());
    }

    @Test
    void testCustomersHighestAverageTransactionAmountInOrder() throws Exception {
        FeatureSpace featureSpace = new FeatureSpace("src/main/resources/mockFileTwo.json");
        featureSpace.customersHighestAverageTransactionAmount();
        assertEquals("Cust1\nCust2", outputStreamCaptor.toString().trim());
    }

    @Test
    void testMerchantsHighestAverageTransactionAmountInOrder() throws Exception {
        FeatureSpace featureSpace = new FeatureSpace("src/main/resources/mockFileThree.json");
        featureSpace.merchantsHighestAverageTransactionAmount();
        assertEquals("Merch1\nMerch1", outputStreamCaptor.toString().trim());
    }



}