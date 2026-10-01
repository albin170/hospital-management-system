package hospital.models;

/** Nurse model class */
public class Nurse {

    private int    nurseId;
    private String name;
    private String phone;
    private String wardAssigned;
    private double salary;

    public Nurse() {}

    public Nurse(int nurseId, String name, String phone,
                 String wardAssigned, double salary) {
        this.nurseId      = nurseId;
        this.name         = name;
        this.phone        = phone;
        this.wardAssigned = wardAssigned;
        this.salary       = salary;
    }

    public int    getNurseId()      { return nurseId; }
    public String getName()         { return name; }
    public String getPhone()        { return phone; }
    public String getWardAssigned() { return wardAssigned; }
    public double getSalary()       { return salary; }

    public void setNurseId(int id)          { this.nurseId      = id; }
    public void setName(String name)         { this.name         = name; }
    public void setPhone(String phone)       { this.phone        = phone; }
    public void setWardAssigned(String ward) { this.wardAssigned = ward; }
    public void setSalary(double salary)     { this.salary       = salary; }

    @Override
    public String toString() { return "Nurse[" + nurseId + "] " + name; }
}
