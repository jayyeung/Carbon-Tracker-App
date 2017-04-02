package com.as3.parmjohal.carbontracker.UI;

import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
<<<<<<< HEAD
=======
import android.os.Build;
>>>>>>> master
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
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.as3.parmjohal.carbontracker.Model.Car;
import com.as3.parmjohal.carbontracker.Model.CarbonTrackerModel;
import com.as3.parmjohal.carbontracker.Model.Day;
import com.as3.parmjohal.carbontracker.Model.DayManager;
import com.as3.parmjohal.carbontracker.Model.Journey;
import com.as3.parmjohal.carbontracker.Model.Utility;
import com.as3.parmjohal.carbontracker.R;
import com.db.chart.Tools;
import com.db.chart.listener.OnEntryClickListener;
import com.db.chart.model.ChartSet;
import com.db.chart.model.LineSet;
import com.db.chart.model.Point;
import com.db.chart.renderer.AxisRenderer;
import com.db.chart.tooltip.Tooltip;
import com.db.chart.view.ChartView;
import com.db.chart.view.LineChartView;
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
import android.widget.RelativeLayout;
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

/**
 * --MainActivity--
 *The Dashboard with selectable graphs
 * and listviews of utilities and Journeys.
 * Can create new journey/utility with the
 * floating action button.
 */
public class MainActivity extends AppCompatActivity {

    private Boolean isFabOpen = false;
    private LinearLayout fabs;
    private FloatingActionButton fab, fab_transport, fab_utility;
    private ImageView fab_overlay;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward, fade_in, fade_out, pulse;

    private enum Chart_options { DAILY, MONTHLY, YEARLY };

    CarbonTrackerModel model;
    DayManager day_manager;

    private ArrayList<Journey> journey;
    private ArrayList<Utility> utilities;
    public static final int REQUEST_CODE_JOURNEY= 2020;
    public static final int REQUEST_CODE_UTILITY= 2021;
    private static final int REQUEST_CODE_EDIT = 2022;
    public static final int GET_DATE_FOR_CHART = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // add icon to dashboard action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.app_icon_white);
        setTitle(getString(R.string.Dashboard));

        SharedPreference.saveCurrentModel(this);
        model = CarbonTrackerModel.getCarbonTrackerModel(this);
        model.setEditJourney(false);
        model.setConfirmTrip(true);
        journey = model.getJourneyManager().getJourneyCollection();
        utilities = model.getUtilityManager();
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
        Collections.reverse(utilities);

        // set Overview
        setOverview();

        // set FAB
        setFAB();

        // show Journeys
        setJourneys();

        //show Utilities
        setUtilities();

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

