package com.as3.parmjohal.carbontracker.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.as3.parmjohal.carbontracker.Model.Skytrain;
import com.as3.parmjohal.carbontracker.R;

import java.util.ArrayList;
import java.util.List;

public class TrainActivity extends AppCompatActivity {
    Skytrain newTrain;
    ArrayList<String> stopsList =  new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("SkyTrain Journey");

        Skytrain newTrain = new Skytrain("","","","");
        String[] stops = newTrain.getStops();
        for(int i=0;i<stops.length;i++){
            stopsList.add(stops[i]);
        }

       setUpSpinners();

    }

    private void setUpSpinners() {
        Spinner start = (Spinner) findViewById(R.id.startSpinner);
        Spinner end = (Spinner) findViewById(R.id.endSpinner);
        // Spinner Drop down elements
        ArrayList<String> categories = stopsList;
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        start.setAdapter(dataAdapter2);
        end.setAdapter(dataAdapter2);
        start.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        end.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }





    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_confirm:



                Intent intent = ConfirmTripActivity.makeIntent(TrainActivity.this);
                finish();
                startActivity(intent);//go to dashboard

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
