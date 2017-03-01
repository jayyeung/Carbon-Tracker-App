package com.as3.parmjohal.carbontracker;

/**
 * Created by ParmJohal on 2017-03-01.
 */

public class Car {

    private String make = "";
    private String model = "";
    private int year = 0;
    private double highwayFuel = 0;
    private double cityFuel = 0;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getHighwayFuel() {
        return highwayFuel;
    }

    public void setHighwayFuel(double highwayFuel) {
        this.highwayFuel = highwayFuel;
    }

    public double getCityFuel() {
        return cityFuel;
    }

    public void setCityFuel(double cityFuel) {
        this.cityFuel = cityFuel;
    }

    @Override
    public String toString() {
        return "Car{" +
                "make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", highwayFuel=" + highwayFuel +
                ", cityFuel=" + cityFuel +
                '}';
    }
}
