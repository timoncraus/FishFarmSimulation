package models;

public class Carp extends Fish{
    public static float[] adultWF = {20f, 3.92f};
    public static float[] youngWF = {6.5f, 35f};

    public Carp(Boolean adult) {
        super(adult ? adultWF : youngWF, adult);
    }

    public String getType() {
        return "Карп";
    }
}
