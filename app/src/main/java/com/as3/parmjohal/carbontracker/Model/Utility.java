package com.as3.parmjohal.carbontracker.Model;

import android.util.Log;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.joda.time.Days.daysBetween;

/**
 * Created by Shane Salarda` on 3/22/2017.
 */

public class Utility {
    private int electricity,gas,persons;
    private boolean isElectricity;
    private double totalCo2,dailyCo2;
    private double CO2_Elec_COVERTOR = 9000;
    private double CO2_Gas_COVERTOR = 56.1;
    private Date startDate,endDate;
    private int totalDays;



    public Utility(boolean isElectricity, int amount, int persons, Date startDate, Date endDate){
        this.isElectricity=isElectricity;
        this.startDate = startDate;
        this.endDate = endDate;
        LocalDate sDate = new LocalDate(startDate);
        LocalDate eDate = new LocalDate(endDate);
        Days total = daysBetween(sDate,eDate);
        totalDays= total.getDays();
        if(isElectricity){
            electricity = amount;
            totalCo2 = (CO2_Elec_COVERTOR * (double)amount)/(double)persons;
            dailyCo2 =totalCo2/totalDays;
        }
        else{
            gas = amount;

            totalCo2 = ((CO2_Gas_COVERTOR * (double)amount))/(double)persons;
            dailyCo2 =totalCo2/totalDays;
        }

        Log.i("Test", "" +getTotalDays()+" " + getDailyCo2() + " " +getTotalCo2());
    }

    public double getTotalCo2() {
        return totalCo2;
    }

    public double getDailyCo2() {
        return dailyCo2;
    }

    public int getTotalDays() {
        return totalDays;
    }
    public int getElectricity() {
        return electricity;
    }

    public void setElectricity(int electricity) {
        this.electricity = electricity;
    }

    public int getGas() {
        return gas;
    }

    public void setGas(int gas) {
        this.gas = gas;
    }

    public int getPersons() {
        return persons;
    }

    public void setPersons(int persons) {
        this.persons = persons;
    }

    public boolean isElectricity() {
        return isElectricity;
    }

    public void setElectricity(boolean electricity) {
        isElectricity = electricity;
    }

    public void setTotalCo2(double totalCo2) {
        this.totalCo2 = totalCo2;
    }

    public void setDailyCo2(double dailyCo2) {
        this.dailyCo2 = dailyCo2;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    @Override
    public String toString() {
        if(isElectricity)
            return "Electricity : " +electricity+ "KWh " +totalCo2+ "kg total co2";
        else{
            return "Natural Gas : " +gas+ "Gj " +totalCo2+ "kg total co2";
        }
    }
}

