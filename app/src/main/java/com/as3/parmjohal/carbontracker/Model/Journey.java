package com.as3.parmjohal.carbontracker.Model;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ParmJohal on 2017-03-03.
 */

public class Journey {

    private Route route = null;
    private Transportation transportation = null;
    private double co2 = 0;
    private double CO2_COVERTOR = 8.89;
    private Date date = new Date();

    public Journey(Transportation transportation , Route route ) {
        this.transportation = transportation;
        this.route = route;
        calculateCO2();
    }

    public void calculateCO2() {

        boolean checkElectricity = transportation.getFuelType().equals("Electricity");
        boolean checkBus = transportation.getFuelType().equals("Bus");
        boolean checkSkyTrain = transportation.getFuelType().equals("Skytrain");

        if(!checkElectricity && !checkSkyTrain && !checkBus ) {

            if (transportation.getFuelType().equals("Diesel")) {
                CO2_COVERTOR = 10.16;
            }

            double hwyGallons = (double) route.getHwyDistance() / (double) transportation.getHighwayFuel() ;
            double cityGallons = (double) route.getCityDistance() / (double) transportation.getCityFuel();
            double hwyCO2 = CO2_COVERTOR * hwyGallons;
            double cityCO2 = CO2_COVERTOR * cityGallons;

            co2 = hwyCO2 + cityCO2;

        }
        else if(checkSkyTrain || checkBus)
        {
            co2 = route.getCityDistance() * transportation.getCityFuel();
        }
        else {
            co2 = 0;
        }

    }


    public String getTransportationInfo()
    {
        return transportation.getInfo();
    }

    public String getRouteInfo()
    {
        return route.getRouteName() + " City: " + route.getCityDistance() + " HWY: " + route.getHwyDistance();
    }

    public String getDateInfo()
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        return df.format(date) ;
    }

    public String getDateInfo2()
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        return df.format(date) ;
    }

    public double getCo2() {
        return co2;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public void setCar(Car car) {
        this.transportation = car;
    }

    public void setDate(int year, int month,int day){
        Calendar cal = Calendar.getInstance();
        cal.set(year,month,day);
        date = cal.getTime();
    }

    public String getTransportationName()
    {
        return transportation.getName();
    }

    public Car getCar() {
        return (Car) transportation;
    }



    @Override
    public String toString() {
        return "Journey{" +
                "route=" + route +
                ", car=" + transportation +
                ", co2=" + co2 +
                ", CO2_COVERTOR=" + CO2_COVERTOR +
                '}';
    }
}
