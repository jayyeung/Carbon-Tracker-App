package com.as3.parmjohal.carbontracker.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.as3.parmjohal.carbontracker.Model.Bus;
import com.as3.parmjohal.carbontracker.Model.CarbonTrackerModel;
import com.as3.parmjohal.carbontracker.Model.Route;
import com.as3.parmjohal.carbontracker.R;
import com.as3.parmjohal.carbontracker.SharedPreference;

/**
 * -- BusActivity--
 * Creates new Bus Transportation and Route
 * by entering bus name and distance.
 * Proceeds to ConfirmTripActivity on completion
 */

public class BusActivity extends AppCompatActivity {
    private EditText editBus;
    private EditText editDistance;
    private String bus;
    private String distance;
    private int intDistance;
    private CarbonTrackerModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);

        SharedPreference.saveCurrentModel(this);
        model = CarbonTrackerModel.getCarbonTrackerModel(this);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle(getString(R.string.bus_journey));

        editBus = (EditText) findViewById(R.id.editBus);
        editDistance = (EditText) findViewById(R.id.editDistance);

    }


    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_activity_confirm_decline, menu);
        return true;

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_confirm:
                bus = editBus.getText().toString();
                distance=editDistance.getText().toString();
                intDistance= Integer.parseInt(distance);
                Bus newBus = new Bus(bus,intDistance);
                model.getBusManager().add(newBus);
                Route route = newBus.getRoute();
                model.setCurrentTransportation(newBus);
                model.setCurrentRoute(route);
                if(model.isEditJourney()){
                    finish();
                }
                else {
                    Intent intent = ConfirmTripActivity.makeIntent(BusActivity.this);
                    finish();
                    startActivity(intent);
                }


                break;


            default:
                break;
        }
        return true;

    }
    public static Intent makeIntent(Context context) {
        Intent intent= new Intent(context, BusActivity.class);
        return intent;
    }
}
