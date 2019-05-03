package com.example.doctorjava_project;

public class Building {


    //These variables need to be saved to persistent storage for each created instance of Building
    protected int bought;
    protected long total;
    protected double basePrice;
    //End saved

    protected Building produces;

    public Building(Building produces, int bought, long total, double basePrice){
        this.produces = produces;
        this.bought = bought;
        this.total = total;
        this.basePrice = basePrice;
    }


    public double produce(){
        return -1.0;
    }



}
