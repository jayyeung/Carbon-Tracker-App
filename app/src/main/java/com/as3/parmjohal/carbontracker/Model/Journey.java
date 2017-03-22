package com.as3.parmjohal.carbontracker.Model;

import android.util.Log;

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
    private Transportation transportation = null;
    private double co2 = 0;
    private double CO2_COVERTOR = 8.89;
    private Date date = new Date();

    public Journey(Transportation transportation , Route route) {
        this.transportation = transportation;
        this.route = route;
        calculateCO2();
    }

    public void calculateCO2() {

        boolean checkElectricity = transportation.getFuelType().equals("Electricity");
        boolean checkBus = transportation.getFuelType().equals("Bus");
        boolean checkSkyTrain = transportation.getFuelType().equals("Skytrain");
        boolean checkWalk = transportation.getFuelType().equals("Walk");
        boolean checkBike = transportation.getFuelType().equals("Bike");

        if(!checkElectricity && !checkSkyTrain && !checkBus && !checkWalk) {

            if (transportation.getFuelType().equals("Diesel")) {
                CO2_COVERTOR = 10.16;
            }

            double hwyGallons = (double) route.getHwyDistance() / (double) transportation.getHighwayFuel() ;
            double cityGallons = route.getCityDistance() / transportation.getCityFuel();
            double hwyCO2 = CO2_COVERTOR * hwyGallons;
            double cityCO2 = CO2_COVERTOR * cityGallons;

            co2 = hwyCO2 + cityCO2;

        }
        else if(checkSkyTrain || checkBus)
        {
            co2 = route.getCityDistance() * 0.0087;
        }
        else {
            co2 = 0.0;
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
        DateFormat df = new SimpleDateFormat("dd MMM yyyy");
        return df.format(date) ;
    }

    public Date getDateInfoRaw() {
        return date;
    }

    public String getDateInfo2()
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        return df.format(date);
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

    public Transportation getTransportation() {
        return transportation;
    }

    public void setTransportation(Transportation trans){
        this.transportation = trans;
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

    public String getTransportationType()
    {
        return transportation.getObjectType();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Journey journey = (Journey) o;

        if (route != null ? !route.equals(journey.route) : journey.route != null) return false;
        if (transportation != null ? !transportation.equals(journey.transportation) : journey.transportation != null)
            return false;
        return date != null ? date.equals(journey.date) : journey.date == null;
    }

    public static Journey copy(Journey journey) {

        String[] tokens = journey.getDateInfo2().split("/");
        int day = Integer.parseInt(tokens[0]);
        int month = Integer.parseInt(tokens[1]);
        int year = Integer.parseInt(tokens[2]);

        Journey journeyClone = new Journey(journey.getTransportation(),journey.getRoute());
        journeyClone.setDate(year,month - 1,day);

        return journeyClone;
    }

}
