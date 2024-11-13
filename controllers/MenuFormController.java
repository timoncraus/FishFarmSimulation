package controllers;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.FishFarm;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.Parent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuFormController implements Initializable {
    public TextField startMoney;
    public TextField fishDeathFrom;
    public TextField fishDeathTo;
    public ChoiceBox<Integer> numberOfPounds;
    public TextField numberOfFishes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startMoney.setText("560");
        fishDeathFrom.setText("5");
        fishDeathTo.setText("10");
        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 2; i <= 7; i++) {
            list.add(i);
        }
        numberOfPounds.setItems(FXCollections.observableArrayList(list));
        numberOfPounds.getSelectionModel().select(2);
        numberOfFishes.setText("6000");
    }
    public void startSimulationClick(ActionEvent actionEvent) throws IOException {
        try {
            System.out.println(numberOfPounds.getSelectionModel().getSelectedItem());
            FishFarm fishFarm = new FishFarm(
                Float.parseFloat(startMoney.getText()),
                500f,
                numberOfPounds.getSelectionModel().getSelectedItem(),
                Integer.parseInt(numberOfFishes.getText()),
                Float.parseFloat(fishDeathFrom.getText()),
                Float.parseFloat(fishDeathTo.getText())
            );
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../fxml/MainForm.fxml"));
            
            Parent root = loader.load();
            
            MainFormController controllerMain = loader.getController();
            controllerMain.initData(fishFarm);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Симуляция");
            stage.show();

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