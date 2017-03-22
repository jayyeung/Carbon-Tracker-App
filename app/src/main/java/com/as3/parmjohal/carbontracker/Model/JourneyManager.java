package com.as3.parmjohal.carbontracker.Model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ParmJohal on 2017-03-05.
 */

public class JourneyManager {
    private ArrayList<Journey> journeyCollection = new ArrayList<>();

    public void add(Journey journey)
    {
        journeyCollection.add(journey);
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
}
