package com.example.doctorjava_project;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * @author Created by Topias on 25/04/2019.
 * @version 1.1
 * @since 1.0
 */

/**
 * The MainActivity class essentially holds all the functionality in operating
 * the bottom navigation and implementing all the fragments inside the container
 * in activity_main.xml
 * <p>
 * It also holds the alarm system for depositing info gathered during the today
 * into the database
 * <p>
 * The activity_main.xml contains only the bottom navigation bar and the fragment container
 */
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    /**
     * The constant contextOfMain.
     */
    public static Context contextOfMain;
    private SensorManager sensorManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        contextOfMain = getApplicationContext();

        //Loading the default fragment, which is the MainFragment.
        loadFragment(new MainFragment());

        //Getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);



        /**
         * The next 25 lines of codes handle the data sending at daily interval.
         * It checks if there is an alarm created, if not, it creates a new one and sets
         * the time at which the DataAlerm.class is alerted, which will send the data gathered
         * during the day's activities to the database.
         */
        boolean alarmUp = (PendingIntent.getBroadcast(this, 0,
                new Intent(this, DataAlarm.class),
                PendingIntent.FLAG_NO_CREATE) != null);

        if (  !alarmUp) {
            Intent intent = new Intent(this, DataAlarm.class);
            intent.putExtra("activate", true);
            PendingIntent pendingIntent =
                    PendingIntent.getBroadcast(this, 0,
                            intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 1);
            calendar.set(Calendar.SECOND, 0);

            AlarmManager alarmManager =
                    (AlarmManager)
                            this.getSystemService(this.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
                    pendingIntent);

        }
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

    /**
     * Get context of application context.
     *
     * @return the context
     */
    public static Context getContextOfApplication(){
        return contextOfMain;
    }

}
