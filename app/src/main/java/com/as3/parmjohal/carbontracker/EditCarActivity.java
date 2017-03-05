package com.as3.parmjohal.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EditCarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_car);

        setTitle("Edit Transportation");
    }

    public static Intent makeIntent(Context context) {
            Intent intent = new Intent(context, EditCarActivity.class);
            return intent;

    }
}
