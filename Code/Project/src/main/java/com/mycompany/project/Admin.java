package com.mycompany.project;

public class Admin extends Operator {

    public Admin() {
    }

    public static void viewBillsForRegion(String region) {
        // Call the Operator method and return same output
        Operator.viewBillsByRegion(region);
    }
}

