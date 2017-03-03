package com.as3.parmjohal.carbontracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //make	model	year	trany	displ
        //Acura	NSX	1994	Automatic 4-spd	6	3	Premium	16	22
        Car car = new Car("Acura", "NSX", 1994,"Automatic 4-spd", 3);
        try {
            CarManager carManager = new CarManager(this);
            carManager.add(car);
            carManager.update(car, car.getMake(), "Integra",car.getYear(),car.getTranyType(),car.getEngineDisplacment());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
