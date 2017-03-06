package com.as3.parmjohal.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SelectRouteActivity extends AppCompatActivity {

    ArrayList<String> routeList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_route);

        setTitle("Select Route");

        populateListView();
        registerClickCallBack();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    private void populateListView() {
        //Test, Delete This
        routeList.add("test");
        routeList.add("test2");
        routeList.add("test3");

        ArrayAdapter<String> adapter = new SelectRouteActivity.MyListAdaptder();
        ListView list = (ListView) findViewById(R.id.routeListView);
        list.setAdapter(adapter);
    }

    /**
     * Sets up the complex Listview
     */
    private class MyListAdaptder extends ArrayAdapter<String> {
        public MyListAdaptder() {
            super(SelectRouteActivity.this, R.layout.route_list_view, routeList);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.route_list_view, parent, false);
            }


            //Change according to getting the strings of Route

            //  TextView routeName = (TextView) itemView.findViewById(R.id.routeName);
            //  routeName.setText();//fill
            //  TextView highway= (TextView) itemView.findViewById(R.id.distanceH);
            //  highway.setText();//fill
            //  TextView city= (TextView) itemView.findViewById(R.id.distanceC);
            //  city.setText();//fill

            return itemView;


        }
    }


    private void registerClickCallBack() {
        ListView clicklist = (ListView) findViewById(R.id.routeListView);
        clicklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {


                String Car = "String";//Change To Car Class and Get car from car colection (position)
                Intent intent = ConfirmTripActivity.makeIntent(SelectRouteActivity.this);
                startActivity(intent);


            }
        });
        clicklist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                String route = "String";//Change To Car Class and Get car from car colection (position)

                Intent intent2 = EditRouteActivity.makeIntent(SelectRouteActivity.this);
                startActivity(intent2);
                return true;
            }
        });
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
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }

    }




    public static Intent makeIntent(Context context) {
            Intent intent = new Intent(context, SelectRouteActivity.class);
            return intent;
        }


    }

