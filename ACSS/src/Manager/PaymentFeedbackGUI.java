package Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentFeedbackGUI extends JFrame {
    private PaymentSystem paymentSystem;
    private FeedbackSystem feedbackSystem;

    // Main components
    private JTabbedPane tabbedPane;
    private PaymentPanel paymentPanel;
    private FeedbackPanel feedbackPanel;

    public PaymentFeedbackGUI() {
        super("Payment and Feedback ");
        initializeSystems();
        setupGUI();
    }

    private void initializeSystems() {
        paymentSystem = new PaymentSystem();
        feedbackSystem = new FeedbackSystem();
    }

    private void setupGUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create tabbed interface
        tabbedPane = new JTabbedPane();

        // Create panels
        paymentPanel = new PaymentPanel(paymentSystem);
        feedbackPanel = new FeedbackPanel(feedbackSystem);

        // Add tabs
        tabbedPane.addTab("Payment", paymentPanel);
        tabbedPane.addTab("Feedback", feedbackPanel);

        add(tabbedPane);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaymentFeedbackGUI());
    }
}

// Payment System GUI Panel
class PaymentPanel extends JPanel {
    private PaymentSystem paymentSystem;

    // Form components
    private JTextField customerIdField, amountField, methodField;
    private JTextArea outputArea;
    private JButton addButton, listButton, analyzeButton;

    public PaymentPanel(PaymentSystem paymentSystem) {
        this.paymentSystem = paymentSystem;
        setupPanel();
    }

    private void setupPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create form panel
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Payment Information"));

        // Customer ID
        formPanel.add(new JLabel("Customer ID:"));
        customerIdField = new JTextField();
        formPanel.add(customerIdField);

        // Amount
        formPanel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        formPanel.add(amountField);

        // Method
        formPanel.add(new JLabel("Method:"));
        methodField = new JTextField();
        formPanel.add(methodField);

        add(formPanel, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        addButton = new JButton("Add Payment");
        addButton.addActionListener(this::addPayment);

        listButton = new JButton("List Payments");
        listButton.addActionListener(this::listPayments);

        analyzeButton = new JButton("Analyze Payments");
        analyzeButton.addActionListener(this::analyzePayments);

        buttonPanel.add(addButton);
        buttonPanel.add(listButton);
        buttonPanel.add(analyzeButton);

        add(buttonPanel, BorderLayout.CENTER);

        // Output area
        outputArea = new JTextArea(10, 50);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.SOUTH);
    }

    private void addPayment(ActionEvent e) {
        try {
            String customerId = customerIdField.getText().trim();
            double amount = Double.parseDouble(amountField.getText().trim());
            String method = methodField.getText().trim();

            if (customerId.isEmpty() || method.isEmpty()) {
                outputArea.setText("Please fill in all fields");
                return;
            }

            paymentSystem.addPayment(new Payment(customerId, amount, method));
            outputArea.setText("Payment added successfully!");
            clearFields();
        } catch (NumberFormatException ex) {
            outputArea.setText("Invalid amount format. Please enter a valid number.");
        }
    }

    private void listPayments(ActionEvent e) {
        List<Payment> payments = paymentSystem.getPayments();
        if (payments.isEmpty()) {
            outputArea.setText("No payment records found.");
            return;
        }

        StringBuilder sb = new StringBuilder("--- Payment Records ---\n");
        payments.forEach(p -> sb.append(p).append("\n"));
        outputArea.setText(sb.toString());
    }

    private void analyzePayments(ActionEvent e) {
        List<Payment> payments = paymentSystem.getPayments();
        if (payments.isEmpty()) {
            outputArea.setText("No payments to analyze.");
            return;
        }

        double total = payments.stream().mapToDouble(Payment::amount).sum();
        String analysis = String.format(
                "Payment Analysis:\n" +
                        "Total Revenue: RM %.2f\n" +
                        "Total Transactions: %d",
                total, payments.size()
        );
        outputArea.setText(analysis);
    }

    private void clearFields() {
        customerIdField.setText("");
        amountField.setText("");
        methodField.setText("");
    }
}

// Feedback System GUI Panel
class FeedbackPanel extends JPanel {
    private FeedbackSystem feedbackSystem;

    // Form components
    private JTextField customerIdField, ratingField;
    private JTextArea commentArea, outputArea;
    private JButton addButton, listButton, analyzeButton;

    public FeedbackPanel(FeedbackSystem feedbackSystem) {
        this.feedbackSystem = feedbackSystem;
        setupPanel();
    }

    private void setupPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create form panel
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Feedback Information"));

        // Customer ID
        formPanel.add(new JLabel("Customer ID:"));
        customerIdField = new JTextField();
        formPanel.add(customerIdField);

        // Rating
        formPanel.add(new JLabel("Rating (1-5):"));
        ratingField = new JTextField();
        formPanel.add(ratingField);

        // Comment
        formPanel.add(new JLabel("Comment:"));
        commentArea = new JTextArea(3, 20);
        JScrollPane commentScroll = new JScrollPane(commentArea);
        formPanel.add(commentScroll);

