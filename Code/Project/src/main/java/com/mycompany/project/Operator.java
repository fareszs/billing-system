package com.mycompany.project;

import java.util.ArrayList;
import java.util.Date;

public class Operator extends User {


public static int getCustomerIdByMeterCode(int meterCode) {
    // 1. Check if the file is even being read
    ArrayList<String> users = FileHandler.read("Files\\Users.txt");
    if (users.isEmpty()) {
        System.out.println("CRITICAL ERROR: 'Files\\Users.txt' was not found or is empty.");
        return -1;
    }

    for (String line : users) {
        String[] parts = line.split(",");

        // 2. Skip short lines
        if (parts.length < 7) {
            continue; 
        }

        // 3. Read the SPECIFIC column (Index 6)
        String codeInFile = parts[6].trim(); // .trim() removes invisible spaces

        // 4. Skip "Null" values (like Admin/Operator rows)
        if (codeInFile.equalsIgnoreCase("Null")) {
            continue;
        }

        try {
            int fileMeterCode = Integer.parseInt(codeInFile);

            // 5. Check for match
            if (fileMeterCode == meterCode) {
                System.out.println("SUCCESS: Found match for " + meterCode);
                return Integer.parseInt(parts[0].trim()); // Return User ID
            }
        } catch (NumberFormatException e) {
            System.out.println("WARNING: Could not parse meter code in file: '" + codeInFile + "'");
            continue;
        }
    }

    System.out.println("FAILURE: Searched all lines but did not find meter code: " + meterCode);
    return -1; // Not found
}
public static String getRegionByMeterCode(int meterCode) {

    ArrayList<String> users = FileHandler.read("Files\\Users.txt");

    for (String line : users) {
        String[] p = line.split(",");
        if (p.length != 7) continue;

        int fileMeterCode = Integer.parseInt(p[6]);

        if (fileMeterCode == meterCode) {
            return p[5]; // Region
        }
    }
    return null;
}
 public static void inputReading(int meterCode, int newReading) {

    int customerId = getCustomerIdByMeterCode(meterCode);
    if (customerId == -1) {
        System.out.println("Invalid meter code.");
        return;
    }

    String file = "Files\\MeterReadings.txt";
    ArrayList<String> lines = FileHandler.read(file);

    int oldReading = 0;

    for (String line : lines) {
        String[] p = line.split(",");
        if (p.length != 7) continue;

        int fileMeter = Integer.parseInt(p[2]);
        boolean valid = Boolean.parseBoolean(p[6]);

        if (fileMeter == meterCode && valid) {
            oldReading = Integer.parseInt(p[4]);
        }
    }

    boolean isValid = newReading >= oldReading;

    int readingId = (int) (System.currentTimeMillis() % 1_000_000);
    String date = java.time.LocalDate.now().toString();

    // 1️⃣ Save reading (TRUE or FALSE)
    String newLine =
            readingId + "," +
            customerId + "," +
            meterCode + "," +
            oldReading + "," +
            newReading + "," +
            date + "," +
            isValid;

    FileHandler.write(file, newLine);

    // ================== BILL CREATION (ALWAYS) ==================

    String region = getRegionByMeterCode(meterCode);
    if (region == null) {
        System.out.println("Region not found. Bill not created.");
        return;
    }

    double price = Tariff.getPriceByRegion(region);

    double billValue;
    if (isValid) {
        billValue = (newReading - oldReading) * price;
    } else {
        billValue = 0.0;   // 🔴 INVALID READING → ZERO BILL
    }

    int billId = (int) (Math.random() * 100000);
    boolean isPaid = false;

    String billLine =
            billId + "," +
            customerId + "," +
            billValue + "," +
            date + "," +
            isPaid;

    FileHandler.write("Files\\Bills.txt", billLine);

 

    System.out.println("Reading saved. Status = " + isValid);
    System.out.println("Bill created. Amount = " + billValue);
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

        String file = "Files\\Bills.txt";
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
        String file = "Files\\Tariffs.txt";
        FileHandler.write(file, region + "," + price);
    }
    public static ArrayList<String> viewBillsByRegion(String region) {
        ArrayList<String> users = FileHandler.read("Files\\Users.txt");
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
    
        ArrayList<String> bills = FileHandler.read("Files\\Bills.txt");
    
        for (String bill : bills) {
            String[] parts = bill.split(",");
            int customerId = Integer.parseInt(parts[1]);
    
            if (regionUserIds.contains(customerId)) {
                resultBills.add(bill);
            }
        }
    
        return resultBills;
    }
    
    public static void main(String[] args) {
    inputReading(1001,3500);
}
}
