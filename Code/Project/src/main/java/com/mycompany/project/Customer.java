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
        if(isnew == false){
            String userLine = id+","+password+","+"customer"+","+name+","+region+","+meterCode;
            FileHandler.write("Files\\Users.txt" , userLine);
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
        String userData = super.getId() + "," + super.getUsername() + "," + this.meterCode + "," + this.region;
        System.out.println("Customer registered successfully: " + super.getUsername());
        String userLine = super.getId()+","+super.getPassword()+","+"customer"+","+name+","+region+","+meterCode;
        FileHandler.write("Files\\Users.txt" , userLine);
    }

    public void attachContract(String path){
        this.contractImagePath = path;
        System.out.println("Contract attached from path: " + path);
    }

    public static void payBill(String meterCode , double newReading , double oldReading){
        int billId = (int) (Math.random() * 10000);
        Date billDate = new Date();
        double value = (newReading-oldReading)*Tariff.getPricePerUnit;
        boolean isPaid = true;
        Bill newBill = new Bill(billId , super.getId() , value , billDate , isPaid);
        System.out.println("Bill ID : " + billId);
        System.out.println("Customer ID : " + super.getId());
        System.out.println("Meter Code : " + this.getMeterCode() );
        System.out.println("Date : " + billDate);
        System.out.println("Current Reading : " + newReading);
        System.out.println("Previous Reading : " + oldReading);
        System.out.println("The value of bill : " + value);
    }

    public void complaint(String meterCode , String text){
        int complaintId = (int) (Math.random() * 10000);
        Complaint cmp = new Complaint(complaintId, super.getId(), text);
        System.out.println("Complaint filed: " + text);
    }

    public void enterReading(int readingValue, String meterCode){
        Operator.inputReading(readingValue , meterCode);
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
