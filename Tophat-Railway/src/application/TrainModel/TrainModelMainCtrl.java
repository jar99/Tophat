package application.TrainModel;
/**
 * This is the TrainModelMainWindowCtrl controller runs all the code to update
 *  to allow the person to open and close multiple train windows.
 * 
 * @author jar254
 * @version 1.0
 *
 */

import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TrainModelMainCtrl implements Initializable {
	
	//TODO display multiple trains at the same time.
	
	// Links to your Singleton (NO TOUCHY!!)
	private TrainModelSingleton mySin = TrainModelSingleton.getInstance();
	
	@FXML
	ChoiceBox<TrainModel> trainSelector;
	
	@FXML
	Button trainButton;
	
	List<TrainModelCtrl> windowCtrls; 
	
    private AnimationTimer updateAnimation;
    
    private void createTrainWindow(TrainModel trainModel) {
    	try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TrainModelUI.fxml"));
            Parent root = fxmlLoader.load();
            TrainModelCtrl controller = fxmlLoader.getController();
            controller.setTrain(trainModel);
            Stage stage = new Stage();
            stage.setOnCloseRequest((e)->closeWindow(e, stage, controller));           
            windowCtrls.add(controller);
            
            stage.setTitle(trainModel.toString());
            stage.setScene(new Scene(root, 500, 450));
            stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    void closeWindow(WindowEvent e, Stage stage, TrainModelCtrl controller) {
    	windowCtrls.remove(controller);
    	controller.shutdown();	
    }
    
    @FXML
    private void trainSelectWindow(ActionEvent e){
    	TrainModel train = trainSelector.getSelectionModel().getSelectedItem();
    	createTrainWindow(train);
    }

    @Override
    // Starts the automatic update (NO TOUCHY!!)
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	windowCtrls = new ArrayList<>();
    	
        updateAnimation = new AnimationTimer() {
        	
 			@Override
 			public void handle(long now) {
 				update();
 			}
 		};
 		updateAnimation.start();
 		trainModCtrl = this; //This is a bad hack
 		
 		mySin.createTrain(-1, 7, 70.0);
    }
    
    private void update() {
    	for(TrainModelCtrl ctrl: windowCtrls) {
    		ctrl.update();
    	}
    }
    
    
	static TrainModelMainCtrl trainModCtrl;
	
	static void addTrainS(int trainID, TrainModel train) {
		trainModCtrl.addTrain(trainID, train);
	}
	
	static void removeTrainS(int trainID, TrainModel train) {
		trainModCtrl.removeTrain(trainID, train);
	}
	
	void addTrain(int trainID, TrainModel train) {
		ObservableList<TrainModel> list = trainSelector.getItems();
		if(!list.contains(train)) list.add(train);
		
	}

	void removeTrain(int trainID, TrainModel train) {
		ObservableList<TrainModel> list = trainSelector.getItems();
		if(list.contains(train)) list.remove(train);
	}
}
