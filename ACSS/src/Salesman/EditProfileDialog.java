/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// EditProfileDialog.java
package Salesman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EditProfileDialog extends JDialog {

    private final Salesman salesman;
    private final ProfileEditor editor;

    public EditProfileDialog(Salesman salesman) {
        this.salesman = salesman;
        this.editor = new ProfileEditor(salesman);
        initializeDialog();
    }

    private void initializeDialog() {
        setTitle("Edit Profile");
        setModal(true);
        setMinimumSize(new Dimension(450, 550));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Current Information", createInfoPanel());
        tabbedPane.addTab("Edit Information", editor.getEditPanel(this));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(this::saveChanges);

        JButton closeButton = new JButton("Go Back");
        closeButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(closeButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        // ... implementation similar to original ...
        return panel;
    }

    private void saveChanges(ActionEvent e) {
        if (editor.validateAndSave()) {
            JOptionPane.showMessageDialog(this, "Profile updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new SalesmanDashboard(salesman).setVisible(true);
        }
    }
}
