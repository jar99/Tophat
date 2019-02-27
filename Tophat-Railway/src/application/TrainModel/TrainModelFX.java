package application.TrainModel;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TrainModelFX implements Initializable {

    TrainModel trainModel;

    @FXML
    TableView trainInfo;

    public void updateTrainInfo(){
        trainInfo.getItems().add(trainModel);
    }

    public void setTrain(TrainModel trainModel) {
        this.trainModel = trainModel;
        updateTrainInfo();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn keyColumn = new TableColumn("Key");
        keyColumn.setCellValueFactory(new PropertyValueFactory<>("power"));

        TableColumn valueColumn = new TableColumn("Value");
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("velocity"));

        trainInfo.getColumns().addAll(keyColumn, valueColumn);
    }
}
