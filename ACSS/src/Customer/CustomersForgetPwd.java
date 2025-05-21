/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
    
    // Check if customerId already exists in the file
    public static boolean customerExists(String customerId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(QUESTION_FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(customerId + ",")) {
                    return true;
                }
            }
        } catch (IOException e) {
            // File might not exist yet, treat as no existing customer
        }
        return false;
    }

    // Save question and answer to file if customerId does not exist
    public boolean saveSecurityQuestion() {
        if (customerExists(customerId)) {
            return false; // Already exists
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(QUESTION_FILE_NAME, true))) {
            writer.write(customerId + "," + question + "," + answer);
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
                if (line.startsWith(customerId + ",")) {
                    // Replace this line with the new question and answer
                    lines.add(customerId + "," + question + "," + answer);
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
            // If not found, add new entry (optional)
            lines.add(customerId + "," + question + "," + answer);
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


}
