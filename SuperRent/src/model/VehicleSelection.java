/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author eraserxp
 */
public class VehicleSelection {

    private final SimpleStringProperty vlicense;
    private final SimpleStringProperty category;
    private final SimpleStringProperty vehicleType;
    private final SimpleStringProperty brand;
    private final SimpleIntegerProperty odometer;

    public VehicleSelection() {
        this(null, null, null, null, 0);
    }

    public VehicleSelection(String vlicense, String category, String vehicleType, String brand, int odometer) {
        this.vlicense = new SimpleStringProperty(vlicense);
        this.category = new SimpleStringProperty(category);
        this.vehicleType = new SimpleStringProperty(vehicleType);
        this.brand = new SimpleStringProperty(brand);
        this.odometer = new SimpleIntegerProperty(odometer);
    }

    public String getVlicense() {
        return vlicense.get();
    }

    public void setVlicense(String vlicense) {
        this.vlicense.set(vlicense);
    }

    public SimpleStringProperty vlicenseProperty() {
        return vlicense;
    }

    public String getCategory() {
        return category.get();
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public SimpleStringProperty categoryProperty() {
        return category;
    }

    public String getVehicleType() {
        return vehicleType.get();
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType.set(vehicleType);
    }

    public SimpleStringProperty vehicleProperty() {
        return vehicleType;
    }

    public String getBrand() {
        return brand.get();
    }

    public void setBrand(String brand) {
        this.brand.set(brand);
    }

    public SimpleStringProperty brandProperty() {
        return brand;
    }

    public int getOdometer() {
        return odometer.get();
    }

    public void setOdometer(int odometer) {
        this.odometer.set(odometer);
    }

    public SimpleStringProperty odometerProperty() {
        return new SimpleStringProperty( Integer.toString(odometer.get())+".0" );
    }

}
