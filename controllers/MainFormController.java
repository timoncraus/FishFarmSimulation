package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.FishFarm;

public class MainFormController implements Initializable {
    public HBox pondsHBox;
    FishFarm fishFarm;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void initData(FishFarm fishFarm) throws IOException {
        this.fishFarm = fishFarm;

        
        for(int i = 0; i <= this.fishFarm.ponds.size(); i++) {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../fxml/PondForm.fxml"));
            
            Parent root = loader.load();
            
            
            pondsHBox.getChildren().addAll(root);

            PondFormController controller = loader.getController();
            
        }

    }

    public void updateContractBox() {

    }
}
