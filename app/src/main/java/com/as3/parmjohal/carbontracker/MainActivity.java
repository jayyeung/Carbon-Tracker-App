package com.as3.parmjohal.carbontracker;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab_transport;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;

    ArrayList<String> carList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // add icon to dashboard action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.app_icon_white);

        // set FAB
        setFAB();

        // set Graph
        setGraph();

        // show Journeys
        carList.add("Test");
        carList.add("Test");
        carList.add("Test");
        carList.add("Test");
        carList.add("Test");

        setJourneys();

        // intro animation
        animateDashboard();
    }

    // animate Dashboard
    public void animateDashboard() {
        ImageView background_img = (ImageView) findViewById(R.id.background);
        PieChart chart = (PieChart) findViewById(R.id.chart);

        Animation fade_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        Animation fade_in2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        fade_in.setDuration(2000);
        background_img.startAnimation(fade_in);

        fade_in2.setDuration(1800);
        chart.startAnimation(fade_in2);
    }

    //set Graph
    public void setGraph() {
        float rainfall[] = {8.8f,8.8f,8.8f};
        int months[] = {1,2,3};

        // get pie info
        List<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < rainfall.length; i++) {
            entries.add(new PieEntry(rainfall[i], months[i]));
        }

        final int[] CHART_COLOURS = { Color.rgb(38, 166, 91), Color.rgb(63, 195, 128) , Color.rgb(0, 177, 106), Color.rgb(30, 130, 76), Color.rgb(27, 188, 155) };

        PieDataSet dataSet = new PieDataSet(entries, "Rainfall for Van");
        dataSet.setColors(CHART_COLOURS);
        dataSet.setValueTextColor(R.color.white);
        dataSet.setValueTextSize(16f);

        PieData data = new PieData(dataSet);

        // set onto chart
        PieChart chart = (PieChart) findViewById(R.id.chart);
        chart.setTouchEnabled(false);
        chart.setDrawHoleEnabled(false);
        chart.setDescription(null);

        Legend legend = chart.getLegend();
        legend.setTextColor(R.color.colorAccent);
        legend.setTextSize(16f);
        legend.setWordWrapEnabled(true);


        chart.setData(data);
        chart.animateXY(1100,1100);
        chart.invalidate();
    }

    // set Journeys
    public void setJourneys() {
        ArrayAdapter<String> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.journeys);
        list.setAdapter(adapter);

        list.setFocusable(false);
        setListViewHeightBasedOnChildren(list);
    }

    private class MyListAdapter extends ArrayAdapter<String> {
        public MyListAdapter() {
            super(MainActivity.this, R.layout.dashboard_item, carList);
        }
        public View getView(final int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.dashboard_item, parent, false);
            }

            return itemView;
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, LinearLayout.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    // set Floating Action Button
    public void setFAB() {
        fab = (FloatingActionButton) findViewById(R.id.fab_main);
        fab_transport = (FloatingActionButton) findViewById(R.id.fab_transport);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFAB();
            }
        });
    }

    public void animateFAB(){
        if(isFabOpen){
            fab.startAnimation(rotate_backward);
            fab_transport.startAnimation(fab_close);
            fab_transport.setClickable(false);
            isFabOpen = false;

        } else {
            fab.startAnimation(rotate_forward);
            fab_transport.startAnimation(fab_open);
            fab_transport.setClickable(true);
            isFabOpen = true;
        }
    }
}
