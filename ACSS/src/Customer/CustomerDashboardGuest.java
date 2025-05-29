package Customer;

import Utils.WindowNav;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

/**
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
        
        // Now this will work because GuestCustomer extends BaseCustomer
        CarPage carPage = new CarPage();
        JPanel carPagePanel = carPage.createPage(guestCustomer, frame);
        
        // Add the car page to the frame
        frame.add(carPagePanel, BorderLayout.CENTER);
        
        frame.setVisible(true);
    }
}