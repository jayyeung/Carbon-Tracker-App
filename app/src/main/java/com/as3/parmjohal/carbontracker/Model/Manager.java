package com.as3.parmjohal.carbontracker.Model;

import android.util.Log;

import java.util.ArrayList;

/**
 * Takes in a generic type of object and holds that one type
 * provides basic functions such as add/edit/delet and look for
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

    public void displayAll()
    {
        for(T object: collection)
        {
            Log.i("Tip", object.toString());
        }
    }
    public ArrayList<T> getRouteCollection ()
    {
        return (ArrayList<T>) collection.clone();
    }
}
