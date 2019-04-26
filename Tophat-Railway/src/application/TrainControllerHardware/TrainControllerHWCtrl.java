package application.TrainControllerHardware;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.fxml.Initializable;

/**
 * Controller for train controller hardware UI. UI for this module simply shows data, all data is
 * modified via the connected Arduino.
 * @author Roman
 * @version 1.0.0
 */
public class TrainControllerHWCtrl implements Initializable{

	// link to singleton
	
	private AnimationTimer updateAnimation;
	
	@FXML
	private Label trainIdValue;
	@FXML
	private Label suggestedSourceValue;
	@FXML
	private Label suggestedSpeedValue;
	@FXML
	private Label suggestedAuthorityValue;
	@FXML
	private Label engineStatusValue;
	@FXML
	private Label brakeStatusValue;
	@FXML
	private Label signalStatusValue;
	@FXML
	private Label speedValue;
	@FXML
	private Label actualSpeedValue;
	@FXML
	private Label powerValue;
	@FXML
	private Label kiValue;
	@FXML
	private Label kpValue;
	@FXML
	private Label serviceBrakeValue;
	@FXML
	private Label emergencyBrakeValue;
	@FXML
	private Label driveModeValue;
	@FXML
	private Label intLightsValue;
	@FXML
	private Label extLightsValue;
	@FXML
	private Label leftDoorValue;
	@FXML
	private Label rightDoorValue;
	@FXML
	private Label temperatureValue;
	
	@Override
	/**
	 * initialize UI
	 */
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		updateAnimation = new AnimationTimer() {
			@Override
			public void handle(long now) {
				update();
			}
		};
		updateAnimation.start();
	}
	
	/**
	 * Function to get information from singleton.
	 */
	private void update() {
		TrainControllerHWSingleton mySin = TrainControllerHWSingleton.getInstance();

		// numeric values
		trainIdValue.setText(mySin.trainId + "");
		suggestedSpeedValue.setText(String.format("%.2f", mySin.trackSpeed) + " km/h");
		suggestedAuthorityValue.setText(mySin.trackAuthority + " blocks");
		if(mySin.mboOrCtc) suggestedSourceValue.setText("CTC");
		else suggestedSourceValue.setText("MBO");
		speedValue.setText(String.format("%.2f", mySin.speed) + " km/h");
		actualSpeedValue.setText(String.format("%.2f", mySin.actualSpeed) + " km/h");
		powerValue.setText(String.format("%.2f", mySin.power) + " kW");
		kiValue.setText(String.format("%.2f", mySin.ki) + "");
		kpValue.setText(String.format("%.2f", mySin.kp) + "");
		temperatureValue.setText(String.format("%.2f", mySin.temp) + " F");
		
		// brakes
		if(mySin.brake) serviceBrakeValue.setText("On");
		else serviceBrakeValue.setText("Off");
		if(mySin.eBrake) emergencyBrakeValue.setText("On");
		else emergencyBrakeValue.setText("Off");
		
		// drive mode
		if(mySin.drivingMode) driveModeValue.setText("Manual");
		else driveModeValue.setText("Automatic");
		
		// faults
		if(mySin.engineState) {
			engineStatusValue.setText("Operational");
			engineStatusValue.setTextFill(Color.web("#13bb40")); // green
		}
		else {
			engineStatusValue.setText("Non-operational");
			engineStatusValue.setTextFill(Color.web("#f5363c")); // red
		}
		if(mySin.brakeState) {
			brakeStatusValue.setText("Operational");
			brakeStatusValue.setTextFill(Color.web("#13bb40"));
		}
		else {
			brakeStatusValue.setText("Non-operational");
			brakeStatusValue.setTextFill(Color.web("#f5363c")); // red
		}
		if(mySin.signalState) {
			signalStatusValue.setText("Operational");
			signalStatusValue.setTextFill(Color.web("#13bb40"));
		}
		else {
			signalStatusValue.setText("Non-operational");
			signalStatusValue.setTextFill(Color.web("#f5363c")); // red
		}
		
		// doors and lights
		if(mySin.extLights) extLightsValue.setText("On");
		else extLightsValue.setText("Off");
		if(mySin.intLights) intLightsValue.setText("On");
		else intLightsValue.setText("Off");
		if(mySin.leftDoor) leftDoorValue.setText("Open");
		else leftDoorValue.setText("Closed");
		if(mySin.rightDoor) rightDoorValue.setText("Open");
		else rightDoorValue.setText("Closed");
	}
}
