/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Salesman;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author hp
 */
/**
 * Base class for GUI windows.
 */
public abstract class BaseGUI extends JFrame {

    public BaseGUI(String title, int width, int height) {
        super(title);
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeComponents();
    }

    // Abstract method to be implemented by subclasses
    protected abstract void initializeComponents();
}
