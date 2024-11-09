package models;

public class Fish {
    int adult;
    int young;
    float currHunger;

    float adultWeight;
    float youngWeight;
    float adultFood;
    float youngFood;
    
    public Fish(int adult, int young){
        this.adult = adult;
        this.young = young;
        this.currHunger = this.adultFood * this.adult + this.youngFood * this.young;
    }
}


