package com.as3.parmjohal.carbontracker.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.as3.parmjohal.carbontracker.R;

public class SelectTransActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_trans);

        setTitle("Select Transportation");

        setupButtons();
    }

    private void setupButtons() {
        Button car = (Button) findViewById(R.id.carButton);
        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SelectCarActivity.makeIntent(SelectTransActivity.this);
                startActivity(intent);
            }
        });
        Button bus = (Button) findViewById(R.id.busButton);
        bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = BusActivity.makeIntent(SelectTransActivity.this);
                startActivity(intent);

            }
        });
        Button skytrain = (Button) findViewById(R.id.trainButton);
        skytrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = TrainActivity.makeIntent(SelectTransActivity.this);
                startActivity(intent);


            }
        });
        Button walk= (Button) findViewById(R.id.walkButton);
        walk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = WalkActivity.makeIntent(SelectTransActivity.this);
                startActivity(intent);

            }
        });
    }
    public static Intent makeIntent(Context context) {
        Intent intent= new Intent(context, SelectTransActivity.class);
        return intent;
    }
}
