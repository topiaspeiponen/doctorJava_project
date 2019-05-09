package com.example.doctorjava_project;

/**
 * The coinBuilding class generates only coins
 */
public class CoinBuilding extends Building {

    private double baseProduction = 0.01;

    /**
     * Instantiates a new Coin building.
     *
     * @param bought    the bought
     * @param total     the total
     * @param basePrice the base price
     */
    CoinBuilding(int bought, long total, double basePrice){
        super(null, bought, total, basePrice);

    }

    @Override
    public double produce(){
        return calculateCoinProduction();
    }

    private double calculateCoinProduction(){
        return baseProduction * this.total * getMultiplier(); //dummy version of formula
    }
}
