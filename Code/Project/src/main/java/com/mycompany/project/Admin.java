package com.mycompany.project;

public class Admin extends Operator {

    public Admin() {
    }

    public static void viewBillsForRegion(String region) {
        // Call the Operator method and return same output
        Operator.viewBillsByRegion(region);
    }

    public static double getTotalCollected() {
        double total = 0.0;
    
        for (String line : FileHandler.read("Files\\Bills.txt")) {
            String[] parts = line.split(",");
    
            boolean isPaid = Boolean.parseBoolean(parts[4]);
            if (isPaid) {
                double amount = Double.parseDouble(parts[2]);
                total += amount;
            }
        }
    
        return total;
    }

}

