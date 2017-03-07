package com.as3.parmjohal.carbontracker;

import android.content.Context;

import java.io.IOException;

/**
 * Created by ParmJohal on 2017-03-05.
 */

public class CarbonTrackerModel {

    protected static CarbonTrackerModel carbonTrackerModel = new CarbonTrackerModel();

    private static CarManager carManager ;
    private static VehicleData vehicleData;
    private RouteManager routeManager = new RouteManager();
    private JourneyManager journeyManager = new JourneyManager();

    private static int count = 0;
    private Car currentCar;
    private Route currentRoute;

    private CarbonTrackerModel() {

    }

    public static CarbonTrackerModel getCarbonTrackerModel(Context context)
    {
        if(count== 0) {
            carManager = new CarManager(context);
            try {
                vehicleData = new VehicleData(context);
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    public VehicleData getVehicleData()
    {
        return vehicleData;
    }

    public Car getCurrentCar() {
        return currentCar;
    }

    public void setCurrentCar(Car currentCar) {
        this.currentCar = currentCar;
    }

    public Route getCurrentRoute() {
        return currentRoute;
    }

    public void setCurrentRoute(Route currentRoute) {
        this.currentRoute = currentRoute;
    }
}