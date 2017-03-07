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

public class SelectCarActivity extends AppCompatActivity {


    ArrayList<Car> carList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_car);


        setTitle("Select Transportation");


        populateListView();
        registerClickCallBack();
    }


    private void populateListView() {
        CarbonTrackerModel model = CarbonTrackerModel.getCarbonTrackerModel(this);
        carList = model.getCarManager().getCarCollection();



        ArrayAdapter<Car> adapter = new SelectCarActivity.MyListAdaptder();
        ListView list = (ListView) findViewById(R.id.carListView);
        list.setAdapter(adapter);

    }


    /**
     * Sets up the complex Listview
     */
    private class MyListAdaptder extends ArrayAdapter<Car> {
        public MyListAdaptder() {
            super(SelectCarActivity.this, R.layout.car_list_view, carList);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.car_list_view, parent, false);
            }


            //Change according to getting the strings of Car

            //  TextView carName = (TextView) itemView.findViewById(R.id.carName);
            //  carName.setText();//fill
            //  TextView description= (TextView) itemView.findViewById(R.id.carDescription);
            //  description.setText();//fill

            return itemView;


        }

    }
    private void registerClickCallBack() {
        ListView clicklist = (ListView) findViewById(R.id.carListView);
        clicklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {


                String Car = "String";//Change To Car Class and Get car from car colection (position)
                Intent intent = SelectRouteActivity.makeIntent(SelectCarActivity.this);
                startActivity(intent);



            }
        });
        clicklist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                String Car = "String";//Change To Car Class and Get car from car colection (position)

                Intent intent2 = EditCarActivity.makeIntent(SelectCarActivity.this);
                startActivity(intent2);
                return true;
            }
        });
    }


    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, SelectCarActivity.class);
        return intent;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_car_select, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = AddCarActivity.makeIntent(SelectCarActivity.this);
                startActivity(intent);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
