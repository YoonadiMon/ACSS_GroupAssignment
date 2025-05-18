package Utils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonStyler {
    private static void addHoverEffect(JButton button, Color normalBg, Color hoverBg) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverBg);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(normalBg);
            }
        });
    }

    public static void styleButton(JButton button) {
        Font buttonFont = new Font("SansSerif", Font.PLAIN, 16);
        Color foregroundColor = Color.DARK_GRAY;
        Color backgroundColor = new Color(220, 220, 220); // Light Gray
        Color hoverBackgroundColor = new Color(200, 200, 200); // Slightly darker gray for hover
        Border border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        );

        button.setFont(buttonFont);
        button.setForeground(foregroundColor);
        button.setBackground(backgroundColor);
        button.setBorder(border);
        button.setFocusPainted(false);
        button.setOpaque(true);

        addHoverEffect(button, backgroundColor, hoverBackgroundColor);
    }

    public static void stylePrimaryButton(JButton button) {
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        Color foregroundColor = Color.WHITE;
        Color backgroundColor = new Color(0, 84, 159); // blue
        Color hoverBackgroundColor = new Color(0, 100, 210); // Darker blue for hover
        Border border = BorderFactory.createEmptyBorder(10, 0, 10, 0);

        button.setFont(buttonFont);
        button.setForeground(foregroundColor);
        button.setBackground(backgroundColor);
        button.setBorder(border);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        button.setOpaque(true);

        addHoverEffect(button, backgroundColor, hoverBackgroundColor);
    }

    public static void styleExitButton(JButton button) {
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        Color foregroundColor = Color.WHITE;
        Color backgroundColor = new Color(170, 74, 68); // Reddish color
        Color hoverBackgroundColor = new Color(140, 50, 45); // Darker reddish for hover
        Border border = BorderFactory.createEmptyBorder(12, 25, 12, 25);

        button.setFont(buttonFont);
        button.setForeground(foregroundColor);
        button.setBackground(backgroundColor);
        button.setBorder(border);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);

        addHoverEffect(button, backgroundColor, hoverBackgroundColor);
    }
}
