package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import models.FishFarm;
import models.Pond;

public class PondFormController implements Initializable {
    public Label titleNum;
    public TextField type;
    public TextField adult;
    public TextField young;
    public TextField hunger;
    public TextField pollution;

    public Accordion actionsAccordion;
    public Button cleanPondButton;
    public TitledPane moveFishPane;
    public TitledPane feedFishPane;
    public TitledPane populatePondPane;
    public TitledPane sellFishPane;

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
        actionsAccordion.getPanes().remove(populatePondPane);
    }

    public void initEmpty(int num) {
        actionsAccordion.getPanes().removeAll(moveFishPane, feedFishPane, sellFishPane);
        this.titleNum.setText(num + "");
        this.type.setText("Пусто");
    }

    public void update(FishFarm fishFarm, Pond pond) {
        if(pond.fishes != null) {
            ArrayList<Integer> list = new ArrayList<>();
            for(int i=0; i < fishFarm.ponds.size(); i++) {
                if(fishFarm.ponds.get(i).fishes == null) {
                    list.add(i+1);
                }
            }
            choosePond.setItems(FXCollections.observableArrayList(list));
            choosePond.getSelectionModel().select(0);

            chooseFood.setMin(0);
            chooseFood.setMax(Math.min(fishFarm.dryFood, pond.getCurrHunger()));
            updateFeedFishSlider();
        }
        else {
            //chooseNewFishNum.setMin(0);
            //chooseNewFishNum.setMax(Math.min(fishFarm.dryFood, fish.currHunger));
            //updateFeedFishSlider();
        }
        
    }

    public void updateFeedFishSlider() {
        displayChosenFood.setText(chooseFood.getValue() + "");
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
