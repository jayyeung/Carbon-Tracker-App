package com.as3.parmjohal.carbontracker.UI;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import com.as3.parmjohal.carbontracker.Model.CarbonTrackerModel;
import com.as3.parmjohal.carbontracker.Model.Journey;
import com.as3.parmjohal.carbontracker.R;

import java.util.Calendar;
import java.util.Date;
/**
 * --CalenderActvity--
 * Pops up the activity with a calender.
 * user selects day and clicks the save button in order
 * to proceed back to previous activity.
 */
public class CalenderActivity extends Activity {
    private CarbonTrackerModel model ;

    CalendarView calender;
    private int setYear;
    private int setDay;
    private int setMonth;
    private boolean clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        model = CarbonTrackerModel.getCarbonTrackerModel(this);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.9),(int)(height*.8));

        calender =(CalendarView) findViewById(R.id.calendarView);
        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                setYear =year;
                setMonth = month;
                setDay = dayOfMonth;
                clicked= true;

            }
        });
        Button save = (Button) findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                if (clicked) {
                    if (model.isEditJourney()) {
                        Journey oldJourney = Journey.copy(model.getCurrentJouney());
                        model.getCurrentJouney().setDate(setYear, setMonth, setDay);
                        Journey newJourney = model.getCurrentJouney();
                        model.getDayManager().updateDay(oldJourney, newJourney);

                        finish();
                    } else {
                        Calendar cal = Calendar.getInstance();
                        cal.set(setYear, setMonth, setDay);
                        Date date = cal.getTime();
                        model.setCurrentDate(date);
                        finish();
                    }
                }


            }
        });
    }
}
