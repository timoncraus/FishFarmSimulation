package controllers;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import models.Contract;
import models.FishFarm;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ContractFormController implements Initializable {
    public ChoiceBox<Integer> numberOfWeeks;
    public TextField moneyFoodEveryWeek;
    public TextField kgFishEveryWeek;

    MainFormController controllerMain;
    FishFarm fishFarm;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 6; i <= 24; i++) {
            list.add(i);
        }
        numberOfWeeks.setItems(FXCollections.observableArrayList(list));
        numberOfWeeks.getSelectionModel().select(3);

        moneyFoodEveryWeek.setText("1000");
        kgFishEveryWeek.setText("1000");
    }
    
    public void initData(MainFormController controllerMain, FishFarm fishFarm) {
        this.controllerMain = controllerMain;
        this.fishFarm = fishFarm;
    }

    public void signContractClick(ActionEvent actionEvent) throws IOException {
        try {
            fishFarm.contract = new Contract(
                fishFarm,
                numberOfWeeks.getSelectionModel().getSelectedItem(),
                Float.parseFloat(moneyFoodEveryWeek.getText()),
                Float.parseFloat(kgFishEveryWeek.getText()),
                1f,
                3
            );
            controllerMain.update();
            ((Stage)((Node)actionEvent.getSource()).getScene().getWindow()).close();
        }
        catch(Exception e) {
            Alert a = new Alert(AlertType.ERROR);
            a.setTitle("Ошибка");
            a.setHeaderText("Неправильный ввод значений");
            a.setContentText(e.getMessage());
            a.show();
        }
    }
}