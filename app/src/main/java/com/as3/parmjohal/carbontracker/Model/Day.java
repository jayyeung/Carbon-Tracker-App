package com.as3.parmjohal.carbontracker.Model;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by ParmJohal on 2017-03-20.
 */

public class Day {

    private JourneyManager journeyManager = new JourneyManager();
    private Manager<Utility> utilityManager = new Manager<>();

    private ArrayList<Double> allCO2Values = new ArrayList<>();
    private double totalCO2 = 0;
    String tip = " ";
    private double totalUtility = 0;

    private String date;
    private int year = 0;
    private int month = 0;
    private int day = 0;


    public Day(String date) {
        this.date = date;
        setUpDate();
    }

    private void setUpDate()
    {
        // dd/MM/yy
        String[] tokens = date.split("/");
        day = Integer.parseInt(tokens[0]);
        month = Integer.parseInt(tokens[1]);
        year = Integer.parseInt(tokens[2]);
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public void addUtility(Utility utility){
        utilityManager.add(utility);
        //allCO2Values.add(utility.getDailyCo2());
        //totalCO2  +=(utility.getDailyCo2());
    }

    public boolean add(Journey journey)
    {
        journeyManager.add(journey);
        allCO2Values.add(journey.getCo2());
        totalCO2 += journey.getCo2();

        DecimalFormat df = new DecimalFormat("####0.00");


        if(journey.getTransportation().getObjectType().equals("car")) {
            tip = "On " + date + " you used " + df.format(totalCO2) + " kg of CO2 \n"
                    + " Maybe consider taking public transit";
        }
        else if(journey.getTransportation().getObjectType().equals("bike")) {
            tip = "On " + date + " you spent " + 0 + " kg of CO2 \n"
                    + " to get to a destination, because you rode a bike";
        }
        else if(journey.getTransportation().getObjectType().equals("bus")) {
            tip = "On " + date + " you spent " + df.format(journey.getCo2()) + " kg of CO2 \n"
                    + " ont the bus. Did you know that the SkyTrain projects less CO2";
        }
        else if(journey.getTransportation().getObjectType().equals("walk")) {
            tip = "Keep walking because On " + date + " you spent " + 0 + " kg of CO2 \n"
                    + " to get to your destination";
        }

        CarbonTrackerModel.getModel().getTipsManager().add(tip);
        return true;
    }

    public void remove(Journey journey){
        totalCO2 -= journey.getCo2();
        journeyManager.remove(journey);
    }

    public ArrayList<Journey> getAllJourneys() {
        return journeyManager.getJourneyCollection();
    }

    public ArrayList<Double> getAllCO2Values() {
        return allCO2Values;
    }

    public double getTotalCO2() {
        return totalCO2;
    }

    public void setTotalUtility(double totalUtility) {
        totalCO2 += totalUtility;
        this.totalUtility = totalUtility;
    }

    @Override
    public String toString() {
        return "Day{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Day day1 = (Day) o;

        if (year != day1.year) return false;
        if (month != day1.month) return false;
        return day == day1.day;

    }
}
