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
import javafx.scene.layout.VBox;
import models.Carp;
import models.Fish;
import models.FishFarm;
import models.Pond;
import models.Roach;
import models.Trout;

public class PondFormController implements Initializable {
    public VBox poundBox;

    public Label titleNum;
    public TextField type;
    public TextField adult;
    public TextField young;
    public TextField hunger;
    public TextField pollution;

    public Button cleanPondButton;

    public Accordion actionsAccordion;
    public TitledPane moveFishPane;
    public TitledPane feedFishPane;
    public TitledPane populatePondPane;
    public VBox populatePondVBox;
    public TitledPane sellFishPane;

    public ChoiceBox<Integer> choosePond;

    public Slider chooseFood;
    public TextField displayChosenFood;

    public Label labelChooseType;
    public ChoiceBox<String> chooseType;
    public Slider chooseNewFishNum;
    public TextField displayNewFishNum;
    public TextField displayNecesMoney;

    public Slider chooseFishSale;
    public TextField displayChosenAdult;
    public TextField displayChosenKg;
    public TextField displayRecieveMoney;

    Pond pond;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void initData(Pond pond, int num, String type, int adult, int young, float hunger, float pollution) {
        this.pond = pond;
        this.titleNum.setText(num + "");
        this.type.setText(type);
        this.adult.setText(adult + "");
        this.young.setText(young + "");
        this.hunger.setText(hunger + "");
        this.pollution.setText(pollution + "");
        this.populatePondVBox.getChildren().removeAll(labelChooseType, chooseType);
        this.poundBox.getChildren().remove(cleanPondButton);
    }

    public void initEmpty(Pond pond, int num) {
        this.pond = pond;
        this.actionsAccordion.getPanes().removeAll(moveFishPane, feedFishPane, sellFishPane);
        this.titleNum.setText(num + "");
        this.type.setText("Пусто");
    }

    public void update(FishFarm fishFarm) {
        if(pond.fishes != null) {
            ArrayList<Integer> choosePondList = new ArrayList<>();
            for(int i=0; i < fishFarm.ponds.size(); i++) {
                if(fishFarm.ponds.get(i).fishes == null) {
                    choosePondList.add(i+1);
                }
            }
            choosePond.setItems(FXCollections.observableArrayList(choosePondList));
            choosePond.getSelectionModel().select(0);

            chooseFood.setMin(0);
            chooseFood.setMax(Math.min(fishFarm.dryFood, pond.getCurrHunger()));
            updateFeedFishSlider();

            chooseFishSale.setMin(0);
            chooseFishSale.setMax(pond.getAdult());
            updateFishSaleSlider();
        }
        else {
            ArrayList<String> chooseTypeList = new ArrayList<>();
            Fish[] kindsFishes = {new Carp(true), new Roach(true), new Trout(true)};
            for(int i=0; i < kindsFishes.length; i++) {
                chooseTypeList.add(kindsFishes[i].getType());
            }
            chooseType.setItems(FXCollections.observableArrayList(chooseTypeList));
            chooseType.getSelectionModel().select(0);
        }
        chooseNewFishNum.setMin(0);
        chooseNewFishNum.setMax(fishFarm.money / FishFarm.priceFishBuy);
        updatePopulatePondSlider();
        
    }

    public void updateFeedFishSlider() {
        displayChosenFood.setText(format(chooseFood.getValue()));
    }

    public void updatePopulatePondSlider() {
        displayNewFishNum.setText(format(chooseNewFishNum.getValue()));
        displayNecesMoney.setText(format(chooseNewFishNum.getValue() * FishFarm.priceFishBuy));
    }

    public void updateFishSaleSlider() {
        displayChosenAdult.setText(format(chooseFishSale.getValue()));
        float weight = 0;
        int count = 0;
        for(Fish fish : pond.fishes) {
            if(count >= chooseFishSale.getValue()) {
                break;
            }
            if(fish.adult) {
                weight += fish.weight;
                count++;
            }
        }
        displayChosenKg.setText(format(weight));
        displayRecieveMoney.setText(format(weight * FishFarm.priceKgSold));

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

    public static String format(double num) {
        return String.format("%.1f", num);
    }
}
