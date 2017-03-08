package com.as3.parmjohal.carbontracker;

import android.app.Activity;
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

public class SelectCarActivity extends AppCompatActivity {

    CarbonTrackerModel model;

    ArrayList<Car> carList;
    private int position;
    public static final int REQUEST_CODE_ADD= 1024;
    public static final int REQUEST_CODE_EDIT= 1025;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_car);
        model = CarbonTrackerModel.getCarbonTrackerModel(this);
        setTitle("Select Transportation");

        populateListView();
        registerClickCallBack();
    }

    private void populateListView() {

        carList = model.getCarManager().getCarCollection();

        ArrayAdapter<Car> adapter = new SelectCarActivity.MyListAdaptder();
        ListView list = (ListView) findViewById(R.id.carListView);
        list.setAdapter(adapter);
        registerForContextMenu(list);
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


            Car thisCar = carList.get(position);

            TextView carName = (TextView) itemView.findViewById(R.id.carName);
            carName.setText(thisCar.getName());

            TextView description = (TextView) itemView.findViewById(R.id.carDescription);
            description.setText(thisCar.getMake() + ", " + thisCar.getModel() + ", " + thisCar.getYear());

            TextView description2 = (TextView) itemView.findViewById(R.id.carDescription2);
            description2.setText(thisCar.getTranyType() + ", " + thisCar.getFuelType() + " Fuel, " +thisCar.getEngineDisplacment()+"L");//fill

            return itemView;
        }

    }

    private void registerClickCallBack() {
        ListView clicklist = (ListView) findViewById(R.id.carListView);
        clicklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                model.setCurrentCar(carList.get(position));
                Intent intent = SelectRouteActivity.makeIntent(SelectCarActivity.this);
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
        ListView clicklist = (ListView) findViewById(R.id.carListView);

        Car clickedCar = (Car) clicklist.getItemAtPosition(info.position);

        if(item.getItemId() == R.id.delete_id)
        {
            //do stuff if the delete button is clicked...
            Toast.makeText(SelectCarActivity.this, "DELETED", Toast.LENGTH_SHORT).show();
            model.getCarManager().remove(clickedCar);
            restart();
        }
        else if(item.getItemId() == R.id.edit_id)
        {
            //do stuff if the edit is clicked
            Toast.makeText(SelectCarActivity.this, "EDIT", Toast.LENGTH_SHORT).show();

            model.setCurrentCar(clickedCar);
            Intent intent2 = EditCarActivity.makeIntent(SelectCarActivity.this);
            startActivityForResult(intent2,REQUEST_CODE_EDIT);

        }
        return super.onContextItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case (REQUEST_CODE_ADD):
                if (resultCode == Activity.RESULT_OK) {
                    Intent intent = SelectRouteActivity.makeIntent(SelectCarActivity.this);
                    startActivity(intent);
                    populateListView();
                    break;


                }
            case (REQUEST_CODE_EDIT):
                if(resultCode == Activity.RESULT_OK){
                    populateListView();
                    break;

                }
        }

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
                startActivityForResult(intent,REQUEST_CODE_ADD);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void restart()
    {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}

