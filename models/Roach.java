package models;

public class Roach extends Fish{
    public Roach(int adult, int young) {
        super(adult, young);
    }

    public String getType() {
        return "Плотва";
    }
}
