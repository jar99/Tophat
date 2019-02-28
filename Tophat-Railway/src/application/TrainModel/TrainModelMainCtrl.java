package application.TrainModel;

import javafx.animation.AnimationTimer;
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

public class TrainModelMainCtrl implements Initializable {
	
	// Links to your Singleton (NO TOUCHY!!)
	private TrainModelSingleton mySin = TrainModelSingleton.getInstance();
	
    @FXML
    private ChoiceBox<TrainModel> trainSelectorModel;
    private AnimationTimer updateAnimation;


    public void clickSelect(MouseEvent actionEvent) {
        try {
            TrainModel trainModel = trainSelectorModel.getSelectionModel().getSelectedItem();
            if(trainModel == null) return;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TrainModelUI.fxml"));
            Parent root = fxmlLoader.load();
            TrainModelCtrl controller = fxmlLoader.getController();
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
		for(TrainModel trainModel : mySin.getTrains()) {
			if(!trainSelectorModel.getItems().contains(trainModel)){
				trainSelectorModel.getItems().add(trainModel); 
				} 
			}
    }


    @Override
    // Starts the automatic update (NO TOUCHY!!)
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateAnimation = new AnimationTimer() {

 			@Override
 			public void handle(long now) {
 				update();
 			}
 		};
 		updateAnimation.start();
    }
    
    private void update() {
    	loadTrains();
    }
}
