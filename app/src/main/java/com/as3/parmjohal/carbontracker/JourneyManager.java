package com.as3.parmjohal.carbontracker;

import java.util.ArrayList;

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

    public ArrayList<Journey> getJourneyCollection() {
        return (ArrayList<Journey>) journeyCollection.clone();
    }
}
