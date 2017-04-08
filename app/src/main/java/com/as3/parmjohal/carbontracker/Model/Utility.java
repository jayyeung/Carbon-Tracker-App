package com.as3.parmjohal.carbontracker.Model;

import android.util.Log;

import com.as3.parmjohal.carbontracker.R;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import static org.joda.time.Days.daysBetween;

/**
 * Holds the information that the user would want to store about a monthlu utility bill
 * Calculates the CO2 for both electricity and ags bills and finds the daily average of both
 * generates tips based on the entered info
 */

public class Utility {

    private int electricity,gas,persons;
    private boolean isElectricity;
    private double totalCo2,dailyCo2,co2StartMonth,co2EndMonth;
    private double CO2_Elec_COVERTOR = 9000;
    private double CO2_Gas_COVERTOR = 56.1;
    private Date startDate,endDate;
    private int totalDays;
    private int uImage= R.drawable.flash;
    private String tip = " ";

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
            totalCo2 = (CO2_Elec_COVERTOR * (double)amount)/((double)persons * 1000000);
            dailyCo2 =totalCo2/totalDays;
        }
        else{
            gas = amount;
            uImage=R.drawable.flammable;
            totalCo2 = ((CO2_Gas_COVERTOR * (double)amount))/(double)persons;
            dailyCo2 =totalCo2/totalDays;
        }

        Log.i("Test", "" +getTotalDays()+" " + getDailyCo2() + " " +getTotalCo2());

        getMonthlyCo2();
        generateTips();
    }

    private void generateTips() {
        CarbonTrackerModel model = CarbonTrackerModel.getModel();
        Random rand = new Random();

        String[] electricityHelp = {"turning off the Lights help", "turning off all un-used electronics"
        ,"Spending more time outside doing activities" };

        String[] gasHelp = {" Shorter showers might help cut down emissions from hot water heater"
        ,"Lowering the House temp While no one is Home"
        };

        if(isElectricity)
        {
            tip = "You generate " + totalCo2 + " kg of CO2 from Electricity in a month \n"
                    + " Simple things like " + electricityHelp[rand.nextInt(electricityHelp.length)];
        }
        else {
            tip = "You generate " + totalCo2 + " kg of CO2 from Natural Gas in a month \n"
                    + gasHelp[rand.nextInt(gasHelp.length)];
        }

        model.getTipsManager().addUtilityTip(tip);
    }
    public String getDateInfo(Date date)
    {
        DateFormat df = new SimpleDateFormat("dd MMM yyyy");
        return df.format(date) ;
    }
    public String getDateInfo2()
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        return df.format(startDate);
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

    public int getuImage() {
        return uImage;
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
        DateFormat df = new SimpleDateFormat("dd MMM yyyy");
        DecimalFormat df2 = new DecimalFormat("####0.00##");
        if(isElectricity)
            return "" +df2.format(dailyCo2)+ "kg c02/day " +
                    "from "+ df.format(startDate)+" to "+df.format(endDate) ;
        else{
            return "" +df2.format(dailyCo2)+ "kg c02/day " +
                    "from " + df.format(startDate)+" to "+df.format(endDate) ;
        }
    }

    public double getCo2StartMonth() {
        return co2StartMonth;
    }

    public double getCo2EndMonth() {
        return co2EndMonth;
    }

    public void getMonthlyCo2() {
        LocalDate startDay = new LocalDate(startDate);
        LocalDate endDay = new LocalDate(endDate);
        int endMonth = endDay.getMonthOfYear();
        int startMonth = startDay.getMonthOfYear();
        do{
            co2EndMonth += dailyCo2;
           endDay= endDay.minusDays(1);
            Log.i("month1",endDay.toString());
        }while (endDay.getMonthOfYear() ==endMonth);
        co2EndMonth -= dailyCo2;
        if(startMonth!=endMonth) {
            do {
                co2StartMonth += dailyCo2;
                startDay=startDay.plusDays(1);
                Log.i("month2", startDay.toString());
            } while (startDay.getMonthOfYear() == startMonth);

        }

    }
}

