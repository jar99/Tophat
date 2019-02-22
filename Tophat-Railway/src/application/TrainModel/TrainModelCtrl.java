package application.TrainModel;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class TrainModelCtrl implements Initializable {

	// Links to your Singleton (NO TOUCHY!!)
	private TrainModelSingleton mySin = TrainModelSingleton.getInstance();

	private AnimationTimer updateAnimation;

	// NOTE: This is where you link to elements in your FXML file
	// Example:(fx:id="counter")
	// WARNING: Your fx:id and variable name Must Match!
	// Links to FXML elements
	@FXML
	private Label counter;

	// NOTE: This is where you build UI functionality
	// functions can be linked through FX Builder or manually
	// Control Functions
	public void buttonClicked() {
		// System.out.print("Click");
		mySin.increment();
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
		int count = mySin.getCount();
		counter.setText(Integer.toString(count));

	}
}
