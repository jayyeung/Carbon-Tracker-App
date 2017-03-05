package com.as3.parmjohal.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SelectRouteActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_route);

        setTitle("Select Route");
    }

    public static Intent makeIntent(Context context){
         Intent intent = new Intent(context, SelectRouteActivity.class);
         return intent;
    }
}
