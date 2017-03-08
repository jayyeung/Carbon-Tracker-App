package com.as3.parmjohal.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmTripActivity extends AppCompatActivity {

    CarbonTrackerModel model = CarbonTrackerModel.getCarbonTrackerModel(this);
    private Journey journey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_trip);

        setTitle("Confirm Trip");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getJourneyData();
        setupTextView(R.id.display_CO2, String.format("%.2f", journey.getCo2()));
        setupTextView(R.id.display_CO2Units, "kg of CO2");
        setupTextView(R.id.display_mainCar, journey.getCarInfo());
        setupTextView(R.id.display_Route, journey.getRouteInfo());
        Toast.makeText(this,journey.getDateInfo(), Toast.LENGTH_SHORT).show();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_confirm_decline, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_confirm:

                //Add code to add journey to collection here

                addJourney();

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
        Car currentCar = model.getCurrentCar();
        Route currentRoute = model.getCurrentRoute();
        journey = new Journey(currentCar,currentRoute);
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


}
