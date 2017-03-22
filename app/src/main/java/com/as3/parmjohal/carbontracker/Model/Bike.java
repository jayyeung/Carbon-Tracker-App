package com.as3.parmjohal.carbontracker.Model;

/**
 * Created by ParmJohal on 2017-03-20.
 */

public class Bike extends  Transportation{

    private String name = " ";
    private int distance = 0;
    private Route bikeRoute;

    public Bike(String name, int distance) {
        super(0, 0, " ");

        this.name = name;
        this.distance = distance;
        setupSuperClass();
        bikeRoute = new Route(distance,0,"Bike Trip: "+name);
    }

    private void setupSuperClass() {
        super.setFuelType("Bike");
        super.setCityFuel(0);
        super.setHighwayFuel(0);
    }
    public Route getRoute()
    {
        return bikeRoute;
    }
}
