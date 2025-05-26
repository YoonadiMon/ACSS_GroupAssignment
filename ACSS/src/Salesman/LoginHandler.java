/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
// LoginHandler.java - Interface for handling login events
package Salesman;

public interface LoginHandler {
    void handleLogin(String id, String password);
    void handleForgotPassword();
    void handleBack();
}