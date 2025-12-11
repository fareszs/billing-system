package com.mycompany.project;

import java.io.File;
import java.util.Scanner;

public class Tariff {

    private String region;
    private double pricePerUnit;
    public static double getPriceByRegion(String searchRegion) {
        try {
            Scanner sc = new Scanner(new File("Tariffs.txt"));

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(",");

                if (parts.length == 2) {
                    String fileRegion = parts[0].trim();
                    double filePrice = Double.parseDouble(parts[1].trim());

                    if (fileRegion.equalsIgnoreCase(searchRegion)) {
                        sc.close();
                        return filePrice;
                    }
                }
            }

            sc.close();
        } catch (Exception e) {
            return -1; // file error or region not found
        }

        return -1; // region not found
    }
}
