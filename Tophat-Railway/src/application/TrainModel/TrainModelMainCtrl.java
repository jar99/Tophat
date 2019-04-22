package application.TrainModel;
/**
 * This is the TrainModelMainWindowCtrl controller runs all the code to update
 *  to allow the person to open and close multiple train windows.
 * 
 * @author jar254
 * @version 1.0
 *
 */

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class TrainModelMainCtrl implements Initializable {

	@FXML
	ChoiceBox<TrainModel> trainSelector;

	@FXML
	Button trainButton;

	@FXML
	private void trainSelectWindow(ActionEvent e) {
		TrainModel train = trainSelector.getSelectionModel().getSelectedItem();
		TrainWindowFactory.createTrainWindow(train);
	}

	@Override
	// Starts the automatic update (NO TOUCHY!!)
	public void initialize(URL url, ResourceBundle resourceBundle) {
		if (trainModCtrl == null)
			trainModCtrl = this; // This is a bad hack
	}

	static TrainModelMainCtrl trainModCtrl;

	protected static void addTrainS(int trainID, TrainModel train) {
		trainModCtrl.addTrain(trainID, train);
	}

	protected static void removeTrainS(int trainID, TrainModel train) {
		trainModCtrl.removeTrain(trainID, train);
	}

	void addTrain(int trainID, TrainModel train) {
		ObservableList<TrainModel> list = trainSelector.getItems();
		if (!list.contains(train))
			list.add(train);

	}

	void removeTrain(int trainID, TrainModel train) {
		ObservableList<TrainModel> list = trainSelector.getItems();
		if (list.contains(train))
			list.remove(train);
	}
}
