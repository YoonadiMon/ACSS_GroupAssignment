/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Manager;

/**
 *
 * @author hp
 */
public class ManagerMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SalesmanList.initializeSalesman();
        SalesmanList.saveInitializedSalesmanDataToFile();
        
    }
    
}