        add(formPanel, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        addButton = new JButton("Add Feedback");
        addButton.addActionListener(this::addFeedback);

        listButton = new JButton("List Feedback");
        listButton.addActionListener(this::listFeedback);

        analyzeButton = new JButton("Analyze Feedback");
        analyzeButton.addActionListener(this::analyzeFeedback);

        buttonPanel.add(addButton);
        buttonPanel.add(listButton);
        buttonPanel.add(analyzeButton);

        add(buttonPanel, BorderLayout.CENTER);

        // Output area
        outputArea = new JTextArea(10, 50);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.SOUTH);
    }

    private void addFeedback(ActionEvent e) {
        try {
            String customerId = customerIdField.getText().trim();
            int rating = Integer.parseInt(ratingField.getText().trim());
            String comment = commentArea.getText().trim();

            if (customerId.isEmpty() || comment.isEmpty()) {
                outputArea.setText("Please fill in all fields");
                return;
            }

            if (rating < 1 || rating > 5) {
                outputArea.setText("Rating must be between 1 and 5");
                return;
            }

            feedbackSystem.addFeedback(new Feedback(customerId, comment, rating));
            outputArea.setText("Feedback added successfully!");
            clearFields();
        } catch (NumberFormatException ex) {
            outputArea.setText("Invalid rating format. Please enter a number between 1-5.");
        }
    }

    private void listFeedback(ActionEvent e) {
        List<Feedback> feedbacks = feedbackSystem.getFeedbacks();
        if (feedbacks.isEmpty()) {
            outputArea.setText("No feedback records found.");
            return;
        }

        StringBuilder sb = new StringBuilder("--- Feedback Records ---\n");
        feedbacks.forEach(f -> sb.append(f).append("\n"));
        outputArea.setText(sb.toString());
    }

    private void analyzeFeedback(ActionEvent e) {
        List<Feedback> feedbacks = feedbackSystem.getFeedbacks();
        if (feedbacks.isEmpty()) {
            outputArea.setText("No feedback to analyze.");
            return;
        }

        double average = feedbacks.stream()
                .mapToInt(Feedback::rating)
                .average()
                .orElse(0.0);

        String analysis = String.format(
                "Feedback Analysis:\n" +
                        "Average Rating: %.2f\n" +
                        "Total Feedback Entries: %d",
                average, feedbacks.size()
        );
        outputArea.setText(analysis);
    }

    private void clearFields() {
        customerIdField.setText("");
        ratingField.setText("");
        commentArea.setText("");
    }
}

// Modified PaymentSystem with GUI support
class PaymentSystem {
    private final List<Payment> paymentList = new ArrayList<>();
    private static final String FILE_NAME = "payments.txt";

    public PaymentSystem() {
        loadPaymentsFromFile();
    }

    public void addPayment(Payment payment) {
        paymentList.add(payment);
        savePaymentsToFile();
    }

    public List<Payment> getPayments() {
        return new ArrayList<>(paymentList);
    }

    private void savePaymentsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            paymentList.forEach(payment -> {
                try {
                    writer.write(payment.toFileString());
                    writer.newLine();
                } catch (IOException e) {
                    System.err.println("Error writing payment: " + e.getMessage());
                }
            });
        } catch (IOException e) {
            System.err.println("Error saving payments to file: " + e.getMessage());
        }
    }

    private void loadPaymentsFromFile() {
        paymentList.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            reader.lines()
                    .map(Payment::fromFileString)
                    .forEach(paymentList::add);
        } catch (FileNotFoundException e) {
            System.out.println("No existing payment file found. Starting fresh.");
        } catch (IOException e) {
            System.err.println("Error loading payments: " + e.getMessage());
        }
    }
}

// Modified FeedbackSystem with GUI support
class FeedbackSystem {
    private final List<Feedback> feedbackList = new ArrayList<>();
    private static final String FILE_NAME = "feedback.txt";

    public FeedbackSystem() {
        loadFeedbackFromFile();
    }

    public void addFeedback(Feedback feedback) {
        feedbackList.add(feedback);
        saveFeedbackToFile();
    }

    public List<Feedback> getFeedbacks() {
        return new ArrayList<>(feedbackList);
    }

    private void saveFeedbackToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            feedbackList.forEach(feedback -> {
                try {
                    writer.write(feedback.toFileString());
                    writer.newLine();
                } catch (IOException e) {
                    System.err.println("Error writing feedback: " + e.getMessage());
                }
            });
        } catch (IOException e) {
            System.err.println("Error saving feedback: " + e.getMessage());
        }
    }

    private void loadFeedbackFromFile() {
        feedbackList.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            reader.lines()
                    .map(Feedback::fromFileString)
                    .forEach(feedbackList::add);
        } catch (FileNotFoundException e) {
            System.out.println("No existing feedback file found. Starting fresh.");
        } catch (IOException e) {
            System.err.println("Error loading feedback: " + e.getMessage());
        }
    }
}

// Payment and Feedback records remain the same as in your original code
