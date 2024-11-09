package models;

public class Fish {
    public int adult;
    public int young;
    public float currHunger;

    public float adultWeight;
    public float youngWeight;
    public float adultFood;
    public float youngFood;
    
    public Fish(int adult, int young){
        this.adult = adult;
        this.young = young;
        this.currHunger = this.adultFood * this.adult + this.youngFood * this.young;
    }
    public String getType() {
        return "Рыба";
    }
}


