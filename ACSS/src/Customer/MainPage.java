package Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author YOON
 */
public class MainPage implements DashboardPage {
    private static JFrame mainFrame;
    
    @Override
    public JPanel createPage(Customer customer, JFrame frame) {
        JPanel mainPage = DashboardUIUtils.createBasicPagePanel("Welcome To ACSS, " + customer.getUsername() + "!", frame);
        JPanel contentPanel, accountPanel, headerPanel, fieldsPanel, emptyPanel, wrapperPanel;
        JLabel accountLabel, usernameLabel, emailLabel, passwordLabel, statusLabel, forgotPasswordLbl;
        JButton editButton, saveButton;
        JTextField usernameField, emailField;
        JPasswordField passwordField;

        // Get the content panel (which is at index 1 in BorderLayout.CENTER)
        contentPanel = (JPanel) ((BorderLayout) mainPage.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Page-specific content 
        accountPanel = new JPanel();
        accountPanel.setLayout(new BorderLayout(10, 10));
        accountPanel.setMaximumSize(new Dimension(600, 400));
        accountPanel.setPreferredSize(new Dimension(500, 330));
        accountPanel.setBackground(new Color(240, 240, 240));
        accountPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header -> Title + Button
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(accountPanel.getBackground());
        accountLabel = new JLabel("Account Information");
        accountLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(accountLabel, BorderLayout.WEST);

        editButton = new JButton("Edit Profile");
        editButton.setBackground(DashboardUIUtils.PRIMARY_COLOR); 
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        editButton.setFont(new Font("Arial", Font.BOLD, 14));
        editButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        headerPanel.add(editButton, BorderLayout.EAST);     

        // Field Section with fixed height rows
        fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(5, 2, 10, 20)); // Added an extra row for consistent spacing
        fieldsPanel.setBackground(accountPanel.getBackground());

        // Username field
        usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameField = new JTextField();
        usernameField.setText(customer.getUsername());
        usernameField.setEditable(false);

        // Email field
        emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 14));
        emailField = new JTextField();
        emailField.setText(customer.getEmail());
        emailField.setEditable(false);

        // Password field
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordField = new JPasswordField();
        passwordField.setText("***");
        passwordField.setEditable(false);
        
        // Account status
        statusLabel = new JLabel("Account Status:");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        String status = customer.isApproved() ? "Approved" : "Pending Approval";
        JLabel statusValueLabel = new JLabel(status);
        statusValueLabel.setForeground(customer.isApproved() ? new Color(0, 128, 0) : new Color(255, 140, 0));
        statusValueLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // Save button - Always in the layout but initially hidden
        saveButton = new JButton("Save");
        saveButton.setBackground(DashboardUIUtils.PRIMARY_COLOR);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        saveButton.setVisible(false);

        // Empty placeholder panel for the 5th row's first column
        emptyPanel = new JPanel();
        emptyPanel.setOpaque(false);

        // Add all components to the fields panel
        fieldsPanel.add(usernameLabel);
        fieldsPanel.add(usernameField);
        fieldsPanel.add(emailLabel);
        fieldsPanel.add(emailField);
        fieldsPanel.add(passwordLabel);
        fieldsPanel.add(passwordField);
        fieldsPanel.add(statusLabel);
        fieldsPanel.add(statusValueLabel);
        fieldsPanel.add(emptyPanel);
        fieldsPanel.add(saveButton);

        accountPanel.add(headerPanel, BorderLayout.NORTH);
        accountPanel.add(fieldsPanel, BorderLayout.CENTER);

        // Center the account panel in the content panel + Hide extra space 
        wrapperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapperPanel.setOpaque(false);
        wrapperPanel.add(accountPanel);
        
        // Add forgot password label
        forgotPasswordLbl = new JLabel("Add secuity question in case of forgotten password?");
        forgotPasswordLbl.setFont(new Font("Arial", Font.BOLD, 14));
        forgotPasswordLbl.setForeground(DashboardUIUtils.LIGHT_TEXT_COLOR);
        forgotPasswordLbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgotPasswordLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        forgotPasswordLbl.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        wrapperPanel.add(forgotPasswordLbl);
        contentPanel.add(wrapperPanel);

        // Add action listeners
        setupActionListeners(customer, mainPage, editButton, saveButton, usernameField, emailField, passwordField, forgotPasswordLbl);
        
        return mainPage;
    }
    
    private void setupActionListeners(Customer customer, JPanel mainPage, JButton editButton, JButton saveButton, 
                                    JTextField usernameField, JTextField emailField, JPasswordField passwordField,
                                    JLabel forgotPasswordLbl) {
                                      
        // Edit button action
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Toggle edit mode
                boolean editMode = !usernameField.isEditable();

                // Update UI based on edit mode
                usernameField.setEditable(editMode);
                emailField.setEditable(editMode);
                passwordField.setEditable(editMode);

                // If switching to edit mode, clear password field and show actual values
                if (editMode) {
                    usernameField.setText(customer.getUsername());
                    emailField.setText(customer.getEmail());
                    passwordField.setText("");

                    // Change edit button appearance
                    editButton.setBackground(new Color(100, 100, 100)); // Gray color

                    // Show the save button
                    saveButton.setVisible(true);
                } else {
                    // Revert to display mode
                    usernameField.setText(customer.getUsername());
                    emailField.setText(customer.getEmail());
                    passwordField.setText("***");

                    editButton.setBackground(DashboardUIUtils.PRIMARY_COLOR); 
                    saveButton.setVisible(false);
                }
            }
        });

        // Save button action
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the updated information
                String newUsername = usernameField.getText().trim();
                String newEmail = emailField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();

                // Validate all fields are not empty
                if (newUsername.isEmpty() || newEmail.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Username and email cannot be empty!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if username is valid format
                if (!CustomerDataValidator.isValidUsername(newUsername)) {
                    JOptionPane.showMessageDialog(null, "Username is not valid! (At least 3 characters. Must not contain any special characters such as commas, quotes, newlines except underscore)", "Invalid Username Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if email is valid format
                if (!CustomerDataValidator.isValidEmail(newEmail)) {
                    JOptionPane.showMessageDialog(null, "Email is not valid! Must not contain any special characters such as commas, quotes, newlines.", "Invalid Email Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if username is banned
                if (CustomerDataValidator.isUsernameBanned(newUsername)) {
                    JOptionPane.showMessageDialog(null, 
                            "This username has been banned by the administrator.\n" +
                            "Please contact support if you believe this was done in error\n" +
                            "or choose a different username.", 
                            "Username Banned", 
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if email is banned
                if (CustomerDataValidator.isEmailBanned(newEmail)) {
                    JOptionPane.showMessageDialog(null, 
                            "This email address has been banned by the administrator.\n" +
                            "Please contact support if you believe this was done in error\n" +
                            "or use a different email address.", 
                            "Email Banned", 
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check for duplicate username (only if username changed)
                if (!newUsername.equals(customer.getUsername())) {
                    if (CustomerDataIO.searchName(newUsername) != null) {
                        JOptionPane.showMessageDialog(null, "Username already taken!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                // Check for duplicate email (only if email changed)
                if (!newEmail.equals(customer.getEmail())) {
                    if (CustomerDataIO.searchEmail(newEmail) != null) {
                        JOptionPane.showMessageDialog(null, "Email already registered!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                // Validate password if it was changed
                if (!password.isEmpty()) {
                    if (!CustomerDataValidator.isValidPassword(password)) {
                        JOptionPane.showMessageDialog(null, "Passwords is not valid! Must not contain any special characters such as commas, quotes, newlines. (At least 8 characters containing one uppercase letter)", "Invalid Password Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                // All validations passed - save the updated information
                customer.setUsername(newUsername);
                customer.setEmail(newEmail);
                if (!password.isEmpty()) {
                    customer.setPassword(password);
                }

                CustomerDataIO.writeCustomer();
                JOptionPane.showMessageDialog(null,
                            "Account Information has been edited!\nNote: All values have been trimed to remove extra spaces at front and end!",
                            "Account edit Successful",
                            JOptionPane.INFORMATION_MESSAGE);

                // Return to display mode
                usernameField.setEditable(false);
                emailField.setEditable(false);
                passwordField.setEditable(false);
                usernameField.setText(customer.getUsername());
                emailField.setText(customer.getEmail());
                passwordField.setText("***");
                editButton.setBackground(DashboardUIUtils.PRIMARY_COLOR);
                saveButton.setVisible(false);

                // Update page title with new username
                JLabel titleLabel = (JLabel) ((JPanel) mainPage.getComponent(0)).getComponent(0);
                titleLabel.setText("Welcome To ACSS, " + customer.getUsername() + "!");
            }
        });
        
        // Forgot password label action
        forgotPasswordLbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                String customerId = customer.getCustomerId(); 

                String question = JOptionPane.showInputDialog(null, "Enter your security question:");
                if (question == null || question.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Security question cannot be empty.");
                    return;
                }

                String answer = JOptionPane.showInputDialog(null, "Enter your answer:");
                if (answer == null || answer.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Answer cannot be empty.");
                    return;
                }

                // No need to manually escape - the CustomersForgetPwd class handles CSV escaping
                CustomersForgetPwd customerForgetPwd = new CustomersForgetPwd(customerId, question, answer);

                if (CustomersForgetPwd.customerExists(customerId)) {
                    int option = JOptionPane.showConfirmDialog(null,
                        "You already have a saved security question. Do you want to override it?",
                        "Confirm Override",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                    if (option == JOptionPane.YES_OPTION) {
                        // User chose to override
                        boolean success = customerForgetPwd.overrideSecurityQuestion();
                        if (success) {
                            JOptionPane.showMessageDialog(null, "Security question updated successfully.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Error updating security question.");
                        }
                    } else {
                        // User chose NO or closed the dialog
                        JOptionPane.showMessageDialog(null, "Security question not changed.");
                    }
                } else {
                    // Customer ID does not exist, save new question
                    boolean success = customerForgetPwd.saveSecurityQuestion();
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Security question saved.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error saving security question.");
                    }
                }
            }
        });
    }
}