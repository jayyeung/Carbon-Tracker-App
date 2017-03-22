package com.as3.parmjohal.carbontracker.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.as3.parmjohal.carbontracker.Model.CarbonTrackerModel;
import com.as3.parmjohal.carbontracker.Model.Route;
import com.as3.parmjohal.carbontracker.Model.Skytrain;
import com.as3.parmjohal.carbontracker.R;
import com.as3.parmjohal.carbontracker.SharedPreference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class UtilitiesActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_START_DATE =111;
    private static final int REQUEST_CODE_END_DATE =222;
    private CarbonTrackerModel model;
    private EditText editElec;
    private EditText editGas;
    private EditText editPersons;
    private Button start;
    private Button end;

    private int electricity;
    private int gas;
    private int persons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utilities);
        SharedPreference.saveCurrentModel(this);
        model = CarbonTrackerModel.getCarbonTrackerModel(this);
        setupButtons();



    }

    private void setupButtons() {
        start = (Button) findViewById(R.id.startButton);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(UtilitiesActivity.this,CalenderActivity.class),REQUEST_CODE_START_DATE);
            }
        });
        end = (Button) findViewById(R.id.endButton);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(UtilitiesActivity.this,CalenderActivity.class),REQUEST_CODE_END_DATE);
            }
        });
    }


    public static Intent makeIntent(Context context) {
        Intent intent= new Intent(context, UtilitiesActivity.class);
        return intent;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case (REQUEST_CODE_START_DATE):
                if(resultCode == Activity.RESULT_OK){
                    DateFormat df = new SimpleDateFormat("dd MMM yyyy");
                    String dateString = df.format(model.getCurrentDate());
                    start.setText(dateString);
                    //pass date to utilities

                    model.setCurrentDate(null);
                    break;


                }
            case (REQUEST_CODE_END_DATE):
                if(resultCode == Activity.RESULT_OK){
                    DateFormat df = new SimpleDateFormat("dd MMM yyyy");
                    String dateString = df.format(model.getCurrentDate());
                    end.setText(dateString);
                    //pass date to utilities

                    model.setCurrentDate(null);
                    break;


                }

        }


    }
}
