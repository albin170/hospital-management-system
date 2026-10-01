package hospital.gui;

import hospital.dao.DoctorDAO;
import hospital.models.Doctor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/** DoctorPanel - GUI panel for managing doctors */
public class DoctorPanel extends JPanel {

    private DoctorDAO dao = new DoctorDAO();

    private JTextField idField, nameField, specField, phoneField, emailField, salaryField;
    private JTextField searchField;
    private JTable table;
    private DefaultTableModel tableModel;

    public DoctorPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(buildFormPanel(),   BorderLayout.NORTH);
        add(buildTablePanel(),  BorderLayout.CENTER);
        add(buildButtonPanel(), BorderLayout.SOUTH);
        loadTable("");
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 4, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Doctor Information"));

        panel.add(new JLabel("Doctor ID (update/delete):")); idField = new JTextField(); panel.add(idField);
        panel.add(new JLabel("Name:"));           nameField  = new JTextField(); panel.add(nameField);
        panel.add(new JLabel("Specialization:")); specField  = new JTextField(); panel.add(specField);
        panel.add(new JLabel("Phone:"));          phoneField = new JTextField(); panel.add(phoneField);
        panel.add(new JLabel("Email:"));          emailField = new JTextField(); panel.add(emailField);
        panel.add(new JLabel("Salary:"));         salaryField = new JTextField(); panel.add(salaryField);
        return panel;
    }

    private JPanel buildTablePanel() {
        tableModel = new DefaultTableModel(
            new String[]{"ID","Name","Specialization","Phone","Email","Salary"}, 0
        ) { @Override public boolean isCellEditable(int r, int c) { return false; } };

        table = new JTable(tableModel);
        table.setRowHeight(22);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int row = table.getSelectedRow();
                idField.setText   (tableModel.getValueAt(row,0).toString());
                nameField.setText (tableModel.getValueAt(row,1).toString());
                specField.setText (tableModel.getValueAt(row,2).toString());
                phoneField.setText(tableModel.getValueAt(row,3).toString());
                emailField.setText(tableModel.getValueAt(row,4).toString());
                salaryField.setText(tableModel.getValueAt(row,5).toString());
            }
        });

        JPanel panel = new JPanel(new BorderLayout(5,5));
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search by Name:")); searchField = new JTextField(20); searchPanel.add(searchField);
        JButton s = new JButton("Search"); s.addActionListener(e -> loadTable(searchField.getText().trim())); searchPanel.add(s);
        JButton a = new JButton("Show All"); a.addActionListener(e -> { searchField.setText(""); loadTable(""); }); searchPanel.add(a);
        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        JButton addBtn = new JButton("Add Doctor");
        JButton updBtn = new JButton("Update Doctor");
        JButton delBtn = new JButton("Delete Doctor");
        JButton clrBtn = new JButton("Clear Fields");
        styleButton(addBtn, new Color(0,150,80)); styleButton(updBtn, new Color(0,100,200));
        styleButton(delBtn, new Color(200,50,50)); styleButton(clrBtn, new Color(130,130,130));
        addBtn.addActionListener(e -> addDoctor());
        updBtn.addActionListener(e -> updateDoctor());
        delBtn.addActionListener(e -> deleteDoctor());
        clrBtn.addActionListener(e -> clearFields());
        panel.add(addBtn); panel.add(updBtn); panel.add(delBtn); panel.add(clrBtn);
        return panel;
    }

    private void loadTable(String name) {
        tableModel.setRowCount(0);
        List<Doctor> list = name.isEmpty() ? dao.getAllDoctors() : dao.searchByName(name);
        for (Doctor d : list)
            tableModel.addRow(new Object[]{d.getDoctorId(),d.getName(),d.getSpecialization(),d.getPhone(),d.getEmail(),d.getSalary()});
    }

    private void addDoctor() {
        if (nameField.getText().trim().isEmpty()) { JOptionPane.showMessageDialog(this,"Name required."); return; }
        Doctor d = buildFromForm();
        if (dao.addDoctor(d)) { JOptionPane.showMessageDialog(this,"Doctor added!"); clearFields(); loadTable(""); }
    }

    private void updateDoctor() {
        if (idField.getText().isEmpty()) { JOptionPane.showMessageDialog(this,"Select a doctor."); return; }
        Doctor d = buildFromForm(); d.setDoctorId(Integer.parseInt(idField.getText().trim()));
        if (dao.updateDoctor(d)) { JOptionPane.showMessageDialog(this,"Doctor updated!"); clearFields(); loadTable(""); }
    }

    private void deleteDoctor() {
        if (idField.getText().isEmpty()) { JOptionPane.showMessageDialog(this,"Select a doctor."); return; }
        if (JOptionPane.showConfirmDialog(this,"Delete this doctor?","Confirm",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
            dao.deleteDoctor(Integer.parseInt(idField.getText().trim()));
            clearFields(); loadTable("");
        }
    }

    private Doctor buildFromForm() {
        Doctor d = new Doctor();
        d.setName(nameField.getText().trim());
        d.setSpecialization(specField.getText().trim());
        d.setPhone(phoneField.getText().trim());
        d.setEmail(emailField.getText().trim());
        try { d.setSalary(Double.parseDouble(salaryField.getText().trim())); } catch(NumberFormatException ex){ d.setSalary(0); }
        return d;
    }

    private void clearFields() {
        idField.setText(""); nameField.setText(""); specField.setText("");
        phoneField.setText(""); emailField.setText(""); salaryField.setText("");
        table.clearSelection();
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg); btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false); btn.setFont(new Font("Arial",Font.BOLD,12));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}
