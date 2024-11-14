package models;

public class Contract {
    public float priceKgFoodBuy = 0.000000008f;

    public int totalWeeks;
    public float moneyFoodEveryWeek;
    public float kgFishEveryWeek;
    public float penaltyForEachKg;
    public int numWeeksPrices;

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
        this.moneyFoodLeft = this.moneyFoodEveryWeek;
        this.kgFishEveryWeek = kgFishEveryWeek;
        this.kgFishLeft = this.kgFishEveryWeek;
        this.penaltyForEachKg = penaltyForEachKg;
        this.penaltyLeftUpdate();
        this.numWeeksPrices = numWeeksPrices;
        this.priceFood = this.priceKgFoodBuy * (this.fishFarm.totalYoungKgStartWeek / 2 + this.fishFarm.totalAdultKgStartWeek);
    }

    public void penaltyLeftUpdate() {
        this.penaltyLeft = this.kgFishLeft * this.penaltyForEachKg;
    }
}
