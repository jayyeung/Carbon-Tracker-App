package com.as3.parmjohal.carbontracker.Model;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ParmJohal on 2017-03-17.
 */

public class Skytrain extends Transportation{

    private int highwayFuel =0;
    private double cityFuel = 0;
    String fuelType = "Skytrain";
    String name = " ";

    Route route;

    private String startStation = " ";
    private String endStation = " ";
    private final double SPEED = 45.00;
    private final double MIN_TO_HOURS = 0.0166667;

    private String[] stops = {"King George","Surrey Central","Gateway","Scott Road","Columbia","New Westminster","22nd Street",
    "Edmonds","Royal Oak","Metrotown", "Patterson","Joyce","29th Avenue","Nanaimo","Commercial-Broadway","Main","Stadium-Chinatown",
    "Granville","Burrard","Waterfront"
    };
    private int[] minutes = {2,1,3,3,1,4,2,3,2,1,2,2,1,3,3,2,1,1,2};

    public Skytrain(String startStation, String endStation, String name) {
        super(0,0," ");
        this.name= name;
        this.startStation = startStation;
        this.endStation = endStation;
        setUpSuperClass();
        route = new Route((int) getDistance(),0,"Skytrain Trip: " + name);
    }

    private void setUpSuperClass()
    {
        super.setCityFuel(0.0087);
        super.setFuelType(fuelType);
    }

    public double getHours()
    {
        int stop1 = 0;
        int stop2 = 0;
        for(int i = 0; i < stops.length;i++)
        {
            if(stops[i].equals(startStation)) {
                stop1 = i;
            }

            if(stops[i].equals(endStation)) {
                stop2 = i;
            }
        }

        int difference = stop1 - stop2;
        int startValue = 0;
        int totalMinutes = 0;

        if (difference < 0)
        {
            startValue = stop2;
            for(int i = startValue - 1; i > stop1 - 1;i--)
            {
                totalMinutes += minutes[i];
            }
        }
        else {
            for(int i = startValue; i < stop1;i++)
            {
                totalMinutes += minutes[i];
            }
        }
        return (totalMinutes * MIN_TO_HOURS) ;
    }

    public String[] getStops(){
        return stops;
    }

    public Route getRoute()
    {
        return route;
    }

    public double getDistance()
    {
        return  getHours() * SPEED;
    }

    @Override
    public int getHighwayFuel() {
        return highwayFuel;
    }

    @Override
    public double getCityFuel() {
        return cityFuel;
    }

    @Override
    public String getFuelType() {
        return fuelType;
    }

    @Override
    public String getInfo() {
        return null;
    }

}
