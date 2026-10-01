package hospital.gui;

import hospital.dao.PatientDAO;
import hospital.models.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * PatientPanel - GUI panel for managing patients.
 * Supports: Add, Update, Delete, Search, View All
 */
public class PatientPanel extends JPanel {

    // DAO for database operations
    private PatientDAO dao = new PatientDAO();

    // Form fields
    private JTextField idField, nameField, ageField, phoneField, addressField;
    private JComboBox<String> genderCombo, bloodGroupCombo;

    // Search field
    private JTextField searchField;

    // Table to display patients
    private JTable table;
    private DefaultTableModel tableModel;

    public PatientPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(buildFormPanel(),   BorderLayout.NORTH);
        add(buildTablePanel(),  BorderLayout.CENTER);
        add(buildButtonPanel(), BorderLayout.SOUTH);

        loadTable("");  // load all patients initially
    }

    // ---- Form: input fields ----
    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 4, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Patient Information"));

        panel.add(new JLabel("Patient ID (for update/delete):"));
        idField = new JTextField(); panel.add(idField);

        panel.add(new JLabel("Name:"));
        nameField = new JTextField(); panel.add(nameField);

        panel.add(new JLabel("Age:"));
        ageField = new JTextField(); panel.add(ageField);

        panel.add(new JLabel("Gender:"));
        genderCombo = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        panel.add(genderCombo);

        panel.add(new JLabel("Blood Group:"));
        bloodGroupCombo = new JComboBox<>(new String[]{"A+","A-","B+","B-","AB+","AB-","O+","O-"});
        panel.add(bloodGroupCombo);

        panel.add(new JLabel("Phone:"));
        phoneField = new JTextField(); panel.add(phoneField);

        panel.add(new JLabel("Address:"));
        addressField = new JTextField(); panel.add(addressField);

        return panel;
    }

    // ---- Table to display records ----
    private JPanel buildTablePanel() {
        tableModel = new DefaultTableModel(
            new String[]{"ID", "Name", "Age", "Gender", "Blood Group", "Phone", "Address"}, 0
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(22);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        // When a row is clicked, fill the form fields
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int row = table.getSelectedRow();
                idField.setText    (tableModel.getValueAt(row, 0).toString());
                nameField.setText  (tableModel.getValueAt(row, 1).toString());
                ageField.setText   (tableModel.getValueAt(row, 2).toString());
                genderCombo.setSelectedItem(tableModel.getValueAt(row, 3).toString());
                bloodGroupCombo.setSelectedItem(tableModel.getValueAt(row, 4).toString());
                phoneField.setText (tableModel.getValueAt(row, 5).toString());
                addressField.setText(tableModel.getValueAt(row, 6).toString());
            }
        });

        JPanel panel = new JPanel(new BorderLayout(5, 5));

        // Search bar
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search by Name:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);
        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(e -> loadTable(searchField.getText().trim()));
        searchPanel.add(searchBtn);
        JButton showAllBtn = new JButton("Show All");
        showAllBtn.addActionListener(e -> { searchField.setText(""); loadTable(""); });
        searchPanel.add(showAllBtn);

        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    // ---- Action buttons ----
    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));

        JButton addBtn    = new JButton("Add Patient");
        JButton updateBtn = new JButton("Update Patient");
        JButton deleteBtn = new JButton("Delete Patient");
        JButton clearBtn  = new JButton("Clear Fields");

        styleButton(addBtn,    new Color(0, 150, 80));
        styleButton(updateBtn, new Color(0, 100, 200));
        styleButton(deleteBtn, new Color(200, 50, 50));
        styleButton(clearBtn,  new Color(130, 130, 130));

        addBtn.addActionListener(e -> addPatient());
        updateBtn.addActionListener(e -> updatePatient());
        deleteBtn.addActionListener(e -> deletePatient());
        clearBtn.addActionListener(e -> clearFields());

        panel.add(addBtn);
        panel.add(updateBtn);
        panel.add(deleteBtn);
        panel.add(clearBtn);
        return panel;
    }

    // ---- Load data into the table ----
    private void loadTable(String searchName) {
        tableModel.setRowCount(0);  // clear existing rows
        List<Patient> list;
        if (searchName.isEmpty()) {
            list = dao.getAllPatients();
        } else {
            list = dao.searchByName(searchName);
        }
        for (Patient p : list) {
            tableModel.addRow(new Object[]{
                p.getPatientId(), p.getName(), p.getAge(),
                p.getGender(), p.getBloodGroup(), p.getPhone(), p.getAddress()
            });
        }
    }

    // ---- CRUD operations ----

    private void addPatient() {
        if (!validateFields()) return;
        Patient p = buildPatientFromForm();
        if (dao.addPatient(p)) {
            JOptionPane.showMessageDialog(this, "Patient added successfully!");
            clearFields();
            loadTable("");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add patient.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updatePatient() {
        if (idField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select a patient to update (ID is required).", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validateFields()) return;
        Patient p = buildPatientFromForm();
        p.setPatientId(Integer.parseInt(idField.getText().trim()));
        if (dao.updatePatient(p)) {
            JOptionPane.showMessageDialog(this, "Patient updated successfully!");
            clearFields();
            loadTable("");
        }
    }

    private void deletePatient() {
        if (idField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select a patient to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this patient?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(idField.getText().trim());
            if (dao.deletePatient(id)) {
                JOptionPane.showMessageDialog(this, "Patient deleted.");
                clearFields();
                loadTable("");
            }
        }
    }

    // ---- Helper methods ----

    private Patient buildPatientFromForm() {
        Patient p = new Patient();
        p.setName     (nameField.getText().trim());
        p.setAge      (Integer.parseInt(ageField.getText().trim()));
        p.setGender   (genderCombo.getSelectedItem().toString());
        p.setBloodGroup(bloodGroupCombo.getSelectedItem().toString());
        p.setPhone    (phoneField.getText().trim());
        p.setAddress  (addressField.getText().trim());
        return p;
    }

    private boolean validateFields() {
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name is required.", "Validation", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            Integer.parseInt(ageField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Age must be a number.", "Validation", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        ageField.setText("");
        phoneField.setText("");
        addressField.setText("");
        genderCombo.setSelectedIndex(0);
        bloodGroupCombo.setSelectedIndex(0);
        table.clearSelection();
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}
