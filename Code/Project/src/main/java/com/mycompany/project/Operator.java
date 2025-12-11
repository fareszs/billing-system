package com.mycompany.project;

import java.util.ArrayList;
import java.util.Date;

public class Operator extends User {


    public static int getCustomerIdByMeterCode(int meterCode) {
        ArrayList<String> users = FileHandler.read("Users.txt");

        for (String line : users) {
            String[] parts = line.split(",");

            int userId = Integer.parseInt(parts[0]);
            int fileMeterCode = Integer.parseInt(parts[4]);
            if (fileMeterCode == meterCode) {
                return userId; // customerId = userId
            }
        }

        return -1; // not found
    }
    public static void inputReading(int meterCode, int newReading) {

    try {
        int customerId = getCustomerIdByMeterCode(meterCode);

        if (customerId == -1) {
            System.out.println("Error: Meter code does not belong to any customer.");
            return;
        }

        String file = "MeterReadings.txt";
        ArrayList<String> lines = FileHandler.read(file);

        int oldReading = 0;
        boolean foundPrevious = false;
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length < 7) 
                continue;

            int fileCustomerId;
            try {
                fileCustomerId = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                continue;
            }

            if (fileCustomerId == customerId) {
                try {
                    oldReading = Integer.parseInt(parts[4]); 
                    foundPrevious = true;
                } catch (NumberFormatException ignored) {}
            }
        }
        boolean isValid = validateReading(oldReading, newReading);
        int readingId = (int)(System.currentTimeMillis() % 10000000);
        String date = java.time.LocalDate.now().toString();
        String newLine =
                readingId + "," +
                customerId + "," +
                meterCode + "," +
                oldReading + "," +
                newReading + "," +
                date + "," +
                isValid;

        FileHandler.write(file, newLine);

    } catch (Exception e) {
        System.out.println("Unexpected error while writing reading: " + e.getMessage());
    }
}
    public static boolean validateReading(int prev, int current) {
        return current >= prev;
    }
    public static void printBill(int meterCode) {
        int customerId = getCustomerIdByMeterCode(meterCode);

        if (customerId == -1) {
            System.out.println("Meter code not linked to any customer.");
            return;
        }

        String file = "Bills.txt";
        ArrayList<String> bills = FileHandler.read(file);

        for (String line : bills) {
            String[] parts = line.split(",");
            int fileCustomerId = Integer.parseInt(parts[1]);

            if (fileCustomerId == customerId) {
                System.out.println(line);
                return;
            }
        }

        System.out.println("No bill found for this customer.");
    }
    public static void defineTariff(String region, double price) {
        String file = "Tariffs.txt";
        FileHandler.write(file, region + "," + price);
    }
    public static ArrayList<String> viewBillsByRegion(String region) {
        ArrayList<String> users = FileHandler.read("Users.txt");
        ArrayList<Integer> regionUserIds = new ArrayList<>();
        ArrayList<String> resultBills = new ArrayList<>();
    
        for (String line : users) {
            String[] parts = line.split(",");
            int userId = Integer.parseInt(parts[0]);
            String userRegion = parts[5];
    
            if (userRegion.equalsIgnoreCase(region)) {
                regionUserIds.add(userId);
            }
        }
    
        if (regionUserIds.isEmpty()) {
            return resultBills;
        }
    
        ArrayList<String> bills = FileHandler.read("Bills.txt");
    
        for (String bill : bills) {
            String[] parts = bill.split(",");
            int customerId = Integer.parseInt(parts[1]);
    
            if (regionUserIds.contains(customerId)) {
                resultBills.add(bill);
            }
        }
    
        return resultBills;
    }
    
}
