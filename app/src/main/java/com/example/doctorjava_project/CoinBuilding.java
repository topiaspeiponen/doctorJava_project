package com.example.doctorjava_project;

public class CoinBuilding extends Building {

    private double baseProduction = 0.01;

    CoinBuilding(int bought, long total, double basePrice){
        super(null, bought, total, basePrice);

    }

    @Override
    public double produce(){
        return calculateCoinProduction();
    }

    private double calculateCoinProduction(){
        return baseProduction; //dummy version of formula
    }
}
