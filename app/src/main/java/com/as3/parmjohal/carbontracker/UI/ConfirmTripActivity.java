package com.as3.parmjohal.carbontracker.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.as3.parmjohal.carbontracker.Model.Bike;
import com.as3.parmjohal.carbontracker.Model.Bus;
import com.as3.parmjohal.carbontracker.Model.Journey;
import com.as3.parmjohal.carbontracker.Model.Car;
import com.as3.parmjohal.carbontracker.Model.CarbonTrackerModel;
import com.as3.parmjohal.carbontracker.Model.Skytrain;
import com.as3.parmjohal.carbontracker.Model.Transportation;
import com.as3.parmjohal.carbontracker.Model.Walk;
import com.as3.parmjohal.carbontracker.R;
import com.as3.parmjohal.carbontracker.Model.Route;
import com.as3.parmjohal.carbontracker.SharedPreference;

/**
 * --ConfirmTripActivity--
 * Shows final details of the journey that was created.
 * User can confirm if journey is right, and add journey
 * to the dashboard. Otherwise, user can go back to
 * correct.
 */

public class ConfirmTripActivity extends AppCompatActivity {

    public static CarbonTrackerModel model;
    private Journey journey;

    public static final int REQUEST_CODE_CAR= 2017;
    public static final int REQUEST_CODE_ROUTE= 2018;
    public static final int REQUEST_CODE_DATE= 2019;

