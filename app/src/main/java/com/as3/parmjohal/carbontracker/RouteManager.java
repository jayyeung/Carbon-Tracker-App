package com.as3.parmjohal.carbontracker;

import java.util.ArrayList;

/**
 * Created by ParmJohal on 2017-03-03.
 */

public class RouteManager {

    private ArrayList<Route> routeCollection = new ArrayList<>();

    public void add(Route route)
    {
        routeCollection.add(route);
    }

    public void remove(Route route)
    {
        routeCollection.remove(route);
    }

    public void update(Route currentRoute, Route newRoute)
    {
        int index = routeCollection.indexOf(currentRoute);
        routeCollection.set(index, newRoute);
    }

    public ArrayList<Route> getRouteCollection ()
    {
        return (ArrayList<Route>) routeCollection.clone();
    }
}
