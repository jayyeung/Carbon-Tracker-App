package com.as3.parmjohal.carbontracker.Model;

import java.util.ArrayList;

/**
 * Created by ParmJohal on 2017-03-20.
 */

public class Manager<T> {

    private ArrayList<T> collection = new ArrayList<>();

    public void add(T object)
    {
        collection.add(object);
    }

    public void remove(T object)
    {
        collection.remove(object);
    }

    public void update(T object1, T object2)
    {
        int index = collection.indexOf(object1);
        collection.set(index, object2);
    }

    public ArrayList<T> getRouteCollection ()
    {
        return (ArrayList<T>) collection.clone();
    }
}
