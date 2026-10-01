package hospital.models;

/** Appointment model class */
public class Appointment {

    private int    appointmentId;
    private int    patientId;
    private int    doctorId;
    private String appointmentDate;  // stored as String for simplicity
    private String appointmentTime;
    private String status;           // Scheduled, Completed, Cancelled
    private String notes;

    public Appointment() {}

    public Appointment(int appointmentId, int patientId, int doctorId,
                       String appointmentDate, String appointmentTime,
                       String status, String notes) {
        this.appointmentId   = appointmentId;
        this.patientId       = patientId;
        this.doctorId        = doctorId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status          = status;
        this.notes           = notes;
    }

    public int    getAppointmentId()   { return appointmentId; }
    public int    getPatientId()       { return patientId; }
    public int    getDoctorId()        { return doctorId; }
    public String getAppointmentDate() { return appointmentDate; }
    public String getAppointmentTime() { return appointmentTime; }
    public String getStatus()          { return status; }
    public String getNotes()           { return notes; }

    public void setAppointmentId(int id)       { this.appointmentId   = id; }
    public void setPatientId(int pid)          { this.patientId       = pid; }
    public void setDoctorId(int did)           { this.doctorId        = did; }
    public void setAppointmentDate(String d)   { this.appointmentDate = d; }
    public void setAppointmentTime(String t)   { this.appointmentTime = t; }
    public void setStatus(String status)       { this.status          = status; }
    public void setNotes(String notes)         { this.notes           = notes; }
}
