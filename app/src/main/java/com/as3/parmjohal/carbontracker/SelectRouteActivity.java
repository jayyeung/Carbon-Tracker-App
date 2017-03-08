package com.as3.parmjohal.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SelectRouteActivity extends AppCompatActivity {
    CarbonTrackerModel model = CarbonTrackerModel.getCarbonTrackerModel(this);
    ArrayList<Route> routeList = new ArrayList<Route>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_route);

        setTitle("Select Route");

        routeList = model.getRouteManager().getRouteCollection();

        populateListView();
        registerClickCallBack();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    private void populateListView() {

        ArrayAdapter<Route> adapter = new SelectRouteActivity.MyListAdaptder();
        ListView list = (ListView) findViewById(R.id.routeListView);
        list.setAdapter(adapter);
        registerForContextMenu(list);
    }

    /**
     * Sets up the complex Listview
     */
    private class MyListAdaptder extends ArrayAdapter<Route> {
        public MyListAdaptder() {
            super(SelectRouteActivity.this, R.layout.route_list_view, routeList);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.route_list_view, parent, false);
            }
            Route thisRoute= routeList.get(position);

            //Change according to getting the strings of Route

              TextView routeName = (TextView) itemView.findViewById(R.id.routeName);
              routeName.setText(thisRoute.getRouteName());
              TextView highway= (TextView) itemView.findViewById(R.id.distanceH);
              highway.setText("" + thisRoute.getHwyDistance());//fill
              TextView city= (TextView) itemView.findViewById(R.id.distanceC);
              city.setText(""+ thisRoute.getCityDistance());//fill

            return itemView;


        }
    }


    private void registerClickCallBack() {
        ListView clicklist = (ListView) findViewById(R.id.routeListView);
        clicklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {


                model.setCurrentRoute(routeList.get(position));
                Intent intent = ConfirmTripActivity.makeIntent(SelectRouteActivity.this);
                startActivity(intent);


            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_delete_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        ListView clicklist = (ListView) findViewById(R.id.routeListView);

        Route clickedRoute = (Route) clicklist.getItemAtPosition(info.position);

        if(item.getItemId() == R.id.delete_id)
        {
            //do stuff if the delete button is clicked...
            Toast.makeText(SelectRouteActivity.this, "DELETED", Toast.LENGTH_SHORT).show();
            model.getRouteManager().remove(clickedRoute);
            restart();
        }
        else if(item.getItemId() == R.id.edit_id)
        {
            //do stuff if the edit is clicked
            Toast.makeText(SelectRouteActivity.this, "EDIT", Toast.LENGTH_SHORT).show();

            model.setCurrentRoute(clickedRoute);
            Intent intent2 = EditRouteActivity.makeIntent(SelectRouteActivity.this);
            startActivity(intent2);
            finish();
        }
        return super.onContextItemSelected(item);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_route_select, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_add:
                Intent intent = AddRouteActivity.makeIntent(SelectRouteActivity.this);
                startActivity(intent);
                finish();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }

    }




    public static Intent makeIntent(Context context) {
            Intent intent = new Intent(context, SelectRouteActivity.class);
            return intent;
        }
    private void restart()
    {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    }

