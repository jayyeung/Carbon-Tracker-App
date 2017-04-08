package com.as3.parmjohal.carbontracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.as3.parmjohal.carbontracker.Model.Car;
import com.as3.parmjohal.carbontracker.Model.CarbonTrackerModel;
import com.google.gson.Gson;

/**
 * Created by ParmJohal on 2017-03-17.
 */

public class SharedPreference {

    public static void saveCurrentModel(Context context)
    {
        SharedPreferences mPrefs = context.getSharedPreferences("saved model",0);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();

        Gson gson = new Gson();
        String json = gson.toJson(CarbonTrackerModel.getCarbonTrackerModel(context));

        prefsEditor.putString("CarbonTrackerModel18", json);
        prefsEditor.commit();
    }

    public static CarbonTrackerModel getCurrentModel(Context context)
    {
        SharedPreferences mPrefs = context.getSharedPreferences("saved model",0);
        Gson gson = new Gson();
        String json = mPrefs.getString("CarbonTrackerModel18", " ");

        CarbonTrackerModel obj = gson.fromJson(json, CarbonTrackerModel.class);

        return obj;
    }
}
