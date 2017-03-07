package com.as3.parmjohal.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //make	model	year	trany	displ
        //Toyota,Truck 2WD,1985,Manual 5-spd,4

        CarbonTrackerModel model = CarbonTrackerModel.getCarbonTrackerModel(this);
    //********************
    //     TEST CODE
    //********************
        startNewJourney();

        Car car = new Car("Pama","Toyota", "Truck 2WD", 1985,"Manual 5-spd", 2.4);
        CarManager carManager = model.getCarManager();

        Car car1 = carManager.add(car);
        Route route = new Route(12,12, "WORK");
        Journey journey = new Journey(car1,route);
        Log.i("Journey ", journey.getCarInfo() +" " + journey.getRouteInfo() + " " + "CO2: " +journey.getCo2());
    }

    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, ConfirmTripActivity.class);
        return intent;
    }
    /**
     Call This Method to Start New Journey
     **/
    private void startNewJourney() {
        Intent intent = SelectCarActivity.makeIntent(MainActivity.this);
        startActivity(intent);
    }


}
