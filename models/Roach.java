package models;

public class Roach extends Fish{
    public static float[] adultWF = {0.2f, 0.6f};
    public static float[] youngWF = {0.15f, 1.5f};

    public Roach(int adult, int young) {
        super(adult, young, adultWF, youngWF);
    }

    public String getType() {
        return "Плотва";
    }
}
