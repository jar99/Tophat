package Train_Model;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TrainModelMainFX implements Initializable {

    @FXML
    private ChoiceBox<TrainModel> trainSelectorModel;


    public void clickSelect(MouseEvent actionEvent) {
        try {
            TrainModel trainModel = trainSelectorModel.getSelectionModel().getSelectedItem();
            if(trainModel == null) return;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("train_model_FX.fxml"));
            Parent root = fxmlLoader.load();
            TrainModelFX controller = fxmlLoader.getController();
            controller.setTrain(trainModel);

            Stage stage = new Stage();
            stage.setTitle(trainModel.toString());
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void selectTrain(MouseEvent actionEvent) {
        loadTrains();
    }

    public void addTrainModel(String id){

    }

    private void loadTrains(){
        TrainModelSingleton trainSingleton = TrainModelSingleton.getInstance();
        for (TrainModel trainModel : trainSingleton.getTrains()) {
            if(!trainSelectorModel.getItems().contains(trainModel)){
                trainSelectorModel.getItems().add(trainModel);
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTrains();
    }
}
