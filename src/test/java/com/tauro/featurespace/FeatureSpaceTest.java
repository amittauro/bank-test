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
        FeatureSpace featureSpace = new FeatureSpace("src/test/resources/mockFile.json");
        featureSpace.parseFile();
        featureSpace.showCustomersHighestAverageTransactions();
        assertEquals("Cust1: 10", outputStreamCaptor.toString().trim());
    }

    @Test
    void testCustomersHighestAverageTransactionAmountInOrder() throws Exception {
        FeatureSpace featureSpace = new FeatureSpace("src/test/resources/mockFileTwo.json");
        featureSpace.parseFile();
        featureSpace.showCustomersHighestAverageTransactions();
        assertEquals("Cust1: 100\nCust2: 60", outputStreamCaptor.toString().trim());
    }

    @Test
    void testMerchantsHighestAverageTransactionAmountInOrder() throws Exception {
        FeatureSpace featureSpace = new FeatureSpace("src/test/resources/mockFileThree.json");
        featureSpace.parseFile();
        featureSpace.showMerchantsHighestAverageTransactions();
        assertEquals("Merch2: 90\nMerch1: 46", outputStreamCaptor.toString().trim());
    }

    @Test
    void testCustomersHighestRemainingBalanceInOrder() throws Exception {
        FeatureSpace featureSpace = new FeatureSpace("src/test/resources/mockFileFour.json");
        featureSpace.parseFile();
        featureSpace.showCustomersHighestRemainingBalance();
        assertEquals("Cust2: 85\nCust1: 70", outputStreamCaptor.toString().trim());
    }

    @Test
    void testMerchantsShortestTimeBetweenTransactions() throws Exception {
        FeatureSpace featureSpace = new FeatureSpace("src/test/resources/mockFileFive.json");
        featureSpace.parseFile();
        featureSpace.showMerchantsShortestTimeBetweenTransactions();
        assertEquals("Merch2: 3 days\nMerch1: 4 days", outputStreamCaptor.toString().trim());
    }

    @Test
    void featureTest() throws Exception {
        FeatureSpace featureSpace = new FeatureSpace("src/test/resources/mockFileSix.json");
        featureSpace.parseFile();
        featureSpace.createReport();
        String result1 = "Customers with highest average Transactions:\nCust5: 200\nCust4: 73\nCust3: 30\nCust6: 25\nCust2: 20\n";
        String result2 = "Merchants with highest average Transactions:\nMerch2: 80\nMerch1: 30\n";
        String result3 = "Customers with greatest remaining balance:\nCust2: 330\nCust6: 140\nCust1: 70\nCust3: 0\nCust5: -180\n";
        String result4 = "Merchants with shortest time between N and N + 4 transaction:\nMerch2: 2 days\nMerch1: 4 days";
        String result = result1 + result2 + result3 + result4;
        assertEquals(result, outputStreamCaptor.toString().trim());
    }

    @Test
    void testMerchantsShortestTimeBetweenTransactionsEdgeCase() throws Exception {
        FeatureSpace featureSpace = new FeatureSpace("src/test/resources/mockFileSeven.json");
        featureSpace.parseFile();
        featureSpace.showMerchantsShortestTimeBetweenTransactions();
        assertEquals("Merch1: 4 days", outputStreamCaptor.toString().trim());
    }

    @Test
    void testCustomersHighestAverageTransactionAmountEdgeCase() throws Exception {
        FeatureSpace featureSpace = new FeatureSpace("src/test/resources/mockFileSeven.json");
        featureSpace.parseFile();
        featureSpace.showCustomersHighestAverageTransactions();
        assertEquals("Cust1: 77\nCust5: 60\nCust6: 50\nCust2: 40\nCust4: 30", outputStreamCaptor.toString().trim());
    }



}