package com.mycompany.project;

import java.util.ArrayList;

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
    
}
