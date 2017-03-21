package com.as3.parmjohal.carbontracker.Model;

import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * Created by ParmJohal on 2017-03-20.
 */

public class Day {

    private JourneyManager journeyManager = new JourneyManager();
    private ArrayList<Double> allCO2Values = new ArrayList<>();

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

    public boolean add(Journey journey)
    {
        if(journey.getDateInfo().equals(date))
        {
            journeyManager.add(journey);
            allCO2Values.add(journey.getCo2());
            return true;
        }
        else {
            return false;
        }
    }

    public ArrayList<Journey> getAllJourneys() {
        return journeyManager.getJourneyCollection();
    }

    public ArrayList<Double> getAllCO2Values() {
        return allCO2Values;
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
