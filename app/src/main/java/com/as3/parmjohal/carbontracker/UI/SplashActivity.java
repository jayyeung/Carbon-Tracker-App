package com.as3.parmjohal.carbontracker.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.as3.parmjohal.carbontracker.Model.CarbonTrackerModel;
import com.as3.parmjohal.carbontracker.Model.Day;
import com.as3.parmjohal.carbontracker.Model.DayManager;
import com.as3.parmjohal.carbontracker.Model.Journey;
import com.as3.parmjohal.carbontracker.R;

public class SplashActivity extends AppCompatActivity {
    CarbonTrackerModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        model = CarbonTrackerModel.getCarbonTrackerModel(this);
        model.setEditJourney(false);
        model.setConfirmTrip(true);

        // dd/MM/yy

        Log.i("Day", " Day Started");
        DayManager dayManager = model.getDayManager();
        dayManager.getPast28Days_Journeys(21,03,17);
        for(Journey journey: dayManager.getPast28Days_Journeys(21,03,17))
        {
            Log.i("Day", "Journey: " + journey.toString());
        }

        final Button new_journey = (Button) findViewById(R.id.new_journey_btn),
                to_dash = (Button) findViewById(R.id.continue_dashboard);

        // continue to dashboard
        to_dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDashBoard();
            }
        });

        // create a new journey
        new_journey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_journey.performLongClick();
            }
        });
        registerForContextMenu(new_journey);

        playAnimations();
    }

    // set immersive mode
    // this hides the status and navigation mode
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        if(hasFocus) {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }

    // play animations
    public void playAnimations() {
        ImageView icon = (ImageView) findViewById(R.id.icon),
                  title = (ImageView) findViewById(R.id.title);

        Button new_journey = (Button) findViewById(R.id.new_journey_btn),
               to_dash = (Button) findViewById(R.id.continue_dashboard);

        // animations
        Animation fade_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        fade_in.setDuration(1500);

        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in);
        slide_in.setStartOffset(1000);
        slide_in.setDuration(1000);

        // execute
        icon.startAnimation(fade_in);
        title.startAnimation(fade_in);

        new_journey.startAnimation(slide_in);
        to_dash.startAnimation(fade_in);
    }

    // New Journey Popup
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select Menu");
        menu.add(0, v.getId(), 0, "Transportation");
        menu.add(0, v.getId(), 0, "Food");
        menu.add(0, v.getId(), 0, "Electricity");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Transportation") {
            startNewJourney();
        }
        return true;
    }

    // start new journey
    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        return intent;
    }

    private void startNewJourney() {
        Intent intent = SelectCarActivity.makeIntent(SplashActivity.this);
        startActivity(intent);
    }

    private void openDashBoard()
    {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
