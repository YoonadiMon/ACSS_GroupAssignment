package Manager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PaymentFeedback {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        PaymentSystem.initialize();
        FeedbackSystem.initialize();
        paymentAndFeedbackMenu();
        scanner.close();
    }

    public static void paymentAndFeedbackMenu() {
        int choice;
        do {
            System.out.println("\n--- Payment & Feedback Main Menu ---");
            System.out.println("1. Payment Menu");
            System.out.println("2. Feedback Menu");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");

            choice = getIntInput();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> PaymentSystem.paymentMenu();
                case 2 -> FeedbackSystem.feedbackMenu();
                case 0 -> System.out.println("Returning to Manager Menu...");
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 0);
    }

    private static int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a number!");
            scanner.next();
        }
        return scanner.nextInt();
    }
}

class PaymentSystem {
    private static final List<Payment> paymentList = new ArrayList<>();
    private static final String FILE_NAME = "payments.txt";
    private static final Scanner scanner = new Scanner(System.in);

    public static void initialize() {
        loadPaymentsFromFile();
    }

    public static void paymentMenu() {
        int choice;
        do {
            System.out.println("\n--- Payment Menu ---");
            System.out.println("1. Add Payment");
            System.out.println("2. List All Payments");
            System.out.println("3. Analyze Payments");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");

            choice = getIntInput();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> addPayment();
                case 2 -> listPayments();
                case 3 -> analyzePayments();
                case 0 -> System.out.println("Returning to Payment & Feedback Menu...");
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 0);
    }

    private static int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a number!");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static void addPayment() {
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine().trim();

        System.out.print("Enter Payment Amount: ");
        double amount = getDoubleInput();
        scanner.nextLine(); // consume newline

        System.out.print("Enter Payment Method (e.g., Cash, Credit Card): ");
        String method = scanner.nextLine().trim();

        paymentList.add(new Payment(customerId, amount, method));
        savePaymentsToFile();
        System.out.println("Payment added successfully.");
    }

    private static double getDoubleInput() {
        while (!scanner.hasNextDouble()) {
            System.out.println("Please enter a valid amount!");
            scanner.next();
        }
        return scanner.nextDouble();
    }

    private static void listPayments() {
        if (paymentList.isEmpty()) {
            System.out.println("No payment records found.");
            return;
        }
        System.out.println("\n--- Payment Records ---");
        paymentList.forEach(System.out::println);
    }

    private static void analyzePayments() {
        if (paymentList.isEmpty()) {
            System.out.println("No payments to analyze.");
            return;
        }

        double total = paymentList.stream().mapToDouble(Payment::amount).sum();
        System.out.printf("Total Revenue: RM %.2f\n", total);
        System.out.println("Total Transactions: " + paymentList.size());
    }

    private static void savePaymentsToFile() {
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

    private static void loadPaymentsFromFile() {
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

class FeedbackSystem {
    private static final List<Feedback> feedbackList = new ArrayList<>();
    private static final String FILE_NAME = "feedback.txt";
    private static final Scanner scanner = new Scanner(System.in);

    public static void initialize() {
        loadFeedbackFromFile();
    }

    public static void feedbackMenu() {
        int choice;
        do {
            System.out.println("\n--- Feedback Menu ---");
            System.out.println("1. Add Feedback");
            System.out.println("2. List All Feedback");
            System.out.println("3. Analyze Feedback");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");

            choice = getIntInput();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> addFeedback();
                case 2 -> listFeedback();
                case 3 -> analyzeFeedback();
                case 0 -> System.out.println("Returning to Payment & Feedback Menu...");
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 0);
    }

    private static int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a number!");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static void addFeedback() {
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine().trim();

        System.out.print("Enter Feedback Comment: ");
        String comment = scanner.nextLine().trim();

        System.out.print("Enter Rating (1-5): ");
        int rating = getRatingInput();
        scanner.nextLine(); // consume newline

        feedbackList.add(new Feedback(customerId, comment, rating));
        saveFeedbackToFile();
        System.out.println("Feedback recorded successfully.");
    }

    private static int getRatingInput() {
        int rating;
        while (true) {
            rating = getIntInput();
            if (rating >= 1 && rating <= 5) break;
            System.out.println("Invalid rating. Please enter a value between 1 and 5.");
        }
        return rating;
    }

    private static void listFeedback() {
        if (feedbackList.isEmpty()) {
            System.out.println("No feedback records found.");
            return;
        }
        System.out.println("\n--- Feedback Records ---");
        feedbackList.forEach(System.out::println);
    }

    private static void analyzeFeedback() {
        if (feedbackList.isEmpty()) {
            System.out.println("No feedback to analyze.");
            return;
        }

        double average = feedbackList.stream()
                .mapToInt(Feedback::rating)
                .average()
                .orElse(0.0);

        System.out.printf("Average Customer Rating: %.2f\n", average);
        System.out.println("Total Feedback Entries: " + feedbackList.size());
    }

    private static void saveFeedbackToFile() {
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

    private static void loadFeedbackFromFile() {
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

record Payment(String customerId, double amount, String method) {
    public String toFileString() {
        return String.join(",", customerId, String.valueOf(amount), method);
    }

    public static Payment fromFileString(String line) throws IllegalArgumentException {
        String[] parts = line.split(",", 3);
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid payment data format");
        }
        try {
            return new Payment(parts[0], Double.parseDouble(parts[1]), parts[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid amount format");
        }
    }

    @Override
    public String toString() {
        return String.format("Customer ID: %s, Amount: RM %.2f, Method: %s",
                customerId, amount, method);
    }
}

record Feedback(String customerId, String comment, int rating) {
    public String toFileString() {
        return String.join(",", customerId, comment, String.valueOf(rating));
    }

    public static Feedback fromFileString(String line) throws IllegalArgumentException {
        String[] parts = line.split(",", 3);
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid feedback data format");
        }
        try {
            int rating = Integer.parseInt(parts[2]);
            if (rating < 1 || rating > 5) {
                throw new IllegalArgumentException("Rating out of range");
            }
            return new Feedback(parts[0], parts[1], rating);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid rating format");
        }
    }

    @Override
    public String toString() {
        return String.format("Customer ID: %s, Rating: %d, Comment: %s",
                customerId, rating, comment);
    }
}