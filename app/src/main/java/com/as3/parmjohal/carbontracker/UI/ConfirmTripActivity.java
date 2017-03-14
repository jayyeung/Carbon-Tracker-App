package com.as3.parmjohal.carbontracker.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.as3.parmjohal.carbontracker.Model.Journey;
import com.as3.parmjohal.carbontracker.Model.Car;
import com.as3.parmjohal.carbontracker.Model.CarbonTrackerModel;
import com.as3.parmjohal.carbontracker.R;
import com.as3.parmjohal.carbontracker.Model.Route;

public class ConfirmTripActivity extends AppCompatActivity {

    CarbonTrackerModel model = CarbonTrackerModel.getCarbonTrackerModel(this);
    private Journey journey;

    public static final int REQUEST_CODE_CAR= 2017;
    public static final int REQUEST_CODE_ROUTE= 2018;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_trip);

        if(model.isConfirmTrip()) {
            setTitle("Confirm Trip");

        }
        else {
            setTitle("Journey Data");
            Button editButton = (Button) findViewById(R.id.editCarButton);
            editButton.setVisibility(View.VISIBLE);
            editButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    editCar();

                }
            });

        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getJourneyData();

        populateTextViews();
    }

    private void populateTextViews(){
        Log.i("TAG", ""+ journey.toString());
        setupTextView(R.id.display_CO2, String.format("%.2f", journey.getCo2()));
        setupTextView(R.id.display_CO2Units, "kg of CO₂");
        setupTextView(R.id.date, "On " + journey.getDateInfo());
        setupTextView(R.id.display_mainCar, journey.getCarInfo());
        setupTextView(R.id.display_Route, journey.getRouteInfo());

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        if(model.isConfirmTrip()) {
            getMenuInflater().inflate(R.menu.menu_activity_confirm_decline, menu);
            return true;
        }
      return false;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_confirm:

                //Add code to add journey to collection here

                if(model.isConfirmTrip()) {
                    Log.i("Journey: ", "ADDED");
                    addJourney();
                }
                else {
                    Log.i("Journey: ", "NOT ADDED");
                    model.setConfirmTrip(true);
                }

                Intent intent = MainActivity.makeIntent(ConfirmTripActivity.this);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//reset activity stack
                model.setCurrentCar(null);
                model.setCurrentRoute(null);
                finish();
                startActivity(intent);//go to dashboard

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void addJourney() {
        model.getJourneyManager().add(journey);
    }

    private void getJourneyData()
    {
        if(model.isConfirmTrip()) {
            Log.i("Journey: ", "New Journey");
            Car currentCar = model.getCurrentCar();
            Route currentRoute = model.getCurrentRoute();
            journey = new Journey(currentCar, currentRoute);
        }
        else {
            Log.i("Journey: ", "Clicked Journey");
            journey = model.getCurrentJouney();
        }
    }

    private void editCar()
    {
        model.setEditJourney(true);
        Intent intent = SelectCarActivity.makeIntent(ConfirmTripActivity.this);
        startActivityForResult(intent,REQUEST_CODE_CAR);
    }

    private void editRoute(){
        model.setEditJourney(true);
        Intent intent = SelectCarActivity.makeIntent(ConfirmTripActivity.this);
        startActivityForResult(intent,REQUEST_CODE_ROUTE);
    }

    private void setupTextView(int id, String displayString)
    {
        TextView textView = (TextView) findViewById(id);
        textView.setText(displayString);
    }

    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, ConfirmTripActivity.class);
        return intent;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case (REQUEST_CODE_CAR):
                if (resultCode == Activity.RESULT_OK) {
                    journey.setCar(model.getCurrentCar());
                    journey.calculateCO2();
                    model.setCurrentCar(null);
                    model.setEditJourney(false);
                    populateTextViews();

                    break;


                }
            case (REQUEST_CODE_ROUTE):
                if(resultCode == Activity.RESULT_OK){

                    break;

                }
        }

    }


}
