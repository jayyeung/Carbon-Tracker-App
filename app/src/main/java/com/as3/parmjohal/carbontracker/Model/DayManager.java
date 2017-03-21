package com.as3.parmjohal.carbontracker.Model;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ParmJohal on 2017-03-20.
 */

public class DayManager {

    private ArrayList<Day> days = new ArrayList<>();

    public DayManager() {

        // dd/MM/yy
        // Adding dummy Day objects for debugging

//        for(int i = 1; i < 30; i++)
//        {
//            Day day = new Day(i+"/01/17");
//            days.add(day);
//        }
//        for(int i = 1; i < 30; i++)
//        {
//            Day day = new Day(i+"/12/16");
//            days.add(day);
//        }
        
    }

    public boolean add(Journey journey)
    {
        String journeyDate = journey.getDateInfo2();

        String[] tokens = journeyDate.split("/");
        int day = Integer.parseInt(tokens[0]);
        int month = Integer.parseInt(tokens[1]);
        int year = Integer.parseInt(tokens[2]);

        for(int i =0; i < days.size(); i++)
        {
            Day day1 = days.get(i);
            if(day == day1.getDay() && month == day1.getMonth() && year == day1.getYear())
            {
                days.get(i).add(journey);
                return true;
            }
        }
        Day day1 = new Day(day+"/"+month+"/"+year);
        day1.add(journey);
        days.add(day1);

        return true;
    }

    public ArrayList<Journey> getDay_Journeys(int day, int month, int year)
    {
        for(int i=0; i < days.size(); i++)
        {
            Day dayObject = days.get(i);
            if(dayObject.getDay() == day && dayObject.getMonth() == month && dayObject.getYear() == year)
            {
                return days.get(i).getAllJourneys();
            }
        }
        return null;
    }


    public ArrayList<Day> getPast365Days(int day, int month, int year)
    {
        ArrayList<Day> pastDays = new ArrayList<>();
        /*
        if the year is the same, and the month is equal or smaller, and the day is smaller.

        if the year is less than the year, the month is greater than or equal to the month, same with day
         */

        return pastDays;
    }


    //***** USE THE SAME CODE LAYOUT FOR PAST 28 DAYS UTILITIES ********
    //Returns all Journeys within the past 28 Days

    public ArrayList<Journey> getPast28Days_Journeys(int day, int month, int year)
    {
        ArrayList<Journey> journeys = new ArrayList<>();
        ArrayList<Day> past28Days = getPast28Days(day,month,year);

        for(int i = 0; i < past28Days.size(); i++)
        {
            Day currentDay = past28Days.get(i);
            journeys.addAll(currentDay.getAllJourneys());
        }
        return journeys;
    }

    private ArrayList<Day> getPast28Days(int day, int month, int year)
    {
        // dd/MM/yy

        ArrayList<Day> days1 = new ArrayList<>();

        int smallestDay = day - 28;
        int smallestMonth = month;
        int smallestYear = year;

        if(smallestDay < 0)
        {
            smallestDay = smallestDay + 30;
            smallestMonth --;
            if(smallestMonth == 0)
            {
                smallestMonth = 12;
                smallestYear--;
            }
        }

        for(int i=0; i < days.size(); i++)
        {
            Day currentDay = days.get(i);

            if(currentDay.getMonth() == smallestMonth && currentDay.getYear() == smallestYear)
            {
                if(currentDay.getDay() >= smallestDay)
                {
                    Log.i("Day", " 1) Day ADDED: " + currentDay.toString());
                    days1.add(currentDay);
                }
            }
            else if(currentDay.getMonth() == month && currentDay.getYear() == year)
            {
                if(currentDay.getDay() <= day)
                {
                    Log.i("Day", " 2) Day ADDED: " + currentDay.toString());
                    days1.add(currentDay);
                }
            }
        }
        return days1;
    }

}
