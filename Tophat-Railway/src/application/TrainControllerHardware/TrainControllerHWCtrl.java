package application.TrainControllerHardware;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.fxml.Initializable;

/**
 * Controller for train controller hardware UI. UI for this module simply shows data, all data is
 * modified via the connected Arduino.
 * @author Roman
 * @version 1.0.0
 */
public class TrainControllerHWCtrl implements Initializable{

	// link to singleton
	private TrainControllerHWSingleton mySin = TrainControllerHWSingleton.getInstance();
	
	private AnimationTimer updateAnimation;
	
	private Label trainIdValue;
	private Label ctcSpeedValue, ctcAuthorityValue;
	private Label mboSpeedValue, mboAuthorityValue;
	private Label engineStatusValue, brakeStatusValue, signalStatusValue;
	private Label speedValue, powerValue, kiValue, kpValue;
	private Label serviceBrakeValue, emergencyBrakeValue;
	private Label driveModeValue;
	private Label lightsValue, leftDoorValue, rightDoorValue, temperatureValue;
	
	@Override
	/**
	 * initialize UI
	 */
	public void initialize(URL url, ResourceBundle resourceBundle) {
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
		
	}
}
