package com.as3.parmjohal.carbontracker.Model;

import android.content.Context;
import android.util.Log;

import com.as3.parmjohal.carbontracker.SharedPreference;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ParmJohal on 2017-03-05.
 */

public class CarbonTrackerModel {

    protected static CarbonTrackerModel carbonTrackerModel = new CarbonTrackerModel();

    private CarManager carManager  = new CarManager();
    private static VehicleData vehicleData;
    private RouteManager routeManager = new RouteManager();
    private JourneyManager journeyManager = new JourneyManager();
    private DayManager dayManager = new DayManager();

    private Manager<Skytrain> skytrainManager = new Manager<>();
    private Manager<Walk> walkManager = new Manager<>();
    private Manager<Bike> bikeManager = new Manager<>();

    public static ArrayList<Car> cars = new ArrayList<Car>();

    private static int count = 0;
    private Car currentCar;
    private Route currentRoute;
    public int currentPos;
    private Journey currentJouney;
    private boolean confirmTrip = true;
    private boolean editJourney = false;

    private CarbonTrackerModel() {

    }

    public static CarbonTrackerModel getCarbonTrackerModel(Context context)
    {
        if(count == 0) {
            try {
                vehicleData = new VehicleData(context);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(SharedPreference.getCurrentModel(context) != null && count == 0)
        {
            carbonTrackerModel = SharedPreference.getCurrentModel(context);
        }

        count++;
        return carbonTrackerModel;
    }

//*********************************************************************
//          MANAGERS
//*********************************************************************

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

    public Manager<Skytrain> getSkytrainManager() {
        return skytrainManager;
    }

    public Manager<Walk> getWalkManager() {
        return walkManager;
    }

    public Manager<Bike> getBikeManager() {
        return bikeManager;
    }

    public DayManager getDayManager() {
        return dayManager;
    }
    //*********************************************************************


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

    public int getCurrentPos(){return currentPos;}

    public void setCurrentPos(int currentPos){this.currentPos = currentPos;}

    public Route getCurrentRoute() {
        return currentRoute;
    }

    public void setCurrentRoute(Route currentRoute) {
        this.currentRoute = currentRoute;
    }

    public Journey getCurrentJouney() {
        return currentJouney;
    }

    public void setCurrentJouney(Journey currentJouney) {
        this.currentJouney = currentJouney;
    }

    public boolean isConfirmTrip() {
        return confirmTrip;
    }

    public void setConfirmTrip(boolean confirmTrip) {
        this.confirmTrip = confirmTrip;
    }

    public boolean isEditJourney(){return  editJourney;}

    public void setEditJourney(boolean editJourney){this.editJourney = editJourney;}
}
