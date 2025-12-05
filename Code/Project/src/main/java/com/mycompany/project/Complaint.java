
public class Complaint {
    private int complaintId, customerId;
    private String text;
    private boolean isResolved;

    public Complaint(int complaintId, int customerId, String text) {
        this.complaintId = complaintId;
        this.customerId = customerId;
        this.text = text;
        this.isResolved = false;
        String complaintLine = complaintId + "," + customerId + "," + text + "," + isResolved();
        FileHandler.write("Files\\Complaints.txt", complaintLine);
    }

    public Complaint(int complaintId, int customerId, String text, boolean isResolved) {
        this.complaintId = complaintId;
        this.customerId = customerId;
        this.text = text;
        this.isResolved = isResolved;
    }

    public int getComplaintId() {
        return this.complaintId;
    }

    public String getText() {
        return this.text;
    }

    public boolean isResolved() {
        return this.isResolved;
    }

    public void setResolved(boolean isResolved) {
        this.isResolved = isResolved;
    }

    public String toString() {
        return complaintId + "," + customerId + "," + text + "," + isResolved;
    }
}
