/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Salesman;

/**
 *
 * @author hp
 */
public class Salesman {

    String ID;
    String name;
    String password;
    String securityQuestion;
    String securityAnswer;

    public Salesman(String ID, String name, String password, String securityQuestion, String securityAnswer) {
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
    
    public void setSecurityPassword(String SecurityPassword) {
        this.securityAnswer = SecurityPassword;
    }
    
    public void setSecurityQuestion(String SecurityQuestion) {
        this.securityQuestion = SecurityQuestion;
    }
    

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }
}

