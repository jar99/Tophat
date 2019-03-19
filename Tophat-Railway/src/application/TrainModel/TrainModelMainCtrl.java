package application.TrainModel;
/**
 * This is the TrainModelMainCtrl controller runs all the code to update
 * the planes on the train UI window
 * 
 * @author jar254
 * @version 1.0
 *
 */

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;
import java.util.ResourceBundle;

public class TrainModelMainCtrl implements Initializable {
	
	// Links to your Singleton (NO TOUCHY!!)
	private TrainModelSingleton mySin = TrainModelSingleton.getInstance();
	
	@FXML
	TabPane trainTabs;
	
    private AnimationTimer updateAnimation;
   
    
    private Hashtable<TrainModel, Tab> trainTabTable = new Hashtable<>();
    
    private void createTrainTab(TrainModel trainModel) {
    	try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TrainModelUI.fxml"));
            Parent root = fxmlLoader.load();
            TrainModelCtrl controller = fxmlLoader.getController();
            controller.setTrain(trainModel);
            Tab tab = new Tab("Train " + trainModel.getTrainID(), root);
            trainTabTable.put(trainModel, tab);
			trainTabs.getTabs().add(tab);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void updateTrains(){
    	for(TrainModel train : trainTabTable.keySet()) {
    		// Remove all unused trains
    		if(!mySin.trainExists(train.getTrainID())) {
//    			System.out.println("Removed " + train.getTrainID());
    			trainTabs.getTabs().remove(trainTabTable.get(train));
    			trainTabTable.remove(train);
    		}
    	}
    	
    	// Add new trains
		for(TrainModel trainModel : mySin.getTrains()) {
			if(!trainTabTable.containsKey(trainModel)) {
//				System.out.println("Adding a new train " + trainModel);
				createTrainTab(trainModel);
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
    	updateTrains();
    }
}
