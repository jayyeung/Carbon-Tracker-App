package com.as3.parmjohal.carbontracker;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ParmJohal on 2017-03-02.
 */

public class CarManager {

    //make	model	year	trany	cylinders	displ	fuelType	city08	highway08

    private ArrayList<Car> carCollection = new ArrayList<>();
    private ArrayList<Car> allCars;

    public CarManager(Context context) throws IOException {
        VehicleData vehicleData = new VehicleData(context);
        allCars = vehicleData.getAllCars();
    }
    public boolean add(Car car)
    {
       if(getCarFromCSVFile(car) != null)
       {
           carCollection.add(car);
           return true;
       }
       else {
           return false;
       }
    }

    private Car getCarFromCSVFile(Car car)
    {
        for(int i = 0; i < allCars.size(); i++)
        {
            if(allCars.get(i).equals(car))
            {
                Log.i("ADDED ", allCars.get(i).toString());
                return allCars.get(i);
            }
        }
        return null;
    }

    public void remove(Car car)
    {
        carCollection.remove(car);
    }

    public void update(Car updateCar, String make, String model, int year, String tranyType, double engineDisplacment)
    {
        Car car = getCarFromArray(updateCar);
        Log.i("Before ", car.toString());

        car.setMake(make);
        car.setModel(model);
        car.setYear(year);
        car.setTranyType(tranyType);
        car.setEngineDisplacment(engineDisplacment);
        //carCollection.set(index, car);

        Log.i("After ", car.toString());
    }

    private int getIndex(Car car)
    {
        int index = carCollection.indexOf(car);
        return index;
    }

    private Car getCarFromArray(Car getCar)
    {
        int index = getIndex(getCar);
        Car car = carCollection.get(index);

        return car;
    }

    public ArrayList<Car> getCarCollection() {
        return ( ArrayList<Car>) carCollection.clone();
    }
}