package Utils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ButtonStyler {

    public static void styleButton(JButton button) {
        Font buttonFont = new Font("SansSerif", Font.PLAIN, 16);
        Color foregroundColor = Color.DARK_GRAY;
        Color backgroundColor = new Color(220, 220, 220); // Light Gray
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
    }

    public static void stylePrimaryButton(JButton button) {
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        Color foregroundColor = Color.WHITE;
        Color backgroundColor = new Color(0, 123, 255); // Primary Blue
        Border border = BorderFactory.createEmptyBorder(12, 25, 12, 25);
        button.setFont(buttonFont);
        button.setForeground(foregroundColor);
        button.setBackground(backgroundColor);
        button.setBorder(border);
        button.setFocusPainted(false);
        button.setOpaque(true);
    }

    public static void styleExitButton(JButton button) {
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        Color foregroundColor = Color.WHITE;
        Color backgroundColor = new Color(170, 74, 68); // Primary Blue
        Border border = BorderFactory.createEmptyBorder(12, 25, 12, 25);
        button.setFont(buttonFont);
        button.setForeground(foregroundColor);
        button.setBackground(backgroundColor);
        button.setBorder(border);
        button.setFocusPainted(false);
        button.setOpaque(true);
    }
}
