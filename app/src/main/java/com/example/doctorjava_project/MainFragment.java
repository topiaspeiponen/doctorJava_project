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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toolbar;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
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
    private Button resetButton;

    //view variables for layout update
    private ImageButton bcButton;
    private TextView bcMulti;
    private TextView bcBought;
    private TextView bcTotal;
    private TextView bcPrice;

    private ImageButton b1Button;
    private TextView b1Multi;
    private TextView b1Bought;
    private TextView b1Total;
    private TextView b1Price;

    private ImageButton b2Button;
    private TextView b2Multi;
    private TextView b2Bought;
    private TextView b2Total;
    private TextView b2Price;

    private ImageButton b3Button;
    private TextView b3Multi;
    private TextView b3Bought;
    private TextView b3Total;
    private TextView b3Price;

    private ImageButton b4Button;
    private TextView b4Multi;
    private TextView b4Bought;
    private TextView b4Total;
    private TextView b4Price;

    private ImageButton b5Button;
    private TextView b5Multi;
    private TextView b5Bought;
    private TextView b5Total;
    private TextView b5Price;
    //view variables end

    private int deltaStepTracker = -1;

    Timer timer = new Timer();
    Timer updateTimer = new Timer();

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
        assignViewsToVariables();
        stepPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                debugAddSteps(v);
            }});

        //Easy way to create onclick events does not work with fragments. Gotta do it manually
        bcButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v){coinBuildingClick(v);}
        });

        b1Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v){b1Click(v);}
        });

        b2Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v){b2Click(v);}
        });

        b3Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v){b3Click(v);}
        });

        b4Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v){b4Click(v);}
        });

        b5Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v){b5Click(v);}
        });

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        resetButton = getView().findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("doctorDebug", "Building database nuked!");
                //Accesses the database
                final DayStatsDatabase db = Room.databaseBuilder(applicationContext, DayStatsDatabase.class, "building_production")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build();

                //Reset database
                db.dayStatsDao().nukeTable();
                db.close();
            }});
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
        updateTimer.cancel();
        updateTimer = new Timer();
    }


    @Override
    public void onResume() {
        super.onResume();

        updateTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateStuff();
            }
        }, 0, 500);
        Log.d("doctorDebug", "onResume()");
        Log.d("doctorDebug", "Retrieving stepCount, coinCount and unprocessedSteps from SharedPreferences");


        count = getView().findViewById(R.id.stepCount);
        coinCount = getView().findViewById(R.id.coinCount);
        processedStepCount = getView().findViewById(R.id.processedStepCount);

        //Getting the step and coin counts from SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        String savedString = prefs.getString("double", "0.0");
        double lastCoinCountSaved = Double.parseDouble((new BigDecimal(savedString)).toPlainString());
        int lastStepCountSaved = prefs.getInt("StepCount", 0);
        int lastUnprocessedStepCountSaved = prefs.getInt("UnprocStepCount", 0);

        steps = lastStepCountSaved;
        coins = lastStepCountSaved;
        unprocessedSteps = lastUnprocessedStepCountSaved;
        coinCount.setText((String.format("%.2g", lastCoinCountSaved)));
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
        prefEditor.putString("double", (Double.toString(this.coins)));
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
        db.buildingDataDao().insertAll(new BuildingData(2, building1.getBought(), building1.getTotal(), building1.getBasePrice()));
        db.buildingDataDao().insertAll(new BuildingData(3, building2.getBought(), building2.getTotal(), building2.getBasePrice()));
        db.buildingDataDao().insertAll(new BuildingData(4, building3.getBought(), building3.getTotal(), building3.getBasePrice()));
        db.buildingDataDao().insertAll(new BuildingData(5, building4.getBought(), building4.getTotal(), building4.getBasePrice()));
        db.buildingDataDao().insertAll(new BuildingData(6, building5.getBought(), building5.getTotal(), building5.getBasePrice()));
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

    }

    public void buyBuilding(Building building){
        double coinsSpent = building.buy(this.coins);
        if (coinsSpent > 0){
            this.coins -= coinsSpent;
        }
    }

    public void updateUI(){
        //Log.d("UIUpdate", "updateUI()");
        count.setText(Integer.toString(this.steps));
        coinCount.setText((String.format(Locale.US, "%.2g", this.coins)));
        processedStepCount.setText(Integer.toString(this.unprocessedSteps));
        //update TextViews of buildings to show stats
        bcMulti.setText(coinBuilding.getMultiplier()+ "X");
        b1Multi.setText(building1.getMultiplier()+ "X");
        b2Multi.setText(building2.getMultiplier()+ "X");
        b3Multi.setText(building3.getMultiplier()+ "X");
        b4Multi.setText(building4.getMultiplier()+ "X");
        b5Multi.setText(building5.getMultiplier()+ "X");

        bcBought.setText(Integer.toString(coinBuilding.getBought()));
        b1Bought.setText(Integer.toString(building1.getBought()));
        b2Bought.setText(Integer.toString(building2.getBought()));
        b3Bought.setText(Integer.toString(building3.getBought()));
        b4Bought.setText(Integer.toString(building4.getBought()));
        b5Bought.setText(Integer.toString(building5.getBought()));

        bcTotal.setText(Long.toString(coinBuilding.getTotal()));
        b1Total.setText(Long.toString(building1.getTotal()));
        b2Total.setText(Long.toString(building2.getTotal()));
        b3Total.setText(Long.toString(building3.getTotal()));
        b4Total.setText(Long.toString(building4.getTotal()));
        b5Total.setText(Long.toString(building5.getTotal()));

        bcPrice.setText(String.format(Locale.US, "%.2g", coinBuilding.getPrice()));
        b1Price.setText(String.format(Locale.US, "%.2g", building1.getPrice()));
        b2Price.setText(String.format(Locale.US, "%.2g", building2.getPrice()));
        b3Price.setText(String.format(Locale.US, "%.2g", building3.getPrice()));
        b4Price.setText(String.format(Locale.US, "%.2g", building4.getPrice()));
        b5Price.setText(String.format(Locale.US, "%.2g", building5.getPrice()));


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }  //mandatory override to implement SensorEventListener, does nothing

    private void checkTick(){  //Check unprocessed step count and either update bar or process a tick if you have 5+ steps

        if (this.unprocessedSteps >= 5){
            this.unprocessedSteps -= 5;
            processTick();
        } else {

        }
    }

    private void processTick(){
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

    private void assignViewsToVariables(){
        this.stepPlusButton = getView().findViewById(R.id.stepAdd);

        bcButton = getView().findViewById(R.id.CoinBuilding);
        bcMulti  = getView().findViewById(R.id.CBViewMulNum);
        bcBought  = getView().findViewById(R.id.CBViewBouNum);
        bcTotal  = getView().findViewById(R.id.CBViewTotNum);
        bcPrice  = getView().findViewById(R.id.CBViewPriNum);

        b1Button  = getView().findViewById(R.id.UpgradeBuilding1);
        b1Multi  = getView().findViewById(R.id.UB1ViewMulNum);
        b1Bought  = getView().findViewById(R.id.UB1ViewBouNum);
        b1Total  = getView().findViewById(R.id.UB1ViewTotNum);
        b1Price  = getView().findViewById(R.id.UB1ViewPriNum);

        b2Button  = getView().findViewById(R.id.UpgradeBuilding2);
        b2Multi  = getView().findViewById(R.id.UB2ViewMulNum);
        b2Bought  = getView().findViewById(R.id.UB2ViewBouNum);
        b2Total  = getView().findViewById(R.id.UB2ViewTotNum);
        b2Price  = getView().findViewById(R.id.UB2ViewPriNum);

        b3Button  = getView().findViewById(R.id.UpgradeBuilding3);
        b3Multi  = getView().findViewById(R.id.UB3ViewMulNum);
        b3Bought  = getView().findViewById(R.id.UB3ViewBouNum);
        b3Total  = getView().findViewById(R.id.UB3ViewTotNum);
        b3Price  = getView().findViewById(R.id.UB3ViewPriNum);

        b4Button  = getView().findViewById(R.id.UpgradeBuilding4);
        b4Multi  = getView().findViewById(R.id.UB4ViewMulNum);
        b4Bought  = getView().findViewById(R.id.UB4ViewBouNum);
        b4Total  = getView().findViewById(R.id.UB4ViewTotNum);
        b4Price  = getView().findViewById(R.id.UB4ViewPriNum);

        b5Button  = getView().findViewById(R.id.UpgradeBuilding5);
        b5Multi  = getView().findViewById(R.id.UB5ViewMulNum);
        b5Bought  = getView().findViewById(R.id.UB5ViewBouNum);
        b5Total  = getView().findViewById(R.id.UB5ViewTotNum);
        b5Price  = getView().findViewById(R.id.UB5ViewPriNum);

    }

    //Building onclick methods
    public void coinBuildingClick(View v){
        buyBuilding(this.coinBuilding);
    }

    public void b1Click(View v){
        buyBuilding(this.building1);
    }

    public void b2Click(View v){
        buyBuilding(this.building2);
    }

    public void b3Click(View v){
        buyBuilding(this.building3);
    }

    public void b4Click(View v){
        buyBuilding(this.building4);
    }

    public void b5Click(View v){
        buyBuilding(this.building5);
    }


}
