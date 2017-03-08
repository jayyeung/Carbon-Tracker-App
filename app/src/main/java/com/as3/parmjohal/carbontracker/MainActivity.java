package com.as3.parmjohal.carbontracker;

import android.graphics.Color;
import android.media.Image;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
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

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab_transport;
    private ImageView fab_overlay;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward, fade_in, fade_out;

    CarbonTrackerModel model;

    private ArrayList<Journey> journey ;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // add icon to dashboard action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.app_icon_white);

        model = CarbonTrackerModel.getCarbonTrackerModel(this);
        journey = model.getJourneyManager().getJourneyCollection();

        // set FAB
        setFAB();

        // set Graph
        setGraph();

        // show Journeys
        setJourneys();

        // intro animation
        animateDashboard();

        registerClickCallBack();
    }

    // animate Dashboard
    public void animateDashboard() {
        ImageView background_img = (ImageView) findViewById(R.id.background);
        PieChart chart = (PieChart) findViewById(R.id.chart);

        Animation fade_in2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        fade_in.setDuration(2000);
        background_img.startAnimation(fade_in);

        fade_in2.setDuration(1800);
        chart.startAnimation(fade_in2);
    }

    //set Graph
    public void setGraph() {
        // get pie info
        List<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < journey.size(); i++) {
            entries.add(new PieEntry(Float.parseFloat(String.format("%.2f",journey.get(i).getCo2())), i));
        }

        final int[] CHART_COLOURS = { Color.rgb(38, 166, 91), Color.rgb(63, 195, 128) , Color.rgb(0, 177, 106), Color.rgb(30, 130, 76), Color.rgb(27, 188, 155) };

        PieDataSet dataSet = new PieDataSet(entries, "Journey CO₂");
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
        ArrayAdapter<Journey> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.journeys);
        list.setAdapter(adapter);

        list.setFocusable(false);
        setListViewHeightBasedOnChildren(list);
    }

    private void registerClickCallBack() {
        final ListView list = (ListView) findViewById(R.id.journeys);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                model.setCurrentJouney(journey.get(position));
                model.setConfirmTrip(false);
                Intent intent = ConfirmTripActivity.makeIntent(MainActivity.this);
                startActivity(intent);

            }
        });


    }

    private class MyListAdapter extends ArrayAdapter<Journey> {
        public MyListAdapter() {
            super(MainActivity.this, R.layout.dashboard_item, journey);
        }
        public View getView(final int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.dashboard_item, parent, false);
            }

            Journey cur_journey = journey.get(position);

            // TITLE
            TextView title = (TextView) itemView.findViewById(R.id.title);
            title.setText("Car Trip " + (position+1));

            // META
            TextView meta = (TextView) itemView.findViewById(R.id.meta);
            meta.setText("Driven on " + cur_journey.getDateInfo());

            // RESULTS
            TextView results = (TextView) itemView.findViewById(R.id.result_value);
            results.setText(String.format("%.2f", cur_journey.getCo2()) + "kg CO₂");

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
        fab_overlay = (ImageView) findViewById(R.id.fab_overlay);

        fab = (FloatingActionButton) findViewById(R.id.fab_main);
        fab_transport = (FloatingActionButton) findViewById(R.id.fab_transport);

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
                isFabOpen = true;
                animateFAB();
            }
        });
    }

    public void animateFAB(){
        fade_in.setDuration(300);
        fade_out.setDuration(300);

        if(isFabOpen){
            fab.startAnimation(rotate_backward);
            fab_overlay.startAnimation(fade_out);
            fab_transport.startAnimation(fab_close);
            fab_transport.setClickable(false);
            isFabOpen = false;

        } else {
            fab.startAnimation(rotate_forward);
            fab_overlay.startAnimation(fade_in);
            fab_transport.startAnimation(fab_open);
            fab_transport.setClickable(true);
            isFabOpen = true;
        }
    }

    // start new journey
    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    private void startNewJourney() {
        Intent intent = SelectCarActivity.makeIntent(MainActivity.this);
        startActivity(intent);
    }
}
