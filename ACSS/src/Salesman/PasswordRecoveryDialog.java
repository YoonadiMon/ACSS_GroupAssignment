/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// PasswordRecoveryDialog.java - Handles password recovery flow
package Salesman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PasswordRecoveryDialog extends JDialog {

    private PasswordRecoveryHandler handler;

    public PasswordRecoveryDialog(JFrame parent, PasswordRecoveryHandler handler) {
        super(parent, "Forgot Password", true);
        this.handler = handler;
        initializeUI();
    }

    private void initializeUI() {
        setSize(400, 250);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        addComponents(panel, gbc);
        add(panel, BorderLayout.CENTER);
    }

    private void addComponents(JPanel panel, GridBagConstraints gbc) {
        JLabel titleLabel = new JLabel("Password Recovery", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        panel.add(titleLabel, gbc);

        JLabel idLabel = new JLabel("Enter your Salesman ID:");
        panel.add(idLabel, gbc);

        JTextField idField = new JTextField(20);
        panel.add(idField, gbc);

        JButton nextButton = new JButton("Next");
        nextButton.setBackground(new Color(70, 130, 180));
        nextButton.setForeground(Color.WHITE);
        panel.add(nextButton, gbc);

        JLabel statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setForeground(Color.RED);
        panel.add(statusLabel, gbc);

        nextButton.addActionListener(e -> {
            String id = idField.getText().trim();
            handler.handleNext(id, statusLabel, this);
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> handler.handleCancel(this));
        panel.add(cancelButton, gbc);
    }
}

