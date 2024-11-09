package models;

public class Carp extends Fish{
    float adultWeight = 1.8f;
    float youngWeight = 0.8f;
    float adultFood = 1f;
    float youngFood = 0.5f;

    public Carp(int adult, int young) {
        super(adult, young);
    }
    
    public String getType() {
        return "Карп";
    }
}
