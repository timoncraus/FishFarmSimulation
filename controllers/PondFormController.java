package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class PondFormController implements Initializable {
    public Label titleNum;
    public TextField type;
    public TextField adult;
    public TextField young;
    public TextField hunger;
    public TextField pollution;

    public ChoiceBox<Integer> choosePond;

    public Slider chooseFood;
    public TextField displayChosenFood;

    public ChoiceBox<String> chooseType;
    public Slider chooseNewFishNum;
    public TextField displayNewFishNum;
    public TextField displayNecesMoney;

    public Slider populatePond;
    public TextField displayChosenAdult;
    public TextField displayChosenKg;
    public TextField displayRecieveMoney;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void initData(int num, String type, int adult, int young, float hunger, float pollution) {
        this.titleNum.setText(num + "");
        this.type.setText(type);
        this.adult.setText(adult + "");
        this.young.setText(young + "");
        this.hunger.setText(hunger + "");
        this.pollution.setText(pollution + "");
    }

    public void cleanPond() {

    }

    public void moveFish() {

    }

    public void feedFish() {

    }

    public void populatePond() {

    }

    public void sellFish() {

    }
}
