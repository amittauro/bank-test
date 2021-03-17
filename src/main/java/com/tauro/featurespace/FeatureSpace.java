package com.tauro.featurespace;

//import org.json.simple.JSONObject;
import java.io.File;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONString;



public class FeatureSpace {

    private String file;

    public FeatureSpace(String uploadedFile) {

        file = uploadedFile;
    }

    public void customersHighestAverageTransactionAmount() {
//        JSONObject customer = events[0];
//        Object customerName = customer.get("customerId");
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    file));
            String line = reader.readLine();
            JSONObject obj = new JSONObject(line);
            String pageName = obj.getString("customerId");
            System.out.print(pageName);
            while (line != null) {
//                System.out.println(line);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
