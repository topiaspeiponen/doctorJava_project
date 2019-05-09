package com.example.doctorjava_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * The type Settings.
 */
public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    /**
     * Make intent intent.
     *
     * @param context the context
     * @return the intent
     */
    public static Intent makeIntent(Context context){
        return new Intent(context, Settings.class);
    }
}
