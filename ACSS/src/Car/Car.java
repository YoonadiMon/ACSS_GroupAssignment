/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Car;

import java.util.ArrayList;

/**
 *
 * @author hp
 */
public class Car {

    private String carId;
    private String brand;
    private double price;
    private String status; // available / booked / paid / cancel
    private String salesmanId; // the ID of the salesman responsible for this car

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSalesmanId(String salesmanId) {
        this.salesmanId = salesmanId;
    }

    //constructor
    public Car(String carId, String brand, double price, String status, String salesmanId) {
        this.carId = carId;
        this.brand = brand;
        this.price = price;
        this.status = status;
        this.salesmanId = salesmanId;
    }

    // Getters and setters for all fields
    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public int getPrice() {
        return (int) price;
    }

    public String getStatus() {
        return status;
    }

    public String getSalesmanId() {
        return salesmanId;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
