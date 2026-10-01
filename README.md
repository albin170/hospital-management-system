# Hospital Management System
A Java-based Hospital Management System using Java Swing GUI, MySQL database, and JDBC.

## Project Structure

```
HospitalManagementSystem/
|-- compile.bat              <- Compiles the project (Windows)
|-- run.bat                  <- Runs the application
|-- sql/
|   `-- hospital_db.sql      <- Database setup script (run this first!)
|-- lib/
|   `-- mysql-connector-j.jar  <- Place the MySQL JDBC driver here
|-- src/hospital/
|   |-- Main.java            <- Entry point
|   |-- db/
|   |   `-- DBConnection.java
|   |-- models/              <- Plain Java classes (OOP model layer)
|   |   |-- Patient.java
|   |   |-- Doctor.java
|   |   |-- Nurse.java
|   |   |-- Employee.java
|   |   |-- Room.java
|   |   |-- Appointment.java
|   |   |-- Medicine.java
|   |   |-- MedicalRecord.java
|   |   `-- Bill.java
|   |-- dao/                 <- Database Access Objects (JDBC layer)
|   |   |-- PatientDAO.java
|   |   |-- DoctorDAO.java
|   |   |-- NurseDAO.java
|   |   |-- EmployeeDAO.java
|   |   |-- RoomDAO.java
|   |   |-- AppointmentDAO.java
|   |   |-- MedicineDAO.java
|   |   |-- MedicalRecordDAO.java
|   |   `-- BillingDAO.java
|   `-- gui/                 <- Java Swing GUI panels
|       |-- LoginFrame.java
|       |-- MainFrame.java
|       |-- PatientPanel.java
|       |-- DoctorPanel.java
|       |-- NursePanel.java
|       |-- EmployeePanel.java
|       |-- RoomPanel.java
|       |-- AppointmentPanel.java
|       |-- MedicinePanel.java
|       |-- MedicalRecordPanel.java
|       `-- BillingPanel.java
`-- out/                     <- Compiled .class files (auto-created)
```

---

## Step 1 - Install MySQL & Setup Database

1. Install MySQL from https://dev.mysql.com/downloads/mysql/
2. Open MySQL Workbench or MySQL Command Line
3. Run the setup script:
   ```
   mysql -u root -p < sql\hospital_db.sql
   ```
   Or paste the contents of `sql/hospital_db.sql` into MySQL Workbench and execute it.

**Default login created:** username=`admin`, password=`admin123`

---

## Step 2 - Download MySQL JDBC Driver

1. Go to: https://dev.mysql.com/downloads/connector/j/
2. Download the "Platform Independent" ZIP
3. Extract it and find the file named `mysql-connector-j-X.X.X.jar`
4. Rename it to `mysql-connector-j.jar`
5. Copy it into the `lib/` folder of this project

---

## Step 3 - Configure Database Connection

Open `src/hospital/db/DBConnection.java` and update these 3 lines:

```java
private static final String URL      = "jdbc:mysql://localhost:3306/hospital_db";
private static final String USER     = "root";
private static final String PASSWORD = "";   // <-- enter your MySQL root password
```

---

## Step 4 - Compile

Double-click `compile.bat` or run in CMD:
```
compile.bat
```

---

## Step 5 - Run

Double-click `run.bat` or run in CMD:
```
run.bat
```

---

## Modules

| Module           | Features                                                  |
|------------------|-----------------------------------------------------------|
| Patients         | Add, Update, Delete, Search patients                      |
| Doctors          | Add, Update, Delete, Search doctors by name/specialization|
| Nurses           | Add, Update, Delete, assign to wards                      |
| Employees        | Add, Update, Delete staff (receptionists, admin, etc.)    |
| Rooms / Wards    | Add rooms, set status (Available/Occupied), filter        |
| Appointments     | Schedule appointments linking patients and doctors        |
| Medicines        | Track medicines, quantity, price, expiry date             |
| Medical Records  | Record diagnosis and prescription per patient             |
| Billing          | Create bills, track paid/unpaid amounts                   |

---

## OOP Concepts Used

- **Encapsulation**: All model classes use private fields with getters/setters
- **Classes & Objects**: Each hospital entity has its own class
- **Abstraction**: DAO layer abstracts all SQL from GUI code
- **Constructors**: Both default and parameterized constructors in models
- **Inheritance**: Swing GUI inherits from JPanel, JFrame

---

## Default Login
- **Username**: admin
- **Password**: admin123
