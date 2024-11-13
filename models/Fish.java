package models;

public class Fish {
    public float weight;
    public float needForFood;
    public float currHunger;
    public Boolean adult;
    
    public Fish(float[] listWF, Boolean adult){
        this.weight = listWF[0];
        this.needForFood = listWF[1];
        this.currHunger = listWF[1];
        this.adult = adult;
    }
    public String getType() {
        return "Рыба";
    }
}