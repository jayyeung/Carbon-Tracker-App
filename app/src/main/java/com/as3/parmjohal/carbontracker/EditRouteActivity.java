package com.as3.parmjohal.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EditRouteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_route);

        setTitle("Edit Route");
    }

    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, EditRouteActivity.class);
        return intent;
    }
}
