package models;

public class Trout extends Fish{
    public static float[] adultWF = {3f, 1.6f};
    public static float[] youngWF = {1.4f, 6.5f};

    public Trout(int adult, int young) {
        super(adult, young, adultWF, youngWF);
    }

    public String getType() {
        return "Форель";
    }
}
