/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Salesman;

/**
 * Subclass that extends the abstract AccountSalesman class.
 *
 * Demonstrates: - Inheritance (inherits all properties and methods from
 * AccountSalesman) - Polymorphism (overrides abstract method displayRole)
 */
public class Salesman extends AccountSalesman {

    // === INHERITANCE: Calls the constructor of the superclass ===
    public Salesman(String ID, String name, String password, String securityQuestion, String securityAnswer) {
        super(ID, name, password, securityQuestion, securityAnswer);
    }
    // === POLYMORPHISM: Provides its own implementation of displayRole ===
    @Override
    public void displayRole() {
        System.out.println("Role: Salesman");
    }
    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }
    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

   
}

//public class Salesman {
//
//    // === ENCAPSULATION: Data is kept private and accessed via getters/setters ===
//    String ID;
//    String name;
//    String password;
//    String securityQuestion;
//    String securityAnswer;
//
//    // === CONSTRUCTOR: Used to initialize the object when it's created ===
//    public Salesman(String ID, String name, String password, String securityQuestion, String securityAnswer) {
//        this.ID = ID;
//        this.name = name;
//        this.password = password;
//        this.securityQuestion = securityQuestion;
//        this.securityAnswer = securityAnswer;
//    }
//
//    // === ENCAPSULATION: Getters provide read-only access to private attributes ===
//    public String getID() {
//        return ID;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    // === ENCAPSULATION: Setters allow controlled modification of attributes ===
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public void setSecurityPassword(String SecurityPassword) {
//        this.securityAnswer = SecurityPassword;
//    }
//
//    public void setSecurityQuestion(String SecurityQuestion) {
//        this.securityQuestion = SecurityQuestion;
//    }
//
//    // === ENCAPSULATION: Getters for security-related fields ===
//    public String getSecurityQuestion() {
//        return securityQuestion;
//    }
//
//    public String getSecurityAnswer() {
//        return securityAnswer;
//    }
//}
