package com.as3.parmjohal.carbontracker;

/**
 * Created by ParmJohal on 2017-03-03.
 */

public class Journey {

    //private Route route = null;
    private Car car = null;
    private double co2 = 0;
    private double CO2_COVERTOR = 8.89;

    public Journey(Car car /* , Route route */) {
        this.car = car;
        //this.route =route;
        calculateCO2();
    }

    private void calculateCO2()
    {
        if(car.getFuelType() == "Diesel") {
            CO2_COVERTOR = 10.16;
        }
        /*
        int hwyGallons = route.getHwyDistance() / car.getHighwayFuel() ;
        int cityGallons = route.getCityDistance() / car.getCityFuel();
        double hwyCO2 = CO2_COVERTOR * hwyGallons;
        double cityCO2 = CO2_COVERTOR * cityGallons;

        co2 = hwyCO2 + cityCO2;
        */
    }

    public String getCarInfo()
    {
        return "" + car.getYear()+", " + car.getMake()+ " " + car.getModel();
    }


    public String getRouteInfo()
    {
        return "";
    }

    public double getCo2() {
        return co2;
    }
}
