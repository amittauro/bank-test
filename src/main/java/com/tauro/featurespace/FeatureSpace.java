package com.tauro.featurespace;

import org.json.JSONObject;

public class FeatureSpace {

    private JSONObject[] events;

    public FeatureSpace(JSONObject[] eventList) {
        events = eventList;
    }

    public String customersHighestAverageTransactionAmount() {
        JSONObject customer = events[0];
        String customerName = customer.getString("customerId");
        return customerName;
    }
}
