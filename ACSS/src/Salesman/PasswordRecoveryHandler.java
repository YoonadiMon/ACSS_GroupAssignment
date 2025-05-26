/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
// PasswordRecoveryHandler.java - Interface for handling password recovery events
package Salesman;

import javax.swing.*;

public interface PasswordRecoveryHandler {

    void handleNext(String id, JLabel statusLabel, JDialog dialog);

    void handleSubmit(Salesman salesman, String answer, JLabel statusLabel, JDialog dialog);

    void handleBack(JDialog dialog);

    void handleCancel(JDialog dialog);
}
