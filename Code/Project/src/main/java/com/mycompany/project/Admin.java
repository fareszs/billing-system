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

    public static void addUser(int id, String username, String password, String role, String name, String region) {
        String line = id + "," + username + "," + password + "," + role + "," + name + "," + region;
        FileHandler.write("Files\\Users.txt", line);
    }

    public static void deleteUser(int id) {
        FileHandler.deleteLineById("Files\\Users.txt", id);
    }

    public static void updateUser(int id, String username, String password, String role, String name, String region) {
        String newLine = id + "," + username + "," + password + "," + role + "," + name + "," + region;
        FileHandler.updateLineById("Files\\Users.txt", id, newLine);
    }

}

