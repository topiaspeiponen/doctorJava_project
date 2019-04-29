package com.example.doctorjava_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;

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
public class MainFragment extends Fragment {

    //permanently stored variables, tentative list (could change)
    private double coins = 0.0;
    private int steps = 0;
    private int unprocessedSteps;
    //end stored
    //each building also has variables to store

    public double getCoins(){
        return this.coins;
    }

    public void setCoins(double amount){
        this.coins = amount;
    }

    public void addCoins(double amount){
        this.coins += amount;
    }

    public void removeCoins(double amount){
        this.coins -= amount;
    }

    //creating Buildings
    private Building coinBuilding = new CoinBuilding(1, 1, 1);
    private Building building1 = new Building(coinBuilding, 0,0,1);
    private Building building2 = new Building(building1, 0, 0, 1);
    private Building building3 = new Building(building2, 0, 0, 1);
    private Building building4 = new Building(building3, 0, 0, 1);
    private Building building5 = new Building(building4, 0, 0, 1);
    private ArrayList<Building> buildingList = new ArrayList<Building>(Arrays.asList(coinBuilding, building1, building2, building3, building4, building5));
    //buildingList contains references to all buildings, with index 0 being a CoinBuilding.

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, null);
    }
}
