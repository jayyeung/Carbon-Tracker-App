package com.as3.parmjohal.carbontracker.Model;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ParmJohal on 2017-03-22.
 */

public class TipManager {

    ArrayList<String> tips = new ArrayList<>();

    static int count =-1;
    public void add(String tip)
    {
        if(!tips.contains(tip))
        tips.add(tip);
    }

    private String getLargestCO2_tip(Context context)
    {
        String largestCO2_Tip = "Bitch";
        CarbonTrackerModel model = CarbonTrackerModel.getCarbonTrackerModel(context);
        JourneyManager journeyManager = model.getJourneyManager();
        ArrayList<Journey> allJourneys = journeyManager.getJourneyCollection();

        double largestCO2 = 0;
        for(Journey journey: allJourneys)
        {
            if(journey.getCo2() > largestCO2)
            {
                largestCO2 = journey.getCo2();
                largestCO2_Tip = journey.getTip();
            }
        }
        //Log.i("Tip", " " + largestCO2_Tip);
        if(!tips.contains(largestCO2_Tip))
        tips.add(0,largestCO2_Tip);

        return largestCO2_Tip;
    }

    public String getTip(Context context)
    {
        getLargestCO2_tip(context);
        count++;
        if(count >= tips.size())
            count = 0;

        return tips.get(count);
    }

    public void displayAll()
    {
        for(String tip: tips)
        {
            Log.i("Tip" ,tip);
        }
    }

}
