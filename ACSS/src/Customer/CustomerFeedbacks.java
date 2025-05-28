package Customer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author YOON
 */
public class CustomerFeedbacks {
    private String customerId;
    private String itemId;      // carId or salesmanId
    private String feedbackType; // SALESMAN, CAR_VIEWED, or CAR_PURCHASED
    private int rating;         // 1-5 star rating
    private String review;      // Text review
    
    private static final String FEEDBACK_FILE_PATH = "data/customerFeedbacks.txt";
    
    // Feedback type constants
    public static final String TYPE_SALESMAN = "SALESMAN";
    public static final String TYPE_CAR_VIEWED = "CAR_VIEWED";
    public static final String TYPE_CAR_PURCHASED = "CAR_PURCHASED";
    
    // Getters
    public String getCustomerId() { return customerId; }
    public String getItemId() { return itemId; }
    public String getFeedbackType() { return feedbackType; }
    public int getRating() { return rating; }
    public String getReview() { return review; }
    
    public CustomerFeedbacks(String customerId, String itemId, String feedbackType, int rating, String review) {
        this.customerId = customerId;
        this.itemId = itemId;
        this.feedbackType = feedbackType;
        this.rating = rating;
        this.review = review;
    }

    public boolean saveFeedback() {
        try {
            // Ensure directory exists
            File directory = new File("data");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            // Format: customerId|itemId|feedbackType|rating|review
            String feedbackData = String.format("%s,%s,%s,%d,%s\n", 
                    customerId, itemId, feedbackType, rating, review);
            
            // Append to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(FEEDBACK_FILE_PATH, true));
            writer.write(feedbackData);
            writer.close();
            return true;
        } catch (IOException e) {
            System.err.println("Error saving feedback: " + e.getMessage());
            return false;
        }
    }
    
    public static List<CustomerFeedbacks> getFeedbacksByCustomerId(String customerId) {
        List<CustomerFeedbacks> feedbacks = new ArrayList<>();
        
        try {
            if (!Files.exists(Paths.get(FEEDBACK_FILE_PATH))) {
                return feedbacks; // Empty list if file doesn't exist yet
            }
            
            List<String> lines = Files.readAllLines(Paths.get(FEEDBACK_FILE_PATH));
            
            for (String line : lines) {
                String[] parts = line.split("\\|");
                if (parts.length >= 5 && parts[0].equals(customerId)) {
                    CustomerFeedbacks feedback = new CustomerFeedbacks(
                            parts[0], // customerId
                            parts[1], // itemId
                            parts[2], // feedbackType
                            Integer.parseInt(parts[3]), // rating
                            parts[4]  // review
                    );
                    feedbacks.add(feedback);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading feedbacks: " + e.getMessage());
        }
        
        return feedbacks;
    }

    public static boolean hasFeedback(String customerId, String itemId, String feedbackType) {
        try {
            if (!Files.exists(Paths.get(FEEDBACK_FILE_PATH))) {
                return false;
            }
            
            List<String> lines = Files.readAllLines(Paths.get(FEEDBACK_FILE_PATH));
            
            for (String line : lines) {
                String[] parts = line.split("\\|");
                if (parts.length >= 3 && 
                    parts[0].equals(customerId) && 
                    parts[1].equals(itemId) && 
                    parts[2].equals(feedbackType)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error checking feedback: " + e.getMessage());
        }
        
        return false;
    }
    
    public static List<CustomerFeedbacks> getAllFeedbacks() {
        List<CustomerFeedbacks> feedbacks = new ArrayList<>();

        try {
            if (!Files.exists(Paths.get(CustomerFeedbacks.FEEDBACK_FILE_PATH))) {
                return feedbacks;
            }

            List<String> lines = Files.readAllLines(Paths.get(CustomerFeedbacks.FEEDBACK_FILE_PATH));

            for (String line : lines) {
                String[] parts = parseCSVLine(line); // Use proper CSV parsing
                if (parts.length >= 5) {
                    CustomerFeedbacks feedback = new CustomerFeedbacks(
                        parts[0], parts[1], parts[2], 
                        Integer.parseInt(parts[3]), parts[4]
                    );
                    feedbacks.add(feedback);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading feedbacks: " + e.getMessage());
        }

        return feedbacks;
    }
    
    private static String[] parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder currentField = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    // Double quote - add single quote to field
                    currentField.append('"');
                    i++; // Skip next quote
                } else {
                    // Toggle quote state
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                // Field separator
                result.add(currentField.toString());
                currentField = new StringBuilder();
            } else {
                currentField.append(c);
            }
        }

        // Add final field
        result.add(currentField.toString());

        return result.toArray(new String[0]);
    }
}
