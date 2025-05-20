/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Customer;

import javax.swing.*;

/**
 *
 * @author DELL
 */
public interface DashboardPage {
    /**
     * Creates and returns the page panel
     * @param customer The customer data
     * @return The configured JPanel for this page
     */
    JPanel createPage(Customer customer, JFrame frame);
}