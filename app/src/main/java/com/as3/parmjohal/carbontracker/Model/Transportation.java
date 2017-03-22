package com.as3.parmjohal.carbontracker.Model;

import android.util.Log;

/**
 * Created by ParmJohal on 2017-03-18.
 */

public class Transportation {

    private int highwayFuel1 = 0;
    private double cityFuel1 = 0;
    private String fuelType1 = " ";
    private String info = " ";
    private String objectType = " ";

    public Transportation(int highwayFuel, int cityFuel, String fuelType, String objectType) {
        this.highwayFuel1 = highwayFuel;
        this.cityFuel1 = cityFuel;
        this.fuelType1 = fuelType;
        this.objectType = objectType;
    }

    public int getHighwayFuel() {
        return highwayFuel1;
    }

    public double getCityFuel() {
        return cityFuel1;
    }

    public String getFuelType() {
        return fuelType1;
    }

    public void setHighwayFuel(int highwayFuel) {
        this.highwayFuel1 = highwayFuel;
    }

    public void setCityFuel(double cityFuel) {
        this.cityFuel1 = cityFuel;
    }

    public void setFuelType(String fuelType) {
        this.fuelType1 = fuelType;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getObjectType() {
        return objectType;
    }

    public String getInfo(){
        Log.i("Info ", "In Transportation class: " + info);
        return info;
    }

}
