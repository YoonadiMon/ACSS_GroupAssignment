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
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author YOON
 */
public class CarPage implements DashboardPage {
    
    // Change this field type from Customer to BaseCustomer
    private BaseCustomer customer;
    private boolean isGuest;
    private ArrayList<Car> allCarsList;
    private ArrayList<Car> filteredCarsList;
    private JPanel carListContainer;
    private JScrollPane scrollPane;
    private JPanel contentPanel;
    private JPanel parentPanel;
    
    // Filter components
    private JComboBox<String> brandFilter;
    private JTextField minPriceField;
    private JTextField maxPriceField;

    // Constructor for regular customers
    public CarPage() {
        this.isGuest = false;
    }
    
    // Constructor for guests
    public CarPage(boolean isGuest) {
        this.isGuest = isGuest;
    }
    
    @Override
    public JPanel createPage(BaseCustomer customer, JFrame frame) {
        // Store the customer reference (change the field type to BaseCustomer)
        this.customer = customer; 
        this.parentPanel = null;

        // Determine if this is a guest based on user type
        this.isGuest = "Guest".equals(customer.getUserType());
        
        if (!this.isGuest && "Guest".equals(customer.getUserType())) {
            this.isGuest = true;
        }

        // Page Unique Code
        String pageTitle = isGuest ? "Available Cars (Guest View: " + customer.getUsername() + ")" : "Available Cars";
        JPanel CarPage = DashboardUIUtils.createBasicPagePanel(pageTitle, frame);
        this.parentPanel = CarPage;

        // Load salesman data
        ArrayList<Salesman> salesmanList = SalesmanList.loadSalesmanDataFromFile();

        // Get the content panel (which is at index 1 in BorderLayout.CENTER)
        contentPanel = (JPanel) ((BorderLayout) CarPage.getLayout()).getLayoutComponent(BorderLayout.CENTER);

        // Set layout for the content panel
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Load all available cars
        allCarsList = CarList.loadCarDataFromFile();
        filteredCarsList = new ArrayList<>();
        for (Car car : allCarsList) {
            if (car.getStatus().equals("available")) {
                filteredCarsList.add(car);
            }
        }

        // Add some spacing at the top
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Create and add filter panel
        JPanel filterPanel = createFilterPanel();
        contentPanel.add(filterPanel);

        // Add spacing between filter and car list
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Create the car list container
        createCarListContainer();

        // Add the car container to a scroll pane
        scrollPane = new JScrollPane(carListContainer);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        contentPanel.add(scrollPane);

        // Add guest-specific footer if needed
        if (isGuest) {
            JPanel footerPanel = createGuestFooter(frame);
            CarPage.add(footerPanel, BorderLayout.SOUTH);
        }

        // Initial display of all cars
        updateCarDisplay();

        return CarPage;
    }
    
