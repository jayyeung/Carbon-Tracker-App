package com.as3.parmjohal.carbontracker.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.as3.parmjohal.carbontracker.Model.CarbonTrackerModel;
import com.as3.parmjohal.carbontracker.R;
import com.as3.parmjohal.carbontracker.Model.Route;
/**
 * --EditRouteActivity--
 * Edits existing route in routelist and sends it back
 * with the correct info.
 * Proceeds back to SelectRouteActivity
 */
public class EditRouteActivity extends AppCompatActivity {
    private CarbonTrackerModel model;
    private EditText routeName;
    private EditText distanceHighway;
    private EditText distanceCity;
    private int highway;
    private int city;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_route);
        model = CarbonTrackerModel.getCarbonTrackerModel(this);
        setTitle(getString(R.string.Edit_Route));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        routeName = (EditText) findViewById(R.id.editRouteName);
        distanceHighway = (EditText) findViewById(R.id.editHighwayDistance);
        distanceCity = (EditText) findViewById(R.id.editCityDistance);

        routeName.setText(model.getCurrentRoute().getRouteName());
        distanceCity.setText(""+model.getCurrentRoute().getCityDistance());
        distanceHighway.setText(""+model.getCurrentRoute().getHwyDistance());
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

                String route = routeName.getText().toString();
                String stringHighway = distanceHighway.getText().toString();
                String stringCity = distanceCity.getText().toString();

                //Error Checker
                if (route.isEmpty() == true || (stringCity.isEmpty() == true && stringHighway.isEmpty() == true)) {
                    Toast.makeText(EditRouteActivity.this, R.string.Please_complete_info, Toast.LENGTH_SHORT).show();
                    return false;

                } else {
                    if (containsOnlyNumbers(stringCity) == false && containsOnlyNumbers(stringHighway) == false) {
                        Toast.makeText(EditRouteActivity.this, R.string.Distancemust_contain_only_numbers, Toast.LENGTH_SHORT).show();
                        return false;}

                        else if(containsOnlyNumbers(stringCity) == false || containsOnlyNumbers(stringHighway) == false){
                            Toast.makeText(EditRouteActivity.this, R.string.Distance_must_contain_only_numbers, Toast.LENGTH_SHORT).show();
                            return false;
                        }
                        else if( stringCity.isEmpty() == false && stringHighway.isEmpty() == true){
                            city = Integer.parseInt(stringCity);
                            highway=0;
                            model.getCurrentRoute().setRouteName(route);
                            model.getCurrentRoute().setHwyDistance(highway);
                            model.getCurrentRoute().setCityDistance(city);


                        }
                        else if( stringCity.isEmpty() == true&& stringHighway.isEmpty() == false){
                            city =0;
                            highway=Integer.parseInt(stringHighway);
                            model.getCurrentRoute().setRouteName(route);
                            model.getCurrentRoute().setHwyDistance(highway);
                            model.getCurrentRoute().setCityDistance(city);

                        }
                        else {
                            highway = Integer.parseInt(stringHighway);
                            city = Integer.parseInt(stringCity);
                            model.getCurrentRoute().setRouteName(route);
                            model.getCurrentRoute().setHwyDistance(highway);
                            model.getCurrentRoute().setCityDistance(city);
                    }


                        model.setCurrentRoute(null);
                        Intent intent = new Intent();
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                        return true;
                    }






            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private boolean containsOnlyNumbers(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i)))
                return false;
        }
        return true;
    }

    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, EditRouteActivity.class);
        return intent;
    }
}
