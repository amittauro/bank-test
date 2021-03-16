package com.tauro.featurespace;

import org.json.JSONObject;

public class FeatureSpace {

    private JSONObject[] events;

    public FeatureSpace(JSONObject[] eventList) {
        events = eventList;
    }

    public String highestAverageTransactionAmount() {
        return "hello";
    }
}
