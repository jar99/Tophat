package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.TrainModel.TrainModelSingleton;
import application.TrainModel.TrainModelWindowFactory;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * <h1>User Interface Launcher</h1> Creates a master UI for all of the modules.
 *
 * WARNING: You MUST run this program from TopHatRailwayUIMain.java, otherwise
 * the Singleton's won't talk to each other
 * 
 * @author Jan Reihl
 * @version 1.0
 * @since 2019-04-24
 */
public class TopHatRailwayUI extends Application implements Initializable {

	@FXML
	ChoiceBox<Integer> trainSelector; // This holds the IDs of the existing trains

	@FXML
	Button mbo; // This is the mbo mode selection button

	@FXML
	Button ctc; // This is the ctc mode selection button

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
	void launch_train_controller() {
		makeWindow("Train Controller", "./TrainController/TrainController.fxml", 650, 500);
	}

	@FXML
	void launch_train_controller_hw() {
		makeWindow("Train Controller Hardware", "./TrainControllerHardware/TrainControllerHardware.fxml", 600, 400);
	}

	/**
	 * This method is called on a train being selected. This calls the
	 * TrainModelWindowFactory to create a new TrainModelWindow
	 */
	@FXML
	void launch_train_model() {
		Integer value = trainSelector.getSelectionModel().getSelectedItem();
		if (value != null)
			TrainModelWindowFactory.createTrain(value);
	}

	/**
	 * This is called to set the mode of operations depending on which button is
	 * pressed once it has been selected the buttons are disabled
	 */
	@FXML
	void set_mode(ActionEvent event) {
		TrainModelSingleton.setMode(event.getSource() == ctc);
		ctc.setDisable(true);
		mbo.setDisable(true);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (app == null) // This is the UI connection hack
			app = this;
	}

	static TopHatRailwayUI app; // Reference to the most reasent window.

	/**
	 * Call this to add a train to the ID list
	 * 
	 * @param trainID the ID of the train
	 */
	public static void addTrainS(int trainID) {
		if (app != null)
			Platform.runLater(() -> app.addTrain(trainID));
	}

	/**
	 * Call this to remove a train to the ID list This is called from the
	 * 
	 * @param trainID the ID of the train
	 */
	public static void removeTrainS(int trainID) {
		if (app != null)
			Platform.runLater(() -> app.removeTrain(trainID));
	}

	private void addTrain(int trainID) {
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
	public void start(Stage primaryStage) throws IOException {
		if (app != null)
			throw new IOException("Can not launch the same program twice."); // Prevents launching of multiple UI
																				// instances

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainUI.fxml"));
		Parent root = fxmlLoader.load();

		Scene scene = new Scene(root, 100, 300);

//		Code to close all windows if main window is closed
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

	/**
	 * This creates a new window from the FXML file
	 * 
	 * @param title  of the new window
	 * @param path   to the FXML file
	 * @param width  of window
	 * @param height of window
	 */
	private void makeWindow(String title, String path, int width, int height) {
		try {
			Stage trnModStage = new Stage();
			Parent trnModRoot = FXMLLoader.load(getClass().getResource(path));
			trnModStage.setTitle(title);
			Scene trnModScene = new Scene(trnModRoot, width, height);

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
