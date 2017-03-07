package com.as3.parmjohal.carbontracker;

import android.util.Log;

/**
 * Created by ParmJohal on 2017-03-03.
 */

public class Journey {

    private Route route = null;
    private Car car = null;
    private double co2 = 0;
    private double CO2_COVERTOR = 8.89;

    public Journey(Car car , Route route ) {
        this.car = car;
        this.route =route;
        calculateCO2();
    }

    private void calculateCO2() {
        if (car.getFuelType().equals("Diesel")) {
            CO2_COVERTOR = 10.16;
        }
        else if(car.getFuelType().equals("Electricity"))
        {
            CO2_COVERTOR = 0;
        }


        double hwyGallons = (double) route.getHwyDistance() / (double) car.getHighwayFuel() ;
        double cityGallons = (double) route.getCityDistance() / (double) car.getCityFuel();
        double hwyCO2 = CO2_COVERTOR * hwyGallons;
        double cityCO2 = CO2_COVERTOR * cityGallons;

        co2 = hwyCO2 + cityCO2;

    }

    public String getCarInfo()
    {
        return "" + car.getYear()+", " + car.getMake()+ " " + car.getModel();
    }


    public String getRouteInfo()
    {
        return route.getRouteName() + " City: " + route.getCityDistance() + " HWY: " + route.getHwyDistance();
    }

    public double getCo2() {
        return co2;
    }

    @Override
    public String toString() {
        return "Journey{" +
                "route=" + route +
                ", car=" + car +
                ", co2=" + co2 +
                ", CO2_COVERTOR=" + CO2_COVERTOR +
                '}';
    }
}