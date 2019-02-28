package application.TrainModel;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TrainModelCtrl implements Initializable {

	// Links to your Singleton (NO TOUCHY!!)
	private TrainModelSingleton mySin = TrainModelSingleton.getInstance();

    TrainModel trainModel;
    private AnimationTimer updateAnimation;

    @FXML
    TableView train_info;
    
    @FXML
    TableColumn information_item;
    
    @FXML
    TableColumn information_value;

    public void setTrain(TrainModel trainModel) {
        this.trainModel = trainModel;
    }

    // Starts the automatic update (NO TOUCHY!!)
 	@Override
 	public void initialize(URL arg0, ResourceBundle arg1) {

 		updateAnimation = new AnimationTimer() {

 			@Override
 			public void handle(long now) {
 				update();
 			}
 		};
 		updateAnimation.start();

 	}
    
    // NOTE: This is where you get new information from your singleton
 	// You can read/change fx elements linked above
 	// WARNING: This assumes your singleton is updating its information
 	private void update() {
 		//train_info.getItems().add(trainModel.getPower());
 	}
}
