package com.as3.parmjohal.carbontracker.Model;

import android.util.Log;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Keeps track of all the days and the corresponding journeys
 * Provides the ability to filter out all the days within a given tome period
 */

public class DayManager {

    private ArrayList<Day> days = new ArrayList<>();
    private ArrayList<Day> daysUtilities = new ArrayList<>();
    private Manager<Utility> utilityManager = new Manager<>();


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

//    public void addUtility(Utility utility) {
//        for(int i =0; i<days.size();i++) {
//           if( checkDayExists(days.get(i),utility.getStartDate(),utility.getEndDate())){
//               days.get(i).addUtility(utility);
//           }
//        }
//    }

    // **** PARMS CODE *****
    public void addUtility1(Utility utility) {
        utilityManager.add(utility);
        for(int i =0; i<days.size();i++) {
            if( checkDayExists(days.get(i).getRawDate(),utility.getStartDate(),utility.getEndDate())){
                Log.i("Utility", "Added to day " + days.get(i).toString());
                days.get(i).setTotalUtility(utility.getDailyCo2());
            }

        }
    }


    public void recalculateDaysUtilities(ArrayList<Utility> utilities ){
        daysUtilities= new ArrayList<>();
        for(int i =0; i < utilities.size(); i++){
           // Log.i("test",utilities.get(i).toString());
            addUtility1(utilities.get(i));

        }

    }

    private boolean checkDayExists(Date day, Date startDate, Date endDate) {
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


    public void recalculateDays(ArrayList<Journey> journeys ){
        days = new ArrayList<>();
        for(int i =0; i < journeys.size(); i++){
            Log.i("test",journeys.get(i).toString());
            add(journeys.get(i));
        }

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

    public ArrayList<Utility> getDay_Utilities(int day, int month, int year) {
        for(int i=0; i < days.size(); i++)
        {
            Day dayObject = daysUtilities.get(i);
            if(dayObject.getDay() == day && dayObject.getMonth() == month && dayObject.getYear() == year)
            {
                return days.get(i).getAllUtilities();
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
            if (month - dayObject.getMonth() >=0)
            {
                int currentMonth = month - dayObject.getMonth();
                totalCO2_perMonth.set(currentMonth, totalCO2_perMonth.get(currentMonth) + dayObject.getTotalCO2());
            }
            else{
            int currentMonth = 12 + month - dayObject.getMonth() ;
            totalCO2_perMonth.set(currentMonth, totalCO2_perMonth.get(currentMonth) + dayObject.getTotalCO2());
        }
        }
        return totalCO2_perMonth;
    }

    public ArrayList<Double> getPast365Days_UtilityCO2(int day, int month, int year,ArrayList<Utility> utilities)
    {

        ArrayList<Double> totalUtilityCO2_perMonth = new ArrayList<>();
        ArrayList<Double> totalUtilityCO2_perMonth2 = new ArrayList<>();

        for(int i =0; i < 12;i++)
        {
            totalUtilityCO2_perMonth.add(0.0);
            totalUtilityCO2_perMonth2.add(0.0);
        }

        for (int i = 0;i<utilities.size();i++) {
            LocalDate startDay = new LocalDate(utilities.get(i).getStartDate());
            LocalDate endDay = new LocalDate(utilities.get(i).getEndDate());

            totalUtilityCO2_perMonth2.set(startDay.getMonthOfYear()-1, totalUtilityCO2_perMonth2.get(startDay.getMonthOfYear()-1)+utilities.get(i).getCo2StartMonth() );
            totalUtilityCO2_perMonth2.set(endDay.getMonthOfYear()-1,totalUtilityCO2_perMonth2.get(endDay.getMonthOfYear()-1)+utilities.get(i).getCo2EndMonth() );


        }


        for(int i = 0;i<totalUtilityCO2_perMonth.size();i++)
        {
            if (month - (i +1)>=0)
          {
               int currentMonth = month-(i+1);
               totalUtilityCO2_perMonth.set(currentMonth, totalUtilityCO2_perMonth2.get(i));
              Log.i("used","used1");

          }
            else{
                int currentMonth = 12 + month -(i+1) ;
                totalUtilityCO2_perMonth.set(currentMonth, totalUtilityCO2_perMonth2.get(i));
                Log.i("used","used2");
            }
 }

        for(double num: totalUtilityCO2_perMonth2)
        {
           Log.i("Utility", " " + num);
        }
        for(double num: totalUtilityCO2_perMonth)
        {
            Log.i("Utility", " " + num);
        }

        return totalUtilityCO2_perMonth;
    }

    public ArrayList<Double> getPast365Days_JourneysCO2(int day, int month, int year,ArrayList<Journey> journeys)
    {
        //ArrayList<Journey> journeys = new ArrayList<>();
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
            int journeyMonth = Integer.parseInt(tokens[1]);
            if (month - journeyMonth >=0)
            {
                int currentMonth = month - journeyMonth;
                totalJourneyCO2_perMonth.set(currentMonth, totalJourneyCO2_perMonth.get(currentMonth) + journey.getCo2());
            }
            else{
                int currentMonth = 12 + month - journeyMonth ;
                totalJourneyCO2_perMonth.set(currentMonth, totalJourneyCO2_perMonth.get(currentMonth) + journey.getCo2());
            }

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
                    if(currentDay <= day)
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

        return totalJourneyCO2_perDay;
    }

    public ArrayList<Double> getPast28Days_UtilityCO2(int day, int month, int year)
    {
        ArrayList<Double> totalUtilityCO2_perDay = new ArrayList<>();
        ArrayList<Day> past28Days = getPast28Days(day,month,year);

        for(Day dayObject: past28Days)
        {
            totalUtilityCO2_perDay.add(dayObject.getTotalUtility());
        }

        return totalUtilityCO2_perDay;
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
