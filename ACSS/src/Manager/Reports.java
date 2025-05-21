package Manager;

import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

public class Reports {
    private static final Scanner scanner = new Scanner(System.in);

    // File paths
    private static final String STAFF_FILE = "staffList.txt";
    private static final String SALESMEN_FILE = "salesmenList.txt";
    private static final String CUSTOMERS_FILE = "customerList.txt";
    private static final String CARS_FILE = "carList.txt";
    private static final String SOLD_CARS_FILE = "soldCars.txt";
    private static final String PAYMENTS_FILE = "paymentList.txt";
    private static final String FEEDBACK_FILE = "feedbackList.txt";

    public static void ReportsMenu() {
        loadAllData();

        int choice;
        do {
            System.out.println("\n--- Reports Menu ---");
            System.out.println("1. User Statistics Report");
            System.out.println("2. Car Inventory Reports");
            System.out.println("3. Financial Summary");
            System.out.println("4. Customer Feedback Analysis");
            System.out.println("5. Generate All Reports");
            System.out.println("0. Return to Main Menu");
            System.out.print("Enter your choice: ");

            choice = getIntInput();

            switch (choice) {
                case 1 -> generateUserStatistics();
                case 2 -> generateCarInventoryReport();
                case 3 -> generateFinancialReport();
                case 4 -> generateFeedbackAnalysis();
                case 5 -> generateAllReports();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private static void loadAllData() {
        try {
            loadStaff();
            loadSalesmen();
            loadCustomers();
            loadCars();
            loadSoldCars();
            loadPayments();
            loadFeedback();
        } catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }

    // Data loading methods
    private static void loadStaff() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(STAFF_FILE));
        // Parse staff data from file
    }

    private static void loadSalesmen() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(SALESMEN_FILE));
        // Parse salesmen data from file
    }

    private static void loadCustomers() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(CUSTOMERS_FILE));
        // Parse customer data from file
    }

    private static void loadCars() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(CARS_FILE));
        // Parse car data from file
    }

    private static void loadSoldCars() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(SOLD_CARS_FILE));
        // Parse sold car data from file
    }

    private static void loadPayments() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(PAYMENTS_FILE));
        // Parse payment data from file
    }

    private static void loadFeedback() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FEEDBACK_FILE));
        // Parse feedback data from file
    }

    // Report generation methods
    private static void generateUserStatistics() {
        try {
            List<String> staff = Files.readAllLines(Paths.get(STAFF_FILE));
            List<String> salesmen = Files.readAllLines(Paths.get(SALESMEN_FILE));
            List<String> customers = Files.readAllLines(Paths.get(CUSTOMERS_FILE));

            System.out.println("\n=== USER STATISTICS REPORT ===");
            System.out.printf("%-20s: %d\n", "Staff", staff.size());
            System.out.printf("%-20s: %d\n", "Salesmen", salesmen.size());

            long approvedCustomers = customers.stream()
                    .filter(line -> line.split(",")[3].equalsIgnoreCase("true"))
                    .count();

            System.out.printf("%-20s: %d (Approved: %d, Pending: %d)\n",
                    "Total Customers", customers.size(),
                    approvedCustomers, customers.size() - approvedCustomers);

        } catch (IOException e) {
            System.err.println("Error generating user statistics: " + e.getMessage());
        }
    }

    private static void generateCarInventoryReport() {
        try {
            List<String> availableCars = Files.readAllLines(Paths.get(CARS_FILE));
            List<String> soldCars = Files.readAllLines(Paths.get(SOLD_CARS_FILE));

            System.out.println("\n=== CAR INVENTORY REPORTS ===");
            System.out.printf("Total Cars in Inventory: %d\n", availableCars.size());
            System.out.printf("Total Cars Sold: %d\n", soldCars.size());

            if (!availableCars.isEmpty()) {
                // Price statistics for available cars
                DoubleSummaryStatistics availablePriceStats = availableCars.stream()
                        .map(line -> Double.parseDouble(line.split(",")[3]))
                        .mapToDouble(Double::doubleValue)
                        .summaryStatistics();

                System.out.println("\nAvailable Cars - Price Statistics:");
                System.out.printf("Average Price: RM%,.2f\n", availablePriceStats.getAverage());
                System.out.printf("Highest Priced: RM%,.2f\n", availablePriceStats.getMax());
                System.out.printf("Lowest Priced: RM%,.2f\n", availablePriceStats.getMin());
            }

            if (!soldCars.isEmpty()) {
                // Price statistics for sold cars
                DoubleSummaryStatistics soldPriceStats = soldCars.stream()
                        .map(line -> Double.parseDouble(line.split(",")[3]))
                        .mapToDouble(Double::doubleValue)
                        .summaryStatistics();

                System.out.println("\nSold Cars - Price Statistics:");
                System.out.printf("Average Sale Price: RM%,.2f\n", soldPriceStats.getAverage());
                System.out.printf("Highest Sale Price: RM%,.2f\n", soldPriceStats.getMax());
                System.out.printf("Lowest Sale Price: RM%,.2f\n", soldPriceStats.getMin());
            }

            // Combined brand distribution
            if (!availableCars.isEmpty() || !soldCars.isEmpty()) {
                System.out.println("\nBrand Distribution:");

                // Available cars by brand
                Map<String, Long> availableBrands = availableCars.stream()
                        .collect(Collectors.groupingBy(
                                line -> line.split(",")[1],
                                Collectors.counting()
                        ));

                // Sold cars by brand
                Map<String, Long> soldBrands = soldCars.stream()
                        .collect(Collectors.groupingBy(
                                line -> line.split(",")[1],
                                Collectors.counting()
                        ));

                // Combine all brands
                Set<String> allBrands = new TreeSet<>();
                allBrands.addAll(availableBrands.keySet());
                allBrands.addAll(soldBrands.keySet());

                int totalCars = availableCars.size() + soldCars.size();

                for (String brand : allBrands) {
                    long availableCount = availableBrands.getOrDefault(brand, 0L);
                    long soldCount = soldBrands.getOrDefault(brand, 0L);
                    long totalCount = availableCount + soldCount;

                    System.out.printf("%-15s: %d total (Available: %d, Sold: %d) - %.1f%% of inventory\n",
                            brand, totalCount, availableCount, soldCount,
                            (totalCount * 100.0) / totalCars);
                }
            }

            // Sales performance by month (if we have sold cars with dates)
            if (!soldCars.isEmpty()) {
                try {
                    System.out.println("\nMonthly Sales Performance:");

                    Map<YearMonth, Long> monthlySales = soldCars.stream()
                            .collect(Collectors.groupingBy(
                                    line -> YearMonth.from(LocalDate.parse(line.split(",")[4])),
                                    TreeMap::new,
                                    Collectors.counting()
                            ));

                    monthlySales.forEach((month, count) ->
                            System.out.printf("%s: %d cars sold\n", month, count));

                } catch (Exception e) {
                    System.out.println("\n(No date information available for sales performance analysis)");
                }
            }

        } catch (IOException e) {
            System.err.println("Error generating car inventory report: " + e.getMessage());
        }
    }

    private static void generateFinancialReport() {
        try {
            List<String> payments = Files.readAllLines(Paths.get(PAYMENTS_FILE));
            List<String> soldCars = Files.readAllLines(Paths.get(SOLD_CARS_FILE));

            System.out.println("\n=== FINANCIAL SUMMARY REPORT ===");

            if (payments.isEmpty() && soldCars.isEmpty()) {
                System.out.println("No financial records available.");
                return;
            }

            // Basic financial stats from payments
            double totalRevenue = payments.stream()
                    .mapToDouble(line -> Double.parseDouble(line.split(",")[1]))
                    .sum();

            // Additional stats from sold cars (if payments are separate)
            double totalSalesValue = soldCars.stream()
                    .mapToDouble(line -> Double.parseDouble(line.split(",")[3]))
                    .sum();

            System.out.printf("Total Revenue from Payments: RM%,.2f\n", totalRevenue);
            System.out.printf("Total Value of Cars Sold: RM%,.2f\n", totalSalesValue);
            System.out.printf("Total Transactions: %d\n", payments.size());
            System.out.printf("Average Transaction: RM%,.2f\n",
                    totalRevenue/payments.size());

            // Payment method analysis
            if (!payments.isEmpty()) {
                System.out.println("\nPayment Method Breakdown:");
                payments.stream()
                        .collect(Collectors.groupingBy(
                                line -> line.split(",")[2],
                                Collectors.summingDouble(line -> Double.parseDouble(line.split(",")[1]))
                        ))
                        .forEach((method, amount) ->
                                System.out.printf("%-15s: RM%,.2f (%.1f%%)\n",
                                        method, amount, (amount*100)/totalRevenue));
            }

        } catch (IOException e) {
            System.err.println("Error generating financial report: " + e.getMessage());
        }
    }

    private static void generateFeedbackAnalysis() {
        try {
            List<String> feedbacks = Files.readAllLines(Paths.get(FEEDBACK_FILE));

            System.out.println("\n=== CUSTOMER FEEDBACK ANALYSIS ===");

            if (feedbacks.isEmpty()) {
                System.out.println("No feedback records available.");
                return;
            }

            // Rating statistics
            IntSummaryStatistics ratingStats = feedbacks.stream()
                    .mapToInt(line -> Integer.parseInt(line.split(",")[2]))
                    .summaryStatistics();

            System.out.printf("Average Rating: %.1f/5\n", ratingStats.getAverage());
            System.out.printf("Highest Rating: %d/5\n", ratingStats.getMax());
            System.out.printf("Lowest Rating: %d/5\n", ratingStats.getMin());

            // Rating distribution
            System.out.println("\nRating Distribution:");
            feedbacks.stream()
                    .collect(Collectors.groupingBy(
                            line -> Integer.parseInt(line.split(",")[2]),
                            TreeMap::new,
                            Collectors.counting()
                    ))
                    .forEach((rating, count) ->
                            System.out.printf("%d stars: %d (%.1f%%)\n",
                                    rating, count, (count*100.0)/feedbacks.size()));

            // Recent comments
            System.out.println("\nRecent Customer Comments:");
            feedbacks.stream()
                    .sorted((a, b) -> {
                        LocalDate dateA = LocalDate.parse(a.split(",")[3]);
                        LocalDate dateB = LocalDate.parse(b.split(",")[3]);
                        return dateB.compareTo(dateA);
                    })
                    .limit(5)
                    .forEach(line -> {
                        String[] parts = line.split(",");
                        System.out.printf("[%s] %s: \"%s\"\n",
                                parts[3], parts[0], parts[1]);
                    });

        } catch (IOException e) {
            System.err.println("Error generating feedback analysis: " + e.getMessage());
        }
    }

    private static void generateAllReports() {
        System.out.println("\n=== GENERATING ALL REPORTS ===\n");
        generateUserStatistics();
        generateCarInventoryReport();
        generateFinancialReport();
        generateFeedbackAnalysis();
    }

    private static int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number!");
            scanner.next();
        }
        int input = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return input;
    }
}