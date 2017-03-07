package com.as3.parmjohal.carbontracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddRouteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        setTitle("Add Route");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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
                String route = routeName.getText().toString();
                String highway = distanceHighway.getText().toString();
                String city = distanceCity.getText().toString();

                //Error Checker
                if (route.isEmpty() == true || city.isEmpty() == true || highway.isEmpty() == true) {
                    Toast.makeText(AddRouteActivity.this, "Please complete info", Toast.LENGTH_SHORT).show();

                } else {
                    if (containsOnlyNumbers(city) == false || containsOnlyNumbers(city) == false) {
                        Toast.makeText(AddRouteActivity.this, "Distancemust contain only numbers", Toast.LENGTH_SHORT).show();



                    }
                    else {
                        //add Name, and distance to new route
                        //add route to collection
                        finish();
                        return true;
                    }


                }


            case R.id.action_decline:

                finish();

                return true;


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
        Intent intent= new Intent(context, AddRouteActivity.class);
        return intent;
    }
}
