package com.as3.parmjohal.carbontracker.Model;

import android.util.Log;

import com.as3.parmjohal.carbontracker.R;
import com.as3.parmjohal.carbontracker.UI.ConfirmTripActivity;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Holds both a means of transportation the user has entered and the length of the route
 * With both we can calculate the total CO2 for the specific journey
 */


public class Journey {

    private Route route = null;
    private Transportation transportation = null;
    private double co2 = 0;
    private double CO2_COVERTOR = 8.89;
    private Date date = new Date();
    private String tip = " ";

    // Transportation Variables

    private int highwayFuel1 = 0;
    private double cityFuel1 = 0;
    private String fuelType1 = " ";
    private String info = " ";
    private String objectType = " ";
    private int image = R.drawable.cycle;


    //**************************

    public Journey(Transportation transportation , Route route) {

        this.transportation = transportation;
        this.route = route;

        this.highwayFuel1 = transportation.getHighwayFuel();
        this.cityFuel1 = transportation.getCityFuel();
        this.fuelType1 = transportation.getFuelType();
        this.info = transportation.getInfo();
        this.objectType = transportation.getObjectType();
        this.image = transportation.getImage();

        calculateCO2();
    }

    public void calculateCO2() {

        boolean checkElectricity = transportation.getFuelType().equals("Electricity");
        boolean checkBus = transportation.getFuelType().equals("Bus");
        boolean checkSkyTrain = transportation.getFuelType().equals("Skytrain");
        boolean checkWalk = transportation.getFuelType().equals("Walk");
        boolean checkBike = transportation.getFuelType().equals("Bike");

        if(!checkElectricity && !checkSkyTrain && !checkBus && !checkWalk && !checkBike) {

            if (fuelType1.equals("Diesel")) {
                CO2_COVERTOR = 10.16;
            }

            double hwyGallons = (double) route.getHwyDistance() / (double) highwayFuel1 ;
            double cityGallons = route.getCityDistance() / cityFuel1;
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

        DecimalFormat df = new DecimalFormat("####0.00");

        if(co2 > 0) {
            String tip = "You used " + df.format(co2) + " of CO2. Did you know the Skytrain produces the least amount of CO2 out of all Public Transit";
            this.tip = tip;
        }
        else if(!checkElectricity && transportation.getObjectType().equals("car")){
            String tip = "You used " + df.format(co2) + " of CO2, Need a new Car? Electric cars use less CO2";

            this.tip = tip;
        }
        else {
            String tip = "You used " + df.format(co2) + ", You are Saving the Environment. Maybe try another something besides " + transportation.getObjectType() + " For More Fun";
            this.tip = tip;
        }

        CarbonTrackerModel.getModel().getTipsManager().add(tip);
    }

    public String getTip() {
        return tip;
    }

    public String getTransportationInfo()
    {
        return info;
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

    public int getImage() {
        return image;
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
    public boolean equals(Object obj) {

        Journey j = (Journey) obj;

        return j.getRoute().equals(route) && j.getTransportationType().equals(objectType);
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
