package com.as3.parmjohal.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CarbonTrackerModel model = CarbonTrackerModel.getCarbonTrackerModel(this);
        startNewJourney();
    }

    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }
    /**
     Call This Method to Start New Journey
     **/
    private void startNewJourney() {
        Intent intent = SelectCarActivity.makeIntent(MainActivity.this);
        startActivity(intent);
    }


}
