package Customer;

import Car.Car;
import Car.CarList;
import Car.CarRequest;
import MainProgram.MainMenuGUI;
import Salesman.Salesman;
import Salesman.SalesmanList;
import Utils.ButtonStyler;
import Utils.WindowNav;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author YOON
 */
public class CustomerDashboardGuest {
    private JFrame frame;
    private GuestCustomer customer;
    
    // Navigation buttons
    private JButton[] navButtons;

    // Colors
    private final Color PRIMARY_COLOR = new Color(0, 84, 159);
    private final Color LIGHT_TEXT_COLOR = new Color(111, 143, 175);
    private final Color BACKGROUND_COLOR = Color.WHITE;

    public CustomerDashboardGuest(GuestCustomer guestCustomer) {
        this.customer = guestCustomer;

        frame = new JFrame("Guest Customer Dashboard");
        frame.setSize(860, 620);
        frame.setLocationRelativeTo(null);
        WindowNav.setCloseOperation(frame, () -> new CustomerLandingGUI());
        
        // Create and display the car page directly
        JPanel carPage = createCarPage();
        frame.add(carPage);
        
        frame.setVisible(true);
    }
    
    private JPanel createCarPage() {
        JPanel carPage = new JPanel(new BorderLayout());
        carPage.setBackground(BACKGROUND_COLOR);
        
        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(860, 70));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel titleLabel = new JLabel("Available Cars (Guest View: " + customer.getUsername() + " )");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        // Add some spacing at the top
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Load cars
        ArrayList<Car> allCarsList = CarList.loadCarDataFromFile();
        ArrayList<Car> showCarsList = new ArrayList<>();
        for (Car car : allCarsList) {
            if (car.getStatus().equals("available")) {
                showCarsList.add(car);
            }
        }
        
        // Create a panel to hold car boxes with some margin on the sides
        JPanel carListContainer = new JPanel();
        carListContainer.setLayout(new BoxLayout(carListContainer, BoxLayout.Y_AXIS));
        carListContainer.setBorder(BorderFactory.createEmptyBorder(0, 50, 20, 50));
        carListContainer.setBackground(Color.WHITE);
        
        // Create a box for each car
        for (Car car : showCarsList) {
            JPanel carBox = createCarBox(car);
            carListContainer.add(carBox);
            carListContainer.add(Box.createRigidArea(new Dimension(0, 15))); // Space between car boxes
        }
        
        // Add the car container to a scroll pane in case there are many cars
        JScrollPane scrollPane = new JScrollPane(carListContainer);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        contentPanel.add(scrollPane);
        
        // Footer panel with "Sign Up to Book" prompt
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(new Color(240, 240, 240));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        JLabel signUpPrompt = new JLabel("Create an account to book cars");
        signUpPrompt.setFont(new Font("SansSerif", Font.BOLD, 16));
        signUpPrompt.setForeground(PRIMARY_COLOR);
        
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
        
        // Add panels to the main panel
        carPage.add(headerPanel, BorderLayout.NORTH);
        carPage.add(contentPanel, BorderLayout.CENTER);
        carPage.add(footerPanel, BorderLayout.SOUTH);
        
        return carPage;
    }
    
    private JPanel createCarBox(Car car) {
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

        // Add action listener to the button - Show message that guests cannot book
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame,
                    "Guests cannot book cars. Please register for an account to book this car.",
                    "Registration Required",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });

        buttonPanel.add(bookButton);

        // Add components to the main car box
        carBox.add(infoPanel, BorderLayout.CENTER);
        carBox.add(buttonPanel, BorderLayout.EAST);

        return carBox;
    }
}