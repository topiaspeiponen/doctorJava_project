package com.example.doctorjava_project;

public class Building {


    //These variables need to be saved to persistent storage for each created instance of Building
    protected int bought;
    protected long total;
    protected double basePrice;
    //End saved

    private double priceMultiplier = 1.3;

    protected Building produces;

    public Building(Building produces, int bought, long total, double basePrice){
        this.produces = produces;
        this.bought = bought;
        this.total = total;
        this.basePrice = basePrice;
    }

    public int getBought(){
        return this.bought;
    }

    public long getTotal(){
        return this.total;
    }

    public double getPrice(){
        return this.basePrice * (Math.pow(priceMultiplier, this.bought));
    }

    public void addTotal(long num){
        this.total += num;
    }

    public double getBasePrice(){
        return this.basePrice;
    }

    public double buy(double currentCoins){
        if (currentCoins >= getPrice()){
            double priceTemp = getPrice();
            this.bought++;
            this.total++;
            return priceTemp;

        }
        return -1.0;

    }

    public int getMultiplier(){
        return (int) Math.pow(2, Math.floor(this.bought/25)) ;  //Multiplier starts at 1 and gets +1 for every 25 bought. Multiplies production
    }

    public double produce(){
        this.produces.addTotal(this.total * getMultiplier());
        return -1.0;
    }



}
