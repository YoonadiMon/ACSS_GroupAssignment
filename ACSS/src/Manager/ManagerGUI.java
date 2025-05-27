package Manager;

import MainProgram.MainMenuGUI;
import Manager.ManagerDataIO;
import Utils.ButtonStyler;
import Utils.WindowNav;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerGUI implements ActionListener {

    private static void showLoginScreen() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private JFrame frame;
    private JPanel cards;
    private CardLayout cardLayout;
    private Manager manager;

    private JPanel loginPage, mainPage;
    private JButton[] navButtons;

    private final Color PRIMARY_COLOR = new Color(0, 84, 159);
    private final Color BACKGROUND_COLOR = Color.WHITE;

    private int loginAttempts = 0;
    private final int MAX_ATTEMPTS = 3;
    private final String SECURITY_PHRASE = "secureaccess"; // You can customize this

    public ManagerGUI() {
        frame = new JFrame("Manager Login");
        frame.setSize(400, 250);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        showLoginPanel();
        
        WindowNav.setCloseOperation(frame, () -> new ManagerLogin());
    }

    private void showLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 40, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(140, 40, 180, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 80, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(140, 80, 180, 25);
        panel.add(passwordText);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(140, 120, 100, 30);
        loginButton.addActionListener(e -> {
            String username = userText.getText().trim();
            String password = new String(passwordText.getPassword());

            ManagerDataIO.readManager();
            Manager found = ManagerDataIO.searchName(username);
            if (found != null && found.getPassword().equals(password)) {
                this.manager = found;
                frame.dispose();
                launchDashboard();
            } else {
                loginAttempts++;
                if (loginAttempts >= MAX_ATTEMPTS) {
                    String securityInput = JOptionPane.showInputDialog(frame, "Too many failed attempts. Please enter the security phrase:");
                    if (securityInput != null && securityInput.equals(SECURITY_PHRASE)) {
                        loginAttempts = 0;
                        JOptionPane.showMessageDialog(frame, "Access granted. You may try again.", "Security Verified", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Incorrect security phrase. Access denied.", "Security Check Failed", JOptionPane.ERROR_MESSAGE);
                        frame.dispose();
                        new MainMenuGUI();
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password. Attempt " + loginAttempts + " of 3.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(loginButton);

        JButton backButton = new JButton("Go Back");
        backButton.setBounds(250, 120, 100, 30);
        backButton.addActionListener(e -> {
            frame.dispose();
            new MainMenuGUI();
        });
        panel.add(backButton);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private void launchDashboard () {
        frame = new JFrame("Manager Dashboard");
        frame.setSize(900, 620);
        frame.setLocationRelativeTo(null);
        WindowNav.setCloseOperation(frame, ManagerGUI::new);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        createPages();

        cards.add(mainPage, "Main");

        JPanel navigationPanel = createNavigationPanel();

        frame.setLayout(new BorderLayout());
        frame.add(navigationPanel, BorderLayout.NORTH);
        frame.add(cards, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void createPages() {
        mainPage = createBasicPagePanel("Welcome, " + manager.getUsername());
    }

    private JPanel createNavigationPanel() {
        JPanel navPanel = new JPanel(new GridLayout(1, 1, 5, 0));
        navPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        navPanel.setBackground(BACKGROUND_COLOR);

        navButtons = new JButton[1];
        String[] buttonLabels = {"Main"};
        String[] cardNames = {"Main"};

        for (int i = 0; i < navButtons.length; i++) {
            navButtons[i] = createNavButton(buttonLabels[i], cardNames[i]);
            navPanel.add(navButtons[i]);
        }

        updateNavButtonsState(0);

        return navPanel;
    }

    private JButton createNavButton(String text, String cardName) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setActionCommand(cardName);
        button.addActionListener(this);
        return button;
    }

    private void updateNavButtonsState(int activeIndex) {
        for (int i = 0; i < navButtons.length; i++) {
            if (i == activeIndex) {
                navButtons[i].setBackground(PRIMARY_COLOR);
                navButtons[i].setForeground(Color.WHITE);
                navButtons[i].setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
            } else {
                navButtons[i].setBackground(BACKGROUND_COLOR);
                navButtons[i].setForeground(Color.BLACK);
                navButtons[i].setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                        BorderFactory.createEmptyBorder(10, 0, 10, 0)));
            }
        }
    }

    private JPanel createBasicPagePanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(new EmptyBorder(10, 40, 20, 40));
        topPanel.setBackground(BACKGROUND_COLOR);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(titleLabel);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(0, 40, 0, 40));
        contentPanel.setBackground(BACKGROUND_COLOR);

        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.setBackground(BACKGROUND_COLOR);
        JButton logoutButton = new JButton("Logout");
        ButtonStyler.styleExitButton(logoutButton);
        logoutButton.addActionListener(e -> {
            frame.dispose();
            new MainMenuGUI();
        });
        logoutPanel.add(logoutButton);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);
        panel.add(logoutPanel, BorderLayout.SOUTH);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        cardLayout.show(cards, command);

        for (int i = 0; i < navButtons.length; i++) {
            if (navButtons[i].getActionCommand().equals(command)) {
                updateNavButtonsState(i);
                break;
            }
        }
    }

}