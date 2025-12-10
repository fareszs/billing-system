package com.mycompany.project;

import java.util.ArrayList;
import java.util.Date;

public class Operator extends User {

    public Operator() {
    }

    private static int getCustomerIdByMeterCode(int meterCode) {
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

    public static void inputReading(int meterCode, int value) {
        int customerId = getCustomerIdByMeterCode(meterCode);

        if (customerId == -1) {
            System.out.println("Meter code does not belong to any customer.");
            return;
        }

        String file = "MeterReadings.txt";
        ArrayList<String> lines = FileHandler.read(file);

        int prevValue = 0;
        for (String line : lines) {
            String[] parts = line.split(",");
            int fileCustomerId = Integer.parseInt(parts[1]);

            if (fileCustomerId == customerId) {
                prevValue = Integer.parseInt(parts[3]);
            }
        }

        boolean isValidated = validateReading(prevValue, value);

        // Unique reading ID
        int readingId = (int)(System.currentTimeMillis() % 10000000);
        String date = java.time.LocalDate.now().toString();

        String newLine =
                readingId + "," +
                customerId + "," +
                date + "," +
                value + "," +
                isValidated;

        FileHandler.write(file, newLine);
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
    public void payBill(String meterCode , double newReading , double oldReading){
        int billId = (int) (Math.random() * 10000);
        Date billDate = new Date();
        double billValue = (newReading - oldReading) * Tariff.getPriceByRegion(region);
        Bill bill = new Bill(billId, super.getId(), billValue, billDate.toString(), true);
    }

    
}
