package com.as3.parmjohal.carbontracker.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.as3.parmjohal.carbontracker.R;

public class BusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Bus Journey");
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



                Intent intent = ConfirmTripActivity.makeIntent(BusActivity.this);
                finish();
                startActivity(intent);//go to dashboard

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
