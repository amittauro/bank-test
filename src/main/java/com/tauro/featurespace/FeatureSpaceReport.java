package com.tauro.featurespace;

public class FeatureSpaceReport {

    public static void main(String args[]) {
        FeatureSpace featureSpace = new FeatureSpace("src/main/resources/exampleInput.json");
        featureSpace.parseFile();
        featureSpace.createReport();
    }
}
