/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// SecurityQuestionDialog.java
package Salesman;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionListener;

public class SecurityQuestionDialog extends JDialog {

    private PasswordRecoveryHandler handler;
    private Salesman salesman;

    public SecurityQuestionDialog(JDialog parent, Salesman salesman, PasswordRecoveryHandler handler) {
        super(parent, "Security Question", true);
        this.salesman = salesman;
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
        JLabel questionLabel = new JLabel(salesman.getSecurityQuestion());
        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(questionLabel, gbc);

        JPasswordField answerField = new JPasswordField(20);
        panel.add(answerField, gbc);

        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(70, 130, 180));
        submitButton.setForeground(Color.WHITE);
        panel.add(submitButton, gbc);

        JLabel statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setForeground(Color.RED);
        panel.add(statusLabel, gbc);

        submitButton.addActionListener(e -> {
            String answer = new String(answerField.getPassword()).trim();
            handler.handleSubmit(salesman, answer, statusLabel, this);
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> handler.handleBack(this));
        panel.add(backButton, gbc);
    }
}
