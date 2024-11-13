package models;

import java.util.ArrayList;

public class Pond {
    public float pollution;
    public ArrayList<Fish> fishes;
    
    public Pond(ArrayList<Fish> fishes){
        this.pollution = 0;
        this.fishes = fishes;
    }

    public float getCurrHunger() {
        float hunger = 0;
        for(Fish fish : fishes) {
            hunger += fish.currHunger;
        }
        return hunger;
    }

    public int getAdult() {
        int adult = 0;
        for(Fish fish : fishes) {
            if(fish.adult) {
                adult++;
            }
        }
        return adult;
    }

    public int getYoung() {
        int young = 0;
        for(Fish fish : fishes) {
            if(! fish.adult) {
                young++;
            }
        }
        return young;
    }
}
