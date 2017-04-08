package com.as3.parmjohal.carbontracker.Model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Keeps track of all journeys
 * provides basic functions such as add/edit/delet and look for
 */

public class JourneyManager {
    private ArrayList<Journey> journeyCollection = new ArrayList<>();
    private ArrayList<Car> cars = new ArrayList<>();

    double totalCO2_Car = 0;
    double totalCO2_Skytrain = 0;
    double totalCO2_Bus = 0;

    private int[] totalWalkDistance = {0};

    public void add(Journey journey)
    {
        journeyCollection.add(journey);
        addToArray(journey);
        if(journey.getTransportationType().equals("walk"))
            totalWalkDistance[0] = totalWalkDistance[0] + (int) journey.getRoute().getCityDistance();
    }

    private void addToArray(Journey journey)
    {
        try {
            switch (journey.getTransportationType()) {
                case "car":
                    cars.add((Car) journey.getTransportation());
                    totalCO2_Car += journey.getCo2();
                    break;
                case "bus":
                    System.out.println("******************" + journey.getCo2());
                    totalCO2_Bus += journey.getCo2();
                    break;
                case "skytrain":
                    totalCO2_Skytrain += journey.getCo2();
                    break;
            }
        }
        catch (java.lang.ClassCastException e)
        {

        }
    }
    public void remove(Journey journey)
    {
        Log.i("Day", "All Journey's in Collection");

        for(Journey journey1: journeyCollection)
        {
            Log.i("Day", journey1.toString());
        }

        if(journeyCollection.remove(journey)){
            Log.i("Day", "REMOVED " + journey.toString());
        }
        else {
            Log.i("Day", "Not Removed " + journey.toString());

        }
    }

    public void update(Journey currentjourney, Journey newJourney)
    {
        int index = journeyCollection.indexOf(currentjourney);
        journeyCollection.set(index, newJourney);
    }
    public void recalculateCarbon(){
        for(int i=0;i<journeyCollection.size();i++){
            journeyCollection.get(i).calculateCO2();
        }
    }

    public ArrayList<Journey> getJourneyCollection() {
        ArrayList<Journey> journeys = journeyCollection;

        // else return all Journeys
        return journeys;
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    public double getTotalCO2_Car() {
        return totalCO2_Car;
    }

    public double getTotalCO2_Skytrain() {
        return totalCO2_Skytrain;
    }

    public double getTotalCO2_Bus() {
        return totalCO2_Bus;
    }

    public void displayAll()
    {
        for(Journey journey: journeyCollection)
        {
            Log.i("Journey", journey.toString());
        }
    }
}
