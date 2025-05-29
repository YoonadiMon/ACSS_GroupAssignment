package Customer;

import javax.swing.*;

/**
 *
 * @author YOON
 */
public interface DashboardPage {
    JPanel createPage(BaseCustomer customer, JFrame frame);
    
}