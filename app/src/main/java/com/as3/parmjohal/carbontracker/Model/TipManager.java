package com.as3.parmjohal.carbontracker.Model;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Keeps track of all tips and has the ability to find the journey with the largest CO2 and display that journey's tip
 */

public class TipManager {

    ArrayList<String> tips = new ArrayList<>();
    ArrayList<String> utilityTips = new ArrayList<>();

    static int count =-1;
    public void add(String tip)
    {
        if(!tips.contains(tip))
        tips.add(tip);
    }
    public void addUtilityTip(String tip)
    {
        if(!utilityTips.contains(tip))
            utilityTips.add(tip);
            utilityTips.remove("Tips will appear as you add entries.");
    }

    private String getLargestCO2_tip(Context context)
    {
        String largestCO2_Tip = "Tips will appear as you add entries.";
        CarbonTrackerModel model = CarbonTrackerModel.getCarbonTrackerModel(context);
        JourneyManager journeyManager = model.getJourneyManager();
        tips = new ArrayList<>();
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
        if(count ==0) {
            Collections.shuffle(tips);
        }

        getLargestCO2_tip(context);
        count++;
        if(count >= tips.size())
            count = 0;

        return tips.get(count);
    }
    public String getUtilityTip(Context context)
    {
        if(utilityTips.isEmpty())
        {
             return "Tips will appear as you add entries.";

        }
        if(count ==0) {
            Collections.shuffle(utilityTips);
        }

        getLargestCO2_tip(context);
        count++;
        if(count >= utilityTips.size())
            count = 0;

        return utilityTips.get(count);
    }

    public void displayAll()
    {
        for(String tip: tips)
        {
            Log.i("Tip" ,tip);
        }
    }

}
