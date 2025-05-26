package Customer;

import javax.swing.*;

/**
 *
 * @author YOON
 */
public interface DashboardPage {
    JPanel createPage(Customer customer, JFrame frame);
}