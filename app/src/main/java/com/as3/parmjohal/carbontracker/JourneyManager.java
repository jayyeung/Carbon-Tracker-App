package com.as3.parmjohal.carbontracker;

import java.util.ArrayList;

/**
 * Created by ParmJohal on 2017-03-05.
 */

public class JourneyManager {

    private ArrayList<Journey> routeCollection = new ArrayList<>();

    public void add(Journey journey)
    {
        routeCollection.add(journey);
    }

    public void remove(Journey journey)
    {
        routeCollection.remove(journey);
    }

    public void update(Journey currentjourney, Journey newJourney)
    {
        int index = routeCollection.indexOf(currentjourney);
        routeCollection.set(index, newJourney);
    }

    public ArrayList<Journey> getRouteCollection() {
        return (ArrayList<Journey>) routeCollection.clone();
    }
}
