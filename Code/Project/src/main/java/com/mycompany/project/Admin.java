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
        ArrayList<Integer> regionUserIds = Operator.getUserIdsByRegion(region);
    
        if (regionUserIds.isEmpty()) {
            System.out.println("No users found in this region.");
            return;
        }
    
        // Load readings once
        ArrayList<String> readings = FileHandler.read("Files\\MeterReadings.txt");
    
        double totalConsumption = 0;
        int counted = 0;
    
        System.out.println("Consumption statistics for region: " + region);
        System.out.println("--------------------------------------");
    
        // Compute consumption per customer
        for (int userId : regionUserIds) {
    
            ArrayList<Integer> values = new ArrayList<>();
    
            for (String r : readings) {
                String[] p = r.split(",");
                int cid = Integer.parseInt(p[1]);
    
                if (cid == userId) {
                    values.add(Integer.parseInt(p[3]));
                }
            }
    
            if (values.size() < 2) {
                System.out.println("User " + userId + " does not have enough readings.");
                continue;
            }
    
            Collections.sort(values);
            int prev = values.get(values.size() - 2);
            int last = values.get(values.size() - 1);
    
            int consumption = last - prev;
    
            System.out.println("User " + userId + " consumption: " + consumption);
    
            totalConsumption += consumption;
            counted++;
        }
    
        if (counted > 0) {
            System.out.println("--------------------------------------");
            System.out.println("Total region consumption: " + totalConsumption);
            System.out.println("Average consumption: " + (totalConsumption / counted));
        }

        // Shape of output related to that method
        // Consumption statistics for region: Cairo
        //--------------------------------------
        //User 2 total consumption: 150
        //User 4 total consumption: 50
        //--------------------------------------
        //Total region consumption: 200
        //Average consumption: 100.0

    }


}

