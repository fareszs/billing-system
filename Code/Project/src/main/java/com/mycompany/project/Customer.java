package com.mycompany.project;
import java.time.LocalDateTime;
import java.util.*;

public class Customer extends User{
    private String meterCode , region , email , contractImagePath , name;
    private boolean isnew = false;
    private LocalDateTime creationTime;
    public Customer(int id , String username , String password , String name , String meterCode , String region , String email , boolean isnew){
        super(id, username, password, "Customer");
        this.meterCode = meterCode;
        this.region = region;
        this.email = email;
        this.isnew = isnew;
        this.name = name;
        creationTime = LocalDateTime.now();
        if (isnew){
            this.register();
        }
    }
    public void setMetercode(String meterCode){
        this.meterCode = meterCode;
    }
    public String getMeterCode(){
        return this.meterCode;
    }
    public void setRegion(String region){
        this.region = region;
    }
    public String getRegion(){
        return this.region;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return this.email;
    }

    public void register(){
        String userLine = super.getId() + "," +
                          super.getUsername() + "," +
                          super.getPassword() + "," +
                          "newcustomer," +
                          name + "," +
                          region + "," +
                          meterCode;

        FileHandler.write("Files\\Users.txt", userLine);
        System.out.println("Customer registered successfully: " + super.getUsername());
    }

    public void attachContract(String path){
        this.contractImagePath = path;
        System.out.println("Contract attached from path: " + path);
    }

    public void payBill(double newReading, double oldReading) {
        int billId = (int) (Math.random() * 10000);
        Date billDate = new Date();
        double billValue = (newReading - oldReading) * Tariff.getPricePerUnit();

        Bill bill = new Bill(billId, super.getId(), billValue, billDate.toString(), true);

        System.out.println("----- Bill Issued -----");
        System.out.println("Bill ID: " + billId);
        System.out.println("Customer ID: " + super.getId());
        System.out.println("Meter Code: " + meterCode);
        System.out.println("Date: " + billDate);
        System.out.println("Old Reading: " + oldReading);
        System.out.println("New Reading: " + newReading);
        System.out.println("Bill Value: " + billValue);
        System.out.println("-----------------------");
    }

    public void complaint(String meterCode , String text){
        int complaintId = (int) (Math.random() * 10000);
        Complaint cmp = new Complaint(complaintId, super.getId(), text);
        System.out.println("Complaint filed: " + text);
    }

    public void enterReading(int readingValue, String meterCode){
        Operator.inputReading( Integer.parseInt(meterCode), readingValue);
    }

    public boolean isThreeMonthsPassed() {
        LocalDateTime after3Months = creationTime.plusMonths(3);
        return LocalDateTime.now().isAfter(after3Months);
    }

    public void sendEmail(){
        if(this.isnew==true && isThreeMonthsPassed()){
            System.out.println("Meter of customer number : " +super.getId()+" is ready" );
        }
    }

    public void notifyCustomer(){
        if(this.isnew == false && isThreeMonthsPassed()){
            System.out.println("Customer number : "+super.getId()+" didnot pay for 3 months");
        }
    }
}
