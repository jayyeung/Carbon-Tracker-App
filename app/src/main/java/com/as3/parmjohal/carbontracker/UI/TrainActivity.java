package com.as3.parmjohal.carbontracker.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.as3.parmjohal.carbontracker.Model.CarbonTrackerModel;
import com.as3.parmjohal.carbontracker.Model.Route;
import com.as3.parmjohal.carbontracker.Model.Skytrain;
import com.as3.parmjohal.carbontracker.R;
import com.as3.parmjohal.carbontracker.SharedPreference;

import java.util.ArrayList;
import java.util.List;

public class TrainActivity extends AppCompatActivity {
    private Skytrain newTrain;
    private ArrayList<String> stopsList =  new ArrayList<String>();
    private String TransitLine;
    private String startStation;
    private String endStation;
    private CarbonTrackerModel model;

    private String[] stops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);
        SharedPreference.saveCurrentModel(this);
        model = CarbonTrackerModel.getCarbonTrackerModel(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("SkyTrain Journey");

        newTrain = new Skytrain("","","","");

        setUpLineSpinner();

    }

    private void setUpLineSpinner() {
        final Spinner line = (Spinner) findViewById(R.id.lineSpinner);
        final ArrayList<String> lines = new ArrayList<String>();
        lines.add("Expo Line");
        lines.add("Millennium Line");
        lines.add("Canada Line");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lines);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        line.setAdapter(dataAdapter1);
        line.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TransitLine = (String) line.getSelectedItem();
                Log.i("test",""+TransitLine);
                stopsList=new ArrayList<String>();
                stops = newTrain.getStops(TransitLine);
                for(int i=0;i<stops.length;i++){
                    stopsList.add(stops[i]);
                    Log.i("test",""+stops[i]);
                }
                setUpStationSpinners();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                TransitLine = "Expo Line";
                stops = newTrain.getStops(TransitLine);
                stopsList=new ArrayList<String>();
                for(int i=0;i<stops.length;i++){
                    stopsList.add(stops[i]);
                }
                setUpStationSpinners();

            }
        });
    }

    private void setUpStationSpinners() {
        final Spinner start = (Spinner) findViewById(R.id.startSpinner);
        final Spinner end = (Spinner) findViewById(R.id.endSpinner);
        // Spinner Drop down elements

        final ArrayList<String> categories = stopsList;
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        start.setAdapter(dataAdapter2);
        end.setAdapter(dataAdapter2);
        start.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                startStation=(String) start.getSelectedItem();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                startStation = (String) categories.get(0);

            }

        });
        end.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                endStation=(String) end.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                endStation=(String) categories.get(0);

            }
        });
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
                newTrain= new Skytrain(startStation,endStation,"",TransitLine);
                model.setCurrentTransportation(newTrain);
                model.getSkytrainManager().add(newTrain);
                Route route= newTrain.getRoute();
                model.setCurrentRoute(route);
                if(model.isEditJourney()){
                    finish();
                }
                else {
                    Intent intent = ConfirmTripActivity.makeIntent(TrainActivity.this);
                    finish();
                    startActivity(intent);//go to dashboard
                }

                break;


            default:
                break;
        }
        return true;

    }

    public static Intent makeIntent(Context context) {
        Intent intent= new Intent(context, TrainActivity.class);
        return intent;
    }
}
