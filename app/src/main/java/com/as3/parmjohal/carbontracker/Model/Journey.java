package com.as3.parmjohal.carbontracker.Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by ParmJohal on 2017-03-03.
 */

public class Journey {

    private Route route = null;
    private Car car = null;
    private double co2 = 0;
    private double CO2_COVERTOR = 8.89;
    private Date date = new Date();



    public Journey(Car car , Route route ) {
        this.car = car;
        this.route = route;

        calculateCO2();
    }

    public void calculateCO2() {
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

    public String getDateInfo()
    {
        DateFormat df = new SimpleDateFormat("d MMM yyyy");
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
        this.car = car;
    }

    public void setDate(int year, int month,int day){
        Calendar cal = Calendar.getInstance();
        cal.set(year,month,day);
        date = cal.getTime();



    }

    public Car getCar() {

        return car;
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
