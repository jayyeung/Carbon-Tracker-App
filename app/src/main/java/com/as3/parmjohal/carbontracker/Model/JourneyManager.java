package com.as3.parmjohal.carbontracker.Model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ParmJohal on 2017-03-05.
 */

public class JourneyManager {
    private ArrayList<Journey> journeyCollection = new ArrayList<>();
    private int[] totalWalkDistance = {0};

    public void add(Journey journey)
    {
        journeyCollection.add(journey);
        if(journey.getTransportationType().equals("walk"))
            totalWalkDistance[0] = totalWalkDistance[0] + (int) journey.getRoute().getCityDistance();
    }

    public void remove(Journey journey)
    {
        journeyCollection.remove(journey);
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

    public int getTotalWalkDistance() {
        return totalWalkDistance[0];
    }

    public void displayAll()
    {
        for(Journey journey: journeyCollection)
        {
            Log.i("Journey", journey.toString());
        }
    }
}
