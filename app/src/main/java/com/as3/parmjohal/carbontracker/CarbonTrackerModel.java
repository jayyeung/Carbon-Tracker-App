package com.as3.parmjohal.carbontracker;

import android.content.Context;

import java.io.IOException;

/**
 * Created by ParmJohal on 2017-03-05.
 */

public class CarbonTrackerModel {

    protected static CarbonTrackerModel carbonTrackerModel = new CarbonTrackerModel();

    private static CarManager carManager ;
    private RouteManager routeManager = new RouteManager();
    private JourneyManager journeyManager = new JourneyManager();

    private static int count = 0;

    private CarbonTrackerModel() {

    }

    public static CarbonTrackerModel getCarbonTrackerModel(Context context)
    {
        if(count== 0) {
            carManager = new CarManager(context);
        }
        count++;
        return carbonTrackerModel;
    }


    public CarManager getCarManager() {
        return carManager;
    }

    public RouteManager getRouteManager()
    {
        return routeManager;
    }

    public JourneyManager getJourneyManager() {
        return journeyManager;
    }
}
