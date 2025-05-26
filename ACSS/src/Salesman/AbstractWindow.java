/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// AbstractWindow.java - Abstract base class for window management
package Salesman;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractWindow {

    protected JFrame frame;
    protected int windowWidth;
    protected int windowHeight;

    public AbstractWindow(int width, int height, String title) {
        this.windowWidth = width;
        this.windowHeight = height;
        initializeFrame(title);
    }

    private void initializeFrame(String title) {
        frame = new JFrame(title);
        frame.setSize(windowWidth, windowHeight);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    protected void showFrame() {
        frame.setVisible(true);
    }

    protected void closeFrame() {
        frame.dispose();
    }
}
