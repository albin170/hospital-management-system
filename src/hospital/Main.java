package hospital;

import hospital.gui.LoginFrame;
import javax.swing.*;

/**
 * Main - Entry point of the Hospital Management System.
 * Starts the application by showing the Login window.
 */
public class Main {

    public static void main(String[] args) {
        // Use system look and feel for a native appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // If it fails, Swing's default look and feel is used
        }

        // Start the Login window on the Event Dispatch Thread (Swing thread-safety)
        SwingUtilities.invokeLater(() -> {
            LoginFrame login = new LoginFrame();
            login.setVisible(true);
        });
    }
}
