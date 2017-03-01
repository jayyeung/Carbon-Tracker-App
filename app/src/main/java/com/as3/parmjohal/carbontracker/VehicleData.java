package com.as3.parmjohal.carbontracker;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Created by ParmJohal on 2017-03-01.
 */

public class VehicleData {

    public  VehicleData(Context context) throws IOException {

        InputStream inputStream = context.getResources().openRawResource(R.raw.vehiclesinfo);

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
        String line = "";

        while((line = reader.readLine()) != null){

            try {

                String tokens[] = line.split(",");
                Car car = new Car();

                car.setMake(tokens[0]);
                car.setModel(tokens[1]);
                car.setCityFuel(Double.parseDouble(tokens[2]));
                car.setHighwayFuel(Double.parseDouble(tokens[3]));
                car.setYear(Integer.parseInt(tokens[4]));

            }
            catch (java.lang.NumberFormatException e)
            {

            }
        }

    }

}
