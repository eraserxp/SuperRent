/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author eraserxp
 */
public class VehicleSelection {

    private String vlicense;
    private String category;
    private String vehicleType;
    private String brand;
    private int odometer;
    
    public VehicleSelection() {
        
    }

    public VehicleSelection(String vlicense, String category, String vehicleType, String brand, int odometer) {
        this.vlicense = vlicense;
        this.category = category;
        this.vehicleType = vehicleType;
        this.brand = brand;
        this.odometer = odometer;
    }

    public String getVlicense() {
        return vlicense;
    }

    public void setVlicense(String vlicense) {
        this.vlicense = vlicense;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getOdometer() {
        return odometer;
    }

    public void setOdometer(int odometer) {
        this.odometer = odometer;
    }

}