                            final String date = day.getDay() + "/" + day.getMonth() + "/" + day.getYear();
                            MenuItem item = menu.add(date);
                            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    chart_status.setText(date);
                                    chart_type.setText(R.string.Daily_Carbon_Usage);
                                    setGraph(Chart_options.DAILY, day.getDay(), day.getMonth(), day.getYear());
                                    return true;
                                }
                            });
                        }

                        popup.show(); //showing popup menu
                        break;
                    case R.id.month_radio:
                        chart_status.setText(R.string.Last_28_days);
                        chart_type.setText(R.string.Monthly_Carbon_Usage);
                        setGraph(Chart_options.MONTHLY, 0,0,0);
                        break;
                    case R.id.year_radio:
                        chart_status.setText(R.string.Last_365_days);
                        chart_type.setText(R.string.Annual_Carbon_Usage);
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
        HorizontalScrollView chart_container = (HorizontalScrollView) findViewById(R.id.chart_container);
        chart_container.removeAllViewsInLayout();

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        // Get Current Date
        DateFormat df = new SimpleDateFormat("dd MM yy");
        String[] date = (df.format(new Date())).split("\\s+");

        // if no input day, month, and year is given, default current day, month, year
        int day = (inp_day != 0) ? inp_day : Integer.parseInt(date[0]),
            month = (inp_month != 0) ? inp_month : Integer.parseInt(date[1]),
            year = (inp_year != 0) ? inp_year : Integer.parseInt(date[2]);

        ////////////////
        // DAILY GRAPH
        ////////////////

        if (option == option.DAILY) {

        }

        ////////////////
        // MONTHLY GRAPH
        ////////////////

        else if (option == option.MONTHLY) {
            final LineChartView chart = new LineChartView(getBaseContext());

            //// JOURNEY
            LineSet dataset = new LineSet();
            dataset.addPoint(new Point("first", 5));
            dataset.addPoint(new Point("second",3));
            dataset.addPoint(new Point("third", 4));
            dataset.addPoint(new Point("forth", 3));
            dataset.addPoint(new Point("fifth", 3));

            // set line styles
            chart.setHorizontalScrollBarEnabled(true);

            dataset.setDotsColor( Color.rgb(255,255,255) );
            dataset.setDotsStrokeColor( Color.rgb(52, 152, 219) );
            dataset.setColor( Color.rgb(44, 133, 193) );
            dataset.setDotsRadius(14f);
            dataset.setDotsStrokeThickness(8f);
            dataset.setThickness(12f);
            dataset.setSmooth(true);

            setGeneralChartStylings(chart, dataset, Color.rgb(52, 152, 219), 0);

            //// UTILITY
            LineSet dataset2 = new LineSet();
            dataset2.addPoint(new Point("first", 1));
            dataset2.addPoint(new Point("second",2));
            dataset2.addPoint(new Point("third", 5));
            dataset2.addPoint(new Point("forth", 3));
            dataset2.addPoint(new Point("fifth", 3));

            // set line styles
            dataset2.setDotsColor( Color.rgb(255, 255, 255) );
            dataset2.setDotsStrokeColor( Color.rgb(230, 126, 34) );
            dataset2.setColor( Color.rgb(211, 84, 0) );
            dataset2.setDotsRadius(14f);
            dataset2.setDotsStrokeThickness(8f);
            dataset2.setThickness(12f);
            dataset2.setSmooth(true);

            setGeneralChartStylings(chart, dataset2, Color.rgb(230, 126, 34), 4f);

            // sexy animation
            // NOTE: com.db.chart.animation.Animation == Animation, but since Animation is already used
            // we have to use the entire path
            int entry_size = dataset.getEntries().size();
            int[] order = new int[entry_size];
            for (int i = 0; i < entry_size; i++) { order[i] = i; }

            com.db.chart.animation.Animation anim = new com.db.chart.animation.Animation(1000);
            anim.setEasing(new DecelerateInterpolator());
            anim.setAlpha(2);
            anim.setOverlap(0.5f, order);

            chart.setPadding(35,36,45,12);
            chart_container.addView(chart, params);
            chart.show(anim);
        }

        ////////////////
        // YEARLY GRAPH
        ////////////////

        else if (option == option.YEARLY) {
        }
    }

    // set chart stylings for line/stacked bar chart
    private void setGeneralChartStylings(final ChartView chart, ChartSet set, final int tooltip_colour, float national_target) {
        // set general chart styles
        chart.setYAxis(false);
        chart.setXAxis(false);
        chart.setYLabels(AxisRenderer.LabelPosition.OUTSIDE);
        chart.setAxisLabelsSpacing(36f);
        chart.setFontSize(27);
        chart.setLabelsColor( Color.rgb(255,255,255) );

        // set national target line if needed
        if (national_target > 0) {
            // national average
            Paint paint = new Paint();
            paint.setColor(Color.parseColor("#95a5a6"));
            paint.setStyle(Paint.Style.STROKE);
            paint.setAntiAlias(true);
            paint.setStrokeWidth(Tools.fromDpToPx(4f));
            paint.setPathEffect(new DashPathEffect(new float[]{10.0f, 5.0f}, 0));
            chart.setValueThreshold(national_target, national_target, paint);
        }

        // set click listeners for tool tips
        chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chart.dismissAllTooltips();
            }
        });

        chart.setOnEntryClickListener(new OnEntryClickListener() {
            @Override
            public void onClick(int setIndex, int entryIndex, Rect rect) {
                chart.dismissAllTooltips();

                // Tooltip
                Tooltip tip = new Tooltip(getBaseContext(), R.layout.chart_tooltip, R.id.value);
                tip.setVerticalAlignment(Tooltip.Alignment.BOTTOM_TOP);
                tip.setDimensions((int) Tools.fromDpToPx(58), (int) Tools.fromDpToPx(26));
                ((RelativeLayout) tip.findViewById(R.id.tip)).setBackgroundColor( tooltip_colour );

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    tip.setEnterAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 1)).setDuration(150);
                    tip.setExitAnimation(PropertyValuesHolder.ofFloat(View.ALPHA,0)).setDuration(150);
                }

                chart.setTooltips(tip);
            }
        });

        chart.addData(set);
    }

    // set Journeys
    public void setJourneys() {
        ArrayAdapter<Journey> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.journeys);
        list.setAdapter(adapter);

        list.setFocusable(false);
        setListViewHeightBasedOnChildren(list);
    }
    public void setUtilities() {
        ArrayAdapter<Utility> adapter2 = new MyListAdapter2();
        ListView list = (ListView) findViewById(R.id.utilities);
        list.setAdapter(adapter2);

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
            results.setText(String.format("%.2f", cur_journey.getCo2()) + getString(R.string.kg_co2));

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

                    Log.i(getString(R.string.journey)+": ", getString(R.string.clickedjourney) + model.isConfirmTrip());
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
                            intent.putExtra(getString(R.string.menuselect), item.getItemId());
                            startActivityForResult(intent,REQUEST_CODE_JOURNEY);
                            return true;
                        }
                    });

                }
            });

            return itemView;
        }
    }
    private class MyListAdapter2 extends ArrayAdapter<Utility> {
        private String latest_day = "";
        private int datepos = 0;
        private int totalCo2 = 0;

        public MyListAdapter2() {
            super(MainActivity.this, R.layout.dashboard_item, utilities);
            // get totalCO2 to use later
            for (int i = 0; i < utilities.size(); i++) {
                totalCo2 += utilities.get(i).getTotalCo2();
            }
        }


        public View getView(final int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.dashboard_item, parent, false);
            }

            Utility cur_utility = utilities.get(position);

            // DATE
            // split the given date in the form "Day Month Year" into an array
            String[] date = cur_utility.getDateInfo(cur_utility.getStartDate()).split("\\s+");

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
            meta.setText(cur_utility.toString());

            // RESULTS
            TextView results = (TextView) itemView.findViewById(R.id.result_value);
            results.setText(String.format("%.2f", cur_utility.getTotalCo2()) + getString(R.string.kg_co2));

            // change colour black to orange to red depending on usage
            float Co2_usage = (float) cur_utility.getTotalCo2() / totalCo2;

            float[] HSV = new float[3];
            HSV[0] = (1 - Co2_usage) * 90;
            HSV[1] = 1;
            HSV[2] = 0.5f;

            results.setTextColor(Color.HSVToColor(HSV));

            // on track/item click
            // CardView track = (CardView) itemView.findViewById(R.id.track);
            // track.setOnClickListener(new View.OnClickListener() {
            //     @Override
            //      public void onClick(View v) {
            //         model.setCurrentJouney(journey.get(position));
            //         model.setConfirmTrip(false);

            //        Log.i("Journey: ", "Clicked Journey " + model.isConfirmTrip());
            //        Intent intent = ConfirmTripActivity.makeIntent(MainActivity.this);
            //        startActivityForResult(intent,REQUEST_CODE_JOURNEY);
            //      }
            //   });

            // on Overflow click
            ImageButton overflow = (ImageButton) itemView.findViewById(R.id.overflow);
            overflow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(MainActivity.this, v);
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.menu_activity_utility, popup.getMenu());
                    popup.show();

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            if (item.getItemId() == R.id.delete) {
                                model.getUtilityManager().remove(position);
                                restart();
                            } else if (item.getItemId() == R.id.edit) {
                                model.setCurrentPos(position);
                                model.setEditUtility(true);
                                Intent intent = UtilitiesActivity.makeIntent(MainActivity.this);
                                startActivityForResult(intent,REQUEST_CODE_UTILITY);



                            }
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
        fab_transport = (FloatingActionButton) findViewById(R.id.fab_journey);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);
        pulse = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.pulse);

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
                v.startAnimation(pulse);
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
        startActivityForResult(intent,REQUEST_CODE_UTILITY);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case (REQUEST_CODE_JOURNEY):
                if (resultCode == Activity.RESULT_OK) {
                    model.setConfirmTrip(true);
                    restart();
                    break;
                }
            case (REQUEST_CODE_UTILITY):
                restart();
                break;
            case(REQUEST_CODE_EDIT):
                if (resultCode == Activity.RESULT_OK) {
                    model.setCurrentUtility(null);
                    model.setEditUtility(false);
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

