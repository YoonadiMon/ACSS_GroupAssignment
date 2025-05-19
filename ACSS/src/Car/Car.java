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

    public Car(String carId, String brand, int price, String status, String salesmanId) {
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
