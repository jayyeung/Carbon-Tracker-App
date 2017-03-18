package com.as3.parmjohal.carbontracker.Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ParmJohal on 2017-03-03.
 */

public class Journey {

    private Route route = null;
    private Car car = null;
    private double co2 = 0;
    private double CO2_COVERTOR = 8.89;
    private Date date = new Date();

    public Journey(Transportation transportation , Route route ) {
        this.transportation = transportation;
        this.route = route;

        calculateCO2();
    }


    private void calculateCO2() {
        Log.i("CO2", transportation.toString());
        if (transportation.getFuelType().equals("Diesel")) {
            CO2_COVERTOR = 10.16;
        }
        else if(transportation.getFuelType().equals("Electricity"))
        {
            CO2_COVERTOR = 0;
        }
        else if(transportation.getFuelType().equals("Bus"))
        {

        }

        double hwyGallons = (double) route.getHwyDistance() / (double) transportation.getHighwayFuel() ;
        double cityGallons = (double) route.getCityDistance() / (double) transportation.getCityFuel();
        double hwyCO2 = CO2_COVERTOR * hwyGallons;
        double cityCO2 = CO2_COVERTOR * cityGallons;

        co2 = hwyCO2 + cityCO2;

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
                ", car=" + transportation +
                ", co2=" + co2 +
                ", CO2_COVERTOR=" + CO2_COVERTOR +
                '}';
    }
}
