package Manager;

import Car.SoldCarRecord;
import Customer.CustomerFeedbacks;
import Car.SalesRecords;
import Utils.ButtonStyler;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import Car.CarList;
import Car.Car;
import java.util.Map;
import java.util.HashMap;



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
        manageStaffSalesmanButton = createStyledButton("Staff & Salesman Management", new Color(70, 130, 180));
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
            setVisible(false);
            new ManageStaffSalesman(manager);
        } else if (source == manageCustomerButton) {
            setVisible(false);
            new ManageCustomer(manager);
        } else if (source == manageCarInventoryButton) {
            setVisible(false);
            new ManageCarInventory(manager);
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

        content.add(new JLabel("Salesman Management:", SwingConstants.CENTER));
        content.add(new JButton("Add Salesman"));
        content.add(new JButton("Delete Salesman"));
        content.add(new JButton("Update Salesman"));
        content.add(new JButton("Assign Car"));
        content.add(new JButton("List All"));

        dialog.add(content);
        dialog.setVisible(true);
    }

//    private void openManageCustomer() {
//        JDialog dialog = createFeatureDialog("Customer Management");
//        JPanel content = new JPanel(new GridLayout(4, 1, 5, 5));
//        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
//
//        content.add(new JLabel("Customer Management:", SwingConstants.CENTER));
//        content.add(new JButton("Approve Customer"));
//        content.add(new JButton("Delete Customer"));
//        content.add(new JButton("Update Customer"));
//        content.add(new JButton("Customer List"));
//
//        dialog.add(content);
//        dialog.setVisible(true);
//    }

    private void openPaymentFeedback() {
        JDialog dialog = new JDialog(this, "Payment & Feedback Analysis", true);
        dialog.setSize(700, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(true);

        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Add tabs
        tabbedPane.addTab("Payment Analysis", createPaymentTab());
        tabbedPane.addTab("Feedback Analysis", createFeedbackTab());

        dialog.setLayout(new BorderLayout());
        dialog.add(tabbedPane, BorderLayout.CENTER);
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

        // Buttons: Refresh + Filter
        JPanel paymentButtonPanel = new JPanel(new FlowLayout());
        JButton refreshPaymentsBtn = new JButton("Refresh List");
        refreshPaymentsBtn.addActionListener(e -> loadPaymentData(paymentListArea));
        paymentButtonPanel.add(refreshPaymentsBtn);

        JButton filterPaymentsBtn = new JButton("Filter Payments");
        filterPaymentsBtn.addActionListener(e -> showFilteredPaymentDialog());
        paymentButtonPanel.add(filterPaymentsBtn);

        paymentSubTabs.addTab("List Payments", listPaymentsPanel);
        listPaymentsPanel.add(paymentButtonPanel, BorderLayout.SOUTH);

        // Analyze Payments subtab
        JPanel analyzePaymentsPanel = createPaymentAnalysisPanel();
        paymentSubTabs.addTab("Analyze Payments", analyzePaymentsPanel);

        panel.add(paymentSubTabs, BorderLayout.CENTER);
        return panel;
    }

    private void loadPaymentData(JTextArea textArea) {
        try {
            StringBuilder content = new StringBuilder();
            content.append(String.format("%-12s %-12s %-15s %-10s %-10s %-20s %-12s\n",
                    "Customer ID", "Car ID", "Salesman ID", "Price", "Status", "Comment", "Date"));
            content.append("------------|------------|---------------|----------|----------|--------------------|------------\n");

            List<SalesRecords> records = SalesRecords.loadSalesRecords();

            for (SalesRecords record : records) {
                String comment = record.getComment();
                if (comment.length() > 18) {
                    comment = comment.substring(0, 17) + "...";
                }

                content.append(String.format("%-12s %-12s %-15s %-10.2f %-10s %-20s %-12s\n",
                        record.getCustomerID(),
                        record.getCarID(),
                        record.getSalesmanID(),
                        record.getPrice(),
                        record.getStatus(),
                        comment,
                        record.getDate()));
            }

            textArea.setText(content.toString());
        } catch (Exception e) {
            textArea.setText("Error loading payment data: " + e.getMessage() + "\n\n" +
                    "Sample Data:\n" +
                    String.format("%-12s %-12s %-15s %-10s %-10s %-20s %-12s\n",
                            "Customer ID", "Car ID", "Salesman ID", "Price", "Status", "Comment", "Date") +
                    "------------|------------|---------------|----------|----------|----------------------|------------\n" +
                    String.format("%-12s %-12s %-15s %-10s %-10s %-20s %-12s\n",
                            "C001", "CAR123", "S001", 25000.00, "Completed", "Smooth transaction", "2024-01-01") +
                    String.format("%-12s %-12s %-15s %-10s %-10s %-20s %-12s\n",
                            "C002", "CAR202", "S002", 30000.00, "Pending", "Needs follow-up", "2024-01-02"));
        }
    }

    private void loadPaymentData(JTextArea paidTextArea, JTextArea unpaidTextArea) {
        try {
            List<SalesRecords> records = SalesRecords.loadSalesRecords();

            StringBuilder paidContent = new StringBuilder();
            StringBuilder unpaidContent = new StringBuilder();

            String header = String.format("%-12s %-12s %-15s %-10s %-10s %-20s %-12s\n",
                    "Customer ID", "Car ID", "Salesman ID", "Price", "Status", "Comment", "Date") +
                    "------------|------------|---------------|----------|----------|--------------------|------------\n";

            paidContent.append(header);
            unpaidContent.append(header);

            for (SalesRecords record : records) {
                String comment = record.getComment();
                if (comment.length() > 18) {
                    comment = comment.substring(0, 17) + "...";
                }

                String row = String.format("%-12s %-12s %-15s %-10.2f %-10s %-20s %-12s\n",
                        record.getCustomerID(),
                        record.getCarID(),
                        record.getSalesmanID(),
                        record.getPrice(),
                        record.getStatus(),
                        comment,
                        record.getDate());

                if (record.getStatus().equalsIgnoreCase("paid")) {
                    paidContent.append(row);
                } else {
                    unpaidContent.append(row);
                }
            }

            paidTextArea.setText(paidContent.toString());
            unpaidTextArea.setText(unpaidContent.toString());

        } catch (Exception e) {
            String errorMsg = "Error loading payment data: " + e.getMessage();
            paidTextArea.setText(errorMsg);
            unpaidTextArea.setText(errorMsg);
        }
    }

    private void showFilteredPaymentDialog() {
        JDialog dialog = new JDialog((Frame) null, "Filter Payments", true);
        dialog.setSize(900, 600);
        dialog.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextArea paidTextArea = new JTextArea();
        JTextArea unpaidTextArea = new JTextArea();
        paidTextArea.setEditable(false);
        unpaidTextArea.setEditable(false);
        paidTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        unpaidTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        loadPaymentData(paidTextArea, unpaidTextArea);

        JPanel paidPanel = new JPanel(new BorderLayout());
        paidPanel.setBorder(BorderFactory.createTitledBorder("Paid Transactions"));
        paidPanel.add(new JScrollPane(paidTextArea), BorderLayout.CENTER);

        JPanel unpaidPanel = new JPanel(new BorderLayout());
        unpaidPanel.setBorder(BorderFactory.createTitledBorder("Unpaid Transactions"));
        unpaidPanel.add(new JScrollPane(unpaidTextArea), BorderLayout.CENTER);

        mainPanel.add(paidPanel);
        mainPanel.add(unpaidPanel);

        dialog.setContentPane(mainPanel);
        dialog.setVisible(true);
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

        analyzeContainer.add(analyzePaymentsPanel, BorderLayout.CENTER);
        analyzeContainer.add(analyticsButtonPanel, BorderLayout.SOUTH);

        return analyzeContainer;
    }

    private PaymentAnalytics analyzePaymentData() {
        PaymentAnalytics analytics = new PaymentAnalytics();

        try {
            List<SalesRecords> records = SalesRecords.loadSalesRecords();

            for (SalesRecords record : records) {
                analytics.totalPayments++;
                analytics.totalAmount += record.getPrice();

                if (record.getStatus().equalsIgnoreCase("paid")) {
                    analytics.completedPayments++;
                }
            }

            analytics.averageAmount = analytics.totalPayments > 0
                    ? analytics.totalAmount / analytics.totalPayments
                    : 0;

            analytics.completionRate = analytics.totalPayments > 0
                    ? (analytics.completedPayments * 100.0) / analytics.totalPayments
                    : 0;

        } catch (Exception e) {
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
            ((JLabel) components[1]).setText(String.valueOf(analytics.totalPayments));
            ((JLabel) components[3]).setText(String.format("$%.2f", analytics.totalAmount));
            ((JLabel) components[5]).setText(String.format("$%.2f", analytics.averageAmount));
            ((JLabel) components[7]).setText(String.format("%d (%.1f%%)",
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

        JButton refreshFeedbackButton = new JButton("Refresh List");
        refreshFeedbackButton.addActionListener(e -> loadFeedbackData(feedbackListArea));
        feedbackButtonPanel.add(refreshFeedbackButton);

        JButton filterByRatingButton = new JButton("Filter by Rating (High to Low)");
        filterByRatingButton.addActionListener(e -> loadFeedbackDataSortedByRating(feedbackListArea));
        feedbackButtonPanel.add(filterByRatingButton);

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
            content.append(String.format("%-12s %-15s %-12s %-8s %-30s\n",
                    "Customer ID", "Item ID", "Type", "Rating", "Comment"));
            content.append("------------|----------------|-------------|--------|-------------------------------\n");

            List<CustomerFeedbacks> feedbacks = CustomerFeedbacks.getAllFeedbacks();
            for (CustomerFeedbacks fb : feedbacks) {
                String review = fb.getReview();
                if (review.length() > 28) {
                    review = review.substring(0, 25) + "...";
                }

                content.append(String.format("%-12s %-15s %-12s %-8s %-30s\n",
                        fb.getCustomerId(), fb.getItemId(), fb.getFeedbackType(), fb.getRating(), review));
            }

            textArea.setText(content.toString());
        } catch (Exception e) {
            textArea.setText("Error loading feedback data: " + e.getMessage() + "\n\n" +
                    "Sample Data:\n" +
                    String.format("%-12s %-15s %-12s %-8s %-30s\n",
                            "Customer ID", "Item ID", "Type", "Rating", "Comment") +
                    "------------|----------------|-------------|--------|-------------------------------\n" +
                    String.format("%-12s %-15s %-12s %-8s %-30s\n",
                            "C001", "CAR123", "CAR_VIEWED", "4", "Nice car but could be cheaper") +
                    String.format("%-12s %-15s %-12s %-8s %-30s\n",
                            "C002", "SM002", "SALESMAN", "5", "Very professional service"));
        }
    }

    private void loadFeedbackDataSortedByRating(JTextArea textArea) {
        try {
            StringBuilder content = new StringBuilder();
            content.append(String.format("%-12s %-15s %-12s %-8s %-30s\n",
                    "Customer ID", "Item ID", "Type", "Rating", "Comment"));
            content.append("------------|----------------|-------------|--------|-------------------------------\n");

            List<CustomerFeedbacks> feedbacks = CustomerFeedbacks.getAllFeedbacks();

            // Sort feedbacks from highest to lowest rating
            feedbacks.sort((a, b) -> Integer.compare(b.getRating(), a.getRating()));

            for (CustomerFeedbacks fb : feedbacks) {
                String review = fb.getReview();
                if (review.length() > 28) {
                    review = review.substring(0, 25) + "...";
                }

                content.append(String.format("%-12s %-15s %-12s %-8s %-30s\n",
                        fb.getCustomerId(), fb.getItemId(), fb.getFeedbackType(), fb.getRating(), review));
            }

            textArea.setText(content.toString());
        } catch (Exception e) {
            textArea.setText("Error filtering feedback data: " + e.getMessage());
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

        analyzeContainer.add(analyzeFeedbacksPanel, BorderLayout.CENTER);
        analyzeContainer.add(feedbackAnalyticsButtonPanel, BorderLayout.SOUTH);

        return analyzeContainer;
    }

    private FeedbackAnalytics analyzeFeedbackData() {
        FeedbackAnalytics analytics = new FeedbackAnalytics();

        try {
            List<CustomerFeedbacks> feedbacks = CustomerFeedbacks.getAllFeedbacks();
            analytics.totalFeedbacks = feedbacks.size();

            for (CustomerFeedbacks fb : feedbacks) {
                int rating = fb.getRating();  // This uses getRating() from your model

                analytics.totalRatingPoints += rating;

                if (rating >= 4) {
                    analytics.positiveCount++;
                }
                if (rating == 5) {
                    analytics.fiveStarCount++;
                }
            }

            // Compute metrics
            if (analytics.totalFeedbacks > 0) {
                analytics.averageRating = (double) analytics.totalRatingPoints / analytics.totalFeedbacks;
                analytics.positiveRate = (analytics.positiveCount * 100.0) / analytics.totalFeedbacks;
                analytics.fiveStarRate = (analytics.fiveStarCount * 100.0) / analytics.totalFeedbacks;
            }

        } catch (Exception e) {
            // Sample fallback values
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
            ((JLabel) components[1]).setText(String.valueOf(analytics.totalFeedbacks));
            ((JLabel) components[3]).setText(String.format("%.2f/5", analytics.averageRating));
            ((JLabel) components[5]).setText(String.format("%.1f%%", analytics.positiveRate));
            ((JLabel) components[7]).setText(String.format("%d (%.1f%%)",
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
        JDialog dialog = new JDialog(this, "Reports", true);
        dialog.setSize(900, 600);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(true);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Sales Report", createSalesReportPanel());
        tabbedPane.addTab("Inventory Report", createInventoryReportPanel());
        tabbedPane.addTab("Salesman Performance", createSalesmanPerformancePanel());

        dialog.setLayout(new BorderLayout());
        dialog.add(tabbedPane, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private JPanel createSalesReportPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        textArea.setEditable(false);

        try {
            List<SalesRecords> salesRecords = SalesRecords.loadSalesRecords();
            int totalSales = salesRecords.size();
            double totalRevenue = 0;
            Map<String, Integer> salesPerSalesman = new HashMap<>();
            Map<String, Double> revenuePerSalesman = new HashMap<>();

            for (SalesRecords record : salesRecords) {
                totalRevenue += record.getPrice();

                // Sales per salesman
                salesPerSalesman.put(record.getSalesmanID(),
                        salesPerSalesman.getOrDefault(record.getSalesmanID(), 0) + 1);

                revenuePerSalesman.put(record.getSalesmanID(),
                        revenuePerSalesman.getOrDefault(record.getSalesmanID(), 0.0) + record.getPrice());
            }

            double avgSale = totalSales > 0 ? totalRevenue / totalSales : 0;

            // Compose report
            StringBuilder report = new StringBuilder();
            report.append("====== Sales Report ======\n");
            report.append("Total Sales: ").append(totalSales).append("\n");
            report.append("Total Revenue: $").append(String.format("%.2f", totalRevenue)).append("\n");
            report.append("Average Sale Value: $").append(String.format("%.2f", avgSale)).append("\n\n");

            // Show sold cars (Car ID, Price, Buying Date)
            List<SoldCarRecord> soldCars = SoldCarRecord.getAllSoldCarRecords();
            if (!soldCars.isEmpty()) {
                report.append("\n--- Sold Cars Log (Car ID, Price, Date) ---\n");
                report.append(String.format("%-10s %-12s %-15s\n", "Car ID", "Price", "Date"));
                report.append("---------------------------------------------\n");

                for (SoldCarRecord sold : soldCars) {
                    report.append(String.format("%-10s $%-11s %-15s\n",
                            sold.getCarID(),
                            sold.getCarPrice(),
                            sold.getBuyingDate()));
                }
            }


            textArea.setText(report.toString());

        } catch (Exception e) {
            textArea.setText("Error generating sales report: " + e.getMessage());
        }

        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        return panel;
    }


    private JPanel createInventoryReportPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        textArea.setEditable(false);

        try {
            List<Car> cars = CarList.loadCarDataFromFile();
            List<SoldCarRecord> soldRecords = SoldCarRecord.getAllSoldCarRecords();

            int totalCars = cars.size();
            int available = 0, paid = 0, reserved = 0, unassigned = 0;

            StringBuilder availableCars = new StringBuilder("--- Available Cars ---\n");
            StringBuilder paidCars = new StringBuilder("--- Paid (Sold) Cars ---\n");
            StringBuilder reservedCars = new StringBuilder("--- Reserved Cars ---\n");
            StringBuilder unassignedCars = new StringBuilder("--- Unassigned Cars ---\n");

            for (Car car : cars) {
                boolean isUnassigned = (car.getSalesmanId() == null || car.getSalesmanId().isEmpty());
                if (isUnassigned) unassigned++;

                String line = String.format("Car ID: %s, Brand: %s, Price: %.2f\n",
                        car.getCarId(), car.getBrand(), (double) car.getPrice());

                switch (car.getStatus().toLowerCase()) {
                    case "available" -> {
                        available++;
                        availableCars.append(line);
                    }
                    case "paid" -> {
                        paid++;
                        paidCars.append(line);
                    }
                    case "reserved" -> {
                        reserved++;
                        reservedCars.append(line);
                    }
                }

                if (isUnassigned) {
                    unassignedCars.append(line);
                }
            }

            if (reserved == 0) reservedCars.append("None\n");
            if (unassigned == 0) unassignedCars.append("None\n");

            StringBuilder report = new StringBuilder();
            report.append("====== Inventory Report ======\n");
            report.append("Total Cars: ").append(totalCars).append("\n");
            report.append("Available: ").append(available).append("\n");
            report.append("Paid (Sold): ").append(paid).append("\n");
            report.append("Reserved: ").append(reserved).append("\n");
            report.append("Unassigned Cars: ").append(unassigned).append("\n");
            report.append("Sold Cars (from soldCars.txt): ").append(soldRecords.size()).append("\n\n");

            report.append(availableCars).append("\n");
            report.append(paidCars).append("\n");
            report.append(reservedCars).append("\n");
            report.append(unassignedCars);

            textArea.setText(report.toString());

        } catch (Exception e) {
            textArea.setText("Error generating inventory report: " + e.getMessage());
        }

        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        return panel;
    }


    private JPanel createSalesmanPerformancePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        textArea.setEditable(false);

        try {
            List<Car> cars = CarList.loadCarDataFromFile();
            List<SalesRecords> records = SalesRecords.loadSalesRecords();
            Map<String, Integer> carAssignments = new HashMap<>();
            Map<String, Integer> salesMade = new HashMap<>();
            Map<String, Double> salesRevenue = new HashMap<>();

            for (Car car : cars) {
                String sid = car.getSalesmanId();
                if (sid != null && !sid.isEmpty()) {
                    carAssignments.put(sid, carAssignments.getOrDefault(sid, 0) + 1);
                }
            }

            for (SalesRecords r : records) {
                String sid = r.getSalesmanID();
                salesMade.put(sid, salesMade.getOrDefault(sid, 0) + 1);
                salesRevenue.put(sid, salesRevenue.getOrDefault(sid, 0.0) + r.getPrice());
            }

            StringBuilder sb = new StringBuilder("====== Salesman Performance ======\n");
            for (String sid : carAssignments.keySet()) {
                int assigned = carAssignments.getOrDefault(sid, 0);
                int sold = salesMade.getOrDefault(sid, 0);
                double revenue = salesRevenue.getOrDefault(sid, 0.0);

                sb.append(String.format("Salesman %s:\n", sid));
                sb.append(String.format("   Assigned: %d cars\n", assigned));
                sb.append(String.format("   Sold: %d\n", sold));
                sb.append(String.format("   Revenue: $%.2f\n", revenue));
                sb.append("\n");
            }

            textArea.setText(sb.toString().trim());
        } catch (Exception e) {
            textArea.setText("Error generating performance report: " + e.getMessage());
        }

        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        return panel;
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