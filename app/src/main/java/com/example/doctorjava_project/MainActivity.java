package com.example.doctorjava_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;
import android.widget.TextView;

/**
 * @author Created by Topias on 25/04/2019.
 * @version 1.0
 * @since 1.0
 */

/**
 * The MainActivity class essentially holds all the functionality in operating
 * the bottom navigation and implementing all the fragments inside the container
 * in activity_main.xml
 *
 * The activity_main.xml contains only the bottom navigation bar and the fragment container
 */
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Loading the default fragment, which is the MainFragment.
        loadFragment(new MainFragment());

        //Getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    /**
     * Loads the fragment given as a parameter and replaces
     * the current one in the container.
     *
     * @param fragment
     * @return true/false
     */
    private boolean loadFragment(Fragment fragment) {
        //Switching the fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    /**
     * Determines which navigation button is clicked and creates the respective fragment.
     * Calls the loadFragment method with the chosen fragment which will load in turn
     * load the fragment layout to the fragment container(screen)
     *
     * @param item
     * @return loadFragment(fragment)
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        /**
         * Initializes an empty fragment
         */
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragment = new MainFragment();
                break;

            case R.id.navigation_stats:
                fragment = new StatsFragment();
                break;

            case R.id.navigation_store:
                fragment = new StoreFragment();
                break;
        }

        return loadFragment(fragment);
    }

}
