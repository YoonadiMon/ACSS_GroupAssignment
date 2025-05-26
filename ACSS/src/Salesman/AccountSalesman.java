/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Salesman;

/**
 * This abstract class defines a generic Salesman account with basic attributes
 * and an abstract method to be implemented by subclasses.
 *
 * Demonstrates: - Abstraction (abstract class and method) - Encapsulation
 * (private/protected fields with getters and setters)
 */
public abstract class AccountSalesman {

    // === ENCAPSULATION: Protected fields can be accessed by subclasses ===
    protected String ID;
    protected String name;
    protected String password;
    protected String securityQuestion;
    protected String securityAnswer;

    // === CONSTRUCTOR: Used to initialize all fields ===
    public AccountSalesman(String ID, String name, String password, String securityQuestion, String securityAnswer) {
        this.ID = ID;
        this.name = name;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }

    // === GETTERS: Read-only access to private data ===
    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    // === SETTERS: Allow controlled modification of fields ===
    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    // === ABSTRACTION: Force subclasses to implement this method ===
    public abstract void displayRole();
}
