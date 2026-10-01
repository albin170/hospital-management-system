package hospital.gui;

import hospital.db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/**
 * LoginFrame - The login window shown when the application starts.
 * Validates username and password against the users table.
 */
public class LoginFrame extends JFrame {

    private JTextField     usernameField;
    private JPasswordField passwordField;
    private JButton        loginButton;

    public LoginFrame() {
        setTitle("Hospital Management System - Login");
        setSize(420, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        // Main panel with a nice background colour
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(30, 80, 140));  // dark blue

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        // ---- Title label ----
        JLabel titleLabel = new JLabel("Hospital Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        JLabel subLabel = new JLabel("Please log in to continue", SwingConstants.CENTER);
        subLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        subLabel.setForeground(new Color(180, 220, 255));
        gbc.gridy = 1;
        mainPanel.add(subLabel, gbc);

        // ---- Username ----
        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        gbc.gridwidth = 1; gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(userLabel, gbc);

        usernameField = new JTextField(15);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 13));
        gbc.gridx = 1;
        mainPanel.add(usernameField, gbc);

        // ---- Password ----
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);
        passLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(passLabel, gbc);

        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 13));
        gbc.gridx = 1;
        mainPanel.add(passwordField, gbc);

        // ---- Login button ----
        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(0, 180, 120));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        mainPanel.add(loginButton, gbc);

        // ---- Press Enter to login ----
        passwordField.addActionListener(e -> doLogin());
        loginButton.addActionListener(e -> doLogin());

        add(mainPanel);
    }

    /** Validates credentials against the database */
    private void doLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            String sql  = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Login successful - open the main dashboard
                JOptionPane.showMessageDialog(this, "Welcome, " + username + "!", "Login Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();  // close login window
                new MainFrame().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                passwordField.setText("");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
