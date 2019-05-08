package com.example.doctorjava_project;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

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
    private TextView count, coinCount, processedStepCount;
    boolean activityRunning = true;
    private Button stepPlusButton;
    private static final String STEP_PREF = "StepCountPref";
    private static final String COIN_PREF = "CoinCountPref";
    private static final String PROCESSED_PREF = "ProcessedCountPref";
    Context applicationContext = MainActivity.getContextOfApplication();

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
    private Building coinBuilding = new CoinBuilding(1, 1, 0.05);
    private Building building1 = new Building(coinBuilding, 0, 0, 1);
    private Building building2 = new Building(building1, 0, 0, 100);
    private Building building3 = new Building(building2, 0, 0, 10000);
    private Building building4 = new Building(building3, 0, 0, 1000000);
    private Building building5 = new Building(building4, 0, 0, 100000000);
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
        Log.d("doctorDebug", "Retrieving stepCount, coinCount and unprocessedSteps from SharedPreferences");


        count = getView().findViewById(R.id.stepCount);
        coinCount = getView().findViewById(R.id.coinCount);
        processedStepCount = getView().findViewById(R.id.processedStepCount);

        //Getting the step and coin counts from SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        double lastCoinCountSaved = Double.parseDouble(prefs.getString("double", "0.0"));
        int lastStepCountSaved = prefs.getInt("StepCount", 0);
        int lastUnprocessedStepCountSaved = prefs.getInt("UnprocStepCount", 0);

        steps = lastStepCountSaved;
        coins = lastStepCountSaved;
        unprocessedSteps = lastUnprocessedStepCountSaved;
        coinCount.setText(Double.toString(lastCoinCountSaved));
        count.setText(Integer.toString(lastStepCountSaved));
        processedStepCount.setText(Integer.toString(lastUnprocessedStepCountSaved));

        /**
         * Retrieving all the building data from the database
         */

        //Accesses the database
        final BuildingDataDatabase db = Room.databaseBuilder(applicationContext, BuildingDataDatabase.class, "building_production")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        /**
         * Test if the database list is currently empty
         * If it's NOT empty, get all the building data
         */
        final List<BuildingData> testList = db.buildingDataDao().getAllBuildingData();
        Log.d("doctorDebug", Integer.toString(testList.size()));
        if (testList.isEmpty()) {
            Log.d("doctorDebug", "BuildingData database is empty!");
            db.close();
        } else {
            Log.d("doctorDebug", "Retrieving BuildingData from the BuildingData database!");

            //Assign the variables from the database to these objects
            BuildingData coinBuildingData = db.buildingDataDao().findOneBuilding(1);
            BuildingData building1Data = db.buildingDataDao().findOneBuilding(2);
            BuildingData building2Data = db.buildingDataDao().findOneBuilding(3);
            BuildingData building3Data = db.buildingDataDao().findOneBuilding(4);
            BuildingData building4Data = db.buildingDataDao().findOneBuilding(5);
            BuildingData building5Data = db.buildingDataDao().findOneBuilding(6);

            //Assign the variables to the buildings
            coinBuilding = new CoinBuilding(coinBuildingData.getBought(), coinBuildingData.getTotal(), coinBuildingData.getBasePrice());
            building1 = new Building(coinBuilding, building1Data.getBought(), building1Data.getTotal(), building1Data.getBasePrice());
            building2 = new Building(building1, building2Data.getBought(), building2Data.getTotal(), building2Data.getBasePrice());
            building3 = new Building(building2, building3Data.getBought(), building3Data.getTotal(), building3Data.getBasePrice());
            building4 = new Building(building3, building4Data.getBought(), building4Data.getTotal(), building4Data.getBasePrice());
            building5 = new Building(building4, building5Data.getBought(), building5Data.getTotal(), building5Data.getBasePrice());
            buildingList = new ArrayList<Building>(Arrays.asList(coinBuilding, building1, building2, building3, building4, building5));

            //Clear the table so that the ID finding system works for future saves
            db.buildingDataDao().nukeTable();
            db.close();
        }
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
        Log.d("doctorDebug", "Depositing stepCount, coinCount and unprocessedSteps to SharedPreferences");


        count = getView().findViewById(R.id.stepCount);
        coinCount = getView().findViewById(R.id.coinCount);
        processedStepCount = getView().findViewById(R.id.processedStepCount);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        SharedPreferences.Editor prefEditor = prefs.edit();

        //Saving the current counters into the SharedPreferences
        prefEditor.putInt("StepCount", Integer.parseInt(count.getText().toString()));
        prefEditor.putString("double", ((coinCount.getText().toString())));
        prefEditor.putInt("UnprocStepCount", Integer.parseInt(processedStepCount.getText().toString()));
        prefEditor.commit();

        /**
         * Saving the building data to their respective Room database
         */

        //Accesses the database
        final BuildingDataDatabase db = Room.databaseBuilder(applicationContext, BuildingDataDatabase.class, "building_production")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        //Saving the building data in order (coinBuilding to building5)
        Log.d("doctorDebug", "Depositing building data into the BuildingData database!");
        db.buildingDataDao().insertAll(new BuildingData(1, coinBuilding.getBought(), coinBuilding.getTotal(), coinBuilding.getPrice()));
        db.buildingDataDao().insertAll(new BuildingData(2, building1.getBought(), building1.getTotal(), building1.getPrice()));
        db.buildingDataDao().insertAll(new BuildingData(3, building2.getBought(), building2.getTotal(), building2.getPrice()));
        db.buildingDataDao().insertAll(new BuildingData(4, building3.getBought(), building3.getTotal(), building3.getPrice()));
        db.buildingDataDao().insertAll(new BuildingData(5, building4.getBought(), building4.getTotal(), building4.getPrice()));
        db.buildingDataDao().insertAll(new BuildingData(6, building5.getBought(), building5.getTotal(), building5.getPrice()));
        db.close();

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

    public void buyBuilding(Building building){
        double coinsSpent = building.buy(this.coins);
        if (coinsSpent > 0){
            this.coins -= coinsSpent;
        }
    }

    public void updateUI(){
        count.setText(Integer.toString(this.steps));
        coinCount.setText(Double.toString(this.coins));
        processedStepCount.setText(Integer.toString(this.unprocessedSteps));
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
        for (int i = 0; i < this.buildingList.size(); i++){
            double produced = this.buildingList.get(i).produce();
            if (produced > 0){
                Log.d("updateTest", "Coins produced: " + produced);
                this.coins += produced;
            }
        }
    }

    final Handler myHandler = new Handler(); //For updating GUI on regular intervals. Android dislikes you manually doing that. Need handler + runnable


    final Runnable myRunnable = new Runnable() {  //Android handler will run this to update GUI
        public void run() {
            //Log.d("updateTimer", "updating");
            checkTick();
            updateUI();
        }
    };

    private void updateStuff(){
        myHandler.post(myRunnable);
    }

}
