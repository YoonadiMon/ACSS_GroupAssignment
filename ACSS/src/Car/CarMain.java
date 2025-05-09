/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Car;

/**
 *
 * @author hp
 */
public class CarMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CarList.initializeCars();
        CarList.saveCarListToFile();

        CarRequest.saveInitialCarRequestsToFile();

    }

}
