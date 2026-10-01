package hospital.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * MainFrame - The main application window (dashboard).
 * Uses a JTabbedPane so each module is a separate tab.
 */
public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Hospital Management System");
        setSize(1100, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        // ---- Top header panel ----
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(30, 80, 140));
        headerPanel.setPreferredSize(new Dimension(getWidth(), 60));

        JLabel titleLabel = new JLabel("  Hospital Management System", SwingConstants.LEFT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(new Color(200, 50, 50));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new LoginFrame().setVisible(true);
            }
        });
        JPanel logoutPanel = new JPanel();
        logoutPanel.setBackground(new Color(30, 80, 140));
        logoutPanel.add(logoutBtn);
        headerPanel.add(logoutPanel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // ---- Tabbed pane with all modules ----
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Arial", Font.BOLD, 13));
        tabs.setBackground(Color.WHITE);

        tabs.addTab("Patients",        new PatientPanel());
        tabs.addTab("Doctors",         new DoctorPanel());
        tabs.addTab("Nurses",          new NursePanel());
        tabs.addTab("Employees",       new EmployeePanel());
        tabs.addTab("Rooms / Wards",   new RoomPanel());
        tabs.addTab("Appointments",    new AppointmentPanel());
        tabs.addTab("Medicines",       new MedicinePanel());
        tabs.addTab("Medical Records", new MedicalRecordPanel());
        tabs.addTab("Billing",         new BillingPanel());

        add(tabs, BorderLayout.CENTER);
    }
}
