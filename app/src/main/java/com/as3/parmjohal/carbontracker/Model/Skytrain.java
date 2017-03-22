package com.as3.parmjohal.carbontracker.Model;

import android.util.Log;

/**
 * Created by ParmJohal on 2017-03-17.
 */

public class Skytrain extends Transportation{

    private int highwayFuel = 0;
    private double cityFuel = 0;
    String fuelType = "Skytrain";
    String name = " ";

    Route route;

    private String startStation = " ";
    private String endStation = " ";
    private final double SPEED = 45.00;
    private final double MIN_TO_HOURS = 0.0166667;
    private String trainType = " ";

    private String[] expoLine_stops = {"King George","Surrey Central","Gateway","Scott Road","Columbia","New Westminster","22nd Street",
    "Edmonds","Royal Oak","Metrotown", "Patterson","Joyce","29th Avenue","Nanaimo","Commercial-Broadway","Main","Stadium-Chinatown",
    "Granville","Burrard","Waterfront"
    };
    private int[] expoLine_minutes = {2,1,3,3,1,4,2,3,2,1,2,2,1,3,3,2,1,1,2};

    private String[] millenniumLine_stops = {"VCC/Clark", "Commercial-Broadway", "Renfrew", "Rupert","Gilmore"
            ,"Brentwood Town Centre" ,"Holdom","Sperling","Lake City","Production Way","Lougheed Town Centre" };

    private int[] millenniumLine_minutes = {1,3,1,3,1,2,2,2,3,2};

    private String[] canadeLine_stops = {"YVR-Airport", "Sea Island","VCC/Templeton","Richmond-Brighouse","Landsdowne"
            ,"Aberdeen","Bridgeport","Marine Drive","Langara-49th","Oakridge-41st","King Edward","Broadway-City Hall"
            ,"Olympic Village","Yaletown-Roundhouse","Vancouver City Centre","Waterfront" };

    private int[] canadaLine_minutes = {2,2,9,2,2,2,2,3,2,3,2,1,2,2};


    public Skytrain(String startStation, String endStation, String name, String trainType) {
        super(0,0," ", "skytrain");
        this.name= name;
        this.startStation = startStation;
        this.endStation = endStation;
        setUpSuperClass();

        Log.i("Skytrain", "trainType: " + trainType);
        this.trainType = trainType;

        route = new Route(getDistance(),0,trainType + " Trip: " + name);

    }

    private void setUpSuperClass()
    {
        super.setCityFuel(0.0087);
        super.setFuelType(fuelType);
    }

    public double getHours()
    {
        String[] stops = new String[0];
        int[] minutes = new int[0];

        if(trainType.equals("Expo Line")) {

            stops = expoLine_stops;
            minutes = expoLine_minutes;
        }
        else if(trainType.equals("Millennium Line")) {

            stops = millenniumLine_stops;
            minutes = millenniumLine_minutes;
        }
        else if(trainType.equals("Canada Line")) {

            stops = canadeLine_stops;
            minutes = canadaLine_minutes;
        }
            
        int stop1 = 0;
        int stop2 = 0;

        for(int i = 0; i < stops.length; i++)
        {
            if(stops[i].equals(startStation)) {
                stop1 = i;
            }

            if(stops[i].equals(endStation)) {
                stop2 = i;
            }
        }

        int difference = stop1 - stop2;
        int startValue = 0;
        int totalMinutes = 0;

        if (difference < 0)
        {
            startValue = stop2;
            for(int i = startValue - 1; i > stop1 - 1;i--)
            {
                totalMinutes += minutes[i];
            }
        }
        else {
            for(int i = startValue; i < stop1;i++)
            {
                totalMinutes += minutes[i];
            }
        }

        Log.i("Skytrain", " " + totalMinutes);
        return (totalMinutes * MIN_TO_HOURS) ;
    }

    public String[] getStops(){
        if(trainType.equals("Expo Line")) {

            return expoLine_stops;
        }
        else if(trainType.equals("Millennium Line")) {

           return millenniumLine_stops;
        }
        else if(trainType.equals("Canada Line")) {

            return canadeLine_stops;

        }
        else return null;
    }


    public Route getRoute()
    {
        return route;
    }

    public double getDistance()
    {
        return  getHours() * SPEED;
    }

    @Override
    public int getHighwayFuel() {
        return highwayFuel;
    }

    @Override
    public double getCityFuel() {
        return cityFuel;
    }

    @Override
    public String getFuelType() {
        return fuelType;
    }

    @Override
    public String getInfo() {
        return null;
    }

}
