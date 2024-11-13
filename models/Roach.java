package models;

public class Roach extends Fish{
    public static float[] adultWF = {0.2f, 0.6f};
    public static float[] youngWF = {0.15f, 1.5f};

    public Roach(Boolean adult) {
        super(adult ? adultWF : youngWF, adult);
    }

    public String getType() {
        return "Плотва";
    }
}
