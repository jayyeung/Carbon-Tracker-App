package com.as3.parmjohal.carbontracker.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.as3.parmjohal.carbontracker.R;

public class SelectCarImageActivity extends AppCompatActivity {
    private int image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_car_image);

        setUpCarButtons();
    }

    private void setUpCarButtons() {
        Button car1 = (Button) findViewById(R.id.button);
        Button car2 = (Button) findViewById(R.id.button2);
        Button car3 = (Button) findViewById(R.id.button3);
        Button car4 = (Button) findViewById(R.id.button4);
        Button car5 = (Button) findViewById(R.id.button5);

        car1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image = R.drawable.car1;
                returnImage();

            }
        });

        car2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image = R.drawable.car2;
                returnImage();
            }
        });

        car3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image = R.drawable.car3;
                returnImage();
            }
        });

        car4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image = R.drawable.car4;
                returnImage();
            }
        });

        car5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image = R.drawable.car5;
                returnImage();
            }
        });

    }

    private void returnImage() {
        Intent intent = new Intent();
        intent.putExtra("image",image);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public static Intent makeIntent(Context context) {
        Intent intent= new Intent(context, SelectCarImageActivity.class);
        return intent;
    }
}
