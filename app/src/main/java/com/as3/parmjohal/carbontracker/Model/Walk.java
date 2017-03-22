package com.as3.parmjohal.carbontracker.Model;

/**
 * Created by ParmJohal on 2017-03-20.
 */

public class Walk extends Transportation{

    private String name = " ";
    private int distance = 0;
    private Route walkRoute;

    public Walk(String name, int distance) {
        super(0, 0, " ","walk");

        this.name = name;
        this.distance = distance;
        setupSuperClass();
        walkRoute = new Route(distance,0,"Walk Trip: "+name);
    }

    private void setupSuperClass() {
        super.setFuelType("Walk");
        super.setCityFuel(0);
        super.setHighwayFuel(0);
    }
    public Route getRoute()
    {
        return walkRoute;
    }

}
