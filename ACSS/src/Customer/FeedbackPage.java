/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Customer;

import Car.CarRequest;
import Car.SoldCarRecord;
import Salesman.SalesmanList;
import Utils.ButtonStyler;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author DELL
 */
public class FeedbackPage implements DashboardPage {
    
    // Reference to store the customer for use in the createCarBox method
    private Customer customer;
    
    @Override
    public JPanel createPage(Customer customer, JFrame frame) {
        // Get customer's bookings and purchases
        ArrayList<CarRequest> bookings = CarRequest.getRequestsByCustomerID(customer.getCustomerId());
        List<SoldCarRecord> purchases = SoldCarRecord.findByCustomerID(customer.getCustomerId());

        // Extract booking information (cars they looked at but haven't purchased)
        List<String> bookedCarIDs = new ArrayList<>();
        Map<String, String> bookedCarSalesmanMap = new HashMap<>(); // Maps carID to salesmanID

        for (CarRequest booking : bookings) {
            String carID = booking.getCarID();
            String salesmanID = booking.getSalesmanID();

            bookedCarIDs.add(carID);
            bookedCarSalesmanMap.put(carID, salesmanID);
        }

        // Extract purchase information (cars they bought)
        List<String> purchasedCarIDs = new ArrayList<>();
        Map<String, String> purchasedCarSalesmanMap = new HashMap<>(); // Maps carID to salesmanID

        for (SoldCarRecord purchase : purchases) {
            String carID = purchase.getCarID();
            String salesmanID = purchase.getSalesmanID();

            purchasedCarIDs.add(carID);
            purchasedCarSalesmanMap.put(carID, salesmanID);
        }

        List<String> onlyBookedCarIDs = new ArrayList<>(bookedCarIDs);
        onlyBookedCarIDs.removeAll(purchasedCarIDs);

        JPanel feedbackPage = DashboardUIUtils.createBasicPagePanel("Thank You for Your Feedback", frame);

        // Create main content panel with consistent padding
        JPanel contentPanel = new JPanel(new BorderLayout(15, 15));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create salesmen panel with consistent alignment
        JPanel salesmanPanel = new JPanel();
        salesmanPanel.setLayout(new BoxLayout(salesmanPanel, BoxLayout.Y_AXIS));
        salesmanPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 0, 128), 1), "Salesmen"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Get all unique salesmen from both bookings and purchases
        Set<String> allSalesmenIDs = new HashSet<>();
        allSalesmenIDs.addAll(bookedCarSalesmanMap.values());
        allSalesmenIDs.addAll(purchasedCarSalesmanMap.values());

        // Add salesmen to panel with consistent style
        for (String salesmanID : allSalesmenIDs) {
            // Get salesman name instead of just showing ID
            String salesmanName = SalesmanList.getSalesmanNameById(salesmanID);

            // Create a panel for each salesman with better alignment
            JPanel salesmanItemPanel = new JPanel();
            salesmanItemPanel.setLayout(new BoxLayout(salesmanItemPanel, BoxLayout.X_AXIS));
            salesmanItemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            salesmanItemPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

            JLabel salesmanLabel = new JLabel("Salesman: " + salesmanName);
            salesmanLabel.setPreferredSize(new Dimension(150, 25));

            // Add feedback button with consistent size
            JButton feedbackBtn = new JButton("Give Feedback");
            feedbackBtn.setPreferredSize(new Dimension(130, 25));
            feedbackBtn.addActionListener(e -> {
                showSimpleFeedbackDialog(
                        "Salesman: " + salesmanName,
                        CustomerFeedbacks.TYPE_SALESMAN, 
                        salesmanID);
            });

            salesmanItemPanel.add(salesmanLabel);
            salesmanItemPanel.add(Box.createHorizontalStrut(10)); // Add consistent spacing
            salesmanItemPanel.add(feedbackBtn);
            salesmanItemPanel.add(Box.createHorizontalGlue()); // Push components to the left
            salesmanPanel.add(salesmanItemPanel);
            salesmanPanel.add(Box.createVerticalStrut(10)); // Consistent vertical spacing
        }

        // Create cars panel with matching style
        JPanel carPanel = new JPanel();
        carPanel.setLayout(new BoxLayout(carPanel, BoxLayout.Y_AXIS));
        carPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 0, 128), 1), "Cars"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Add purchased cars (labeled as "Purchased") with consistent styling
        if (!purchasedCarIDs.isEmpty()) {
            JLabel purchasedHeader = new JLabel("Purchased Cars:");
            purchasedHeader.setFont(purchasedHeader.getFont().deriveFont(Font.BOLD));
            purchasedHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
            carPanel.add(purchasedHeader);
            carPanel.add(Box.createVerticalStrut(8));

            for (String carID : purchasedCarIDs) {
                JPanel carItemPanel = new JPanel();
                carItemPanel.setLayout(new BoxLayout(carItemPanel, BoxLayout.X_AXIS));
                carItemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                carItemPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

                JLabel carLabel = new JLabel("• Car ID: " + carID + " (Purchased)");
                carLabel.setForeground(new Color(0, 128, 0)); // Green color for purchased
                carLabel.setPreferredSize(new Dimension(200, 25));

                // Add feedback button with consistent size
                JButton feedbackBtn = new JButton("Give Feedback");
                feedbackBtn.setPreferredSize(new Dimension(130, 25));
                feedbackBtn.addActionListener(e -> {
                    showSimpleFeedbackDialog(
                            "Purchased Car: " + carID,
                            CustomerFeedbacks.TYPE_CAR_PURCHASED, 
                            carID);
                });

                carItemPanel.add(carLabel);
                carItemPanel.add(Box.createHorizontalStrut(10)); // Consistent spacing
                carItemPanel.add(feedbackBtn);
                carItemPanel.add(Box.createHorizontalGlue()); // Push components to the left
                carPanel.add(carItemPanel);
                carPanel.add(Box.createVerticalStrut(5));
            }
            carPanel.add(Box.createVerticalStrut(10));
        }

        // Add viewed cars (only booked, not purchased) with matching styling
        if (!onlyBookedCarIDs.isEmpty()) {
            JLabel viewedHeader = new JLabel("Viewed Cars:");
            viewedHeader.setFont(viewedHeader.getFont().deriveFont(Font.BOLD));
            viewedHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
            carPanel.add(viewedHeader);
            carPanel.add(Box.createVerticalStrut(8));

            for (String carID : onlyBookedCarIDs) {
                JPanel carItemPanel = new JPanel();
                carItemPanel.setLayout(new BoxLayout(carItemPanel, BoxLayout.X_AXIS));
                carItemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                carItemPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

                JLabel carLabel = new JLabel("• Car ID: " + carID + " (Viewed)");
                carLabel.setForeground(new Color(128, 128, 128)); // Gray color for viewed only
                carLabel.setPreferredSize(new Dimension(200, 25));

                // Add feedback button with consistent size
                JButton feedbackBtn = new JButton("Give Feedback");
                feedbackBtn.setPreferredSize(new Dimension(130, 25));
                feedbackBtn.addActionListener(e -> {
                    showSimpleFeedbackDialog(
                            "Viewed Car: " + carID,
                            CustomerFeedbacks.TYPE_CAR_VIEWED, 
                            carID);
                });

                carItemPanel.add(carLabel);
                carItemPanel.add(Box.createHorizontalStrut(10)); // Consistent spacing
                carItemPanel.add(feedbackBtn);
                carItemPanel.add(Box.createHorizontalGlue()); // Push components to the left
                carPanel.add(carItemPanel);
                carPanel.add(Box.createVerticalStrut(5));
            }
        }

        // If no cars at all
        if (purchasedCarIDs.isEmpty() && onlyBookedCarIDs.isEmpty()) {
            JLabel noInteractionLabel = new JLabel("No car interactions found.");
            noInteractionLabel.setForeground(Color.GRAY);
            noInteractionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            carPanel.add(noInteractionLabel);
        }

        // Create a panel to hold both salesman and car panels side by side with better spacing
        JPanel infoPanel = new JPanel(new GridLayout(1, 2, 20, 0)); // Increased horizontal gap

        // Use scroll panes with consistent behavior
        JScrollPane salesmanScrollPane = new JScrollPane(salesmanPanel);
        salesmanScrollPane.setBorder(BorderFactory.createEmptyBorder());
        salesmanScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JScrollPane carScrollPane = new JScrollPane(carPanel);
        carScrollPane.setBorder(BorderFactory.createEmptyBorder());
        carScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        infoPanel.add(salesmanScrollPane);
        infoPanel.add(carScrollPane);

        // Add the info panel to content panel
        contentPanel.add(infoPanel, BorderLayout.CENTER);
        
        // Create a refresh button panel
        JPanel refreshPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton refreshButton = new JButton("Refresh Data");
        ButtonStyler.stylePrimaryButton(refreshButton);
        refreshButton.setPreferredSize(new Dimension(120, 30));
        // No icon - removed to fix NullPointerException
        refreshButton.addActionListener(e -> refreshFeedbackPage(frame));
        refreshPanel.add(refreshButton);
        
        // Add the refresh panel to the top of the content panel
        contentPanel.add(refreshPanel, BorderLayout.NORTH);

        feedbackPage.add(contentPanel, BorderLayout.CENTER);
    }
    
    private void refreshFeedbackPage(JFrame frame) {
        frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        try {
            // Remove and recreate the feedback page
            cards.remove(feedbackPage);
            createFeedbackPage();
            cards.add(feedbackPage, "Feedbacks");  // Match the original identifier "Feedbacks"
            cardLayout.show(cards, "Feedbacks");   // Match the original identifier "Feedbacks"

            // Update navigation button state
            for (int i = 0; i < navButtons.length; i++) {
                if (navButtons[i].getActionCommand().equals("Feedbacks")) {  // Match the original identifier "Feedbacks"
                    updateNavButtonsState(i);
                    break;
                }
            }

            // Revalidate and repaint to ensure UI updates
            cards.revalidate();
            cards.repaint();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, 
                "Error refreshing feedback data: " + ex.getMessage(), 
                "Refresh Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // For debugging
        } finally {
            // Reset cursor
            frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    private void showSimpleFeedbackDialog(String itemName, String feedbackType, String itemId) {
        // Show simple rating dialog
        String ratingInput = JOptionPane.showInputDialog(
                feedbackPage,
                "Please rate " + itemName + " (1-5 stars):",
                "Rating",
                JOptionPane.QUESTION_MESSAGE);

        // Check if user canceled the rating dialog
        if (ratingInput == null || ratingInput.trim().isEmpty()) {
            return;
        }

        // Parse rating
        int rating;
        try {
            rating = Integer.parseInt(ratingInput.trim());
            if (rating < 1 || rating > 5) {
                JOptionPane.showMessageDialog(
                    feedbackPage,
                    "Please enter a rating between 1 and 5.",
                    "Invalid Rating",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                feedbackPage,
                "Please enter a valid number between 1 and 5.",
                "Invalid Rating",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Show review dialog
        String review = JOptionPane.showInputDialog(
                feedbackPage,
                "Please share your feedback about " + itemName + ":",
                "Feedback",
                JOptionPane.PLAIN_MESSAGE);

        // If user cancels review, use empty string
        if (review == null) {
            review = "";
        }

        // Save feedback
        CustomerFeedbacks feedback = new CustomerFeedbacks(
                customer.getCustomerId(),
                itemId,
                feedbackType,
                rating,
                review
        );

        boolean success = feedback.saveFeedback();

        if (success) {
            JOptionPane.showMessageDialog(
                    feedbackPage,
                    "Thank you for your feedback!",
                    "Feedback Submitted",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(
                    feedbackPage,
                    "There was an error saving your feedback. Please try again.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
