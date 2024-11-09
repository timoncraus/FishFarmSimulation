package models;

public class Trout extends Fish{
    public Trout(int adult, int young) {
        super(adult, young);
    }

    public String getType() {
        return "Форель";
    }
}
