package com.mycompany.project;


public class Bill {
    private int billId;
    private int customerId;
    private double amount;
    private String Date;
    private boolean isPaid;


    public Bill(int billId, int customerId, double amount, String Date, boolean isPaid) {
        this.billId = billId;
        this.customerId = customerId;
        this.amount = amount;
        this.Date = Date;
        this.isPaid = isPaid;
        String line = this.billId + "," + this.customerId + "," +  this.amount + "," +  this.Date + "," +   this.isPaid;
        FileHandler.write("Files\\Bills.txt", line);//وهنا كمان    
    }


    public static double calculateBill(int consumption, double tariffPrice) {
        return consumption * tariffPrice;
    }
    public void markAsPaid() {
        this.isPaid = true;
        String newData = this.billId + "," + this.customerId + "," +  this.amount + "," +  this.Date + "," +  this.isPaid;
        FileHandler.updateLineById("Files\\Bills.txt", this.billId, newData);//حد يراجع اللوكيشن بقا
        System.out.println("Bill " + this.billId + " has been marked as paid.");
    }



    public int getBillId() { return billId; }
    public int getCustomerId() { return customerId; }
    public double getAmount() { return amount; }
    public String getDate() { return Date; }
    public boolean isPaid() { return isPaid; }
}