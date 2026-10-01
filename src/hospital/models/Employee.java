package hospital.models;

/** Employee model class (receptionists, admin staff, etc.) */
public class Employee {

    private int    employeeId;
    private String name;
    private String role;
    private String phone;
    private String email;
    private double salary;

    public Employee() {}

    public Employee(int employeeId, String name, String role,
                    String phone, String email, double salary) {
        this.employeeId = employeeId;
        this.name       = name;
        this.role       = role;
        this.phone      = phone;
        this.email      = email;
        this.salary     = salary;
    }

    public int    getEmployeeId() { return employeeId; }
    public String getName()       { return name; }
    public String getRole()       { return role; }
    public String getPhone()      { return phone; }
    public String getEmail()      { return email; }
    public double getSalary()     { return salary; }

    public void setEmployeeId(int id)       { this.employeeId = id; }
    public void setName(String name)         { this.name       = name; }
    public void setRole(String role)         { this.role       = role; }
    public void setPhone(String phone)       { this.phone      = phone; }
    public void setEmail(String email)       { this.email      = email; }
    public void setSalary(double salary)     { this.salary     = salary; }

    @Override
    public String toString() { return "Employee[" + employeeId + "] " + name; }
}
