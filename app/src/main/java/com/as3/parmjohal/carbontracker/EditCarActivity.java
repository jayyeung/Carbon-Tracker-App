package com.as3.parmjohal.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class EditCarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_car);

        setTitle("Edit Transportation");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_confirm_decline, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_confirm:

                //Add code to add Car to collection here


                finish();
                return true;

            case R.id.action_decline:

                finish();

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public static Intent makeIntent(Context context) {
            Intent intent = new Intent(context, EditCarActivity.class);
            return intent;

    }
}
