/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Customer;

import Utils.ButtonStyler;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author DELL
 */
public class DashboardUIUtils {
    
    // Colors
    public static final Color PRIMARY_COLOR = new Color(0, 84, 159);
    public static final Color LIGHT_TEXT_COLOR = new Color(111, 143, 175);
    public static final Color BACKGROUND_COLOR = Color.WHITE;
    
    public static JPanel createBasicPagePanel(String title, JFrame frame) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        
        // Title panel at the top
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(BACKGROUND_COLOR);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        titlePanel.add(titleLabel);
        
        // Content panel in the center
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        
        // Create a logout button panel (fixed at bottom right)
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.setBackground(BACKGROUND_COLOR);
        JButton logoutButton = new JButton("Logout");
        ButtonStyler.styleExitButton(logoutButton);
        logoutButton.addActionListener(e->{
            frame.dispose();
            new CustomerLandingGUI();      
        });
        logoutPanel.add(logoutButton);
        
        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);
        panel.add(logoutPanel, BorderLayout.SOUTH);
        
        return panel;
    }
}