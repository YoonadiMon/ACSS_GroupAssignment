/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// DashboardController.java
package Salesman;

import javax.swing.*;

public class DashboardController {

    private final SalesmanDashboard dashboard;
    private final Salesman salesman;

    public DashboardController(SalesmanDashboard dashboard, Salesman salesman) {
        this.dashboard = dashboard;
        this.salesman = salesman;
    }

    public void handleAction(String actionCommand) {
        dashboard.dispose();

        switch (actionCommand) {
            case "Edit Profile":
                new EditProfileDialog(salesman).setVisible(true);
                break;
            case "View Car Status":
                // Implementation for car status view
                break;
            case "View Car Requests":
                // Implementation for car requests
                break;
            case "Update Car Status":
                // Implementation for status update
                break;
            case "View Sales History":
                // Implementation for sales history
                break;
            case "Mark Car as Paid":
                // Implementation for marking as paid
                break;
            case "Logout":
                new SalesmanGUI(400, 250);
                break;
            default:
                JOptionPane.showMessageDialog(dashboard, "Unknown action: " + actionCommand);
                new SalesmanDashboard(salesman).setVisible(true);
        }
    }
}
