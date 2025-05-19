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

    public Salesman(String ID,String name, String password) {
        this.ID = ID;
        this.name=name;
        this.password = password;
    }

    public String getID() {
        return ID;
    }
    
    public String getName(){
        return name;
    }

    public String getPassword() {
        return password;
    }
    
    //nothing

}
