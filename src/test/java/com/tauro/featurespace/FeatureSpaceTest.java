package com.tauro.featurespace;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.json.simple.JSONObject;
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


    @Test
    void testCustomersHighestAverageTransactionAmount() throws Exception {
        JSONObject transaction = new JSONObject();
        transaction.put("eventType", "transaction");
        transaction.put("customerId", "Cust123");
        transaction.put("amount", 80);
        JSONObject[] transactions = {transaction};
        FeatureSpace featureSpace = new FeatureSpace("src/main/resources/mockFile.json");
        featureSpace.customersHighestAverageTransactionAmount();
        assertEquals("Cust1", outputStreamCaptor.toString().trim());
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}