package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Pond;
import models.FishFarm;

public class MainFormController implements Initializable {
    public TextField moneyText;
    public TextField dryFoodText;

    public VBox bigContractBox;
    public HBox pondsHBox;
    public HBox contractHBox;
    public Button signContractButton;

    ArrayList<PondFormController> pondControllers = new ArrayList<>();

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
            if(pond.fishes != null) {
                controllerPond.initData(num, 
                pond.fishes.get(0).getType(), 
                pond.getAdult(), 
                pond.getYoung(), 
                pond.getCurrHunger(), 
                pond.pollution);
            }
            else {
                controllerPond.initEmpty(num);
            }
            pondControllers.add(controllerPond);

            num++;
        }

        update();

    }

    public void update() {
        moneyText.setText(this.fishFarm.money + "");
        dryFoodText.setText(this.fishFarm.dryFood + "");

        if(this.fishFarm.contract == null) {
            if(bigContractBox.getChildren().contains(contractHBox)) {
                bigContractBox.getChildren().remove(contractHBox);
            }
            if(! bigContractBox.getChildren().contains(signContractButton)) {
                bigContractBox.getChildren().add(signContractButton);
            }
        }
        else {
            if(! bigContractBox.getChildren().contains(contractHBox)) {
                bigContractBox.getChildren().add(contractHBox);
            }
            if(bigContractBox.getChildren().contains(signContractButton)) {
                bigContractBox.getChildren().remove(signContractButton);
            }
        }

        for (int i=0; i < this.pondControllers.size(); i++) {
            this.pondControllers.get(i).update(fishFarm, this.fishFarm.ponds.get(i));
        }
    }
}
