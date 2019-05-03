package com.example.doctorjava_project;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author Created by Topias on 25/04/2019.
 * @version 1.0
 * @since 1.0
 */

/**
 * Inflates the layout (renders it in the memory), which makes it appear
 * in the screen view.
 */
public class MainFragment extends Fragment implements SensorEventListener {

    //permanently stored variables, tentative list (could change)
    private double coins = 0.0;
    private int steps = 0;
    private int unprocessedSteps;
    //end stored
    //each building also has variables to store

    private Context context;
    private SensorManager sensorManager;
    private TextView count;
    boolean activityRunning = true;
    private Button stepPlusButton;

    private int deltaStepTracker = -1;

    Timer timer = new Timer();
    Timer updateTimer;

    public double getCoins() {
        return this.coins;
    }

    public void setCoins(double amount) {
        this.coins = amount;
    }

    public void addCoins(double amount) {
        this.coins += amount;
    }

    public void removeCoins(double amount) {
        this.coins -= amount;
    }

    //creating Buildings
    private Building coinBuilding = new CoinBuilding(1, 1, 1);
    private Building building1 = new Building(coinBuilding, 0, 0, 1);
    private Building building2 = new Building(building1, 0, 0, 1);
    private Building building3 = new Building(building2, 0, 0, 1);
    private Building building4 = new Building(building3, 0, 0, 1);
    private Building building5 = new Building(building4, 0, 0, 1);
    private ArrayList<Building> buildingList = new ArrayList<Building>(Arrays.asList(coinBuilding, building1, building2, building3, building4, building5));
    //buildingList contains references to all buildings, with index 0 being a CoinBuilding.

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Timer updateTimer = new Timer();
        updateTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateStuff();
            }
        }, 0, 500);

        return inflater.inflate(R.layout.fragment_main, null);

    }

    public void debugAddSteps(View v){
        this.steps += 5;
        this.unprocessedSteps +=5;
        this.updateUI();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.count = getView().findViewById(R.id.stepCounter);
        this.count.setText("0");
        context = getContext();
        this.stepPlusButton = getView().findViewById(R.id.stepAdd);
        stepPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                debugAddSteps(v);
            }});


        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        registerSensorListener();
        Log.d("doctorDebug", "onStart()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("doctorDebug", "onStop()");
        unregisterSensorListener();
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("doctorDebug", "onResume()");
        registerSensorListener();

    }


    private void unregisterSensorListener() {
        sensorManager.unregisterListener(this);
    }


    private void registerSensorListener() {
        Log.d("doctorDebug", "trying to register listener");
        context = getContext();
        activityRunning = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_FASTEST);
            Log.d("doctorDebug", "sensor registered");
        } else {
            Log.d("doctorDebug", "sensor was null");


        }
    }




   /* private void sensorRegisterTimer(){


        timer.schedule( new TimerTask() {
            public void run() {
                registerSensor();
            }
        }, 0, 2*1000);

    }
    public void registerSensor(){
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

            context = getContext();
            countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            Log.d("doctorDebug", sensorManager.getSensorList(Sensor.TYPE_STEP_DETECTOR).toString());
             //Log.d("doctorDebug", sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).toString());
            Log.d("doctorDebug", "step detector: " + sensorManager.getSensorList(Sensor.TYPE_STEP_DETECTOR).toString());
        Log.d("doctorDebug", "step counter: " + sensorManager.getSensorList(Sensor.TYPE_STEP_COUNTER).toString());

            if (countSensor != null) {
                sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);


            } else {
                Log.d("doctorDebug", "sensor was null");
                Log.d("doctor sensorlist", sensorManager.getDefaultSensor(Sensor.TYPE_ALL).toString());


        }
    }
    */


    @Override
    public void onPause() {
        super.onPause();
        Log.d("doctorDebug", "onPause()");
        activityRunning = false;
        // if you unregister the last listener, the hardware will stop detecting step events
//        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        activityRunning = true;
        if (activityRunning) {

            Log.d("doctorDebug", "steps: " + this.steps);
            Log.d("doctorDebug", "unprocessedSteps: " + this.unprocessedSteps);
            if (this.deltaStepTracker < 0){
                this.deltaStepTracker = 1;

            }
            else {
                this.steps ++;
                this.unprocessedSteps++;
            }


        }

        Log.d("doctorDebug", "" + this.steps);
        updateUI();
    }

    public void updateUI(){
        count.setText(Integer.toString(this.steps));

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }  //mandatory override to implement SensorEventListener, does nothing

    private void checkTick(){  //Check unprocessed step count and either update bar or process a tick if you have 5+ steps

        if (this.unprocessedSteps >= 5){
            this.unprocessedSteps -= 5;
            processTick();
        } else {
            //Change progress bar
        }
    }

    private void processTick(){
        //TODO: game logic
        Log.d("updateTest", "steps count: " + this.steps);
        Log.d("updateTest", "unprocessedSteps count: " + this.unprocessedSteps);
    }

    final Handler myHandler = new Handler(); //For updating GUI on regular intervals. Android dislikes you manually doing that.


    final Runnable myRunnable = new Runnable() {  //Android handler will run this to update GUI
        public void run() {
            Log.d("updateTimer", "updating");
            checkTick();
            updateUI();
        }
    };

    private void updateStuff(){
        myHandler.post(myRunnable);
    }

}
