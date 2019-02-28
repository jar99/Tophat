package application.TrainController;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TrainControllerCtrl implements Initializable {

	// Links to your Singleton (NO TOUCHY!!)
	private TrainControllerSingleton mySin = TrainControllerSingleton.getInstance();

	private AnimationTimer updateAnimation;

	// NOTE: This is where you link to elements in your FXML file
	// Example:(fx:id="counter")
	// WARNING: Your fx:id and variable name Must Match!
	// Links to FXML elements
	@FXML
	private Label counter;
	@FXML
	private TextField speed, power, ki, kp, temp;
	@FXML 
	private Label actualSpeed, actualPower, currentTemp;
	@FXML
	private Button confirmSpeed, confirmPower, confirmKi, confirmKp, confirmTemp;

	String inputSpeed, inputPower, inputKi, inputKp, inputTemp;
	// NOTE: This is where you build UI functionality
	// functions can be linked through FX Builder or manually
	// Control Functions
	
	public void Speed() {
		// System.out.print("Click");
		inputSpeed = speed.getText();
		mySin.setSpeed(inputSpeed);
		actualSpeed.setText(inputSpeed);
	}
	
	public void Power() {
		inputPower = power.getText();
		mySin.setPower(inputPower);
		actualPower.setText(inputPower);
	}
	
	public void Ki() {
		inputKi = ki.getText();
	}
	
	public void Kp() {
		inputKp = kp.getText();
	}
	
	public void Temperature() {
		inputTemp = temp.getText();
		mySin.setTemperature(inputTemp);
		currentTemp.setText(inputTemp);
		
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
	//	int count = mySin.getIncrement();
	//	int decrease = mySin.getDecrease();
		confirmSpeed.setOnAction(e -> Speed());
		confirmPower.setOnAction(e -> Power());
		confirmKi.setOnAction(e -> Ki());
		confirmKp.setOnAction(e -> Kp());
		confirmTemp.setOnAction(e -> Temperature());
		
		//counter.setText(Integer.toString(count));
		//counter.setText(Integer.toString(decrease));

	}
}
