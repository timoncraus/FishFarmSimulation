package models;

public class Trout extends Fish{
    public static float[] adultWF = {3f, 1.6f};
    public static float[] youngWF = {1.4f, 6.5f};

    public Trout(Boolean adult) {
        super(adult ? adultWF : youngWF, adult);
    }

    public String getType() {
        return "Форель";
    }
}
