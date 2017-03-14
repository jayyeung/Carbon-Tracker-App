package com.as3.parmjohal.carbontracker.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.as3.parmjohal.carbontracker.Model.Car;
import com.as3.parmjohal.carbontracker.Model.CarbonTrackerModel;
import com.as3.parmjohal.carbontracker.R;

import java.util.ArrayList;

public class AddCarActivity extends AppCompatActivity {
    private CarbonTrackerModel model = CarbonTrackerModel.getCarbonTrackerModel(this);
    private String make;
    private String carModel;
    private Integer year;
    private ArrayList<Car> carList = new ArrayList<>();
    private String carNameString;
    private Car carClicked = new Car();
    private boolean carIsClicked = false;
    private EditText editName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        setTitle("Add Transportation");

        //adjust layout position when keyboard is out
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setupMakeSpinner();

         editName = (EditText) findViewById(R.id.editName);

    }

    private void setupMakeSpinner() {

        final Spinner makeSelection = (Spinner) findViewById(R.id.makeSpinner);
        // Spinner Drop down elements
        final ArrayList<String> makeCategories = model.getVehicleData().getCarMakers();
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, makeCategories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        makeSelection.setAdapter(dataAdapter);
        makeSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                make =  makeSelection.getSelectedItem().toString();
                setupModelSpinner();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                make = makeCategories.get(0);
                setupModelSpinner();
            }

            });



    }

    private void setupModelSpinner() {
        final Spinner modelSelection = (Spinner) findViewById(R.id.modelSpinner);
        // Spinner Drop down elements
        final ArrayList<String> modelCategories = model.getVehicleData().getModels(make);
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, modelCategories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        modelSelection.setAdapter(dataAdapter);
        modelSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                carModel =  modelSelection.getSelectedItem().toString();
                setupYearSpinner();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                carModel = modelCategories.get(0);
                setupYearSpinner();
            }

        });
    }

    private void setupYearSpinner() {
        final Spinner yearSelection = (Spinner) findViewById(R.id.yearSpinner);
        // Spinner Drop down elements
        final ArrayList<Integer> yearCategories = model.getVehicleData().getCarYears(make,carModel);
        // Creating adapter for spinner
        ArrayAdapter<Integer> dataAdapter2 = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, yearCategories);
        // Drop down layout style - list view with radio button
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        yearSelection.setAdapter(dataAdapter2);
        yearSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = (Integer) yearSelection.getSelectedItem();
                setupListView();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                year = (Integer) yearCategories.get(0);
                setupListView();
            }

        });
    }

    private void setupListView() {

        carList = model.getVehicleData().getPossibleCars(make,carModel,year);

        ArrayAdapter<Car> adapter = new AddCarActivity.MyListAdaptder();
        ListView list = (ListView) findViewById(R.id.carList);
        list.setAdapter(adapter);
    }

    /**
     * Sets up the complex Listview
     */
    private class MyListAdaptder extends ArrayAdapter<Car> {
        public MyListAdaptder() {
            super(AddCarActivity.this, R.layout.car_list_view,carList);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.car_list_view, parent, false);
            }



            Car thisCar = carList.get(position);


            TextView carName = (TextView) itemView.findViewById(R.id.carName);
            carName.setText(thisCar.getMake() + ", " + thisCar.getModel() + ", " + thisCar.getYear());
            TextView description= (TextView) itemView.findViewById(R.id.carDescription);
            description.setText("Transmission: " +thisCar.getTranyType() + ", " + thisCar.getFuelType() + " Fuel, " +"Engine Displacement: "+thisCar.getEngineDisplacment()+ "L");
            registerClickCallBack();

            return itemView;


        }
        private void registerClickCallBack() {
            ListView clicklist = (ListView) findViewById(R.id.carList);
            clicklist.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            clicklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                    carClicked = carList.get(position);
                    for(int a = 0; a < parent.getChildCount(); a++)
                    {
                        parent.getChildAt(a).setBackgroundColor(Color.WHITE);
                    }
                    viewClicked.setBackgroundColor(Color.GRAY);

                    carIsClicked = true;



                }
            });
        }
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_confirm_decline, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_confirm:
                carNameString = editName.getText().toString();
                Log.i("test", ""+carNameString);


                if (carNameString.isEmpty()){
                    Toast.makeText(AddCarActivity.this, "Please Name Your Car", Toast.LENGTH_SHORT).show();
                    return false;
                }
               else if(!carIsClicked) {
                    Toast.makeText(AddCarActivity.this, "Please Select a Car", Toast.LENGTH_SHORT).show();
                    return false;
                }
               if(checkDuplicate(carClicked)){
                   Toast.makeText(AddCarActivity.this, "This Car Already Exists", Toast.LENGTH_SHORT).show();
                   return false;
                }

                else{
                    carClicked.setName(carNameString);

                    model.setCurrentCar( model.getCarManager().add(carClicked));
                    for (int i = 0; i < model.getCarManager().getCarCollection().size(); i++) {

                        Log.i("car collection: ", model.getCarManager().getCarCollection().get(i).toString());

                    }
                    Intent intent = new Intent();
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                    return true;
                }



            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private boolean checkDuplicate(Car carClicked) {
        for (int i = 0; i < model.getCarManager().getCarCollection().size(); i++) {
            Log.i("test2", ""+model.getCarManager().getCarCollection().get(i).getName());
            if (carClicked.equals(model.getCarManager().getCarCollection().get(i)) && carNameString.equals(model.getCarManager().getCarCollection().get(i).getName())) {
                return true;
            }
        }


        return false;

    }


    public static Intent makeIntent(Context context) {
        Intent intent= new Intent(context, AddCarActivity.class);
        return intent;
    }
}
