package com.as3.parmjohal.carbontracker.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.as3.parmjohal.carbontracker.Model.CarbonTrackerModel;
import com.as3.parmjohal.carbontracker.Model.Journey;
import com.as3.parmjohal.carbontracker.Model.Utility;
import com.as3.parmjohal.carbontracker.R;

import org.w3c.dom.Text;

public class UtilityDetailsActivity extends AppCompatActivity {
    public static CarbonTrackerModel model;
    private Utility utility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);

        setContentView(R.layout.activity_utility_details);
        setTitle(getString(R.string.utilitydata));

        model = CarbonTrackerModel.getCarbonTrackerModel(this);
        utility = model.getCurrentUtility();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView daily = (TextView) findViewById(R.id.display_daily);
        TextView dateText = (TextView) findViewById(R.id.date);
        dateText.setText(getString(R.string.from) + utility.getDateInfo2() + getString(R.string.tooooo) + utility.getEndDateInfo());

        ImageView utilityImage = (ImageView) findViewById(R.id.imageView);
        utilityImage.setImageDrawable(getDrawable(utility.getuImage()));

        TextView bill = (TextView) findViewById(R.id.display_BillType);
        if (utility.isElectricity()) {
            bill.setText(R.string.electro);
        } else {
            bill.setText(R.string.gasssss);
        }
        if (model.isTree()) {
            setupTextView(R.id.display_CO2, String.format("%.2f", CarbonTrackerModel.convertCO2_toTrees(utility.getTotalCo2())));
            setupTextView(R.id.display_CO2Units, " Tree-Years");
            String dailyString = String.format("%.2f", CarbonTrackerModel.convertCO2_toTrees(utility.getDailyCo2()));
            daily.setText("" + dailyString + " per Day.");
        } else {
            String dailyString = String.format("%.2f",utility.getDailyCo2());
            daily.setText("" + dailyString + " per Day.");
            setupTextView(R.id.display_CO2, "" + utility.getTotalCo2());
            setupTextView(R.id.display_CO2Units, " kg of CO₂");
        }
        setupTextView(R.id.display_TotalDays, "For " + utility.getTotalDays() + " Total Days");
    }

    private void setupTextView(int id, String displayString)
    {
        TextView textView = (TextView) findViewById(id);
        textView.setText(displayString);
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
        }
    }

    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, UtilityDetailsActivity.class);
        return intent;
    }
}
