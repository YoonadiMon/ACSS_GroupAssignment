package Manager;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class ReportsGUI {
    private static final String STAFF_FILE = "staffList.txt";
    private static final String SALESMEN_FILE = "salesmenList.txt";
    private static final String CUSTOMERS_FILE = "customerList.txt";
    private static final String SOLD_CARS_FILE = "soldCars.txt";
    private static final String CARS_FILE = "carList.txt";
    private static final String PAYMENTS_FILE = "paymentList.txt";
    private static final String FEEDBACK_FILE = "feedbackList.txt";

    private JFrame frame;
    private JTabbedPane tabbedPane;

    public static void showReportsGUI() {
        SwingUtilities.invokeLater(() -> {
            try {
                new ReportsGUI().initialize();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error initializing reports: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void initialize() {
        frame = new JFrame("Car Dealership Management System - Reports");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setLocationRelativeTo(null);

        createUI();
        loadAllData();

        frame.setVisible(true);
    }

    private void createUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create tabbed pane for different reports
        tabbedPane = new JTabbedPane();

        // User Statistics Tab
        JPanel userStatsPanel = createReportPanel();
        JButton userStatsBtn = new JButton("Generate User Statistics");
        userStatsBtn.addActionListener(event -> generateUserStatistics());
        userStatsPanel.add(createButtonPanel(userStatsBtn), BorderLayout.NORTH);
        tabbedPane.addTab("User Statistics", userStatsPanel);

        // Car Inventory Tab
        JPanel carInventoryPanel = createReportPanel();
        JButton carInventoryBtn = new JButton("Generate Car Inventory Report");
        carInventoryBtn.addActionListener(event -> generateCarInventoryReport());
        carInventoryPanel.add(createButtonPanel(carInventoryBtn), BorderLayout.NORTH);
        tabbedPane.addTab("Car Inventory", carInventoryPanel);

        // Financial Report Tab
        JPanel financialPanel = createReportPanel();
        JButton financialBtn = new JButton("Generate Financial Report");
        financialBtn.addActionListener(event -> generateFinancialReport());
        financialPanel.add(createButtonPanel(financialBtn), BorderLayout.NORTH);
        tabbedPane.addTab("Financial", financialPanel);

        // Feedback Analysis Tab
        JPanel feedbackPanel = createReportPanel();
        JButton feedbackBtn = new JButton("Generate Feedback Analysis");
        feedbackBtn.addActionListener(event -> generateFeedbackAnalysis());
        feedbackPanel.add(createButtonPanel(feedbackBtn), BorderLayout.NORTH);
        tabbedPane.addTab("Feedback", feedbackPanel);

        // All Reports Tab
        JPanel allReportsPanel = createReportPanel();
        JButton allReportsBtn = new JButton("Generate All Reports");
        allReportsBtn.addActionListener(event -> generateAllReports());
        allReportsPanel.add(createButtonPanel(allReportsBtn), BorderLayout.NORTH);
        tabbedPane.addTab("All Reports", allReportsPanel);

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Add export button at the bottom
        JButton exportBtn = new JButton("Export Current Report");
        exportBtn.addActionListener(event -> exportCurrentReport());
        mainPanel.add(exportBtn, BorderLayout.SOUTH);

        frame.add(mainPanel);
    }

    private JPanel createReportPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(reportArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createButtonPanel(JButton button) {
        JPanel panel = new JPanel();
        panel.add(button);
        return panel;
    }

    private void loadAllData() {
        try {
            // These methods are kept for future data processing if needed
            Files.readAllLines(Paths.get(STAFF_FILE));
            Files.readAllLines(Paths.get(SALESMEN_FILE));
            Files.readAllLines(Paths.get(CUSTOMERS_FILE));
            Files.readAllLines(Paths.get(CARS_FILE));
            Files.readAllLines(Paths.get(SOLD_CARS_FILE));
            Files.readAllLines(Paths.get(PAYMENTS_FILE));
            Files.readAllLines(Paths.get(FEEDBACK_FILE));
        } catch (IOException ex) {
            showError("Error loading data: " + ex.getMessage());
        }
    }

    private void generateUserStatistics() {
        try {
            List<String> staff = Files.readAllLines(Paths.get(STAFF_FILE));
            List<String> salesmen = Files.readAllLines(Paths.get(SALESMEN_FILE));
            List<String> customers = Files.readAllLines(Paths.get(CUSTOMERS_FILE));

            StringBuilder report = new StringBuilder();
            report.append("=== USER STATISTICS REPORT ===\n\n");
            report.append(String.format("%-20s: %d\n", "Staff", staff.size()));
            report.append(String.format("%-20s: %d\n", "Salesmen", salesmen.size()));

            long approvedCustomers = customers.stream()
                    .filter(line -> line.split(",")[3].equalsIgnoreCase("true"))
                    .count();

            report.append(String.format("%-20s: %d (Approved: %d, Pending: %d)\n\n",
                    "Total Customers", customers.size(),
                    approvedCustomers, customers.size() - approvedCustomers));

            getCurrentReportArea().setText(report.toString());

        } catch (IOException ex) {
            showError("Error generating user statistics: " + ex.getMessage());
        }
    }

    private void generateCarInventoryReport() {
        try {
            List<String> cars = Files.readAllLines(Paths.get(CARS_FILE));
            List<String> soldCars = Files.readAllLines(Paths.get(SOLD_CARS_FILE));

            StringBuilder report = new StringBuilder();
            report.append("=== CAR INVENTORY REPORTS ===\n\n");
            report.append(String.format("Total Cars in Inventory: %d\n\n", cars.size()));
            report.append(String.format("Total Cars Sold: %d\n\n", soldCars.size()));

            if (cars.isEmpty()) {
                getCurrentReportArea().setText(report.toString());
                return;
            }

            // Price statistics
            DoubleSummaryStatistics priceStats = cars.stream()
                    .map(line -> Double.parseDouble(line.split(",")[3]))
                    .mapToDouble(Double::doubleValue)
                    .summaryStatistics();

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
            report.append("Price Statistics:\n");
            report.append(String.format("Average Price: %s\n", currencyFormat.format(priceStats.getAverage())));
            report.append(String.format("Highest Priced: %s\n", currencyFormat.format(priceStats.getMax())));
            report.append(String.format("Lowest Priced: %s\n\n", currencyFormat.format(priceStats.getMin())));

            // Brand distribution
            report.append("Brand Distribution:\n");
            cars.stream()
                    .collect(Collectors.groupingBy(
                            line -> line.split(",")[1],
                            Collectors.counting()
                    ))
                    .forEach((brand, count) ->
                            report.append(String.format("%-15s: %d (%.1f%%)\n",
                                    brand, count, (count*100.0)/cars.size())));

            getCurrentReportArea().setText(report.toString());

        } catch (IOException | NumberFormatException ex) {
            showError("Error generating car inventory report: " + ex.getMessage());
        }
    }

    private void generateFinancialReport() {
        try {
            List<String> payments = Files.readAllLines(Paths.get(PAYMENTS_FILE));

            StringBuilder report = new StringBuilder();
            report.append("=== FINANCIAL SUMMARY REPORT ===\n\n");

            if (payments.isEmpty()) {
                report.append("No payment records available.\n");
                getCurrentReportArea().setText(report.toString());
                return;
            }

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

            // Basic financial stats
            double totalRevenue = payments.stream()
                    .mapToDouble(line -> Double.parseDouble(line.split(",")[1]))
                    .sum();

            report.append(String.format("Total Revenue: %s\n", currencyFormat.format(totalRevenue)));
            report.append(String.format("Total Transactions: %d\n", payments.size()));
            report.append(String.format("Average Transaction: %s\n\n",
                    currencyFormat.format(totalRevenue/payments.size())));

            // Payment method analysis
            report.append("Payment Method Breakdown:\n");
            payments.stream()
                    .collect(Collectors.groupingBy(
                            line -> line.split(",")[2],
                            Collectors.summingDouble(line -> Double.parseDouble(line.split(",")[1]))
                    ))
                    .forEach((method, amount) ->
                            report.append(String.format("%-15s: %s (%.1f%%)\n",
                                    method, currencyFormat.format(amount), (amount*100)/totalRevenue)));

            getCurrentReportArea().setText(report.toString());

        } catch (IOException ex) {
            showError("Error generating financial report: " + ex.getMessage());
        }
    }

    private void generateFeedbackAnalysis() {
        try {
            List<String> feedbacks = Files.readAllLines(Paths.get(FEEDBACK_FILE));

            StringBuilder report = new StringBuilder();
            report.append("=== CUSTOMER FEEDBACK ANALYSIS ===\n\n");

            if (feedbacks.isEmpty()) {
                report.append("No feedback records available.\n");
                getCurrentReportArea().setText(report.toString());
                return;
            }

            // Rating statistics
            IntSummaryStatistics ratingStats = feedbacks.stream()
                    .mapToInt(line -> Integer.parseInt(line.split(",")[2]))
                    .summaryStatistics();

            report.append(String.format("Average Rating: %.1f/5\n", ratingStats.getAverage()));
            report.append(String.format("Highest Rating: %d/5\n", ratingStats.getMax()));
            report.append(String.format("Lowest Rating: %d/5\n\n", ratingStats.getMin()));

            // Rating distribution
            report.append("Rating Distribution:\n");
            feedbacks.stream()
                    .collect(Collectors.groupingBy(
                            line -> Integer.parseInt(line.split(",")[2]),
                            TreeMap::new,
                            Collectors.counting()
                    ))
                    .forEach((rating, count) ->
                            report.append(String.format("%d stars: %d (%.1f%%)\n",
                                    rating, count, (count*100.0)/feedbacks.size())));

            // Recent comments
            report.append("\nRecent Customer Comments:\n");
            feedbacks.stream()
                    .sorted((a, b) -> {
                        LocalDate dateA = LocalDate.parse(a.split(",")[3]);
                        LocalDate dateB = LocalDate.parse(b.split(",")[3]);
                        return dateB.compareTo(dateA);
                    })
                    .limit(5)
                    .forEach(line -> {
                        String[] parts = line.split(",");
                        report.append(String.format("[%s] %s: \"%s\"\n",
                                parts[3], parts[0], parts[1]));
                    });

            getCurrentReportArea().setText(report.toString());

        } catch (IOException ex) {
            showError("Error generating feedback analysis: " + ex.getMessage());
        }
    }

    private void generateAllReports() {
        StringBuilder allReports = new StringBuilder();

        // Generate each report and combine them
        allReports.append("=== COMPREHENSIVE REPORT ===\n\n");

        allReports.append("--- USER STATISTICS ---\n");
        generateUserStatistics();
        allReports.append(getCurrentReportArea().getText()).append("\n");

        allReports.append("--- CAR INVENTORY ---\n");
        generateCarInventoryReport();
        allReports.append(getCurrentReportArea().getText()).append("\n");

        allReports.append("--- FINANCIAL SUMMARY ---\n");
        generateFinancialReport();
        allReports.append(getCurrentReportArea().getText()).append("\n");

        allReports.append("--- FEEDBACK ANALYSIS ---\n");
        generateFeedbackAnalysis();
        allReports.append(getCurrentReportArea().getText());

        // Set the combined report to the "All Reports" tab
        setReportTextForTab(4, allReports.toString());
        tabbedPane.setSelectedIndex(4);
    }

    private void exportCurrentReport() {
        JTextArea currentArea = getCurrentReportArea();
        if (currentArea.getText().isEmpty()) {
            showError("No report to export. Please generate a report first.");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export Report");
        fileChooser.setSelectedFile(new java.io.File("report.txt"));

        if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileChooser.getSelectedFile();
            try {
                Files.write(file.toPath(), currentArea.getText().getBytes());
                JOptionPane.showMessageDialog(frame, "Report exported successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                showError("Error exporting report: " + ex.getMessage());
            }
        }
    }

    private JTextArea getCurrentReportArea() {
        return (JTextArea) ((JScrollPane) tabbedPane.getSelectedComponent().getComponent(0)).getViewport().getView();
    }

    private void setReportTextForTab(int tabIndex, String text) {
        JTextArea area = (JTextArea) ((JScrollPane) tabbedPane.getComponentAt(tabIndex).getComponent(0)).getViewport().getView();
        area.setText(text);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}. "Total