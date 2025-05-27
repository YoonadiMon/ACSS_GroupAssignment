package Customer;

import Car.Car;
import Car.CarList;
import Car.CarRequest;
import Salesman.Salesman;
import Salesman.SalesmanList;
import Utils.ButtonStyler;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 *
 * @author YOON
 */
public class CarPage implements DashboardPage {
    
    private Customer customer;
    
    @Override
    public JPanel createPage(Customer customer, JFrame frame) {
        // Store the customer reference
        this.customer = customer;
        
        // Page Unique Code
        JPanel CarPage = DashboardUIUtils.createBasicPagePanel("Available Cars", frame);

        // Load salesman data
        ArrayList<Salesman> salesmanList = SalesmanList.loadSalesmanDataFromFile();

        // Get the content panel (which is at index 1 in BorderLayout.CENTER)
        JPanel contentPanel = (JPanel) ((BorderLayout) CarPage.getLayout()).getLayoutComponent(BorderLayout.CENTER);

        // Set layout for the content panel
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Page-specific content 
        ArrayList<Car> allCarsList = CarList.loadCarDataFromFile();
        ArrayList<Car> showCarsList = new ArrayList<>();
        for (Car car : allCarsList) {
            if (car.getStatus().equals("available")) {
                showCarsList.add(car);
            }
        }

        // Add some spacing at the top
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Create a panel to hold car boxes with some margin on the sides
        JPanel carListContainer = new JPanel();
        carListContainer.setLayout(new BoxLayout(carListContainer, BoxLayout.Y_AXIS));
        carListContainer.setBorder(BorderFactory.createEmptyBorder(0, 50, 20, 50));
        carListContainer.setBackground(Color.WHITE);

        // Create a box for each car
        for (Car car : showCarsList) {
            JPanel carBox = createCarBox(car, CarPage);
            carListContainer.add(carBox);
            carListContainer.add(Box.createRigidArea(new Dimension(0, 15))); // Space between car boxes
        }

        // Add the car container to a scroll pane in case there are many cars
        JScrollPane scrollPane = new JScrollPane(carListContainer);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        contentPanel.add(scrollPane);
        
        return CarPage;
    }
    
    /**
     * Creates a panel displaying information about a car with a book button
     * 
     * @param car The car to display
     * @param parentPanel The parent panel for showing dialogs
     * @return A JPanel containing the car information and book button
     */
    private JPanel createCarBox(Car car, JPanel parentPanel) {
        // Main container for car info
        JPanel carBox = new JPanel();
        carBox.setLayout(new BorderLayout());
        carBox.setBackground(new Color(240, 240, 240)); 
        carBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        carBox.setMaximumSize(new Dimension(800, 150)); 

        // Left panel for car details
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(4, 1));
        infoPanel.setBackground(new Color(240, 240, 240));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        // Create and style the information labels
        JLabel carIdLabel = new JLabel("Car ID: " + car.getCarId());
        carIdLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        JLabel brandLabel = new JLabel("Brand: " + car.getBrand());
        brandLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel priceLabel = new JLabel("Price: $" + String.format("%,.2f", (double) car.getPrice()));
        priceLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));


        String salesmanId = car.getSalesmanId();
        String salesmanName = SalesmanList.getSalesmanNameById(salesmanId);
        JLabel salesmanLabel = new JLabel("Salesman: " + salesmanName);
        salesmanLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Add labels to the info panel
        infoPanel.add(carIdLabel);
        infoPanel.add(brandLabel);
        infoPanel.add(priceLabel);
        infoPanel.add(salesmanLabel);

        // Right panel for the Book button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(240, 240, 240));

        // Create and style the Book button
        JButton bookButton = new JButton("Book");
        ButtonStyler.stylePrimaryButton(bookButton);
        bookButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        // Add action listener to the button
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String customerId = customer.getCustomerId(); // Get current logged in customer ID

                int confirm = JOptionPane.showConfirmDialog(
                    parentPanel,  // parent component (your panel or frame)
                    "Confirm booking for " + car.getBrand() + " (ID: " + car.getCarId() + ")",  // message
                    "Confirm Booking",  // dialog title
                    JOptionPane.OK_CANCEL_OPTION,  // options: OK and Cancel buttons
                    JOptionPane.QUESTION_MESSAGE  // icon type
                );

                if (confirm == JOptionPane.OK_OPTION) {
                    try {
                        // Load existing requests
                        ArrayList<CarRequest> requests = CarRequest.loadCarRequestDataFromFile();

                         // Check if this car is already requested
                        boolean alreadyRequested = false;
                        boolean rejected = false;
                        String reason = "";
                        for (CarRequest req : requests) {
                            if (req.getCarID().equals(car.getCarId()) && req.getCustomerID().equals(customerId) ) {
                                alreadyRequested = true;
                                if ((req.getRequestStatus().equals("rejected"))) {
                                    rejected = true;
                                    reason = req.getComment();
                                }
                                break;
                            }
                        }
                        if (reason.equals(".") || reason.trim().isEmpty()) {
                            reason = "Not provided.";
                        }
                        if (rejected) {
                            JOptionPane.showMessageDialog(parentPanel,
                                    "Your previous booking has been rejected by saleman.\nReason: " + reason,
                                    "Car Not Available",
                                    JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        if (alreadyRequested) {
                            JOptionPane.showMessageDialog(parentPanel,
                                    "You have already made a request for a booking for this car previously. Please check your Cars History.",
                                    "Car Not Available",
                                    JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                        // Add new request
                        CarRequest newRequest = new CarRequest(
                                customerId,
                                car.getCarId(),
                                car.getSalesmanId(),
                                "pending", // Initial status is pending
                                "."
                        );

                        requests.add(newRequest);
                        CarRequest.writeCarRequests(requests);

                        // Show success message
                        JOptionPane.showMessageDialog(parentPanel,
                                "Car booking request submitted successfully!\n" +
                                "Car: " + car.getBrand() + " (ID: " + car.getCarId() + ")\n" +
                                "Status: Pending approval by salesman",
                                "Booking Submitted",
                                JOptionPane.INFORMATION_MESSAGE);

                        // Refresh the car list to update availability
                        //refreshCarPage();

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(parentPanel,
                                "Error creating booking: " + ex.getMessage(),
                                "Booking Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        buttonPanel.add(bookButton);

        // Add components to the main car box
        carBox.add(infoPanel, BorderLayout.CENTER);
        carBox.add(buttonPanel, BorderLayout.EAST);

        return carBox;
    }
}