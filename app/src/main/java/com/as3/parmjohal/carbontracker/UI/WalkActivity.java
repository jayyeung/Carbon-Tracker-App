package com.as3.parmjohal.carbontracker.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.as3.parmjohal.carbontracker.Model.Bike;
import com.as3.parmjohal.carbontracker.Model.CarbonTrackerModel;
import com.as3.parmjohal.carbontracker.Model.Route;
import com.as3.parmjohal.carbontracker.Model.Walk;
import com.as3.parmjohal.carbontracker.R;
import com.as3.parmjohal.carbontracker.SharedPreference;
/**
 * --WalkActivity--
 * User creates new Walk journey and route
 * for new Journey.
 */

public class WalkActivity extends AppCompatActivity {
    private String type="";
    private String name="";
    private EditText editDistance;
    private EditText editName;
    private CarbonTrackerModel model;
    private String distance="";
    private int intDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);

        SharedPreference.saveCurrentModel(this);
        model = CarbonTrackerModel.getCarbonTrackerModel(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Walk/Bike Journey");
        
        createRadioButtonListener();

        editDistance = (EditText) findViewById(R.id.editDistance);
        editName = (EditText) findViewById(R.id.editName);
        
    }

    private void createRadioButtonListener() {
       // RadioGroup type = (RadioGroup) findViewById(R.id.radioList);
        RadioButton button = (RadioButton) findViewById(R.id.radioButton);
        RadioButton button2 = (RadioButton) findViewById(R.id.radioButton2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setType(getString(R.string.WALK));
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setType(getString(R.string.bike));
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
                name = editName.getText().toString();
                distance=editDistance.getText().toString();

                if (distance.isEmpty()==true||name.isEmpty()==true) {
                    Toast.makeText(WalkActivity.this, getString(R.string.please_complete_info), Toast.LENGTH_SHORT).show();
                    return false;
                }
                else {
                    intDistance = Integer.parseInt(distance);
                    if (type.equals(getString(R.string.WALK))) {


                        Walk newWalk = new Walk(name, intDistance);
                        model.getWalkManager().add(newWalk);
                        model.setCurrentTransportation(newWalk);

                        Route route = newWalk.getRoute();
                        model.setCurrentRoute(route);

                        if(model.isEditJourney()){
                            finish();
                        }

                        Intent intent = ConfirmTripActivity.makeIntent(WalkActivity.this);
                        finish();
                        startActivity(intent);//go to dashboard
                        break;

                    } else if (type.equals(getString(R.string.bike))) {

                        Bike newBike = new Bike(name, intDistance);
                        model.getBikeManager().add(newBike);
                        model.setCurrentTransportation(newBike);

                        Route route = newBike.getRoute();
                        model.setCurrentRoute(route);
                        if(model.isEditJourney()){
                            finish();
                        }
                        else {
                            Intent intent = ConfirmTripActivity.makeIntent(WalkActivity.this);
                            finish();
                            startActivity(intent);//go to dashboard
                            break;
                        }

                    }
                    else{
                        Toast.makeText(WalkActivity.this, getString(R.string.Select_Transportation), Toast.LENGTH_SHORT).show();
                    }


                }

            default:
                break;
        }
        return true;

    }
    public static Intent makeIntent(Context context) {
        Intent intent= new Intent(context, WalkActivity.class);
        return intent;
    }

    public void setType(String type) {
        this.type = type;
    }
}
