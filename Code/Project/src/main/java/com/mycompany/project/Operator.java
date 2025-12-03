
import java.util.ArrayList;

public class Operator extends User {

    public Operator(int id, String username, String password, String role) {
        super(id, username, password, role);
    }

    public static void inputReading(int meterCode, int value) {
        String file = "MeterReadings.txt";

        // Get previous reading
        int prevValue = 0;
        ArrayList<String> lines = FileHandler.read(file);

        for (String line : lines) {
            String[] parts = line.split(",");
            int customerId = Integer.parseInt(parts[1]);

            if (customerId == meterCode) {
                prevValue = Integer.parseInt(parts[3]);
            }
        }

        
        boolean isValidated = validateReading(prevValue, value);

        int readingId = meterCode;     // may be wrong (here)
        int customerId = meterCode;
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
        return (current >= prev);
    }

    public static void printBill(int meterCode) {
        String file = "Bills.txt";
        ArrayList<String> bills = FileHandler.read(file);

        for (String line : bills) {
            String[] parts = line.split(",");

            int customerId = Integer.parseInt(parts[1]);

            if (customerId == meterCode) {
                System.out.println(line);
                return;
            }
        }

        System.out.println("No bill found for meter code: " + meterCode);
    }

    public static void defineTariff(String region, double price) {
        String file = "Tariffs.txt";
        String line = region + "," + price;
        FileHandler.write(file, line);
    }
}

