package com.example.doctorjava_project;

/**
 * The building class is the default building that generates other buildings
 */
public class Building {


    /**
     * The Bought.
     */
//These variables need to be saved to persistent storage for each created instance of Building
    protected int bought;
    /**
     * The Total.
     */
    protected long total;
    /**
     * The Base price.
     */
    protected double basePrice;
    //End saved

    private double priceMultiplier = 1.3;

    /**
     * The Produces.
     */
    protected Building produces;

    /**
     * Instantiates a new Building.
     *
     * @param produces  the produces
     * @param bought    the bought
     * @param total     the total
     * @param basePrice the base price
     */
    public Building(Building produces, int bought, long total, double basePrice){
        this.produces = produces;
        this.bought = bought;
        this.total = total;
        this.basePrice = basePrice;
    }

    /**
     * Get bought int.
     *
     * @return the int
     */
    public int getBought(){
        return this.bought;
    }

    /**
     * Get total long.
     *
     * @return the long
     */
    public long getTotal(){
        return this.total;
    }

    /**
     * Get price double.
     *
     * @return the double
     */
    public double getPrice(){
        return this.basePrice * (Math.pow(priceMultiplier, this.bought));
    }

    /**
     * Add total.
     *
     * @param num the num
     */
    public void addTotal(long num){
        this.total += num;
    }

    /**
     * Get base price double.
     *
     * @return the double
     */
    public double getBasePrice(){
        return this.basePrice;
    }

    /**
     * Buy double.
     *
     * @param currentCoins the current coins
     * @return the double
     */
    public double buy(double currentCoins){
        if (currentCoins >= getPrice()){
            double priceTemp = getPrice();
            this.bought++;
            this.total++;
            return priceTemp;

        }
        return -1.0;

    }

    /**
     * Get multiplier int.
     *
     * @return the int
     */
    public int getMultiplier(){
        return (int) Math.pow(2, Math.floor(this.bought/25)) ;  //Multiplier starts at 1 and gets +1 for every 25 bought. Multiplies production
    }

    /**
     * Produce double.
     *
     * @return the double
     */
    public double produce(){
        this.produces.addTotal(this.total * getMultiplier());
        return -1.0;
    }



}
