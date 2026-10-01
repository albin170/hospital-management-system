package hospital.models;

/** MedicalRecord model class */
public class MedicalRecord {

    private int    recordId;
    private int    patientId;
    private int    doctorId;
    private String diagnosis;
    private String prescription;
    private String visitDate;    // YYYY-MM-DD
    private String notes;

    public MedicalRecord() {}

    public MedicalRecord(int recordId, int patientId, int doctorId,
                         String diagnosis, String prescription,
                         String visitDate, String notes) {
        this.recordId     = recordId;
        this.patientId    = patientId;
        this.doctorId     = doctorId;
        this.diagnosis    = diagnosis;
        this.prescription = prescription;
        this.visitDate    = visitDate;
        this.notes        = notes;
    }

    public int    getRecordId()     { return recordId; }
    public int    getPatientId()    { return patientId; }
    public int    getDoctorId()     { return doctorId; }
    public String getDiagnosis()    { return diagnosis; }
    public String getPrescription() { return prescription; }
    public String getVisitDate()    { return visitDate; }
    public String getNotes()        { return notes; }

    public void setRecordId(int id)          { this.recordId     = id; }
    public void setPatientId(int pid)        { this.patientId    = pid; }
    public void setDoctorId(int did)         { this.doctorId     = did; }
    public void setDiagnosis(String d)       { this.diagnosis    = d; }
    public void setPrescription(String p)    { this.prescription = p; }
    public void setVisitDate(String date)    { this.visitDate    = date; }
    public void setNotes(String notes)       { this.notes        = notes; }
}
