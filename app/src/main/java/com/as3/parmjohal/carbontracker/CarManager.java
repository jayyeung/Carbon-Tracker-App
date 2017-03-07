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

    public CarManager(Context context) {
        VehicleData vehicleData = null;
        try {
            vehicleData = new VehicleData(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
        allCars = vehicleData.getAllCars();
    }
    public Car add(Car car)
    {
        String name = car.getName();
       if(getCarFromCSVFile(car) != null)
       {
           carCollection.add(getCarFromCSVFile(car));
           Car addCar = getCarFromCSVFile(car);
           addCar.setName(name);
           return addCar;

       }
       else {
           return null;
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

    public void update(Car updateCar, Car newCar)
    {
        Car car = getCarFromArray(updateCar);
        Log.i("Before ", car.toString());

        car.setMake(newCar.getMake());
        car.setModel(newCar.getModel());
        car.setYear(newCar.getYear());
        car.setTranyType(newCar.getTranyType());
        car.setEngineDisplacment(newCar.getEngineDisplacment());
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