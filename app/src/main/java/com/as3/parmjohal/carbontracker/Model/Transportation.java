package com.as3.parmjohal.carbontracker.Model;

import android.util.Log;

import com.as3.parmjohal.carbontracker.R;

/**
 * A base class for every object that "is-a" transportation. Ex) Skytrain
 * holds the basic info needed in the jouney class in order to calculate the CO2
 */

public class Transportation {

    private int highwayFuel1 = 0;
    private double cityFuel1 = 0;
    private String fuelType1 = " ";
    private String info = " ";
    private String objectType = " ";
    private int image = R.drawable.cycle;

    public Transportation(int highwayFuel, int cityFuel, String fuelType, String objectType,int image) {
        this.highwayFuel1 = highwayFuel;
        this.cityFuel1 = cityFuel;
        this.fuelType1 = fuelType;
        this.objectType = objectType;
        this.image=image;

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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getObjectType() {
        return objectType;
    }

    public String getInfo(){
        Log.i("Info ", "In Transportation class: " + info);
        return info;
    }

}
