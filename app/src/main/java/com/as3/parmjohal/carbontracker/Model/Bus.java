package com.as3.parmjohal.carbontracker.Model;

import android.view.LayoutInflater;
import android.view.Menu;

import com.as3.parmjohal.carbontracker.R;

/**
 * Created by ParmJohal on 2017-03-17.
 */

public class Bus extends Transportation{

    private String name = " ";
    private int distance = 0;
    private Route busRoute;

    public Bus(String name, int distance) {
        super(0, 0, " ", "bus");
        this.name = name;
        this.distance = distance;
        setupSuperClass();
        busRoute = new Route(distance,0,"Bus Trip: "+name);

    }

    private void setupSuperClass() {
        super.setFuelType("Bus");
        super.setCityFuel(0.0087);
        super.setHighwayFuel(0);
    }
    public Route getRoute()
    {
        return busRoute;
    }
    @Override
    public String getInfo() {
        return "Bus Trip: "+name+" .";
    }



}
