package Car;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author hp
 */
public class SalesRecords {

    private String customerID;
    private String carID;
    private String salesmanID;
    private double price;
    private String status;
    private String comment;
    private String date;  // Added date field

    public SalesRecords(String customerID, String carID, String salesmanID, double price, String status, String comment, String date) {
        this.customerID = customerID;
        this.carID = carID;
        this.salesmanID = salesmanID;
        this.price = price;
        this.status = status;
        this.comment = comment;
        this.date = date;
    }

    // Getters for all fields
    public String getCustomerID() {
        return customerID;
    }

    public String getCarID() {
        return carID;
    }

    public String getSalesmanID() {
        return salesmanID;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getComment() {
        return comment;
    }

    public String getDate() {
        return date;
    }

    // Convert record to string for file storage
    public String toFileString() {
        return customerID + "," + carID + "," + salesmanID + "," + price + ","
                + status + "," + comment + "," + date;
    }

    // Create record from file string
    public static SalesRecords fromFileString(String line) {
        String[] data = line.split(",");
        // Handle older records that might not have date (backward compatibility)
        String recordDate = data.length > 6 ? data[6] : new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return new SalesRecords(
                data[0], data[1], data[2],
                Double.parseDouble(data[3]),
                data[4],
                data.length > 5 ? data[5] : "",
                recordDate
        );
    }

    // Save a single sales record
    public static void saveSalesRecord(SalesRecords record) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/salesList.txt", true))) {
            writer.write(record.toFileString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving sales record: " + e.getMessage());
        }
    }

    // Load all sales records
    public static ArrayList<SalesRecords> loadSalesRecords() {
        ArrayList<SalesRecords> records = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("data/salesList.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    records.add(SalesRecords.fromFileString(line));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading sales records: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error parsing sales records: " + e.getMessage());
        }
        return records;
    }

    // Helper method to validate date format
    public static boolean isValidDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    // Method to get sales records by date range
    public static ArrayList<SalesRecords> getRecordsByDateRange(String startDate, String endDate) {
        ArrayList<SalesRecords> filteredRecords = new ArrayList<>();
        ArrayList<SalesRecords> allRecords = loadSalesRecords();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);

            for (SalesRecords record : allRecords) {
                Date recordDate = sdf.parse(record.getDate());
                if (!recordDate.before(start) && !recordDate.after(end)) {
                    filteredRecords.add(record);
                }
            }
        } catch (ParseException e) {
            System.out.println("Invalid date format: " + e.getMessage());
        }

        return filteredRecords;
    }
}
