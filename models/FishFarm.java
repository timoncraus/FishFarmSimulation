package models;

import java.util.ArrayList;
import java.util.Random;


public class FishFarm {
    public float money;
    public float dryFood;
    public Contract contract;
    public ArrayList<Pond> ponds;
    public float fishDeathFrom;
    public float fishDeathTo;

    public FishFarm(float startMoney, float startDryFood, int numberOfPounds, int numberOfFishes, float fishDeathFrom, float fishDeathTo){
        this.money = startMoney;
        this.dryFood = startDryFood;
        this.contract = null;
        
        this.ponds = new ArrayList<>();
        Fish fish;
        for(int i = 0; i <= numberOfPounds - 1; i++){
            int number = randomInt(0, 2);
            if(number == 0) {
                fish = new Carp((int)(numberOfFishes*0.7), (int)(numberOfFishes*0.3));
            }
            else if(number == 1) {
                fish = new Roach((int)(numberOfFishes*0.7), (int)(numberOfFishes*0.3));
            }
            else {
                fish = new Trout((int)(numberOfFishes*0.7), (int)(numberOfFishes*0.3));
            }
            this.ponds.add(new Pond(fish));
        }
        this.fishDeathFrom = fishDeathFrom;
        this.fishDeathTo = fishDeathTo;
    }

    public static int randomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max + 1 - min) + min;
    }
}
