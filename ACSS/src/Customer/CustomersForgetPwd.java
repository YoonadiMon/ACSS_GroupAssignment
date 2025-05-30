package Customer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author YOON
 */
public class CustomersForgetPwd {
    private String customerId;
    private String question;
    private String answer;
    
    private static final String QUESTION_FILE_NAME = "data/CustomersForgetPwd.txt";
    
    public CustomersForgetPwd(String customerId, String question, String answer) {
        this.customerId = customerId;
        this.question = question;
        this.answer = answer;
    }
    
    // Getters
    public String getCustomerId() { return customerId; }
    public String getQuestion() { return question; }
    public String getAnswer() { return answer; }
    
    // Helper method to escape CSV fields
    private static String escapeCSVField(String field) {
        if (field == null) return "\"\"";
        // Wrap in quotes and escape any existing quotes by doubling them
        return "\"" + field.replace("\"", "\"\"") + "\"";
    }
    
    // Helper method to parse a CSV line properly
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
    
    // Check if customerId already exists in the file
    public static boolean customerExists(String customerId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(QUESTION_FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = parseCSVLine(line);
                if (parts.length >= 1 && parts[0].equals(customerId)) {
                    return true;
                }
            }
        } catch (IOException e) {
            return false;
            // File might not exist yet, treat as no existing customer
        }
        return false;
    }
    
    // Save question and answer to file if customerId does not exist
    public boolean saveSecurityQuestion() {
        if (customerExists(customerId)) {
            return false; // Already exists
        }
        
        // Ensure directory exists
        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(QUESTION_FILE_NAME, true))) {
            // Format as proper CSV: customerId,question,answer
            String csvLine = escapeCSVField(customerId) + "," + 
                           escapeCSVField(question) + "," + 
                           escapeCSVField(answer);
            writer.write(csvLine);
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Allow override past questions
    public boolean overrideSecurityQuestion() {
        File file = new File(QUESTION_FILE_NAME);
        List<String> lines = new ArrayList<>();
        boolean replaced = false;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = parseCSVLine(line);
                if (parts.length >= 1 && parts[0].equals(customerId)) {
                    // Replace this line with the new question and answer
                    String csvLine = escapeCSVField(customerId) + "," + 
                                   escapeCSVField(question) + "," + 
                                   escapeCSVField(answer);
                    lines.add(csvLine);
                    replaced = true;
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
        if (!replaced) {
            // If not found, add new entry
            String csvLine = escapeCSVField(customerId) + "," + 
                           escapeCSVField(question) + "," + 
                           escapeCSVField(answer);
            lines.add(csvLine);
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Method to retrieve security question for a customer (useful for password reset)
    public static CustomersForgetPwd getSecurityQuestion(String customerId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(QUESTION_FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = parseCSVLine(line);
                if (parts.length >= 3 && parts[0].equals(customerId)) {
                    return new CustomersForgetPwd(parts[0], parts[1], parts[2]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Method to verify security answer
    public static boolean verifySecurityAnswer(String customerId, String providedAnswer) {
        CustomersForgetPwd securityData = getSecurityQuestion(customerId);
        if (securityData != null) {
            return securityData.getAnswer().equals(providedAnswer);
        }
        return false;
    }
}