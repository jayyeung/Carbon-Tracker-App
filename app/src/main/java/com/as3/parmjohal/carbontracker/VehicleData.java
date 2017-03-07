package com.as3.parmjohal.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.DoubleBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ParmJohal on 2017-03-01.
 */


public class VehicleData {

    private ArrayList<Car> allCars = new ArrayList<Car>();
    private ArrayList<String> carMakers = new ArrayList<String>();

    //make	model	year	trany	cylinders	displ	fuelType	city08	highway08
    public VehicleData(Context context) throws IOException {
        initalizeCars(context);

        getModels("Toyota");
        getCarYears("Toyota", "Truck 2WD");
        getPossibleCars("Toyota", "Truck 2WD", 1985);
    }

    private void initalizeCars(Context context) {

        InputStream inputStream = context.getResources().openRawResource(R.raw.vehiclesinfo);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
        String line = "";

        try {
            reader.readLine();
            while ((line = reader.readLine()) != null) {


                String tokens[] = line.split(",");
                Car car = new Car();

                car.setMake(tokens[0]);
                car.setModel(tokens[1]);
                car.setYear(Integer.parseInt(tokens[2]));
                car.setTranyType(tokens[3]);
                car.setNumCylinders(Integer.parseInt(tokens[4]));
                car.setEngineDisplacment(Double.parseDouble(tokens[5]));
                car.setFuelType(tokens[6]);
                car.setCityFuel(Integer.parseInt(tokens[7]));
                car.setHighwayFuel(Integer.parseInt(tokens[8]));

                allCars.add(car);
                if (!carMakers.contains(car.getMake())) {
                    carMakers.add(car.getMake());
                }
            }
            inputStream.close();
            reader.close();
        }
        catch (IOException e)
        {

        }
    }

    public ArrayList<String> getModels(String maker) {
        ArrayList<String> carModels = new ArrayList<>();

        for (int i = 0; i < allCars.size(); i++) {
            Car car = allCars.get(i);
            if (car.getMake().equals(maker)) {
                String model = car.getModel();

                if (!carModels.contains(model)) {
                    carModels.add(model);
                    Log.i("Model : ", model);
                }
            }
        }

        Collections.sort(carModels);
        return carModels;
    }

    public ArrayList<Car> getAllCars() {
        return (ArrayList<Car>) allCars.clone();
    }

    public ArrayList<String> getCarMakers() {
        return (ArrayList<String>) carMakers.clone();
    }

    public ArrayList<Integer> getCarYears(String maker, String model) {
        ArrayList<Integer> carYears = new ArrayList<>();

        for (int i = 0; i < allCars.size(); i++) {
            Car car = allCars.get(i);
            String carMake = car.getMake();
            String carModel = car.getModel();
            int carYear = car.getYear();

            if(carMake.equals(maker) && carModel.equals(model))
            {
                    carYears.add(carYear);
                    Log.i("Year : ", " " + carYear);

            }
        }

        Set<Integer> hs = new HashSet<>();
        hs.addAll(carYears);
        carYears.clear();
        carYears.addAll(hs);
        Collections.sort(carYears);

        return carYears;
    }

    public ArrayList<Car> getPossibleCars(String make, String model, int year)
    {
        ArrayList<Car> possibleCars = new ArrayList<Car>();
        Car car = new Car();
        car.setMake(make);
        car.setModel(model);
        car.setYear(year);

        for(int i =0; i < allCars.size(); i++)
        {
            if(similarCars(allCars.get(i), car))
            {
                possibleCars.add(allCars.get(i));
                Log.i("Possible Car: ", allCars.get(i).toString());
            }
        }

        return possibleCars;
    }

    private boolean similarCars(Car car1, Car car2)
    {
        boolean checkMake = car1.getMake().equals(car2.getMake());
        boolean checkModel= car1.getModel().equals(car2.getModel());
        boolean checkYear = car1.getYear() == car2.getYear();
        return checkMake && checkModel && checkYear;
    }
}