    boolean checkElectricity;
    boolean checkBus;
    boolean checkSkyTrain;
    boolean checkWalk;
    boolean checkBike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_trip);

        model = CarbonTrackerModel.getCarbonTrackerModel(this);

        if(model.isConfirmTrip()) {
            setTitle("Confirm Trip");

        }
        else {
            setTitle("Journey Data");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getJourneyData();
        boolean checkElectricity = journey.getTransportation().getFuelType().equals("Electricity");
        boolean checkBus = journey.getTransportation().getFuelType().equals("Bus");
        boolean checkSkyTrain = journey.getTransportation().getFuelType().equals("Skytrain");
        boolean checkWalk = journey.getTransportation().getFuelType().equals("Walk");
        boolean checkBike = journey.getTransportation().getFuelType().equals("Bike");


        populateTextViews();


        // if a menu option was selected at dashboard:
        int menu_select_id = getIntent().getIntExtra("menu_select", 0);
        if (menu_select_id != 0) { OptionSelect(menu_select_id); }

    }

    private void populateTextViews(){
        Log.i("TAG", ""+ journey.toString());
        setupTextView(R.id.display_CO2, String.format("%.2f", journey.getCo2()));
        setupTextView(R.id.display_CO2Units, "kg of CO₂");
        setupTextView(R.id.date, "On " + journey.getDateInfo());
        if(journey.getTransportation()instanceof Bike ||journey.getTransportation()instanceof Walk|| journey.getTransportation()instanceof Skytrain ||journey.getTransportation()instanceof Bus) {
            setupTextView(R.id.display_CarName, journey.getTransportation().getObjectType());
            setupTextView(R.id.display_MainCar, "");
        }
        else {
            setupTextView(R.id.display_MainCar, journey.getTransportationInfo());
            setupTextView(R.id.display_CarName, journey.getTransportation().getObjectType());
        }
        setupTextView(R.id.display_RouteName, journey.getRoute().getRouteName());
        setupTextView(R.id.display_Route, journey.getRouteInfo());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreference.saveCurrentModel(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if(model.isConfirmTrip()) {
            getMenuInflater().inflate(R.menu.menu_activity_confirm_decline, menu);
            return true;
        }
        else{
            getMenuInflater().inflate(R.menu.menu_activity_journey, menu);
            return true;
        }

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        OptionSelect(item.getItemId());
        return true;

    }

    public void OptionSelect(int id) {
        switch (id) {
            case android.R.id.home:

                finish();
                break;
            case R.id.action_confirm:

                //Add code to add journey to collection here
                Log.i("Journey: ", "ADDED");
                addJourney();

                Intent intent = MainActivity.makeIntent(ConfirmTripActivity.this);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//reset activity stack
                model.setCurrentCar(null);
                model.setCurrentTransportation(null);
                model.setCurrentRoute(null);
                finish();
                startActivity(intent);//go to dashboard

                break;
            case R.id.editCar_id:
                editCar();
                break;
            case R.id.editRoute_id:
                editRoute();
                break;
            case R.id.edit_date_id:
                editDate();
                break;
            case R.id.delete_id:
                model.getJourneyManager().remove(journey);
                model.getDayManager().recalculateDays(model.getJourneyManager().getJourneyCollection());
                model.setConfirmTrip(true);
                finish();
                break;
            default:
                break;
        }
    }

    private void addJourney() {
        model.getJourneyManager().add(journey);
        model.getDayManager().recalculateDays(model.getJourneyManager().getJourneyCollection());
        Log.i("test", journey.getDateInfo2());
    }

    private void getJourneyData()
    {
        if(model.isConfirmTrip()) {
            Log.i("Journey: ", "New Journey");
            Transportation currentTransportation = model.getCurrentTransportation();
            Route currentRoute = model.getCurrentRoute();

            Log.i("CO2", currentTransportation.toString());

            journey = new Journey(currentTransportation, currentRoute);


        }
        else {
            Log.i("Journey: ", "Clicked Journey");
            journey = model.getCurrentJouney();
            Intent intent = new Intent();
            setResult(Activity.RESULT_OK, intent);
        }
    }

    private void editCar()
    {
        model.setEditJourney(true);
        Intent intent = SelectTransActivity.makeIntent(ConfirmTripActivity.this);
        startActivityForResult(intent,REQUEST_CODE_CAR);
    }

    private void editRoute(){
        model.setEditJourney(true);
        Intent intent;
        if(model.getCurrentJouney().getTransportation() instanceof Bike == true ||model.getCurrentJouney().getTransportation() instanceof Walk == true){
            intent = WalkActivity.makeIntent(ConfirmTripActivity.this);
            startActivityForResult(intent,REQUEST_CODE_ROUTE);
        }
        else if (model.getCurrentJouney().getTransportation() instanceof Skytrain == true){
            intent = TrainActivity.makeIntent(ConfirmTripActivity.this);
            startActivityForResult(intent,REQUEST_CODE_ROUTE);
        }
        else if (model.getCurrentJouney().getTransportation() instanceof Bus == true){
            intent = BusActivity.makeIntent(ConfirmTripActivity.this);
            startActivityForResult(intent,REQUEST_CODE_ROUTE);
        }
        else{
            intent = SelectRouteActivity.makeIntent(ConfirmTripActivity.this);
            startActivityForResult(intent,REQUEST_CODE_ROUTE);
        }


    }

    private void editDate()
    {
        model.setEditJourney(true);
        startActivityForResult(new Intent(ConfirmTripActivity.this,CalenderActivity.class),REQUEST_CODE_DATE);
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
                    Log.i("test",""+journey.getTransportationType());
                    if(journey.getTransportationType().equals("Car")==false){
                        Log.i("true",""+journey.getTransportationType());
                    journey.setRoute(model.getCurrentRoute());
                    }
                    journey.setTransportation(model.getCurrentTransportation());
                    journey.calculateCO2();
                    model.getDayManager().recalculateDays(model.getJourneyManager().getJourneyCollection());
                    Log.i("test",""+journey.toString());
                    model.setCurrentCar(null);
                    model.setCurrentTransportation(null);
                    model.setEditJourney(false);

                    restart();

                    break;


                }
            case (REQUEST_CODE_ROUTE):
                if(resultCode == Activity.RESULT_OK){
                    if(journey.getTransportationType().equals("Car")==false){
                        Log.i("true",""+journey.getTransportationType());
                        journey.setRoute(model.getCurrentRoute());
                    }
                    journey.setTransportation(model.getCurrentTransportation());
                    journey.calculateCO2();
                    model.getDayManager().recalculateDays(model.getJourneyManager().getJourneyCollection());
                    Log.i("test",""+journey.toString());
                    model.setCurrentCar(null);
                    model.setCurrentTransportation(null);
                    model.setEditJourney(false);

                    restart();

                    break;

                }
            case (REQUEST_CODE_DATE):
                if (resultCode == Activity.RESULT_OK) {
                    model.setEditJourney(false);
                    model.getDayManager().recalculateDays(model.getJourneyManager().getJourneyCollection());
                    restart();

                    break;


                }
        }


    }
    private void restart()
    {
        populateTextViews();
    }
}
