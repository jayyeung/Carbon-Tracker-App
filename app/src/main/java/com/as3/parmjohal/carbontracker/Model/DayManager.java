package com.as3.parmjohal.carbontracker.Model;

import android.util.Log;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Date;

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
//            Day day = new Day(i+"/03/17");
//            days.add(day);
//        }
//
//        for(int i = 1; i < 30; i++)
//        {
//            Day day = new Day(i+"/03/16");
//            days.add(day);
//        }
//
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

    public void updateDay(Journey oldJourney, Journey newJourney)
    {
        String[] tokens = oldJourney.getDateInfo2().split("/");
        int day = Integer.parseInt(tokens[0]);
        int month = Integer.parseInt(tokens[1]);
        int year = Integer.parseInt(tokens[2]);

        try {
            for (Day dayObject : days) {
                if (day == dayObject.getDay() && month == dayObject.getMonth() && year == dayObject.getYear()) {
                    Log.i("Update ", dayObject.toString());
                    dayObject.remove(oldJourney);
                    add(newJourney);
                }
            }
        }
        catch (java.util.ConcurrentModificationException e)
        {

        }
    }

    public void removeJourney(Journey journey)
    {
        String[] tokens = journey.getDateInfo2().split("/");
        int day = Integer.parseInt(tokens[0]);
        int month = Integer.parseInt(tokens[1]);
        int year = Integer.parseInt(tokens[2]);

        for(Day dayObject: days)
        {
            if(day == dayObject.getDay() && month == dayObject.getMonth() && year == dayObject.getYear())
            {
                dayObject.remove(journey);
            }
        }
    }

    public void addUtility(Utility utility){
        for(int i =0; i<days.size();i++) {
           if( checkDayExists(days.get(i),utility.getStartDate(),utility.getEndDate())){
               days.get(i).addUtility(utility);
           }
        }

    }

    private boolean checkDayExists(Day day, Date startDate, Date endDate) {
        LocalDate min = new LocalDate(startDate);
        LocalDate max = new LocalDate(endDate);   // assume these are set to something
        LocalDate d = new LocalDate(day);          // the date in question

        return (( d.isAfter( min ) ) && ( d.isBefore( max )));
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

    // dd/MM/yy
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


    //returns an arraylist of size 12 with total CO2 for that mont
    //arraylist.get(0) will be the total CO2 for the month of january in the past 365 days
    // dd/MM/yy

    public ArrayList<Double> getPast_12MonthsCO2(int day, int month, int year)
    {
        ArrayList<Double> totalCO2_perMonth = new ArrayList<>();
        ArrayList<Day> past365Days = getPast365Days(day,month,year);

        for(int i =0; i < 12;i++)
        {
            totalCO2_perMonth.add(0.0);
        }

        for(Day dayObject: past365Days)
        {
            int currentMonth = dayObject.getMonth() - 1;
            totalCO2_perMonth.set(currentMonth, totalCO2_perMonth.get(currentMonth) + dayObject.getTotalCO2());
        }
        return totalCO2_perMonth;
    }


    //***** USE THE SAME CODE LAYOUT FOR PAST 365 DAYS UTILITIES ********
    //Returns all Journeys within the past 365 Days
    // dd/MM/yy
    public ArrayList<Double> getPast365Days_JourneysCO2(int day, int month, int year)
    {
        ArrayList<Journey> journeys = new ArrayList<>();
        ArrayList<Double> totalJourneyCO2_perMonth = new ArrayList<>();

        ArrayList<Day> past365Days = getPast365Days(day,month,year);

        for(int i = 0; i < past365Days.size(); i++)
        {
            Day currentDay = past365Days.get(i);
            journeys.addAll(currentDay.getAllJourneys());
        }

        for(int i =0; i < 12;i++)
        {
            totalJourneyCO2_perMonth.add(0.0);
        }

        for(Journey journey: journeys)
        {
            String[] tokens = journey.getDateInfo2().split("/");
            int journeyMonth = Integer.parseInt(tokens[1]) - 1;
            totalJourneyCO2_perMonth.set(journeyMonth, totalJourneyCO2_perMonth.get(journeyMonth) + journey.getCo2());
        }

        for(Double num: totalJourneyCO2_perMonth)
        {
            Log.i("Past 365", " " + num);
        }

        return totalJourneyCO2_perMonth;
    }

    // dd/MM/yy
    public ArrayList<Day> getPast365Days(int day, int month, int year)
    {
        ArrayList<Day> pastDays = new ArrayList<>();

        for(Day dayObject: days)
        {
            int currentDay = dayObject.getDay();
            int currentMonth = dayObject.getMonth();
            int currentYear = dayObject.getYear();

            if(currentYear == year && currentMonth <= month)
            {
                if(currentMonth == month)
                {
                    if(currentDay < day)
                        pastDays.add(dayObject);
                }
                else {
                    pastDays.add(dayObject);
                }
            }
            else if(currentYear < year && currentMonth >= month){

                if(currentMonth == month)
                {
                    if(currentDay > day) {
                        pastDays.add(dayObject);
                    }
                }
                else {
                    pastDays.add(dayObject);
                }
            }
        }
        return pastDays;
    }


    //***** USE THE SAME CODE LAYOUT FOR PAST 28 DAYS UTILITIES ********
    //Returns all Journeys within the past 28 Days
    // dd/MM/yy

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

    public ArrayList<Double> getPast28Days_JourneysCO2(int day, int month, int year)
    {
        ArrayList<Double> totalJourneyCO2_perDay = new ArrayList<>();
        ArrayList<Day> past28Days = getPast28Days(day,month,year);

        for(Day day1: past28Days)
        {
            totalJourneyCO2_perDay.add(day1.getTotalCO2());
        }

        for(Double num: totalJourneyCO2_perDay)
        {
            Log.i("Past 28", " " + num);
        }

        return totalJourneyCO2_perDay;
    }

    // dd/MM/yy
    public ArrayList<Day> getPast28Days(int day, int month, int year)
    {
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

    public ArrayList<Day> getDays() { return days; }
}
