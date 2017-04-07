package com.as3.parmjohal.carbontracker.Model;

import com.as3.parmjohal.carbontracker.R;

/**
 * Holds the name of the walk trip and distance
 * generates a Route object that can be used later to make a Walk journey
 */

public class Walk extends Transportation{

    private String name = " ";
    private int distance = 0;
    private Route walkRoute;

    private int walkImage = R.drawable.walk;

    public Walk(String name, int distance) {
        super(0, 0, " ","walk",0);

        this.name = name;
        this.distance = distance;
        setupSuperClass();
        walkRoute = new Route(distance,0,"Walk Trip: "+name);
    }

    private void setupSuperClass() {
        super.setFuelType("Walk");
        super.setCityFuel(0);
        super.setHighwayFuel(0);
        super.setImage(walkImage);
    }
    public Route getRoute()
    {
        return walkRoute;
    }

}
