package hospital.models;

/**
 * Patient model class.
 * Represents a patient record in the hospital system.
 */
public class Patient {

    private int    patientId;
    private String name;
    private int    age;
    private String gender;
    private String bloodGroup;
    private String phone;
    private String address;

    // ---- Constructors ----

    public Patient() {}

    public Patient(int patientId, String name, int age, String gender,
                   String bloodGroup, String phone, String address) {
        this.patientId  = patientId;
        this.name       = name;
        this.age        = age;
        this.gender     = gender;
        this.bloodGroup = bloodGroup;
        this.phone      = phone;
        this.address    = address;
    }

    // ---- Getters and Setters ----

    public int    getPatientId()  { return patientId; }
    public String getName()       { return name; }
    public int    getAge()        { return age; }
    public String getGender()     { return gender; }
    public String getBloodGroup() { return bloodGroup; }
    public String getPhone()      { return phone; }
    public String getAddress()    { return address; }

    public void setPatientId(int patientId)   { this.patientId  = patientId; }
    public void setName(String name)           { this.name       = name; }
    public void setAge(int age)                { this.age        = age; }
    public void setGender(String gender)       { this.gender     = gender; }
    public void setBloodGroup(String bg)       { this.bloodGroup = bg; }
    public void setPhone(String phone)         { this.phone      = phone; }
    public void setAddress(String address)     { this.address    = address; }

    @Override
    public String toString() {
        return "Patient[" + patientId + "] " + name;
    }
}
