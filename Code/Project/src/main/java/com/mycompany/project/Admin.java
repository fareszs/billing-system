package com.mycompany.project;
import java.util.*;
public class Admin {
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

    public static void addUser(int id, String username, String password, String role, String name, String region,String meterCode) {
        String line = id + "," + username + "," + password + "," + role + "," + name + "," + region+","+meterCode;
        FileHandler.write("Files\\Users.txt", line);
    }

    public static void deleteUser(int id) {
        FileHandler.deleteLineById("Files\\Users.txt", id);
    }

    public static void updateUser(int id, String username, String password, String role, String name, String region,String meterCode) {
        String newLine = id + "," + username + "," + password + "," + role + "," + name + "," + region+","+meterCode;
        FileHandler.updateLineById("Files\\Users.txt", id, newLine);
    }
    
    public static String viewRegionConsumptionStatistics(String region) {
    ArrayList<String> users = FileHandler.read("Files\\Users.txt");
    ArrayList<Integer> regionUserIds = new ArrayList<>();
    StringBuilder sb= new StringBuilder();

    for (String line : users) {
        String[] p = line.split(",");
        int userId = Integer.parseInt(p[0]);
        String userRegion = p[5];

        if (userRegion.equalsIgnoreCase(region)) {
            regionUserIds.add(userId);
        }
    }

    if (regionUserIds.isEmpty()) {
       
        return "No users found in this region.";
    }

    ArrayList<String> readings = FileHandler.read("Files\\MeterReadings.txt");

    double totalConsumption = 0;
    int counted = 0;

    for (int userId : regionUserIds) {
        int latest;

        for (String r : readings) {
            String[] p = r.split(",");
            int cid = Integer.parseInt(p[1]);
            int newValue = Integer.parseInt(p[4]);
            int oldValue = Integer.parseInt(p[3]);
            boolean valid= Boolean.parseBoolean(p[6]);
            if ((cid == userId)&&valid) {
                latest = newValue-oldValue;
                totalConsumption += latest;
                counted++;
            }
        }

      
        
       
    }

    if (counted > 0) {
        sb.append("Consumption statistics for region: ").append(region).append("\n");
        sb.append("Total region consumption: ").append(totalConsumption).append("\n");
        sb.append("Total People: ").append(counted).append("\n");
        sb.append("Average consumption: " ).append((totalConsumption / counted)).append("\n");
        return sb.toString();
    }
    return "No readings found for this region.";
}



}

