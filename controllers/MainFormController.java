package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Pond;
import models.FishFarm;

public class MainFormController implements Initializable {
    public TextField moneyText;
    public TextField dryFoodText;

    public TextField weeksLeft;
    public TextField buyFoodLeft;
    public TextField sellFishLeft;
    public TextField penaltyLeft;

    public Slider chooseFoodBuyContract;
    public TextField displayChosenFoodContract;
    public TextField displayMoneyContract;

    public VBox bigContractBox;
    public HBox pondsHBox;
    public HBox contractHBox;
    public Button signContractButton;

    public ArrayList<PondFormController> pondControllers = new ArrayList<>();

    FishFarm fishFarm;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void initData(FishFarm fishFarm) throws IOException {
        this.fishFarm = fishFarm;

        int num = 1;
        for (Pond pond : this.fishFarm.ponds) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../fxml/PondForm.fxml"));
            
            Parent root = loader.load();
            pondsHBox.getChildren().add(root);

            PondFormController controllerPond = loader.getController();
            controllerPond.initObjects(num, pond, fishFarm, controllerPond, this);

            pondControllers.add(controllerPond);

            num++;
        }
        update();
    }

    public void update() {
        moneyText.setText(this.fishFarm.money + "");
        dryFoodText.setText(this.fishFarm.dryFood + "");

        if(this.fishFarm.contract == null) {
            bigContractBox.getChildren().remove(contractHBox);
            if(! bigContractBox.getChildren().contains(signContractButton)) {
                bigContractBox.getChildren().add(signContractButton);
            }
        }
        else {
            if(! bigContractBox.getChildren().contains(contractHBox)) {
                bigContractBox.getChildren().add(contractHBox);
            }
            bigContractBox.getChildren().remove(signContractButton);

            this.fishFarm.contract.penaltyLeftUpdate();

            weeksLeft.setText(this.fishFarm.contract.leftWeeks + "");
            buyFoodLeft.setText(this.fishFarm.contract.moneyFoodLeft + "");
            sellFishLeft.setText(this.fishFarm.contract.kgFishLeft + "");
            if(this.fishFarm.contract.kgFishLeft > 0) {
                sellFishLeft.setStyle("-fx-opacity: 1;  -fx-text-inner-color: red;");
            }
            else {
                sellFishLeft.setStyle("-fx-opacity: 1;  -fx-text-inner-color: black;");
            }
            penaltyLeft.setText(this.fishFarm.contract.penaltyLeft + "");
            if(this.fishFarm.contract.penaltyLeft > 0) {
                penaltyLeft.setStyle("-fx-opacity: 1;  -fx-text-inner-color: red;");
            }
            else {
                penaltyLeft.setStyle("-fx-opacity: 1;  -fx-text-inner-color: black;");
            }
            setChooseFoodBuySlider();
        }

        for (int i=0; i < this.pondControllers.size(); i++) {
            this.pondControllers.get(i).update();
        }
    }

    public void setChooseFoodBuySlider() {
        chooseFoodBuyContract.setMin(0);
        chooseFoodBuyContract.setMax(Math.min(fishFarm.money, fishFarm.contract.moneyFoodLeft));
        chooseFoodBuyContract.valueChangingProperty().addListener((obs, oldVal, newVal) -> {
            updateChooseFoodBuySlider();
        });
        updateChooseFoodBuySlider();
    }

    public void updateChooseFoodBuySlider() {
        if(chooseFoodBuyContract.getMax() == 0) {
            displayChosenFoodContract.setText("0.0");
            displayMoneyContract.setText("0.0");
            return;
        }
        if(Double.isNaN(chooseFoodBuyContract.getValue())) {
            chooseFoodBuyContract.setValue(chooseFoodBuyContract.getMax());
        }
        displayChosenFoodContract.setText(format(chooseFoodBuyContract.getValue() / this.fishFarm.contract.priceFood));
        displayMoneyContract.setText(format(chooseFoodBuyContract.getValue()));

    }

    public void buyFoodContract() {
        double chosenMoneyFood = chooseFoodBuyContract.getValue();
        fishFarm.dryFood += chosenMoneyFood / fishFarm.contract.priceFood;
        fishFarm.money -= chosenMoneyFood;
        fishFarm.contract.moneyFoodLeft -= chosenMoneyFood;
        update();
    }

    public void payPenaltyContract() {
        float paidMoney = Math.min(fishFarm.money, fishFarm.contract.penaltyLeft);
        fishFarm.money -= paidMoney;
        fishFarm.contract.penaltyLeft -= paidMoney;
        fishFarm.contract.kgFishLeft -= paidMoney / fishFarm.contract.penaltyForEachKg;
        update();
    }

    public void openContractWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/ContractForm.fxml"));
        
        Parent root = loader.load();
        
        ContractFormController controllerContract = loader.getController();
        controllerContract.initData(this, fishFarm);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Оформление контракта");
        stage.showAndWait();
    }

    public static String format(double num) {
        return String.format("%.1f", num).replace(",", ".");
    }
}
