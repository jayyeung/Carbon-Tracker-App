package com.as3.parmjohal.carbontracker.Model;

import java.util.ArrayList;

/**
 * keeps track of all the routes
 * provides basic functions such as add/edit/delet and look for
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
