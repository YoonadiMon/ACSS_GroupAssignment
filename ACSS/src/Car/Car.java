/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Car;

/**
 *
 * @author hp
 */
public class Car {

    String carId;
    String brand;
    double price;
    String status; // available / booked / paid / cancel
    String salesmanId; // the ID of the salesman responsible for this car

    public Car(String carId, String brand, double price, String status, String salesmanId) {
        this.carId = carId;
        this.brand = brand;
        this.price = price;
        this.status = status;
        this.salesmanId = salesmanId;
    }

    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getSalesmanId() {
        return salesmanId;
    }

}
