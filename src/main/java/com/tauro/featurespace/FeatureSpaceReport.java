package com.tauro.featurespace;

public class FeatureSpaceReport {

    public static void main(String args[]) {
        FeatureSpace featureSpace = new FeatureSpace("../resources/main/mockFileSix.json");
        featureSpace.parseFile();
        featureSpace.createReport();
    }
}
