package com.as3.parmjohal.carbontracker.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;

import com.as3.parmjohal.carbontracker.R;

public class WalkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Walk/Bike Journey");
        
        createRadioButtons();
        
    }

    private void createRadioButtons() {
        RadioGroup type = (RadioGroup) findViewById(R.id.radioList);

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



                Intent intent = ConfirmTripActivity.makeIntent(WalkActivity.this);
                finish();
                startActivity(intent);//go to dashboard

                break;


            default:
                break;
        }
        return true;

    }
    public static Intent makeIntent(Context context) {
        Intent intent= new Intent(context, WalkActivity.class);
        return intent;
    }
}
