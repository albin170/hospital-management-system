package hospital.models;

/** Doctor model class */
public class Doctor {

    private int    doctorId;
    private String name;
    private String specialization;
    private String phone;
    private String email;
    private double salary;

    public Doctor() {}

    public Doctor(int doctorId, String name, String specialization,
                  String phone, String email, double salary) {
        this.doctorId       = doctorId;
        this.name           = name;
        this.specialization = specialization;
        this.phone          = phone;
        this.email          = email;
        this.salary         = salary;
    }

    public int    getDoctorId()       { return doctorId; }
    public String getName()           { return name; }
    public String getSpecialization() { return specialization; }
    public String getPhone()          { return phone; }
    public String getEmail()          { return email; }
    public double getSalary()         { return salary; }

    public void setDoctorId(int id)          { this.doctorId       = id; }
    public void setName(String name)          { this.name           = name; }
    public void setSpecialization(String s)   { this.specialization = s; }
    public void setPhone(String phone)        { this.phone          = phone; }
    public void setEmail(String email)        { this.email          = email; }
    public void setSalary(double salary)      { this.salary         = salary; }

    @Override
    public String toString() { return "Doctor[" + doctorId + "] " + name; }
}
