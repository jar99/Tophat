package application.TrainModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * This is the TrainWindowFactory this class is responsible in spawning train
 * model windows.
 * 
 * @author jar254
 * @version 1.0
 *
 */

public class TrainWindowFactory {

	private static final List<TrainModelCtrl> windowCtrls = new ArrayList<>();

	public static void createTrain(int trainID) {
		TrainModelSingleton mysin = TrainModelSingleton.getInstance();
		TrainModel train = mysin.getTrainModel(trainID);
		if(train != null) createTrainWindow(train);
	}
	
	static void createTrainWindow(TrainModel trainModel) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(TrainWindowFactory.class.getResource("TrainModelUI.fxml"));
			Parent root = fxmlLoader.load();

			TrainModelCtrl controller = fxmlLoader.getController();
			controller.setTrain(trainModel);
			Stage stage = new Stage();

			stage.setOnCloseRequest((e) -> closeWindow(e, stage, controller));

			windowCtrls.add(controller);
			stage.setTitle(trainModel.toString());
			stage.setScene(new Scene(root, 520, 600));
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static void update() {
		for (TrainModelCtrl ctrl : windowCtrls) {
			ctrl.update();
		}
	}

	private static void closeWindow(WindowEvent e, Stage stage, TrainModelCtrl controller) {
		windowCtrls.remove(controller);
		controller.shutdown();
	}
}
