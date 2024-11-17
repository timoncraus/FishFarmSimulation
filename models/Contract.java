package models;

public class Contract {
    public float priceKgFoodBuy = 0.000000008f;

    public int totalWeeks;
    public float moneyFoodEveryWeek;
    public float kgFishEveryWeek;
    public float penaltyForEachKg;
    public int numWeeksPrices;
    public int leftNumWeeksPrices;

    public int leftWeeks;
    public float moneyFoodLeft;
    public float kgFishLeft;
    public float penaltyLeft;
    public float priceFood;

    FishFarm fishFarm;

    public Contract(FishFarm fishFarm, int totalWeeks, float moneyFoodEveryWeek, float kgFishEveryWeek, float penaltyForEachKg, int numWeeksPrices) {
        this.fishFarm = fishFarm;
        this.totalWeeks = totalWeeks;
        this.leftWeeks = this.totalWeeks;
        this.moneyFoodEveryWeek = moneyFoodEveryWeek;
        this.kgFishEveryWeek = kgFishEveryWeek;
        this.thingsLeftUpdate();

        this.penaltyForEachKg = penaltyForEachKg;
        this.penaltyLeftUpdate();

        this.numWeeksPrices = numWeeksPrices;
        this.leftNumWeeksPrices = this.numWeeksPrices;
        this.priceFoodUpdate();
    }

    public void penaltyLeftUpdate() {
        this.penaltyLeft = this.kgFishLeft * this.penaltyForEachKg;
    }

    public void thingsLeftUpdate() {
        this.kgFishLeft = this.kgFishEveryWeek;
        this.moneyFoodLeft = this.moneyFoodEveryWeek;
        
    }

    public void priceFoodUpdate() {
        this.priceFood = this.priceKgFoodBuy * (this.fishFarm.totalYoungKgStartWeek / 2 + this.fishFarm.totalAdultKgStartWeek);
    }
    

    public void nextWeek() {
        leftNumWeeksPrices--;
        if(leftNumWeeksPrices < 0) {
            priceFoodUpdate();
            leftNumWeeksPrices = numWeeksPrices;
        }
        thingsLeftUpdate();
        penaltyLeftUpdate();
        this.leftWeeks -= 1;
    }
}
