package com.mycompany.project;
import java.util.*;
public class Admin extends Operator {

    public Admin() {
        
    }

    public static ArrayList<String> viewBillsForRegion(String region) {
        return Operator.viewBillsByRegion(region);
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
    
    public static void viewRegionConsumptionStatistics(String region) {
    ArrayList<String> users = FileHandler.read("Users.txt");
    ArrayList<Integer> regionUserIds = new ArrayList<>();

    for (String line : users) {
        String[] p = line.split(",");
        int userId = Integer.parseInt(p[0]);
        String userRegion = p[5];

        if (userRegion.equalsIgnoreCase(region)) {
            regionUserIds.add(userId);
        }
    }

    if (regionUserIds.isEmpty()) {
        System.out.println("No users found in this region.");
        return;
    }

    ArrayList<String> readings = FileHandler.read("Files\\MeterReadings.txt");

    double totalConsumption = 0;
    int counted = 0;

    for (int userId : regionUserIds) {
        int latest = -1;

        for (String r : readings) {
            String[] p = r.split(",");
            int cid = Integer.parseInt(p[1]);
            int newValue = Integer.parseInt(p[3]);

            if (cid == userId) {
                latest = newValue;
            }
        }

        if (latest == -1) {
            System.out.println("User " + userId + " has no readings.");
            continue;
        }
        
        totalConsumption += latest;
        counted++;
    }

    if (counted > 0) {
        System.out.println("Consumption statistics for region: " + region);
        System.out.println("Total region consumption: " + totalConsumption);
        System.out.println("Total People: " + counted);
        System.out.println("Average consumption: " + (totalConsumption / counted));
    }
}



}

