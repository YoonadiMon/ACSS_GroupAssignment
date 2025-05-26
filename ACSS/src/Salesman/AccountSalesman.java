/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Salesman;

/**
 *
 * @author hp
 */
public abstract class AccountSalesman {

    protected String ID;
    protected String name;
    protected String password;
    protected String securityQuestion;
    protected String securityAnswer;

    public AccountSalesman(String ID, String name, String password, String securityQuestion, String securityAnswer) {
        this.ID = ID;
        this.name = name;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

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

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    // Abstract method for subclasses to implement
    public abstract void displayRole();
}


