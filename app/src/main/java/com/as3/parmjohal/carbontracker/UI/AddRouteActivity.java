package com.as3.parmjohal.carbontracker.UI;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.as3.parmjohal.carbontracker.Model.CarbonTrackerModel;
import com.as3.parmjohal.carbontracker.R;
import com.as3.parmjohal.carbontracker.Model.Route;
import com.as3.parmjohal.carbontracker.SharedPreference;
/**
 * --AddRouteActivity--
 * Creates a Route for creating journey
 * User can save, or not save the created route.
 * Will Proceed to ConfirmTripActivity.
 */

public class AddRouteActivity extends AppCompatActivity {
    private CarbonTrackerModel model;
    private String route;
    private int highway;
    private int city;
    private Route addRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);
        Log.i("CO2 ", getString(R.string.add_route));

        model = CarbonTrackerModel.getCarbonTrackerModel(this);
        setTitle(getString(R.string.add_route));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreference.saveCurrentModel(this);
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

                EditText routeName = (EditText) findViewById(R.id.addRouteName);
                EditText distanceHighway = (EditText) findViewById(R.id.addHighwayDistance);
                EditText distanceCity = (EditText) findViewById(R.id.addCityDistance);
                route = routeName.getText().toString();
                String stringHighway = distanceHighway.getText().toString();
                String stringCity = distanceCity.getText().toString();




                //Error Checker
                if (route.isEmpty() == true ||(stringCity.isEmpty() == true && stringHighway.isEmpty() == true)) {
                    Toast.makeText(AddRouteActivity.this, R.string.please_complete_info, Toast.LENGTH_SHORT).show();
                    return false;
                }
                else if(containsOnlyNumbers(stringCity) == false || containsOnlyNumbers(stringCity) == false){
                    Toast.makeText(AddRouteActivity.this, R.string.Distance_must_contain_only_numbers, Toast.LENGTH_SHORT).show();
                    return false;
                }
                else if( stringCity.isEmpty() == false && stringHighway.isEmpty() == true){
                city = Integer.parseInt(stringCity);
                highway=0;
                    addRoute= new Route(city,highway,route);

            }
             else if( stringCity.isEmpty() == true&& stringHighway.isEmpty() == false){
                city =0;
                highway=Integer.parseInt(stringHighway);
                    addRoute= new Route(city,highway,route);

            }
            else {
                    highway = Integer.parseInt(stringHighway);
                    city = Integer.parseInt(stringCity);
                    addRoute= new Route(city,highway,route);
                }

                if (checkDuplicate(addRoute)){
                    Toast.makeText(AddRouteActivity.this, R.string.This_Route_Already_Exist, Toast.LENGTH_SHORT).show();
                }
                    else {
                    showDialog();
                    return true;
                    }

            default:
                return super.onOptionsItemSelected(item);
        }

    }
    public  void showDialog() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        View V = LayoutInflater.from(this)
                .inflate(R.layout.save_route_message, null);

        b.setTitle(R.string.save_route);
        final TextView msg = new TextView(this);
        b.setView(msg);
        b.setPositiveButton(R.string.dont_save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                model.setCurrentRoute(addRoute);
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();

            }
        });
        b.setNegativeButton(R.string.save, new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                model.setCurrentRoute(addRoute);
                model.getRouteManager().add(addRoute);
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();


            }
        });
        b.setView(V);
        b.show();
    }

    private boolean checkDuplicate(Route route) {
        for (int i = 0; i < model.getRouteManager().getRouteCollection().size(); i++) {
            if (route.equals(model.getRouteManager().getRouteCollection().get(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean containsOnlyNumbers(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i)))
                return false;
        }
        return true;
    }

    public static Intent makeIntent(Context context) {
        Intent intent= new Intent(context, AddRouteActivity.class);
        return intent;
    }
}
