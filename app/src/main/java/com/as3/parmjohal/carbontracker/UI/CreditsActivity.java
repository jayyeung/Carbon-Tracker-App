package com.as3.parmjohal.carbontracker.UI;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.as3.parmjohal.carbontracker.R;
/**
        * --CreditsActivity--
        * Shows Details about app and links/cites
        * the data/images that were used
        */

public class CreditsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        setTitle("About the app");
    }
}
