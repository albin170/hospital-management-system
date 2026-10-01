package hospital.models;

/** Bill model class */
public class Bill {

    private int    billId;
    private int    patientId;
    private double totalAmount;
    private double paidAmount;
    private String paymentStatus;   // Paid, Unpaid, Partial
    private String billingDate;     // YYYY-MM-DD
    private String description;

    public Bill() {}

    public Bill(int billId, int patientId, double totalAmount, double paidAmount,
                String paymentStatus, String billingDate, String description) {
        this.billId        = billId;
        this.patientId     = patientId;
        this.totalAmount   = totalAmount;
        this.paidAmount    = paidAmount;
        this.paymentStatus = paymentStatus;
        this.billingDate   = billingDate;
        this.description   = description;
    }

    public int    getBillId()        { return billId; }
    public int    getPatientId()     { return patientId; }
    public double getTotalAmount()   { return totalAmount; }
    public double getPaidAmount()    { return paidAmount; }
    public String getPaymentStatus() { return paymentStatus; }
    public String getBillingDate()   { return billingDate; }
    public String getDescription()   { return description; }

    public void setBillId(int id)              { this.billId        = id; }
    public void setPatientId(int pid)          { this.patientId     = pid; }
    public void setTotalAmount(double amount)  { this.totalAmount   = amount; }
    public void setPaidAmount(double amount)   { this.paidAmount    = amount; }
    public void setPaymentStatus(String s)     { this.paymentStatus = s; }
    public void setBillingDate(String date)    { this.billingDate   = date; }
    public void setDescription(String desc)    { this.description   = desc; }
}
