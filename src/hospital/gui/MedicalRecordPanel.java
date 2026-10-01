package hospital.gui;

import hospital.dao.MedicalRecordDAO;
import hospital.dao.PatientDAO;
import hospital.dao.DoctorDAO;
import hospital.models.MedicalRecord;
import hospital.models.Patient;
import hospital.models.Doctor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/** MedicalRecordPanel - GUI panel for managing patient medical records */
public class MedicalRecordPanel extends JPanel {

    private MedicalRecordDAO dao     = new MedicalRecordDAO();
    private PatientDAO       patDao  = new PatientDAO();
    private DoctorDAO        docDao  = new DoctorDAO();

    private JTextField idField, diagField, prescField, dateField, notesField;
    private JComboBox<String> patientCombo, doctorCombo;
    private JTable table;
    private DefaultTableModel tableModel;

    public MedicalRecordPanel() {
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(buildFormPanel(), BorderLayout.NORTH);
        add(buildTablePanel(), BorderLayout.CENTER);
        add(buildButtonPanel(), BorderLayout.SOUTH);
        loadTable();
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridLayout(3,4,8,8));
        panel.setBorder(BorderFactory.createTitledBorder("Medical Record Information"));
        panel.add(new JLabel("Record ID:")); idField=new JTextField(); panel.add(idField);
        panel.add(new JLabel("Patient:")); patientCombo=new JComboBox<>(); loadPatientCombo(); panel.add(patientCombo);
        panel.add(new JLabel("Doctor:"));  doctorCombo=new JComboBox<>(); loadDoctorCombo(); panel.add(doctorCombo);
        panel.add(new JLabel("Visit Date (YYYY-MM-DD):")); dateField=new JTextField(); panel.add(dateField);
        panel.add(new JLabel("Diagnosis:")); diagField=new JTextField(); panel.add(diagField);
        panel.add(new JLabel("Prescription:")); prescField=new JTextField(); panel.add(prescField);
        panel.add(new JLabel("Notes:")); notesField=new JTextField(); panel.add(notesField);
        panel.add(new JLabel("")); panel.add(new JLabel(""));
        return panel;
    }

    private void loadPatientCombo() {
        patientCombo.removeAllItems();
        for(Patient p:patDao.getAllPatients()) patientCombo.addItem(p.getPatientId()+" - "+p.getName());
    }

    private void loadDoctorCombo() {
        doctorCombo.removeAllItems();
        for(Doctor d:docDao.getAllDoctors()) doctorCombo.addItem(d.getDoctorId()+" - "+d.getName());
    }

    private int extractId(String val) {
        if(val==null||val.isEmpty()) return 0;
        return Integer.parseInt(val.split(" - ")[0].trim());
    }

    private JPanel buildTablePanel() {
        tableModel=new DefaultTableModel(new String[]{"ID","Patient ID","Doctor ID","Diagnosis","Prescription","Visit Date","Notes"},0){
            @Override public boolean isCellEditable(int r,int c){return false;}};
        table=new JTable(tableModel);
        table.setRowHeight(22);
        table.getTableHeader().setFont(new Font("Arial",Font.BOLD,12));
        table.getSelectionModel().addListSelectionListener(e->{
            if(!e.getValueIsAdjusting()&&table.getSelectedRow()>=0){
                int row=table.getSelectedRow();
                idField.setText(tableModel.getValueAt(row,0).toString());
                diagField.setText(tableModel.getValueAt(row,3).toString());
                prescField.setText(tableModel.getValueAt(row,4).toString());
                dateField.setText(tableModel.getValueAt(row,5).toString());
                notesField.setText(tableModel.getValueAt(row,6).toString());
            }
        });
        JPanel panel=new JPanel(new BorderLayout(5,5));
        JPanel sp=new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton ref=new JButton("Refresh"); ref.addActionListener(e->{loadTable();loadPatientCombo();loadDoctorCombo();}); sp.add(ref);
        panel.add(sp,BorderLayout.NORTH); panel.add(new JScrollPane(table),BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildButtonPanel() {
        JPanel panel=new JPanel(new FlowLayout(FlowLayout.CENTER,15,5));
        JButton add=new JButton("Add Record"); JButton upd=new JButton("Update Record"); JButton del=new JButton("Delete Record"); JButton clr=new JButton("Clear");
        styleButton(add,new Color(0,150,80)); styleButton(upd,new Color(0,100,200)); styleButton(del,new Color(200,50,50)); styleButton(clr,new Color(130,130,130));
        add.addActionListener(e->{MedicalRecord r=build();if(dao.addRecord(r)){JOptionPane.showMessageDialog(this,"Record added!");clearFields();loadTable();}});
        upd.addActionListener(e->{if(idField.getText().isEmpty()){JOptionPane.showMessageDialog(this,"Select a record.");return;}MedicalRecord r=build();r.setRecordId(Integer.parseInt(idField.getText().trim()));if(dao.updateRecord(r)){JOptionPane.showMessageDialog(this,"Updated!");clearFields();loadTable();}});
        del.addActionListener(e->{if(idField.getText().isEmpty()){JOptionPane.showMessageDialog(this,"Select a record.");return;}if(JOptionPane.showConfirmDialog(this,"Delete?","Confirm",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){dao.deleteRecord(Integer.parseInt(idField.getText().trim()));clearFields();loadTable();}});
        clr.addActionListener(e->clearFields());
        panel.add(add); panel.add(upd); panel.add(del); panel.add(clr);
        return panel;
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        for(MedicalRecord r:dao.getAllRecords())
            tableModel.addRow(new Object[]{r.getRecordId(),r.getPatientId(),r.getDoctorId(),r.getDiagnosis(),r.getPrescription(),r.getVisitDate(),r.getNotes()});
    }

    private MedicalRecord build() {
        MedicalRecord r=new MedicalRecord();
        r.setPatientId(extractId(patientCombo.getSelectedItem()!=null?patientCombo.getSelectedItem().toString():""));
        r.setDoctorId(extractId(doctorCombo.getSelectedItem()!=null?doctorCombo.getSelectedItem().toString():""));
        r.setDiagnosis(diagField.getText().trim()); r.setPrescription(prescField.getText().trim());
        r.setVisitDate(dateField.getText().trim()); r.setNotes(notesField.getText().trim());
        return r;
    }

    private void clearFields(){idField.setText("");diagField.setText("");prescField.setText("");dateField.setText("");notesField.setText("");table.clearSelection();}
    private void styleButton(JButton b,Color c){b.setBackground(c);b.setForeground(Color.WHITE);b.setFocusPainted(false);b.setFont(new Font("Arial",Font.BOLD,12));b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));}
}
