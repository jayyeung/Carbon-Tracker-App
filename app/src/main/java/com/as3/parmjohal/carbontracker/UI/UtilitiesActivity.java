package com.as3.parmjohal.carbontracker.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.as3.parmjohal.carbontracker.Model.CarbonTrackerModel;
import com.as3.parmjohal.carbontracker.Model.Route;
import com.as3.parmjohal.carbontracker.Model.Skytrain;
import com.as3.parmjohal.carbontracker.Model.Utility;
import com.as3.parmjohal.carbontracker.R;
import com.as3.parmjohal.carbontracker.SharedPreference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UtilitiesActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_START_DATE =111;
    private static final int REQUEST_CODE_END_DATE =222;
    private CarbonTrackerModel model;
    private EditText editElec;
    private EditText editGas;
    private EditText editPersons;
    private Button start;
    private Button end;
    private boolean isElectricity=true;

    private Date startDate;
    private Date endDate;

    private int amount;
    private int persons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utilities);
        SharedPreference.saveCurrentModel(this);
        model = CarbonTrackerModel.getCarbonTrackerModel(this);
        setupButtons();
        createRadioButtonListener();



    }

    private void setupButtons() {
        start = (Button) findViewById(R.id.startButton);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(UtilitiesActivity.this,CalenderActivity.class),REQUEST_CODE_START_DATE);
            }
        });
        end = (Button) findViewById(R.id.endButton);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(UtilitiesActivity.this,CalenderActivity.class),REQUEST_CODE_END_DATE);
            }
        });
    }


    public static Intent makeIntent(Context context) {
        Intent intent= new Intent(context, UtilitiesActivity.class);
        return intent;
    }
    private void createRadioButtonListener() {
        // RadioGroup type = (RadioGroup) findViewById(R.id.radioList);
        RadioButton button = (RadioButton) findViewById(R.id.radioButton1);
        RadioButton button2 = (RadioButton) findViewById(R.id.radioButton2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) findViewById(R.id.gasText);
                isElectricity=true;
                textView.setText("Amount of Electricity Used (KWh):");

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isElectricity = false;
                TextView textView = (TextView) findViewById(R.id.gasText);
                textView.setText("Amount of Natural Gas Used (Gj): ");



            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_activity_confirm_decline, menu);
            return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        OptionSelect(item.getItemId());
        return true;

    }
    public void OptionSelect(int id) {
        switch (id) {
            case android.R.id.home:

                finish();
                break;
            case R.id.action_confirm:
                editGas = (EditText) findViewById(R.id.editAmount);
                editPersons = (EditText) findViewById(R.id.editPersons);
                String stringAmount =  editGas.getText().toString();
                String stringPersons =  editPersons.getText().toString();
                amount = Integer.parseInt(stringAmount);
                persons = Integer.parseInt(stringPersons);

                Utility utility = new Utility(isElectricity,amount,persons,startDate,endDate);

                Log.i("Test", utility.toString());

                if(model.isEditUtility()) {
                    model.getUtilityManager().remove(model.getCurrentPos());
                    model.getUtilityManager().add(utility);
                    Intent intent = new Intent();
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                else{
                    model.getUtilityManager().add(utility);
                    finish();
                }

                 break;
            default:
                break;
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case (REQUEST_CODE_START_DATE):
                if(resultCode == Activity.RESULT_OK){
                    startDate = model.getCurrentDate();
                    DateFormat df = new SimpleDateFormat("dd MMM yyyy");
                    String dateString = df.format(model.getCurrentDate());
                    start.setText(dateString);


                    model.setCurrentDate(null);
                    break;


                }
            case (REQUEST_CODE_END_DATE):
                if(resultCode == Activity.RESULT_OK){
                    endDate = model.getCurrentDate();
                    DateFormat df = new SimpleDateFormat("dd MMM yyyy");
                    String dateString = df.format(model.getCurrentDate());
                    end.setText(dateString);
                    //pass date to utilities

                    model.setCurrentDate(null);
                    break;


                }

        }


    }
}
