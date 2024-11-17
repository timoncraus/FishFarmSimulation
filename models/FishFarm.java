package models;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;


public class FishFarm {
    public float birthRateYoung = 0.4f;
    public float matureRateYoung = 0.9f;
    public float deathRateYoung = 0.9f;
    public float deathRateAdult = 0.8f;
    public float pondPollutionEveryWeek = 17f;

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
        
        this.totalAdultKgEndWeek = 0;
        this.totalYoungKgEndWeek = 0;
        for(Pond pond : this.ponds) {
            this.totalAdultKgEndWeek += pond.getTotalAdultKg();
            this.totalYoungKgEndWeek += pond.getTotalYoungKg();
        }
        this.totalAdultKgStartWeek = this.totalAdultKgEndWeek;
        this.totalYoungKgStartWeek = this.totalYoungKgEndWeek;
    }

    public void nextWeek() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        Random random = new Random();
        int count;
        float weight;
        float chosenDeathYoungKg;
        float chosenDeathAdultKg;
        float chosenProcent;
        int chosenRandomAdult;
        int chosenRandomYoung;
        float deathProb;
        int chosenMatFishes;
        int chosenNewFishes;
        Fish chosenFish;

        // Смерть голодающих
        for(Pond pond : ponds) {
            if(pond.fishes == null) continue;
            count = 0;
            for(int i=0; i < pond.fishes.size(); i++) {
                Fish fish = pond.fishes.get(i - count);
                if(fish.currHunger > 0) {
                    deathProb = random.nextFloat();
                    if((fish.currHunger / fish.needForFood) < deathProb) {
                        pond.fishes.remove(fish);
                        count++;
                    }
                }
            }
        }

        // Смерть рыбы от случайных обстоятельств
        for(Pond pond : ponds) {
            if(pond.fishes == null) continue;
            // Вычисление процента для каждого пруда
            chosenProcent = random.nextFloat() * (fishDeathTo/100 - fishDeathFrom/100) + fishDeathFrom/100;
            
            // Смерть взрослой рыбы
            chosenRandomAdult = (int) (chosenProcent * pond.getAdult());
            count = 0;
            for(int i=0; count < chosenRandomAdult; i++) {
                Fish fish = pond.fishes.get(i - count);
                if(fish.adult) {
                    pond.fishes.remove(fish);
                    count++;
                }
            }
            // Смерть молодой рыбы
            chosenRandomYoung = (int) (chosenProcent * pond.getYoung());
            count = 0;
            for(int i=0; count < chosenRandomYoung; i++) {
                Fish fish = pond.fishes.get(i - count);
                if(! fish.adult) {
                    pond.fishes.remove(fish);
                    count++;
                }
            }
        }

        // Смерть большинства рыбы в грязных прудах
        for(Pond pond : ponds) {
            if(pond.fishes == null) continue;
            if(pond.pollution >= 65) {
                // Смерть взрослой рыбы
                chosenRandomAdult = (int) (pond.pollution / 100 * pond.getAdult());
                count = 0;
                for(int i=0; count < chosenRandomAdult && i - count < pond.fishes.size(); i++) {
                    Fish fish = pond.fishes.get(i - count);
                    if(fish.adult) {
                        pond.fishes.remove(fish);
                        count++;
                    }
                }
                // Смерть молодой рыбы
                chosenRandomYoung = (int) (pond.pollution / 100 * pond.getYoung());
                count = 0;
                for(int i=0; count < chosenRandomYoung && i - count < pond.fishes.size(); i++) {
                    Fish fish = pond.fishes.get(i - count);
                    if(! fish.adult) {
                        pond.fishes.remove(fish);
                        count++;
                    }
                }
            }
        }

        // Подсчет взрослеющей молодой рыбы
        ArrayList<Integer> maturingYoungFish = new ArrayList<>();
        for(Pond pond : ponds) {
            if(pond.fishes == null) {
                maturingYoungFish.add(0);
                continue;
            } 
            maturingYoungFish.add((int)(matureRateYoung * pond.getYoung()));
        }

        // Подсчет рождающейся новой рыбы
        ArrayList<Integer> beingBornNewFish = new ArrayList<>();
        for(Pond pond : ponds) {
            if(pond.fishes == null) {
                beingBornNewFish.add(0);
                continue;
            } 
            beingBornNewFish.add((int)(birthRateYoung * pond.getAdult()));
        }

        // Смерть лишней молодой рыбы
        for(Pond pond : ponds) {
            if(pond.fishes == null) continue;
            chosenDeathYoungKg = deathRateYoung * pond.getTotalYoungKg();
            weight = 0;
            count = 0;
            for(int i=0; weight < chosenDeathYoungKg; i++) {
                Fish fish = pond.fishes.get(i - count);
                if(! fish.adult) {
                    weight += fish.weight;
                    pond.fishes.remove(fish);
                    count++;
                }
            }
        }
        
        // Смерть лишней взрослой рыбы
        for(Pond pond : ponds) {
            if(pond.fishes == null) continue;
            chosenDeathAdultKg = deathRateAdult * pond.getTotalAdultKg();
            weight = 0;
            count = 0;
            for(int i=0; weight < chosenDeathAdultKg; i++) {
                Fish fish = pond.fishes.get(i - count);
                if(fish.adult) {
                    weight += fish.weight;
                    pond.fishes.remove(fish);
                    count++;
                }
            }
        }

        // Взросление молодой рыбы
        for(int i=0; i < maturingYoungFish.size(); i++) {
            chosenMatFishes = maturingYoungFish.get(i);
            if(chosenMatFishes == 0) continue;
            count = 0;
            for(int j=0; count < chosenMatFishes && j < ponds.get(i).fishes.size(); j++) {
                chosenFish = ponds.get(i).fishes.get(j);
                if(! chosenFish.adult) {
                    chosenFish.adult = true;
                    Class<? extends Fish> clazz = chosenFish.getClass();
                    Field adultWF = clazz.getField("adultWF");
                    chosenFish.weight = ((float[])(adultWF.get(null)))[0];
                    chosenFish.needForFood = ((float[])(adultWF.get(null)))[1];
                    chosenFish.currHunger = chosenFish.needForFood;
                    count++;
                }
            }
        }

        // Рождение новой молодой рыбы
        for(int i=0; i < beingBornNewFish.size(); i++) {
            chosenNewFishes = beingBornNewFish.get(i);
            if(chosenNewFishes == 0) continue;
            count = 0;
            while(count < chosenNewFishes) {
                Fish fish = ponds.get(i).fishes.get(0).getClass().getConstructor(Boolean.class).newInstance(false);
                ponds.get(i).fishes.add(fish);
                count++;
            }
        }

        // Обновление загрязнения в прудах
        for(Pond pond : ponds) {
            pond.pollution += pondPollutionEveryWeek;
            if(pond.pollution > 100) {
                pond.pollution = 100;
            }
        }

        // Признание прудов без рыб пустыми
        for(Pond pond : ponds) {
            if(pond.fishes != null && pond.fishes.size() == 0) {
                pond.fishes = null;
            }
        }


        // Обновление килограммов рыб для последующего обновления цен у контракта
        totalAdultKgStartWeek = totalAdultKgEndWeek;
        totalYoungKgStartWeek = totalYoungKgEndWeek;
        for(Pond pond : this.ponds) {
            totalAdultKgEndWeek += pond.getTotalAdultKg();
            totalYoungKgEndWeek += pond.getTotalYoungKg();
        }

        // Обновление контракта
        if(contract != null) {
            contract.nextWeek();
        }
    }

    public static int randomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max + 1 - min) + min;
    }
}
