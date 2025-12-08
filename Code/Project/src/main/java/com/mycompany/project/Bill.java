
public class Bill {
    private int billId;
    private int customerId;
    private double amount;
    private String Date;
    private boolean isPaid;

    // Constructor
    public Bill(int billId, int customerId, double amount, String Date, boolean isPaid) {
        this.billId = billId;
        this.customerId = customerId;
        this.amount = amount;
        this.Date = Date;
        this.isPaid = isPaid;
    }


    public static double calculateBill(int consumption, double tariffPrice) {
        return consumption * tariffPrice;
    }

    // Updates the object state AND the text file
    public void markAsPaid() {
        this.isPaid = true;
        
        // Reconstruct the CSV line with isPaid = true
        String newData = this.billId + "," + this.customerId + "," +  this.amount + "," +  this.Date + "," +  this.isPaid;

        FileHandler.updateLineById("Files\\Bills.txt", this.billId, newData);//حد يراجع اللوكيشن بقا
        System.out.println("Bill " + this.billId + " has been marked as paid.");
    }

    public void save() {
        String line = this.billId + "," + this.customerId + "," +  this.amount + "," +  this.Date + "," +   this.isPaid;
        FileHandler.write("Files\\Bills.txt", line);//وهنا كمان
    }


    public int getBillId() { return billId; }
    public int getCustomerId() { return customerId; }
    public double getAmount() { return amount; }
    public String getDate() { return Date; }
    public boolean isPaid() { return isPaid; }
}