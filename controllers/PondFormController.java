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

    int num;
    Pond pond;
    FishFarm fishFarm;
    PondFormController pondFormController;
    MainFormController controllerMain;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void initObjects(int num, Pond pond, FishFarm fishFarm, PondFormController pondFormController, MainFormController controllerMain) {
        this.num = num;
        this.pond = pond;
        this.fishFarm = fishFarm;
        this.pondFormController = pondFormController;
        this.controllerMain = controllerMain;
        updateTexts();
    }

    public void updateTexts() {
        adult.setText(pond.getAdult() + "");
        young.setText(pond.getYoung() + "");
        hunger.setText(pond.getCurrHunger() + "");
        if(pond.getCurrHunger() > 0) {
            hunger.setStyle("-fx-opacity: 1;  -fx-text-inner-color: red;");
        }
        else {
            hunger.setStyle("-fx-opacity: 1;  -fx-text-inner-color: black;");
        }
        
        pollution.setText(pond.pollution + "");

        if(pond.fishes != null) {
            titleNum.setText(num + "");
            type.setText(pond.fishes.get(0).getType());
            
            populatePondVBox.getChildren().removeAll(labelChooseType, chooseType);
            poundBox.getChildren().remove(cleanPondButton);

            if(! actionsAccordion.getPanes().contains(moveFishPane) &&
                        ! actionsAccordion.getPanes().contains(feedFishPane) &&
                        ! actionsAccordion.getPanes().contains(sellFishPane)) {
                actionsAccordion.getPanes().add(0, moveFishPane);
                actionsAccordion.getPanes().add(1, feedFishPane);
                actionsAccordion.getPanes().add(3, sellFishPane);
            }
            return;
        }
        if(! populatePondVBox.getChildren().contains(labelChooseType) &&
                    ! populatePondVBox.getChildren().contains(chooseType) &&
                    ! poundBox.getChildren().contains(cleanPondButton)) {
            populatePondVBox.getChildren().add(0, labelChooseType);
            populatePondVBox.getChildren().add(1, chooseType);
            poundBox.getChildren().add(2, cleanPondButton);
        }
        actionsAccordion.getPanes().removeAll(moveFishPane, feedFishPane, sellFishPane);
        titleNum.setText(num + "");
        type.setText("Пусто");
    }

    public void update() {
        updateTexts();
        if(pond.fishes != null) {
            setChoosePond();
            setFeedFishSlider();
            setFishSaleSlider();
        }
        else {
            setChooseType();
        }
        setPopulatePondSlider();
    }

    public void setChoosePond() {
        ArrayList<Integer> choosePondList = new ArrayList<>();
        for(int i=0; i < fishFarm.ponds.size(); i++) {
            if(fishFarm.ponds.get(i).fishes == null) {
                choosePondList.add(i+1);
            }
        }
        choosePond.setItems(FXCollections.observableArrayList(choosePondList));
        choosePond.getSelectionModel().select(0);
    }

    public void setFeedFishSlider() {
        chooseFood.setMin(0);
        chooseFood.setMax(Math.min(fishFarm.dryFood, pond.getCurrHunger()));
        chooseFood.valueChangingProperty().addListener((obs, oldVal, newVal) -> {
            updateFeedFishSlider();
        });
        updateFeedFishSlider();
    }

    public void updateFeedFishSlider() {
       
        if(chooseFood.getMax() == 0) {
            displayChosenFood.setText("0.0");
            return;
        }
        if(Double.isNaN(chooseFood.getValue())) {
            chooseFood.setValue(chooseFood.getMax());
        }
        displayChosenFood.setText(format(chooseFood.getValue()));
    }

    public void setPopulatePondSlider() {
        chooseNewFishNum.setMin(0);
        chooseNewFishNum.setMax(fishFarm.money / FishFarm.priceFishBuy);
        updatePopulatePondSlider();
    }

    public void updatePopulatePondSlider() {
        displayNewFishNum.setText(format(chooseNewFishNum.getValue()));
        displayNecesMoney.setText(format(chooseNewFishNum.getValue() * FishFarm.priceFishBuy));
    }

    public void setFishSaleSlider() {
        chooseFishSale.setMin(0);
        chooseFishSale.setMax(pond.getAdult());
        updateFishSaleSlider();
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

    public void setChooseType() {
        ArrayList<String> chooseTypeList = new ArrayList<>();
        Fish[] kindsFishes = {new Carp(true), new Roach(true), new Trout(true)};
        for(int i=0; i < kindsFishes.length; i++) {
            chooseTypeList.add(kindsFishes[i].getType());
        }
        chooseType.setItems(FXCollections.observableArrayList(chooseTypeList));
        chooseType.getSelectionModel().select(0);
    }

    public void cleanPond() {
        if(pond.fishes == null) {
            pond.pollution = 0;
        }
    }

    public void moveFish() {
        int selIndex = choosePond.getSelectionModel().getSelectedItem() - 1;
        Pond selPond = fishFarm.ponds.get(selIndex);
        if(pond.fishes != null && selPond.fishes == null) {
            selPond.fishes = pond.fishes;
            pond.fishes = null;
            controllerMain.pondControllers.get(selIndex).update();
            pondFormController.update();
        }
    }

    public void feedFish() {
        if(chooseFood.getValue() <= 0 || Double.isNaN(chooseFood.getValue())) {
            return;
        }
        fishFarm.dryFood -= chooseFood.getValue();
        double food, foodAdult, foodYoung;
        do {
            food = (double)chooseFood.getValue();
            foodAdult = (pond.getAdultHunger() / pond.getCurrHunger()) * food;
            foodYoung = (pond.getYoungHunger() / pond.getCurrHunger()) * food;

            for(Fish fish : pond.fishes) {
                if(foodAdult + foodYoung < pond.getAdultMaxHunger() && foodAdult + foodYoung >= pond.getYoungMaxHunger()) {
                    foodYoung = foodAdult;
                    foodAdult = 0;
                }
                else if(foodAdult + foodYoung < pond.getYoungMaxHunger() && foodAdult + foodYoung >= pond.getAdultMaxHunger()) {
                    foodAdult = foodYoung;
                    foodYoung = 0;
                }
                if(fish.currHunger == 0) {
                    continue;
                }
                if(fish.adult && foodAdult - fish.currHunger >= 0) {
                    foodAdult -= fish.currHunger;
                    fish.currHunger = 0;
                }
                if(! fish.adult && foodYoung - fish.currHunger >= 0){
                    foodYoung -= fish.currHunger;
                    fish.currHunger = 0;
                }
            }
            
            fishFarm.dryFood += foodAdult + foodYoung;
            controllerMain.update();
            if(pond.getYoungMaxHunger() == 0 && pond.getAdultMaxHunger() == 0) {
                break;
            }
        } while(foodAdult + foodYoung >= pond.getYoungMaxHunger() || foodAdult + foodYoung >= pond.getAdultMaxHunger());
    }

    public void populatePond() {

    }

    public void sellFish() {

    }

    public static String format(double num) {
        return String.format("%.1f", num);
    }
}