    private JPanel createGuestFooter(JFrame frame) {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(new Color(240, 240, 240));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        JLabel signUpPrompt = new JLabel("Create an account to book cars");
        signUpPrompt.setFont(new Font("SansSerif", Font.BOLD, 16));
        signUpPrompt.setForeground(new Color(0, 84, 159));
        
        JButton signUpButton = new JButton("Sign Up");
        ButtonStyler.stylePrimaryButton(signUpButton);
        signUpButton.setPreferredSize(new Dimension(140, 40));

        signUpButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame,
                    "Redirecting to registration page",
                    "Sign Up",
                    JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
            new CustomerLandingGUI(); 
        });
        
        footerPanel.add(signUpPrompt);
        footerPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        footerPanel.add(signUpButton);
        
        return footerPanel;
    }
    
    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)));
        filterPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        // Brand Filter
        JLabel brandLabel = new JLabel("Brand:");
        brandLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        
        brandFilter = new JComboBox<>();
        brandFilter.addItem("All Brands");
        
        // Populate brand filter with unique brands from available cars
        Set<String> uniqueBrands = new HashSet<>();
        for (Car car : filteredCarsList) {
            uniqueBrands.add(car.getBrand());
        }
        for (String brand : uniqueBrands) {
            brandFilter.addItem(brand);
        }
        
        brandFilter.setPreferredSize(new Dimension(120, 25));
        brandFilter.addActionListener(e -> applyFilters());

        // Price Range Filter
        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        
        JLabel minLabel = new JLabel("Min:");
        minLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        minPriceField = new JTextField(6);
        minPriceField.setPreferredSize(new Dimension(80, 25));
        
        JLabel maxLabel = new JLabel("Max:");
        maxLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        maxPriceField = new JTextField(6);
        maxPriceField.setPreferredSize(new Dimension(80, 25));
        
        // Apply Filter Button
        JButton applyButton = new JButton("Apply");
        ButtonStyler.stylePrimaryButton(applyButton);
        applyButton.setPreferredSize(new Dimension(80, 25));
        applyButton.addActionListener(e -> applyFilters());
        
        // Clear Filter Button
        JButton clearButton = new JButton("Clear");
        clearButton.setPreferredSize(new Dimension(60, 25));
        clearButton.setBackground(new Color(240, 240, 240));
        clearButton.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        clearButton.addActionListener(e -> clearFilters());

        // Add components to filter panel
        filterPanel.add(brandLabel);
        filterPanel.add(brandFilter);
        filterPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        filterPanel.add(priceLabel);
        filterPanel.add(minLabel);
        filterPanel.add(minPriceField);
        filterPanel.add(maxLabel);
        filterPanel.add(maxPriceField);
        filterPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        filterPanel.add(applyButton);
        filterPanel.add(clearButton);

        return filterPanel;
    }
    
    private void createCarListContainer() {
        carListContainer = new JPanel();
        carListContainer.setLayout(new BoxLayout(carListContainer, BoxLayout.Y_AXIS));
        carListContainer.setBorder(BorderFactory.createEmptyBorder(0, 50, 20, 50));
        carListContainer.setBackground(Color.WHITE);
    }
    
    private void applyFilters() {
        ArrayList<Car> filteredCars = new ArrayList<>();
        
        // Get filter values
        String selectedBrand = (String) brandFilter.getSelectedItem();
        String minPriceText = minPriceField.getText().trim();
        String maxPriceText = maxPriceField.getText().trim();
        
        Double minPrice = null;
        Double maxPrice = null;
        
        // Parse price values
        try {
            if (!minPriceText.isEmpty()) {
                minPrice = Double.parseDouble(minPriceText);
            }
            if (!maxPriceText.isEmpty()) {
                maxPrice = Double.parseDouble(maxPriceText);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(parentPanel,
                    "Please enter valid numeric values for price range.",
                    "Invalid Price Range",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Validate price range
        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            JOptionPane.showMessageDialog(parentPanel,
                    "Minimum price cannot be greater than maximum price.",
                    "Invalid Price Range",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Apply filters
        for (Car car : allCarsList) {
            if (!car.getStatus().equals("available")) {
                continue; // Skip non-available cars
            }
            
            boolean matchesBrand = selectedBrand.equals("All Brands") || car.getBrand().equals(selectedBrand);
            boolean matchesPrice = true;
            
            if (minPrice != null && car.getPrice() < minPrice) {
                matchesPrice = false;
            }
            if (maxPrice != null && car.getPrice() > maxPrice) {
                matchesPrice = false;
            }
            
            if (matchesBrand && matchesPrice) {
                filteredCars.add(car);
            }
        }
        
        // Update the filtered list and display
        filteredCarsList = filteredCars;
        updateCarDisplay();
    }
    
    private void clearFilters() {
        brandFilter.setSelectedItem("All Brands");
        minPriceField.setText("");
        maxPriceField.setText("");
        
        // Reset to show all available cars
        filteredCarsList.clear();
        for (Car car : allCarsList) {
            if (car.getStatus().equals("available")) {
                filteredCarsList.add(car);
            }
        }
        
        updateCarDisplay();
    }
    
    private void updateCarDisplay() {
        // Clear existing car boxes
        carListContainer.removeAll();
        
        if (filteredCarsList.isEmpty()) {
            // Show message when no cars match the filter
            JPanel noResultsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            noResultsPanel.setBackground(Color.WHITE);
            noResultsPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
            
            JLabel noResultsLabel = new JLabel("No cars match your filter criteria.");
            noResultsLabel.setFont(new Font("SansSerif", Font.ITALIC, 16));
            noResultsLabel.setForeground(Color.GRAY);
            
            noResultsPanel.add(noResultsLabel);
            carListContainer.add(noResultsPanel);
        } else {
            // Create car boxes for filtered results
            for (Car car : filteredCarsList) {
                JPanel carBox = createCarBox(car, parentPanel);
                carListContainer.add(carBox);
                carListContainer.add(Box.createRigidArea(new Dimension(0, 15))); // Space between car boxes
            }
        }
        
        // Refresh the display
        carListContainer.revalidate();
        carListContainer.repaint();
    }
    
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
        JLabel salesmanLabel = new JLabel("Salesman: " + salesmanName + (isGuest ? "" : " (" + salesmanId + ")"));
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

        // Add different action listeners based on user type
        System.out.println(isGuest);
        if (isGuest) {
            // Guest user - show registration required message
            bookButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(parentPanel,
                        "Guests cannot book cars. Please register for an account to book this car.",
                        "Registration Required",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            });
        } else {
            // Regular customer - handle booking
            bookButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleCarBooking(car, parentPanel);
                }
            });
        }

        buttonPanel.add(bookButton);

        // Add components to the main car box
        carBox.add(infoPanel, BorderLayout.CENTER);
        carBox.add(buttonPanel, BorderLayout.EAST);

        return carBox;
    }
    
    private void handleCarBooking(Car car, JPanel parentPanel) {
        String customerId = customer.getUserId(); 

        int confirm = JOptionPane.showConfirmDialog(
            parentPanel,
            "Confirm booking for " + car.getBrand() + " (ID: " + car.getCarId() + ")",
            "Confirm Booking",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE
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
                    if (req.getCarID().equals(car.getCarId()) && req.getCustomerID().equals(customerId)) {
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
                            "Your previous booking has been rejected by salesman.\nReason: " + reason,
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
                        "pending",
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

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parentPanel,
                        "Error creating booking: " + ex.getMessage(),
                        "Booking Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}