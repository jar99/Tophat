package application.TrainControllerHardware;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * Controller for train controller hardware UI. UI for this module simply shows data, all data is
 * modified via the connected Arduino.
 * @author Roman
 * @version 1.0.0
 */
public class TrainControllerHWCtrl implements Initializable{

	// link to singleton
	private TrainControllerHardwareSingleton mySin = TrainControllerHardwareSingleton.getInstance();
	
	@Override
	/**
	 * initialize UI
	 */
	public void initialize(URL url, ResourceBundle resourceBundle) {
		
	}
	
	private void update() {
		
	}
}
