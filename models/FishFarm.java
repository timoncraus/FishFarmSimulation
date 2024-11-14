package models;

import java.util.ArrayList;
import java.util.Random;


public class FishFarm {
    public float priceFishBuy = 3f;
    public float priceKgSold = 2f;

    public float money;
    public float dryFood;
    public Contract contract;
    public ArrayList<Pond> ponds;
    public float fishDeathFrom;
    public float fishDeathTo;

    public float totalAdultKgStartWeek;
    public float totalYoungKgStartWeek;
    public float totalAdultKgEndWeek;
    public float totalYoungKgEndWeek;

    public FishFarm(float startMoney, float startDryFood, int numberOfPounds, int numberOfFishes, float fishDeathFrom, float fishDeathTo){
        this.money = startMoney;
        this.dryFood = startDryFood;
        this.contract = null;
        
        this.ponds = new ArrayList<>();
        for(int i = 0; i <= numberOfPounds - 2; i++){
            ArrayList<Fish> fishes = new ArrayList<>();
            int number = randomInt(0, 2);
            if(number == 0) {
                for(int j=0; j < numberOfFishes*0.7; j++) {
                    fishes.add(new Carp(true));
                }
                for(int j=0; j < numberOfFishes*0.3; j++) {
                    fishes.add(new Carp(false));
                }
            }
            else if(number == 1) {
                for(int j=0; j < numberOfFishes*0.7; j++) {
                    fishes.add(new Roach(true));
                }
                for(int j=0; j < numberOfFishes*0.3; j++) {
                    fishes.add(new Roach(false));
                }
            }
            else {
                for(int j=0; j < numberOfFishes*0.7; j++) {
                    fishes.add(new Trout(true));
                }
                for(int j=0; j < numberOfFishes*0.3; j++) {
                    fishes.add(new Trout(false));
                }
            }
            this.ponds.add(new Pond(fishes));
        }
        this.ponds.add(new Pond(null));
        this.fishDeathFrom = fishDeathFrom;
        this.fishDeathTo = fishDeathTo;
        
        this.totalAdultKgStartWeek = 0;
        this.totalYoungKgStartWeek = 0;
        for(Pond pond : this.ponds) {
            this.totalAdultKgStartWeek += pond.getTotalAdultKg();
            this.totalYoungKgStartWeek += pond.getTotalYoungKg();
        }
        this.totalAdultKgEndWeek = this.totalAdultKgStartWeek;
        this.totalYoungKgEndWeek = this.totalYoungKgStartWeek;
    }

    public static int randomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max + 1 - min) + min;
    }
}
