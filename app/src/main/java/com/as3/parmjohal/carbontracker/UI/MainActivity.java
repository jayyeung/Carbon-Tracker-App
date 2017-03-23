package com.as3.parmjohal.carbontracker.UI;

import android.app.Activity;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.as3.parmjohal.carbontracker.Model.CarbonTrackerModel;
import com.as3.parmjohal.carbontracker.Model.Day;
import com.as3.parmjohal.carbontracker.Model.DayManager;
import com.as3.parmjohal.carbontracker.Model.Journey;
import com.as3.parmjohal.carbontracker.R;
import com.github.mikephil.charting.charts.LineChart;
import com.as3.parmjohal.carbontracker.SharedPreference;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Boolean isFabOpen = false;
    private LinearLayout fabs;
    private FloatingActionButton fab, fab_transport, fab_utility;
    private ImageView fab_overlay;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward, fade_in, fade_out;

    private enum Chart_options { DAILY, MONTHLY, YEARLY };

    CarbonTrackerModel model;
    DayManager day_manager;

    private ArrayList<Journey> journey;
    public static final int REQUEST_CODE_JOURNEY= 2020;
    public static final int GET_DATE_FOR_CHART = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // add icon to dashboard action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.app_icon_white);
        setTitle("Dashboard");

        SharedPreference.saveCurrentModel(this);
        model = CarbonTrackerModel.getCarbonTrackerModel(this);
        model.setEditJourney(false);
        model.setConfirmTrip(true);
        journey = model.getJourneyManager().getJourneyCollection();
        day_manager = model.getDayManager();

        // sort all track types by date
        Collections.sort(journey, new Comparator<Journey>() {
            public int compare(Journey o1, Journey o2) {
                if (o1.getDateInfo() == null || o2.getDateInfo() == null) return 0;
                return o1.getDateInfoRaw().compareTo(o2.getDateInfoRaw());
            }
        });

        model.setConfirmTrip(true);
        model.setEditJourney(false);

        // we reverse all track types so the latest track is on top
        Collections.reverse(journey);

        // set Overview
        setOverview();

        // set FAB
        setFAB();

        // show Journeys
        setJourneys();

        // set tips
        setTips();

        // intro animation
        animateDashboard();
    }


    // animate Dashboard
    public void animateDashboard() {
        ImageView background_img = (ImageView) findViewById(R.id.background);

        Animation fade_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        fade_in.setDuration(2300);
        background_img.startAnimation(fade_in);
    }

    // Set Overview
    public void setOverview() {
        RadioGroup chart_radio = (RadioGroup) findViewById(R.id.chart_options);

        final TextView chart_status = (TextView) findViewById(R.id.chart_status);
        final TextView chart_type = (TextView) findViewById(R.id.chart_type);

        chart_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.day_radio:
                        RadioButton radio = (RadioButton) findViewById(R.id.day_radio);
                        PopupMenu popup = new PopupMenu(MainActivity.this, radio);

                        ArrayList<Day> days = day_manager.getDays();
                        Menu menu = popup.getMenu();

                        for (int i = 0; i < days.size(); i++) {
                            final Day day = days.get(i);

                            MenuItem item = menu.add(day.getDay() + "/" + day.getMonth() + "/" + day.getYear());
                            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    chart_status.setText(day.getDay() + "/" + day.getMonth() + "/" + day.getYear());
                                    chart_type.setText("Daily Carbon Usage");
                                    setGraph(Chart_options.DAILY, day.getDay(), day.getMonth(), day.getYear());
                                    return true;
                                }
                            });
                        }

                        popup.show(); //showing popup menu
                        break;
                    case R.id.month_radio:
                        chart_status.setText("Last 28 days (Today)");
                        chart_type.setText("Monthly Carbon Usage");
                        setGraph(Chart_options.MONTHLY, 0,0,0);
                        break;
                    case R.id.year_radio:
                        chart_status.setText("Last 365 days (Today)");
                        chart_type.setText("Annual Carbon Usage");
                        setGraph(Chart_options.YEARLY, 0,0,0);
                        break;
                }

            }
        });

        // set default chart at start by selecting a radio button
        RadioButton default_chart = (RadioButton) findViewById(R.id.year_radio);
        default_chart.setChecked(true);
    }

    public void setGraph(Chart_options option, int inp_day, int inp_month, int inp_year) {
        LinearLayout chart_container = (LinearLayout) findViewById(R.id.chart_container);
        chart_container.removeAllViewsInLayout();

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        // Get Current Date
        DateFormat df = new SimpleDateFormat("dd MM yy");
        String[] date = (df.format(new Date())).split("\\s+");

        int day = (inp_day != 0) ? inp_day : Integer.parseInt(date[0]),
            month = (inp_month != 0) ? inp_month : Integer.parseInt(date[1]),
            year = (inp_year != 0) ? inp_year : Integer.parseInt(date[2]);

        ////////////////
        // DAILY GRAPH
        ////////////////

        if (option == option.DAILY) {
            ArrayList<PieEntry> entries = new ArrayList<>();

            // Journey
            ArrayList<Journey> day_journeys = day_manager.getDay_Journeys(day, month, year);
            float total = 0;

            if (day_journeys != null) {
                for (int i = 0; i < day_journeys.size(); i++) {
                    total += day_journeys.get(i).getCo2();
                }
                entries.add(new PieEntry(total, "Journey"));
            }

            // Utility
            //entries.add(new PieEntry(total, "Utility"));


            int[] COLORS = { Color.rgb(52, 152, 219) , Color.rgb(230, 126, 34) };

            PieDataSet journeyDataSet = new PieDataSet(entries, "CO₂");
            journeyDataSet.setValueTextSize(16f);
            journeyDataSet.setColors( COLORS );
            journeyDataSet.setValueTextColor(Color.WHITE);

            // create chart
            PieChart chart = new PieChart(this);
            chart_container.addView(chart, params);

            chart.setUsePercentValues(true);
            chart.setTouchEnabled(false);
            chart.setHoleRadius(50f);
            chart.setTransparentCircleRadius(20f);
            chart.setHoleColor(Color.TRANSPARENT);
            chart.setDescription(null);

            Legend legend = chart.getLegend();
            legend.setXOffset(16);
            legend.setTextColor(R.color.colorAccent);
            legend.setTextSize(16f);
            legend.setWordWrapEnabled(true);

            Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in);
            slide_in.setDuration(1800);
            chart.startAnimation(slide_in);
            chart.animateY(1500);

            // set data
            PieData data = new PieData(journeyDataSet);
            data.setValueFormatter(new PercentFormatter());
            chart.setData(data);
            chart.invalidate();
        }

        ////////////////
        // MONTHLY GRAPH
        ////////////////

        else if (option == option.MONTHLY) {
            final ArrayList<Day> month_CO2 = day_manager.getPast28Days(day, month, year);
            ArrayList<Double> journey_CO2 = day_manager.getPast28Days_JourneysCO2(day, month, year);

            Collections.reverse(month_CO2);
            Collections.reverse(journey_CO2);

            if (month_CO2.size() <= 0) { return; }

            List<ILineDataSet> lines = new ArrayList<ILineDataSet>();

            // Total
            ArrayList<Entry> entries = new ArrayList<>();
            int counter = 0;
            for (Day day_obj : month_CO2) {
                entries.add(new Entry(counter , (float) day_obj.getTotalCO2()));
                counter++;
            }

            LineDataSet totalDataSet = new LineDataSet(entries, "Total CO₂");
            totalDataSet.setColors(Color.rgb(38, 166, 91));
            totalDataSet.setCircleColor( Color.rgb(38, 166, 91) );
            totalDataSet.setDrawCircleHole(false);
            totalDataSet.setValueTextColor(Color.WHITE);
            totalDataSet.setValueTextSize(16f);
            totalDataSet.setCircleRadius(8f);
            totalDataSet.setLineWidth(5f);
            totalDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            lines.add(totalDataSet);

            // Journey
            entries = new ArrayList<>();
            counter = 0;

            for (Double journey_day : journey_CO2) {
                entries.add(new Entry(counter , journey_day.floatValue()));
                counter++;
            }

            LineDataSet journeyDataSet = new LineDataSet(entries, "Journey CO₂");
            journeyDataSet.setColors( Color.rgb(52, 152, 219) );
            journeyDataSet.setCircleColor( Color.rgb(52, 152, 219) );
            journeyDataSet.setDrawCircleHole(false);
            journeyDataSet.setValueTextColor(Color.WHITE);
            journeyDataSet.setValueTextSize(16f);
            journeyDataSet.setCircleRadius(8f);
            journeyDataSet.setLineWidth(5f);
            journeyDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            lines.add(journeyDataSet);

            LineData data = new LineData(lines);

            LineChart chart = new LineChart(this);
            chart_container.addView(chart, params);

            chart.setScaleMinima((5/28)*(month_CO2.size()), 1f); // widen the gaps between points depending on number of points
            chart.setDescription(null);
            chart.getAxisRight().setEnabled(false);
            chart.getAxisLeft().setEnabled(false);

            XAxis xval = chart.getXAxis();
            xval.setGranularity(1f);
            xval.setTextSize(16f);
            xval.setTextColor(Color.WHITE);
            xval.setDrawAxisLine(false);
            xval.setDrawGridLines(false);
            xval.setPosition(XAxis.XAxisPosition.BOTTOM);
            xval.setValueFormatter(new IAxisValueFormatter()
            {
                @Override
                public String getFormattedValue(float value, AxisBase axis)
                {
                    try {
                        Day day = month_CO2.get((int) value);
                        String month = new DateFormatSymbols().getShortMonths()[day.getMonth() - 1];
                        return month + " " + day.getDay();
                    } catch (Exception e) {}

                    return "";
                }
            });


            Legend legend = chart.getLegend();
            legend.setTextColor(R.color.colorAccent);
            legend.setTextSize(16f);
            legend.setWordWrapEnabled(true);

            Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in);
            slide_in.setDuration(1800);
            chart.startAnimation(slide_in);

            try {
                chart.setData(data);
                chart.animateY(1500);
                chart.invalidate();
            } catch (Exception e) {}
        }

        ////////////////
        // YEARLY GRAPH
        ////////////////

        else if (option == option.YEARLY) {
            final ArrayList<Double> year_CO2 = day_manager.getPast_12MonthsCO2(day, month, year);
            ArrayList<Double> month_journey_CO2 = day_manager.getPast365Days_JourneysCO2(day, month, year);

            List<ILineDataSet> lines = new ArrayList<ILineDataSet>();

            // Total
            ArrayList<Entry> entries = new ArrayList<>();
            int counter = 0;
            for (Double month_obj : year_CO2) {
                entries.add(new Entry(counter , month_obj.floatValue()));
                counter++;
            }

            LineDataSet totalDataSet = new LineDataSet(entries, "Total CO₂");
            totalDataSet.setColors(Color.rgb(38, 166, 91));
            totalDataSet.setCircleColor( Color.rgb(38, 166, 91) );
            totalDataSet.setDrawCircleHole(false);
            totalDataSet.setValueTextColor(Color.WHITE);
            totalDataSet.setValueTextSize(16f);
            totalDataSet.setCircleRadius(8f);
            totalDataSet.setLineWidth(5f);
            totalDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            lines.add(totalDataSet);

            // Journey
            entries = new ArrayList<>();
            counter = 0;
            for (Double journey_obj : month_journey_CO2) {
                Log.i("JOURENY", journey_obj+"");

                entries.add(new Entry(counter , journey_obj.floatValue()));
                counter++;
            }

            LineDataSet journeyDataSet = new LineDataSet(entries, "Journey CO₂");
            journeyDataSet.setColors( Color.rgb(52, 152, 219) );
            journeyDataSet.setCircleColor( Color.rgb(52, 152, 219) );
            journeyDataSet.setDrawCircleHole(false);
            journeyDataSet.setValueTextColor(Color.WHITE);
            journeyDataSet.setValueTextSize(16f);
            journeyDataSet.setCircleRadius(8f);
            journeyDataSet.setLineWidth(5f);
            journeyDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            lines.add(journeyDataSet);

            LineData data = new LineData(lines);

            LineChart chart = new LineChart(this);
            chart_container.addView(chart, params);

            chart.setScaleMinima(5f, 1f);
            chart.setDescription(null);
            chart.getAxisRight().setEnabled(false);
            chart.getAxisLeft().setEnabled(false);

            XAxis xval = chart.getXAxis();
            xval.setGranularity(1f);
            xval.setTextSize(16f);
            xval.setTextColor(Color.WHITE);
            xval.setDrawAxisLine(false);
            xval.setDrawGridLines(false);
            xval.setPosition(XAxis.XAxisPosition.BOTTOM);
            xval.setValueFormatter(new IAxisValueFormatter()
            {
                @Override
                public String getFormattedValue(float value, AxisBase axis)
                {
                    try {
                        String month = new DateFormatSymbols().getShortMonths()[(int) value];
                        return month;
                    } catch (Exception e) {}

                    return "";
                }
            });

            Legend legend = chart.getLegend();
            legend.setTextColor(R.color.colorAccent);
            legend.setTextSize(16f);
            legend.setWordWrapEnabled(true);

            Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in);
            slide_in.setDuration(1800);
            chart.startAnimation(slide_in);

            try {
                chart.setData(data);
                chart.animateY(1500);
                chart.invalidate();
            } catch (Exception e) {}
        }
    }

    // set Journeys
    public void setJourneys() {
        ArrayAdapter<Journey> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.journeys);
        list.setAdapter(adapter);

        list.setFocusable(false);
        setListViewHeightBasedOnChildren(list);
    }

    private class MyListAdapter extends ArrayAdapter<Journey> {
        private String latest_day = "";
        private int datepos = 0;
        private int totalCo2 = 0;

        public MyListAdapter() {
            super(MainActivity.this, R.layout.dashboard_item, journey);

            // get totalCO2 to use later
            for (int i = 0; i < journey.size(); i++) {
                totalCo2 += journey.get(i).getCo2();
            }
        }
        public View getView(final int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.dashboard_item, parent, false);
            }

            Journey cur_journey = journey.get(position);

            // DATE
            // split the given date in the form "Day Month Year" into an array
            String[] date = cur_journey.getDateInfo().split("\\s+");

            TextView track_day = (TextView) itemView.findViewById(R.id.track_day);
            track_day.setText(date[0]);

            TextView track_month_year = (TextView) itemView.findViewById(R.id.track_month_year);
            track_month_year.setText(date[1] + " " + date[2]);

            if (!latest_day.equals(date[0])) {
                latest_day = date[0];
                datepos = position;
            } else if (position != datepos) {
                track_day.setAlpha(0.0f);
                track_month_year.setAlpha(0.0f);
            }

            // META
            TextView meta = (TextView) itemView.findViewById(R.id.meta);
            meta.setText(cur_journey.getRouteInfo());

            // RESULTS
            TextView results = (TextView) itemView.findViewById(R.id.result_value);
            results.setText(String.format("%.2f", cur_journey.getCo2()) + "kg CO₂");

            // change colour black to orange to red depending on usage
            float Co2_usage = (float) cur_journey.getCo2() / totalCo2;

            float[] HSV = new float[3];
            HSV[0] = (1-Co2_usage)*90;
            HSV[1] = 1;
            HSV[2] = 0.5f;

            results.setTextColor(Color.HSVToColor(HSV));

            // on track/item click
            CardView track = (CardView) itemView.findViewById(R.id.track);
            track.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    model.setCurrentJouney(journey.get(position));
                    model.setConfirmTrip(false);

                    Log.i("Journey: ", "Clicked Journey " + model.isConfirmTrip());
                    Intent intent = ConfirmTripActivity.makeIntent(MainActivity.this);
                    startActivityForResult(intent,REQUEST_CODE_JOURNEY);
                }
            });

            // on Overflow click
            ImageButton overflow = (ImageButton) itemView.findViewById(R.id.overflow);
            overflow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(MainActivity.this, v);
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.menu_activity_journey, popup.getMenu());
                    popup.show();

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            model.setCurrentJouney(journey.get(position));
                            model.setConfirmTrip(false);

                            Intent intent = ConfirmTripActivity.makeIntent(MainActivity.this);
                            intent.putExtra("menu_select", item.getItemId());
                            startActivityForResult(intent,REQUEST_CODE_JOURNEY);
                            return true;
                        }
                    });

                }
            });

            return itemView;
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            //pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    // set Floating Action Button
    public void setFAB() {
        fab_overlay = (ImageView) findViewById(R.id.fab_overlay);

        fabs = (LinearLayout) findViewById(R.id.fab_buttons);

        fab = (FloatingActionButton) findViewById(R.id.fab_main);
        fab_transport = (FloatingActionButton) findViewById(R.id.fab_journey);
        fab_utility = (FloatingActionButton) findViewById(R.id.fab_utility);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);

        fade_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFAB();
            }
        });

        fab_transport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewJourney();
                model.setConfirmTrip(true);
                isFabOpen = true;
                animateFAB();
            }
        });
        fab_utility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewUtilities();
                isFabOpen = true;
                animateFAB();
            }
        });
    }

    // set tips
    public void setTips() {
        CardView journey_tip_module = (CardView) findViewById(R.id.journey_tip_module);
        final TextView journey_message = (TextView) findViewById(R.id.tip_message_journey);

        journey_tip_module.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tip = model.getTipsManager().getTip(MainActivity.this);
                journey_message.setText(tip);
            }
        });
    };

    public void animateFAB(){
        fade_in.setDuration(300);
        fade_out.setDuration(300);

        if(isFabOpen){
            fab.startAnimation(rotate_backward);
            fab_overlay.startAnimation(fade_out);
            fabs.startAnimation(fab_close);
            fabs.setClickable(false);
            isFabOpen = false;

        } else {
            fab.startAnimation(rotate_forward);
            fab_overlay.startAnimation(fade_in);
            fabs.startAnimation(fab_open);
            fabs.setClickable(true);
            isFabOpen = true;
        }
    }

    // start new journey
    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    private void startNewJourney() {
        Intent intent = SelectTransActivity.makeIntent(MainActivity.this);
        startActivity(intent);
    }
    private void startNewUtilities(){
        Intent intent = UtilitiesActivity.makeIntent(MainActivity.this);
        startActivity(intent);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case (REQUEST_CODE_JOURNEY):
                if (resultCode == Activity.RESULT_OK) {
                    model.setConfirmTrip(true);
                    restart();
                    break;
                }
            default:
                break;

        }

    }

    private void restart()
    {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}

