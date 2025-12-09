package com.mycompany.project;



public class MeterReading {
    private int readingId;
    private int customerId;
    private String date;
    private int value; 
    private boolean isValidated;


    public MeterReading(int readingId, int customerId, String date, int value, boolean isValidated) {
        this.readingId = readingId;
        this.customerId = customerId;
        this.date = date;
        this.value = value;
        this.isValidated = isValidated;
    }


    public int getConsumption(int previousReadingValue) {
        if (this.value < previousReadingValue) {
            return 0; 
        }
        return this.value - previousReadingValue;
    }

    public void save() {
        String line = this.readingId + "," +   this.customerId + "," +   this.date + "," +   this.value + "," +   this.isValidated;
        FileHandler.write("Files\\MeterReadings.txt", line);//اللوكيشن حر يرلجعهس
    }


    public int getReadingId() { return readingId; }
    public int getCustomerId() { return customerId; }
    public String getDate() { return date; }
    public int getValue() { return value; }
    public boolean isValidated() { return isValidated; }
}