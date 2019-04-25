package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.TrainModel.TrainWindowFactory;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * 
 * @author Jan Reihl
 * @version 1.0
 * @since 2019-04-24
 */
public class TopHatRailwayUI extends Application implements Initializable {
	
	@FXML
	ChoiceBox<Integer> trainSelector;
	
	@FXML
	void launch_ctc() {
		makeWindow("CTC", "./CTC/CTC.fxml", 1200, 900);
	}
	
	@FXML
	void launch_mbo() {
		makeWindow("MBO", "./MBO/MBO.fxml", 1000, 600);
		
	}
	
	@FXML
	void launch_track_controller() {
		makeWindow("Track Controller", "./TrackController/TrackController.fxml", 810, 600);
	}
	
	@FXML
	void launch_track_model() {
		makeWindow("Track Model", "./TrackModel/TrackModel.fxml", 1100, 620);
	}
	
	@FXML
	void launch_train_controller(){
		makeWindow("Train Controller", "./TrainController/TrainController.fxml", 650, 500);
	}
	
	@FXML
	void launch_train_controller_hw(){
		makeWindow("Train Controller Hardware", "./TrainControllerHardware/TrainControllerHardware.fxml", 600, 400);
	}
	
	
	@FXML
	void launch_train_model(){
		Integer value = trainSelector.getSelectionModel().getSelectedItem();
		if(value != null)
		TrainWindowFactory.createTrain(value);
	}
	
	static TopHatRailwayUI app;

	public static void addTrainS(int trainID) {
		if(app != null) Platform.runLater(()->app.addTrain(trainID));
	}

	public static void removeTrainS(int trainID) {
		if(app != null) Platform.runLater(()->app.removeTrain(trainID));
	}

	private void addTrain(int trainID) {
		if(trainSelector == null) System.exit(2);
		ObservableList<Integer> list = trainSelector.getItems();
		if (!list.contains(trainID))
			list.add(trainID);

	}

	private void removeTrain(int trainID) {
		ObservableList<Integer> list = trainSelector.getItems();
		if (list.contains(trainID))
			list.remove(trainID);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(app == null) app = this;
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		if(app != null) throw new IOException("Can not launch the same program twice.");
		
		Parent root = FXMLLoader.load(getClass().getResource("mainUI.fxml"));
		Scene scene = new Scene(root, 100, 300);

//		Code to close all windows if one window closes
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent event) {
		        try {
		        	Platform.exit();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		});
		
		primaryStage.setTitle("Tophat-Railway");
		primaryStage.setScene(scene);
		primaryStage.show();
	}


	private void makeWindow(String title, String path, int width, int height) {
		try {
			Stage trnModStage = new Stage();
			Parent trnModRoot = FXMLLoader.load(getClass().getResource(path));
			trnModStage.setTitle(title);
			Scene trnModScene = new Scene(trnModRoot, width, height); // NOTE: Change last two ints to make window
																		// bigger
			trnModStage.setScene(trnModScene);
			trnModStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
