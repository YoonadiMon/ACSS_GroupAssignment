package Manager;

import Utils.ButtonStyler;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManagerDashboard extends JFrame implements ActionListener {
    private final ManagerLogin.Manager manager;
    private JPanel mainPanel, buttonPanel;
    private JLabel welcomeLabel;
    private JButton manageStaffSalesmanButton, manageCustomerButton, manageCarInventoryButton,
            paymentFeedbackButton, generateReportsButton, logoutButton;

    public ManagerDashboard(ManagerLogin.Manager manager) {
        this.manager = manager;

        setTitle("Manager Dashboard");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        initializeComponents();
        layoutComponents();
        setVisible(true);
    }

    private void initializeComponents() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Welcome label with better styling
        welcomeLabel = new JLabel("Welcome, " + manager.getUsername() + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(51, 102, 153));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));

        // Button panel with better spacing
        buttonPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Initialize buttons with better styling
        manageStaffSalesmanButton = createStyledButton("Staff & Salesman Mangement", new Color(70, 130, 180));
        manageCustomerButton = createStyledButton("Customer Management", new Color(60, 179, 113));
        manageCarInventoryButton = createStyledButton("Car Inventory Management", new Color(205, 92, 92));
        paymentFeedbackButton = createStyledButton("Payment & Feedback Analysis", new Color(255, 140, 0));
        generateReportsButton = createStyledButton("Reports", new Color(64, 224, 208));

        // Logout button with different styling
        logoutButton = createStyledButton("Log Out", new Color(220, 20, 60));
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));

        // Add action listeners
        manageStaffSalesmanButton.addActionListener(this);
        ButtonStyler.styleButton(manageStaffSalesmanButton);
        manageCustomerButton.addActionListener(this);
        ButtonStyler.styleButton(manageCustomerButton);
        manageCarInventoryButton.addActionListener(this);
        ButtonStyler.styleButton(manageCarInventoryButton);
        paymentFeedbackButton.addActionListener(this);
        ButtonStyler.styleButton(paymentFeedbackButton);
        generateReportsButton.addActionListener(this);
        ButtonStyler.styleButton(generateReportsButton);
        logoutButton.addActionListener(this);
        ButtonStyler.styleButton(logoutButton);
    }

    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        //button.setBorder(BorderFactory.createRaisedBorderBorder());
        button.setPreferredSize(new Dimension(200, 60));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.brighter());
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
            }
        });

        return button;
    }

    private void layoutComponents() {
        // Add welcome label to top
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);

        // Add buttons to center panel
        buttonPanel.add(manageStaffSalesmanButton);
        buttonPanel.add(manageCustomerButton);
        buttonPanel.add(manageCarInventoryButton);
        buttonPanel.add(paymentFeedbackButton);
        buttonPanel.add(generateReportsButton);

        // Add an empty label to fill the 6th grid space
        buttonPanel.add(new JLabel());

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Add logout button to bottom with padding
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoutPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        logoutPanel.add(logoutButton);
        mainPanel.add(logoutPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == manageStaffSalesmanButton) {
            openManageStaffSalesman();
        } else if (source == manageCustomerButton) {
            openManageCustomer();
        } else if (source == manageCarInventoryButton) {
            openManageCarInventory();
        } else if (source == paymentFeedbackButton) {
            openPaymentFeedback();
        } else if (source == generateReportsButton) {
            openGenerateReports();
        } else if (source == logoutButton) {
            handleLogout();
        }
    }

    private void openManageStaffSalesman() {
        JDialog dialog = createFeatureDialog("Staff & Salesman Management");
        JPanel content = new JPanel(new GridLayout(4, 1, 5, 5));
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        content.add(new JLabel("Staff & Salesman Management:", SwingConstants.CENTER));
        content.add(new JButton("Add Staff/Salesman"));
        content.add(new JButton("Delete Staff/Salesman"));
        content.add(new JButton("Update Staff/Salesman"));
        content.add(new JButton("Assign Car"));
        content.add(new JButton("List All"));

        dialog.add(content);
        dialog.setVisible(true);
    }

    private void openManageCustomer() {
        JDialog dialog = createFeatureDialog("Customer Management");
        JPanel content = new JPanel(new GridLayout(4, 1, 5, 5));
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        content.add(new JLabel("Customer Management:", SwingConstants.CENTER));
        content.add(new JButton("Approve Customer"));
        content.add(new JButton("Delete Customer"));
        content.add(new JButton("Update Customer"));
        content.add(new JButton("Customer List"));

        dialog.add(content);
        dialog.setVisible(true);
    }

    private void openManageCarInventory() {
        JDialog dialog = createFeatureDialog("Car Inventory Management");
        JPanel content = new JPanel(new GridLayout(5, 1, 5, 5));
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        content.add(new JLabel("Car Inventory Management:", SwingConstants.CENTER));
        content.add(new JButton("Edit Car Details"));
        content.add(new JButton("Search Car Details"));
        content.add(new JButton("List All Cars"));

        dialog.add(content);
        dialog.setVisible(true);
    }

    private void openPaymentFeedback() {
        JDialog dialog = new JDialog(this, "Payment & Feedback Analysis", true);
        dialog.setSize(700, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(true);

        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Payment tab
        JPanel paymentPanel = createPaymentTab();
        tabbedPane.addTab("Payment Analysis", paymentPanel);

        // Feedback tab
        JPanel feedbackPanel = createFeedbackTab();
        tabbedPane.addTab("Feedback Analysis", feedbackPanel);

        dialog.add(tabbedPane);
        dialog.setVisible(true);
    }

    private JPanel createPaymentTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Payment Analysis", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(255, 140, 0));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Subtabs for payment
        JTabbedPane paymentSubTabs = new JTabbedPane();

        // List Payments subtab
        JPanel listPaymentsPanel = new JPanel(new BorderLayout());
        JTextArea paymentListArea = new JTextArea();
        paymentListArea.setEditable(false);
        paymentListArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // Load payment data from file
        loadPaymentData(paymentListArea);

        JScrollPane paymentScrollPane = new JScrollPane(paymentListArea);
        listPaymentsPanel.add(paymentScrollPane, BorderLayout.CENTER);

        JPanel paymentButtonPanel = new JPanel(new FlowLayout());
        JButton refreshPaymentsBtn = new JButton("Refresh List");
        refreshPaymentsBtn.addActionListener(e -> loadPaymentData(paymentListArea));
        paymentButtonPanel.add(refreshPaymentsBtn);
        paymentButtonPanel.add(new JButton("Export to CSV"));
        paymentButtonPanel.add(new JButton("Filter Payments"));
        listPaymentsPanel.add(paymentButtonPanel, BorderLayout.SOUTH);

        paymentSubTabs.addTab("List Payments", listPaymentsPanel);

        // Analyze Payments subtab
        JPanel analyzePaymentsPanel = createPaymentAnalysisPanel();
        paymentSubTabs.addTab("Analyze Payments", analyzePaymentsPanel);

        panel.add(paymentSubTabs, BorderLayout.CENTER);
        return panel;
    }

    private void loadPaymentData(JTextArea textArea) {
        try {
            StringBuilder content = new StringBuilder();
            content.append(String.format("%-12s %-20s %-12s %-12s %-12s\n",
                    "Payment ID", "Customer", "Amount", "Date", "Status"));
            content.append("------------|--------------------|-----------|-----------|-----------\n");

            BufferedReader reader = new BufferedReader(new FileReader("data/salesList.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 5) {
                        content.append(String.format("%-12s %-20s %-12s %-12s %-12s\n",
                                parts[0].trim(), parts[1].trim(), parts[2].trim(),
                                parts[3].trim(), parts[4].trim()));
                    }
                }
            }
            reader.close();
            textArea.setText(content.toString());

        } catch (IOException e) {
            textArea.setText("Error loading payment data: " + e.getMessage() + "\n\n" +
                    "Sample Data (File not found):\n" +
                    String.format("%-12s %-20s %-12s %-12s %-12s\n",
                            "Payment ID", "Customer", "Amount", "Date", "Status") +
                    "------------|--------------------|-----------|-----------|-----------\n" +
                    String.format("%-12s %-20s %-12s %-12s %-12s\n",
                            "PAY001", "John Doe", "$25,000", "2024-01-15", "Completed") +
                    String.format("%-12s %-20s %-12s %-12s %-12s\n",
                            "PAY002", "Jane Smith", "$30,000", "2024-01-16", "Pending") +
                    String.format("%-12s %-20s %-12s %-12s %-12s\n",
                            "PAY003", "Bob Wilson", "$22,500", "2024-01-17", "Completed"));
        }
    }

    private JPanel createPaymentAnalysisPanel() {
        JPanel analyzeContainer = new JPanel(new BorderLayout());

        // Analysis data panel
        JPanel analyzePaymentsPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        analyzePaymentsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Load and analyze payment data
        PaymentAnalytics analytics = analyzePaymentData();

        analyzePaymentsPanel.add(new JLabel("Total Payments:"));
        analyzePaymentsPanel.add(new JLabel(String.valueOf(analytics.totalPayments)));
        analyzePaymentsPanel.add(new JLabel("Total Amount:"));
        analyzePaymentsPanel.add(new JLabel(String.format("$%.2f", analytics.totalAmount)));
        analyzePaymentsPanel.add(new JLabel("Average Payment:"));
        analyzePaymentsPanel.add(new JLabel(String.format("$%.2f", analytics.averageAmount)));
        analyzePaymentsPanel.add(new JLabel("Completed Payments:"));
        analyzePaymentsPanel.add(new JLabel(String.format("%d (%.1f%%)",
                analytics.completedPayments, analytics.completionRate)));

        JPanel analyticsButtonPanel = new JPanel(new FlowLayout());
        JButton refreshAnalyticsBtn = new JButton("Refresh Analysis");
        refreshAnalyticsBtn.addActionListener(e -> {
            PaymentAnalytics newAnalytics = analyzePaymentData();
            updatePaymentAnalysisLabels(analyzePaymentsPanel, newAnalytics);
        });
        analyticsButtonPanel.add(refreshAnalyticsBtn);
        analyticsButtonPanel.add(new JButton("Generate Payment Report"));
        analyticsButtonPanel.add(new JButton("View Payment Trends"));

        analyzeContainer.add(analyzePaymentsPanel, BorderLayout.CENTER);
        analyzeContainer.add(analyticsButtonPanel, BorderLayout.SOUTH);

        return analyzeContainer;
    }

    private PaymentAnalytics analyzePaymentData() {
        PaymentAnalytics analytics = new PaymentAnalytics();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("data/salesList.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 5) {
                        analytics.totalPayments++;

                        // Parse amount (remove $ and commas)
                        String amountStr = parts[2].trim().replaceAll("[$,]", "");
                        try {
                            double amount = Double.parseDouble(amountStr);
                            analytics.totalAmount += amount;
                        } catch (NumberFormatException e) {
                            // Skip invalid amounts
                        }

                        // Check if completed
                        if (parts[4].trim().equalsIgnoreCase("completed")) {
                            analytics.completedPayments++;
                        }
                    }
                }
            }
            reader.close();

            analytics.averageAmount = analytics.totalPayments > 0 ?
                    analytics.totalAmount / analytics.totalPayments : 0;
            analytics.completionRate = analytics.totalPayments > 0 ?
                    (analytics.completedPayments * 100.0) / analytics.totalPayments : 0;

        } catch (IOException e) {
            // Use sample data if file not found
            analytics.totalPayments = 4;
            analytics.totalAmount = 112500;
            analytics.averageAmount = 28125;
            analytics.completedPayments = 3;
            analytics.completionRate = 75.0;
        }

        return analytics;
    }

    private void updatePaymentAnalysisLabels(JPanel panel, PaymentAnalytics analytics) {
        Component[] components = panel.getComponents();
        if (components.length >= 8) {
            ((JLabel)components[1]).setText(String.valueOf(analytics.totalPayments));
            ((JLabel)components[3]).setText(String.format("$%.2f", analytics.totalAmount));
            ((JLabel)components[5]).setText(String.format("$%.2f", analytics.averageAmount));
            ((JLabel)components[7]).setText(String.format("%d (%.1f%%)",
                    analytics.completedPayments, analytics.completionRate));
        }
        panel.revalidate();
        panel.repaint();
    }

    private static class PaymentAnalytics {
        int totalPayments = 0;
        double totalAmount = 0;
        double averageAmount = 0;
        int completedPayments = 0;
        double completionRate = 0;
    }

    private JPanel createFeedbackTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Feedback Analysis", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(147, 112, 219));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Subtabs for feedback
        JTabbedPane feedbackSubTabs = new JTabbedPane();

        // List Feedbacks subtab
        JPanel listFeedbacksPanel = new JPanel(new BorderLayout());
        JTextArea feedbackListArea = new JTextArea();
        feedbackListArea.setEditable(false);
        feedbackListArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // Load feedback data from file
        loadFeedbackData(feedbackListArea);

        JScrollPane feedbackScrollPane = new JScrollPane(feedbackListArea);
        listFeedbacksPanel.add(feedbackScrollPane, BorderLayout.CENTER);

        JPanel feedbackButtonPanel = new JPanel(new FlowLayout());
        JButton refreshFeedbackBtn = new JButton("Refresh List");
        refreshFeedbackBtn.addActionListener(e -> loadFeedbackData(feedbackListArea));
        feedbackButtonPanel.add(refreshFeedbackBtn);
        feedbackButtonPanel.add(new JButton("Export to CSV"));
        feedbackButtonPanel.add(new JButton("Filter by Rating"));
        listFeedbacksPanel.add(feedbackButtonPanel, BorderLayout.SOUTH);

        feedbackSubTabs.addTab("List Feedbacks", listFeedbacksPanel);

        // Analyze Feedbacks subtab
        JPanel analyzeFeedbacksPanel = createFeedbackAnalysisPanel();
        feedbackSubTabs.addTab("Analyze Feedbacks", analyzeFeedbacksPanel);

        panel.add(feedbackSubTabs, BorderLayout.CENTER);
        return panel;
    }

    private void loadFeedbackData(JTextArea textArea) {
        try {
            StringBuilder content = new StringBuilder();
            content.append(String.format("%-12s %-20s %-8s %-12s %-30s\n",
                    "Feedback ID", "Customer", "Rating", "Date", "Comment"));
            content.append("------------|--------------------|---------|-----------|-----------------------\n");

            BufferedReader reader = new BufferedReader(new FileReader("data/customerFeedbacks.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 5) {
                        String comment = parts[4].trim();
                        if (comment.length() > 28) {
                            comment = comment.substring(0, 25) + "...";
                        }
                        content.append(String.format("%-12s %-20s %-8s %-12s %-30s\n",
                                parts[0].trim(), parts[1].trim(), parts[2].trim(),
                                parts[3].trim(), comment));
                    }
                }
            }
            reader.close();
            textArea.setText(content.toString());

        } catch (IOException e) {
            textArea.setText("Error loading feedback data: " + e.getMessage() + "\n\n" +
                    "Sample Data (File not found):\n" +
                    String.format("%-12s %-20s %-8s %-12s %-30s\n",
                            "Feedback ID", "Customer", "Rating", "Date", "Comment") +
                    "------------|--------------------|---------|-----------|-----------------------\n" +
                    String.format("%-12s %-20s %-8s %-12s %-30s\n",
                            "FB001", "John Doe", "5/5", "2024-01-15", "Excellent service!") +
                    String.format("%-12s %-20s %-8s %-12s %-30s\n",
                            "FB002", "Jane Smith", "4/5", "2024-01-16", "Good experience overall") +
                    String.format("%-12s %-20s %-8s %-12s %-30s\n",
                            "FB003", "Bob Wilson", "3/5", "2024-01-17", "Average service"));
        }
    }

    private JPanel createFeedbackAnalysisPanel() {
        JPanel analyzeContainer = new JPanel(new BorderLayout());

        // Analysis data panel
        JPanel analyzeFeedbacksPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        analyzeFeedbacksPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Load and analyze feedback data
        FeedbackAnalytics analytics = analyzeFeedbackData();

        analyzeFeedbacksPanel.add(new JLabel("Total Feedbacks:"));
        analyzeFeedbacksPanel.add(new JLabel(String.valueOf(analytics.totalFeedbacks)));
        analyzeFeedbacksPanel.add(new JLabel("Average Rating:"));
        analyzeFeedbacksPanel.add(new JLabel(String.format("%.2f/5", analytics.averageRating)));
        analyzeFeedbacksPanel.add(new JLabel("Positive Feedback Rate:"));
        analyzeFeedbacksPanel.add(new JLabel(String.format("%.1f%%", analytics.positiveRate)));
        analyzeFeedbacksPanel.add(new JLabel("5-Star Ratings:"));
        analyzeFeedbacksPanel.add(new JLabel(String.format("%d (%.1f%%)",
                analytics.fiveStarCount, analytics.fiveStarRate)));

        JPanel feedbackAnalyticsButtonPanel = new JPanel(new FlowLayout());
        JButton refreshFeedbackAnalyticsBtn = new JButton("Refresh Analysis");
        refreshFeedbackAnalyticsBtn.addActionListener(e -> {
            FeedbackAnalytics newAnalytics = analyzeFeedbackData();
            updateFeedbackAnalysisLabels(analyzeFeedbacksPanel, newAnalytics);
        });
        feedbackAnalyticsButtonPanel.add(refreshFeedbackAnalyticsBtn);
        feedbackAnalyticsButtonPanel.add(new JButton("Generate Feedback Report"));
        feedbackAnalyticsButtonPanel.add(new JButton("Sentiment Analysis"));

        analyzeContainer.add(analyzeFeedbacksPanel, BorderLayout.CENTER);
        analyzeContainer.add(feedbackAnalyticsButtonPanel, BorderLayout.SOUTH);

        return analyzeContainer;
    }

    private FeedbackAnalytics analyzeFeedbackData() {
        FeedbackAnalytics analytics = new FeedbackAnalytics();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("data/customerFeedbacks.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 5) {
                        analytics.totalFeedbacks++;

                        // Parse rating (extract number from "X/5" format)
                        String ratingStr = parts[2].trim();
                        try {
                            if (ratingStr.contains("/")) {
                                String[] ratingParts = ratingStr.split("/");
                                int rating = Integer.parseInt(ratingParts[0].trim());
                                analytics.totalRatingPoints += rating;

                                if (rating >= 4) {
                                    analytics.positiveCount++;
                                }
                                if (rating == 5) {
                                    analytics.fiveStarCount++;
                                }
                            }
                        } catch (NumberFormatException e) {
                            // Skip invalid ratings
                        }
                    }
                }
            }
            reader.close();

            analytics.averageRating = analytics.totalFeedbacks > 0 ?
                    (double)analytics.totalRatingPoints / analytics.totalFeedbacks : 0;
            analytics.positiveRate = analytics.totalFeedbacks > 0 ?
                    (analytics.positiveCount * 100.0) / analytics.totalFeedbacks : 0;
            analytics.fiveStarRate = analytics.totalFeedbacks > 0 ?
                    (analytics.fiveStarCount * 100.0) / analytics.totalFeedbacks : 0;

        } catch (IOException e) {
            // Use sample data if file not found
            analytics.totalFeedbacks = 4;
            analytics.totalRatingPoints = 17;
            analytics.averageRating = 4.25;
            analytics.positiveCount = 3;
            analytics.positiveRate = 75.0;
            analytics.fiveStarCount = 2;
            analytics.fiveStarRate = 50.0;
        }

        return analytics;
    }

    private void updateFeedbackAnalysisLabels(JPanel panel, FeedbackAnalytics analytics) {
        Component[] components = panel.getComponents();
        if (components.length >= 8) {
            ((JLabel)components[1]).setText(String.valueOf(analytics.totalFeedbacks));
            ((JLabel)components[3]).setText(String.format("%.2f/5", analytics.averageRating));
            ((JLabel)components[5]).setText(String.format("%.1f%%", analytics.positiveRate));
            ((JLabel)components[7]).setText(String.format("%d (%.1f%%)",
                    analytics.fiveStarCount, analytics.fiveStarRate));
        }
        panel.revalidate();
        panel.repaint();
    }

    private static class FeedbackAnalytics {
        int totalFeedbacks = 0;
        int totalRatingPoints = 0;
        double averageRating = 0;
        int positiveCount = 0;
        double positiveRate = 0;
        int fiveStarCount = 0;
        double fiveStarRate = 0;
    }

    private void openGenerateReports() {
        JDialog dialog = createFeatureDialog("Reports");
        JPanel content = new JPanel(new GridLayout(5, 1, 5, 5));
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        content.add(new JLabel("Reports:", SwingConstants.CENTER));
        content.add(new JButton("Sales Reports"));
        content.add(new JButton("Inventory Reports"));
        content.add(new JButton("Salesman Reports"));
        content.add(new JButton("Customer Reports"));
        content.add(new JButton("All Reports"));

        dialog.add(content);
        dialog.setVisible(true);
    }

    private JDialog createFeatureDialog(String title) {
        JDialog dialog = new JDialog(this, title, true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        return dialog;
    }

    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            // Return to login screen
            SwingUtilities.invokeLater(() -> {
                try {
                    new ManagerLogin();
                } catch (Exception ex) {
                    // If ManagerLogin class is not available, show a message
                    JOptionPane.showMessageDialog(null,
                            "Logged out successfully!\nApplication will now exit.",
                            "Logout",
                            JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // For testing purposes only
            ManagerLogin.Manager testManager = new ManagerLogin.Manager(
                    "manager1",
                    "12345",
                    "rainbow"
            );
            new ManagerDashboard(testManager);
        });
    }
}