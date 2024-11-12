package models;

public class Fish {
    public int adult;
    public int young;
    public float currHunger;
    
    public Fish(int adult, int young, float[] adultWF, float[] youngWF){
        this.adult = adult;
        this.young = young;
        this.currHunger = adultWF[1] * this.adult + youngWF[1] * this.young;
    }
    public String getType() {
        return "Рыба";
    }
}



