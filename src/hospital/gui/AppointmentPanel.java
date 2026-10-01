package hospital.gui;

import hospital.dao.AppointmentDAO;
import hospital.dao.PatientDAO;
import hospital.dao.DoctorDAO;
import hospital.models.Appointment;
import hospital.models.Patient;
import hospital.models.Doctor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/** AppointmentPanel - GUI panel for managing appointments */
public class AppointmentPanel extends JPanel {

    private AppointmentDAO dao      = new AppointmentDAO();
    private PatientDAO     patDao   = new PatientDAO();
    private DoctorDAO      docDao   = new DoctorDAO();

    private JTextField idField, dateField, timeField, notesField;
    private JComboBox<String> statusCombo;
    private JComboBox<String> patientCombo, doctorCombo;
    private JTable table;
    private DefaultTableModel tableModel;

    public AppointmentPanel() {
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(buildFormPanel(), BorderLayout.NORTH);
        add(buildTablePanel(), BorderLayout.CENTER);
        add(buildButtonPanel(), BorderLayout.SOUTH);
        loadTable();
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridLayout(3,4,8,8));
        panel.setBorder(BorderFactory.createTitledBorder("Appointment Information"));

        panel.add(new JLabel("Appt ID:")); idField=new JTextField(); panel.add(idField);
        panel.add(new JLabel("Patient:"));
        patientCombo=new JComboBox<>(); loadPatientCombo(); panel.add(patientCombo);
        panel.add(new JLabel("Doctor:"));
        doctorCombo=new JComboBox<>(); loadDoctorCombo(); panel.add(doctorCombo);

        panel.add(new JLabel("Date (YYYY-MM-DD):")); dateField=new JTextField(); panel.add(dateField);
        panel.add(new JLabel("Time (e.g. 10:00 AM):")); timeField=new JTextField(); panel.add(timeField);
        panel.add(new JLabel("Status:"));
        statusCombo=new JComboBox<>(new String[]{"Scheduled","Completed","Cancelled"}); panel.add(statusCombo);

        panel.add(new JLabel("Notes:")); notesField=new JTextField(); panel.add(notesField);
        panel.add(new JLabel("")); panel.add(new JLabel(""));
        return panel;
    }

    private void loadPatientCombo() {
        patientCombo.removeAllItems();
        for(Patient p : patDao.getAllPatients())
            patientCombo.addItem(p.getPatientId() + " - " + p.getName());
    }

    private void loadDoctorCombo() {
        doctorCombo.removeAllItems();
        for(Doctor d : docDao.getAllDoctors())
            doctorCombo.addItem(d.getDoctorId() + " - " + d.getName());
    }

    // Extract just the ID from combo like "3 - Dr. Smith"
    private int extractId(String comboValue) {
        if(comboValue==null||comboValue.isEmpty()) return 0;
        return Integer.parseInt(comboValue.split(" - ")[0].trim());
    }

    private JPanel buildTablePanel() {
        tableModel=new DefaultTableModel(new String[]{"ID","Patient ID","Doctor ID","Date","Time","Status","Notes"},0){
            @Override public boolean isCellEditable(int r,int c){return false;}};
        table=new JTable(tableModel);
        table.setRowHeight(22);
        table.getTableHeader().setFont(new Font("Arial",Font.BOLD,12));
        table.getSelectionModel().addListSelectionListener(e->{
            if(!e.getValueIsAdjusting()&&table.getSelectedRow()>=0){
                int row=table.getSelectedRow();
                idField.setText(tableModel.getValueAt(row,0).toString());
                dateField.setText(tableModel.getValueAt(row,3).toString());
                timeField.setText(tableModel.getValueAt(row,4).toString());
                statusCombo.setSelectedItem(tableModel.getValueAt(row,5).toString());
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
        JButton add=new JButton("Add"); JButton upd=new JButton("Update"); JButton del=new JButton("Delete"); JButton clr=new JButton("Clear");
        styleButton(add,new Color(0,150,80)); styleButton(upd,new Color(0,100,200)); styleButton(del,new Color(200,50,50)); styleButton(clr,new Color(130,130,130));
        add.addActionListener(e->{Appointment a=build();if(dao.addAppointment(a)){JOptionPane.showMessageDialog(this,"Appointment added!");clearFields();loadTable();}});
        upd.addActionListener(e->{if(idField.getText().isEmpty()){JOptionPane.showMessageDialog(this,"Select an appointment.");return;}Appointment a=build();a.setAppointmentId(Integer.parseInt(idField.getText().trim()));if(dao.updateAppointment(a)){JOptionPane.showMessageDialog(this,"Updated!");clearFields();loadTable();}});
        del.addActionListener(e->{if(idField.getText().isEmpty()){JOptionPane.showMessageDialog(this,"Select an appointment.");return;}if(JOptionPane.showConfirmDialog(this,"Delete?","Confirm",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){dao.deleteAppointment(Integer.parseInt(idField.getText().trim()));clearFields();loadTable();}});
        clr.addActionListener(e->clearFields());
        panel.add(add); panel.add(upd); panel.add(del); panel.add(clr);
        return panel;
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        for(Appointment a:dao.getAllAppointments())
            tableModel.addRow(new Object[]{a.getAppointmentId(),a.getPatientId(),a.getDoctorId(),a.getAppointmentDate(),a.getAppointmentTime(),a.getStatus(),a.getNotes()});
    }

    private Appointment build() {
        Appointment a=new Appointment();
        a.setPatientId(extractId(patientCombo.getSelectedItem()!=null?patientCombo.getSelectedItem().toString():""));
        a.setDoctorId(extractId(doctorCombo.getSelectedItem()!=null?doctorCombo.getSelectedItem().toString():""));
        a.setAppointmentDate(dateField.getText().trim());
        a.setAppointmentTime(timeField.getText().trim());
        a.setStatus(statusCombo.getSelectedItem().toString());
        a.setNotes(notesField.getText().trim());
        return a;
    }

    private void clearFields(){idField.setText("");dateField.setText("");timeField.setText("");notesField.setText("");statusCombo.setSelectedIndex(0);table.clearSelection();}
    private void styleButton(JButton b,Color c){b.setBackground(c);b.setForeground(Color.WHITE);b.setFocusPainted(false);b.setFont(new Font("Arial",Font.BOLD,12));b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));}
}
