package Manager;

import MainProgram.MainMenuGUI;
import Utils.WindowNav;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ManagerLogin extends JFrame {

    private static final String DATA_FILE = "data/managerList.txt";  // Changed filename here

    private static final List<Manager> managerList = new ArrayList<>();
    private static final int MAX_ATTEMPTS = 3;
    private static int loginAttempts = 0;


    private static void createDefaultManagerDataIfMissing(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.println("manager1,12345,rainbow");
                writer.println("admin,admin123,sunshine");
                writer.println("johndoe,password123,bluebird");
                System.out.println("managerList.txt created with default users.");  // Updated message
            } catch (IOException e) {
                System.out.println("Failed to create managerList.txt: " + e.getMessage());  // Updated message
            }
        }
    }

    public ManagerLogin() {
        // Debug: Print the absolute path where Java is looking for the file
        System.out.println("Looking for manager file at: " + new File(DATA_FILE).getAbsolutePath());

        createDefaultManagerDataIfMissing(DATA_FILE);
        loadManagersFromFile(DATA_FILE);
        SwingUtilities.invokeLater(ManagerLogin::showLoginScreen);
        
        
    }
    /**
     *
     *
     */
    public static void loadManagersFromFile(String filename) {
        managerList.clear(); // Clear existing data before loading
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    managerList.add(new Manager(parts[0].trim(), parts[1].trim(), parts[2].trim()));
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading manager data: " + e.getMessage());
        }
    }

    private static void saveManagersToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))) {
            for (Manager m : managerList) {
                writer.println(m.getUsername() + "," + m.getPassword() + "," + m.getSecurityPhrase());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving manager data: " + e.getMessage());
        }
    }

    private static void showLoginScreen() {
        JFrame loginFrame = new JFrame("Manager Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(400, 250);
        loginFrame.setLocationRelativeTo(null);
        
        WindowNav.setCloseOperation(loginFrame, () -> new MainMenuGUI());

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField phraseField = new JTextField();

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Security Phrase:"));
        panel.add(phraseField);

        JButton loginButton = new JButton("Login");
        panel.add(new JLabel());  // spacer
        panel.add(loginButton);

        loginFrame.add(panel);
        loginFrame.setVisible(true);

        loginButton.addActionListener((ActionEvent e) -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String phrase = phraseField.getText().trim();

            Manager m = authenticate(username, password, phrase);
            if (m != null) {
                loginAttempts = 0;
                loginFrame.dispose();
                ManagerLogin.Manager Manager = new ManagerLogin.Manager(
                    username,
                    password,
                    phrase
            );
                new ManagerDashboard(Manager);
                
                
                // showDashboard(m.getUsername());
            } else {
                loginAttempts++;
                if (loginAttempts >= MAX_ATTEMPTS) {
                    JOptionPane.showMessageDialog(loginFrame, "Account locked. Attempting recovery.");
                    attemptPasswordReset(username);
                    loginFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(loginFrame,
                            "Invalid credentials.\nAttempts remaining: " + (MAX_ATTEMPTS - loginAttempts),
                            "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private static Manager authenticate(String username, String password, String phrase) {
        for (Manager m : managerList) {
            if (m.getUsername().equals(username) &&
                    m.getPassword().equals(password) &&
                    m.getSecurityPhrase().equalsIgnoreCase(phrase)) {
                return m;
            }
        }
        return null;
    }

    private static void attemptPasswordReset(String username) {
        for (Manager m : managerList) {
            if (m.getUsername().equals(username)) {
                String input = JOptionPane.showInputDialog(null,
                        "Enter your security phrase to reset password:",
                        "Security Verification", JOptionPane.QUESTION_MESSAGE);

                if (input != null && input.equalsIgnoreCase(m.getSecurityPhrase())) {
                    changePassword(m);
                    return;
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Incorrect security phrase. Exiting.",
                            "Failed", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Username not found. Exiting.");
        System.exit(0);
    }

    private static void changePassword(Manager manager) {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        JPasswordField newPass = new JPasswordField();
        JPasswordField confirmPass = new JPasswordField();

        panel.add(new JLabel("New Password:"));
        panel.add(newPass);
        panel.add(new JLabel("Confirm Password:"));
        panel.add(confirmPass);

        int result = JOptionPane.showConfirmDialog(null, panel,
                "Change Password", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String pass1 = new String(newPass.getPassword());
            String pass2 = new String(confirmPass.getPassword());
            if (pass1.equals(pass2)) {
                manager.setPassword(pass1);
                saveManagersToFile(); // Save the updated password to file
                JOptionPane.showMessageDialog(null, "Password updated. Please log in again.");
                loginAttempts = 0;
                showLoginScreen();
            } else {
                JOptionPane.showMessageDialog(null, "Passwords do not match. Try again.");
                changePassword(manager);
            }
        } else {
            System.exit(0);
        }
    }

    private static void showDashboard(String username) {
        JFrame dashboard = new JFrame("Dashboard");
        dashboard.setSize(400, 200);
        dashboard.setLocationRelativeTo(null);
        dashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Welcome, " + username, JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        dashboard.add(label);

        dashboard.setVisible(true);
    }

    public static class Manager {
        private final String username;
        private String password;
        private final String securityPhrase;

        public Manager(String username, String password, String securityPhrase) {
            this.username = username;
            this.password = password;
            this.securityPhrase = securityPhrase;
        }

        public String getUsername() { return username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getSecurityPhrase() { return securityPhrase; }
    }
}