package com.mycompany.project;
import java.util.*;

public class Bill {
    public static double calculateBill(int consumption, double tariffPrice) {
        return consumption * tariffPrice;
    }
    public static void markAsPaid(int billId) {
    try {
        String file = "Files\\Bills.txt";
        ArrayList<String> lines = FileHandler.read(file);

        boolean found = false;

        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length < 5)
                continue;

            int fileBillId;

            try {
                fileBillId = Integer.parseInt(parts[0]);
            } catch (NumberFormatException e) {
                continue; 
            }

            if (fileBillId == billId) {
                found = true;

                int customerId = Integer.parseInt(parts[1]);
                double amount = Double.parseDouble(parts[2]);
                String date = parts[3];
                boolean isPaid = true; 

                String newData = fileBillId + "," +
                                 customerId + "," +
                                 amount + "," +
                                 date + "," +
                                 isPaid;
                FileHandler.updateLineById(file, billId, newData);

                System.out.println("Bill " + billId + " has been marked as paid.");
                return;
            }
        }
        if (!found) {
            System.out.println("No bill exists with this ID.");
        }

    } catch (Exception e) {
        System.out.println("Error marking bill as paid: ");
    }
}
    public static int AssignBill(int meterCode) {
    try {
        int customerId = Operator.getCustomerIdByMeterCode(meterCode);

        if (customerId == -1) {
            System.out.println(" Error: Meter code does not belong to any customer.");
            return 0;
        }
        String usersFile = "Files\\Users.txt";
        ArrayList<String> users = FileHandler.read(usersFile);

        String region = null;

        for (String line : users) {
            String[] parts = line.split(",");
            if (parts.length < 7)
                continue;

            int fileUserId = Integer.parseInt(parts[0]);

            if (fileUserId == customerId) {
                region = parts[5];
                break;
            }
        }

        if (region == null) {
            System.out.println("Error: Could not find user region.");
            return 0;
        }
        String readingsFile = "Files\\MeterReadings.txt";
        ArrayList<String> readings = FileHandler.read(readingsFile);

        int oldReading = -1;
        int newReading = -1;

        for (String line : readings) {
            String[] parts = line.split(",");
            if (parts.length < 7)
                continue;

            int fileCustomerId = Integer.parseInt(parts[1]);

            if (fileCustomerId == customerId) {
                oldReading = Integer.parseInt(parts[3]);
                newReading = Integer.parseInt(parts[4]);
            }
        }

        if (oldReading == -1 || newReading == -1) {
            System.out.println("Error: No readings exist for this customer.");
            return 0;
        }

        // 4. Calculate bill value
        double price = Tariff.getPriceByRegion(region);
        double billValue = (newReading - oldReading) * price;

        // 5. Generate bill fields
        int billId = (int)(Math.random() * 100000);
        String date = java.time.LocalDate.now().toString();
        boolean isPaid = false;

        // 6. Build line for Bills.txt
        String billLine =
                billId + "," +
                customerId + "," +
                billValue + "," +
                date + "," +
                isPaid;

        // 7. Write bill line
        FileHandler.write("Files\\Bills.txt", billLine);
         return billId;
    } catch (Exception e) {
        System.out.println(" Error creating bill");
        return 0;
    }
   
}
    public static boolean payBillByMeterCode(int meterCode) {
    // 1. Find which customer owns this meter
    int customerId = Operator.getCustomerIdByMeterCode(meterCode);
    
    if (customerId == -1) {
        System.out.println("Customer not found.");
        return false;
    }

    // 2. Look through bills to find an UNPAID bill for this customer
    ArrayList<String> lines = FileHandler.read("Files\\Bills.txt");
    for (String line : lines) {
        String[] parts = line.split(",");
        if (parts.length < 5) continue;

        int billId = Integer.parseInt(parts[0]);
        int cId = Integer.parseInt(parts[1]);
        boolean isPaid = Boolean.parseBoolean(parts[4]);

        // If we find a bill for this customer that is NOT paid
        if (cId == customerId && !isPaid) {
            markAsPaid(billId); // Call the existing function with the CORRECT Bill ID
            return true; // Stop after paying one bill
        }
    }
    return false; // No unpaid bills found
    }


}