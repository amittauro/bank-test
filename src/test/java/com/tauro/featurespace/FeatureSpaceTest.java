package com.tauro.featurespace;

import org.junit.jupiter.api.Test;
import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.*;

class FeatureSpaceTest {
    @Test
    void testHighestAverageTransactionAmount() throws Exception {
        JSONObject transaction = new JSONObject();
        transaction.put("eventType", "transaction");
        transaction.put("customerId", "Cust123");
        transaction.put("amount", 80);
        JSONObject[] transactions = {transaction};
        FeatureSpace featureSpace = new FeatureSpace(transactions);
        assertEquals("Cust123", featureSpace.highestAverageTransactionAmount());



    }
}