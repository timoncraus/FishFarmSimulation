package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import models.Pond;
import models.FishFarm;

public class MainFormController implements Initializable {
    public HBox pondsHBox;
    public HBox contractHBox;
    public Button signContractButton;
    FishFarm fishFarm;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void initData(FishFarm fishFarm) throws IOException {
        contractHBox.setVisible(false);

        this.fishFarm = fishFarm;

        int num = 1;
        for (Pond pond : this.fishFarm.ponds) {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../fxml/PondForm.fxml"));
            
            Parent root = loader.load();
            
            
            pondsHBox.getChildren().addAll(root);

            PondFormController controller = loader.getController();
            controller.initData(num, pond.fish.getType(), pond.fish.adult, pond.fish.young, pond.fish.currHunger, pond.pollution);

            num++;
        }

    }

    public void updateContractBox() {

    }
}
