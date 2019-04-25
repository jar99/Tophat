
package application.TrainController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.sun.glass.events.WindowEvent;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;



public class TrainControllerCtrl implements Initializable {

	// Links to your Singleton (NO TOUCHY!!)
	private TrainControllerSingleton mySin = TrainControllerSingleton.getInstance();
	private AnimationTimer updateAnimation;

	// NOTE: This is where you link to elements in your FXML file
	// Example:(fx:id="counter")
	// WARNING: Your fx:id and variable name Must Match!
	// Links to FXML elements
	@FXML
	private Label counter, sugSpeed, sugAuthority, valuesConfirm, reachedMax;
	@FXML
	private TextField speed, power, ki, kp, temp, enterPassword;
	@FXML 
	private Label actualSpeed, actualPower, currentTemp, driveStatus, beaconStop;
	@FXML
	private Button confirmSpeed, confirmPower, confirmKi, confirmKp, confirmTemp, confirmPassword, halfSpeed;
	@FXML
	private ToggleButton emergencyBrake, serviceBrake, lights, rightDoor, leftDoor, setCTC, setMBO;
	@FXML
	private CheckBox manual = new CheckBox(), automatic;
	@FXML
	private ComboBox<Integer> listTrainID;
	@FXML
	private Circle engineStatus, brakeStatus, signalStatus;

	Train train;

	// NOTE: This is where you build UI functionality
	// functions can be linked through FX Builder or manually
	// Control Functions
	Stage stage;
	@FXML
	public void setValues(ActionEvent e) {
		try {
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("./SetPower.fxml"));
			fxmlLoader.setController(this);
			Parent root = fxmlLoader.load();
			stage = new Stage();
			
			stage.setTitle("Setting Ki and Kp values.");
			stage.setScene(new Scene(root, 450,350));
			stage.show();
		} catch(IOException a) {
			a.printStackTrace();
		}
	}
	

	@FXML
	public void Temperature() {
		String inputTemp = temp.getText();
		if(!inputTemp.isEmpty()) {	
			double ftemperature = Double.parseDouble(inputTemp);
			if(ftemperature < 0) {
				throw new IllegalArgumentException("Cannot have negative temperature.");
			}
			double ctemp = (ftemperature - 32) * 5/9;
			if(train != null) train.setTemperature(ctemp);
		}
		temp.clear();
	}
	
	void UpdateTemprature() {
		double temp = train.getTemperature();
		double ftemp = temp * 9/5 + 32;
		String temperature = String.format("%.2f",ftemp);
		String degree = "\u00b0";
		currentTemp.setText(temperature + degree + "F");
	}

	@FXML
	public void Speed() {
		String inputSpeed = speed.getText();
		if(!inputSpeed.isEmpty()) {			
			double ctrlSpeed = Double.parseDouble(inputSpeed);
			double maxSpeed = train.getMaxSpeed();
			ctrlSpeed = ctrlSpeed / 0.621371;
			if(ctrlSpeed < maxSpeed && train != null) {
				train.setSpeed(ctrlSpeed);
				reachedMax.setText("");
			}
			if(ctrlSpeed < 0) {
				throw new IllegalArgumentException("Cannot have negative speed.");
			}else if(ctrlSpeed > maxSpeed) {
				reachedMax.setText("Train cannot exceed speeds over 43.50mph.");
			}
			speed.clear();
		}
	}
	
	public void setAutoSpeed() {
		if(ctcMode == true) {
			double autoSpeed = train.getCTCSpeed();
			train.setSpeed(autoSpeed);
		}else if(mboMode == true) {
			double autoSpeed = train.getMBOSpeed();
			train.setSpeed(autoSpeed);
		}
	}
	
	void UpdateSpeed() {
		Double speed = train.getActualSpeed();
		Double speedMPH = speed * 0.621371;
		String ctrlSpeed = String.format("%.2f", speedMPH);
		actualSpeed.setText(ctrlSpeed + "mph");
	}
	
	@FXML
	public void halfSpeed() {
		Double speed = train.getActualSpeed();
		Double halfSpeed = speed / 2;
		if(train != null) train.setSpeed(halfSpeed);
	}

	private void restartSpeed() {
		if(train != null) train.setSpeed(0);
	}

	void Power() {
		Double inputPower = train.getPower();
		if(inputPower >= 120) {
			actualPower.setText("120Kwatts");
		}else if(inputPower < 0){
			actualPower.setText("0.00Kwatts");
		}else{
			String power = String.format("%.2f", inputPower);
			actualPower.setText(power + "Kwatts");
		}
	}
	private void restartPower() {
		if(train != null) train.setPower(0);
	}

	@FXML
	public void serviceBrake() {
		if(serviceBrake.isSelected()) {
			serviceBrake.setText("Slowing down Train.");
			restartSpeed();
			restartPower();
		}else {
			serviceBrake.setText("Off.");
			UpdateSpeed();
		}
		if(train != null) train.toggleServiceBrake();
	}
	
	@FXML
	public void emergencyBrake() {
		if(emergencyBrake.isSelected()) {
			if(train != null) train.setSpeed(0);
			emergencyBrake.setText("EMERGENCY STOP!!");
			emergencyBrake.setStyle("-fx-background-color:grey");
			restartSpeed();
			restartPower();
		} else {
			emergencyBrake.setStyle("-fx-background-color:red");
			emergencyBrake.setText("EMERGENCY BRAKE");
		}	
		if(train != null) train.toggleEmergencyBrake();
	}

	boolean ctcMode, mboMode;
	/*
	 * If .setMode is true = CTC mode
	 * If .setMode is false = MBO mode
	 */
	@FXML 
	public boolean click_CTC() { 
		automatic.setVisible(true); 
		setCTC.setVisible(false);
		setMBO.setVisible(false);
		TrainControllerSingleton.setMode(true);
		return ctcMode = true;
	}
	  
	@FXML 
	public boolean click_MBO() { 
		automatic.setVisible(true);
		setCTC.setVisible(false);
		setMBO.setVisible(false);
		TrainControllerSingleton.setMode(false);
		return mboMode = true;
	}
	
	public void updateCTC() {
		Double speedCTC = train.getCTCSpeed();
		int authorityCTC = train.getCTCAuthority();
		sugSpeed.setText(speedCTC + "mph");
		sugAuthority.setText(authorityCTC + "");
		setCTC.setVisible(false);
		setMBO.setVisible(false);
	}
	
	public void updateMBO() {
		Double speedMBO = train.getMBOSpeed();
		int authorityMBO = train.getMBOAuthority();
		sugSpeed.setText(speedMBO + "mph");
		sugAuthority.setText(authorityMBO + "");
		setMBO.setVisible(false);
		setCTC.setVisible(false);
	}
	
	@FXML
	public void ManualMode() {
			manual.setSelected(true);
			automatic.setSelected(false);
			driveStatus.setText("Manual Mode");
			speed.setVisible(true);
			reachedMax.setText("");
			if(train != null) {
				UpdateSpeed();
				train.setDriveMode(true);
			}
	}

	@FXML
	public void AutomaticMode() {
			automatic.setSelected(true);
			manual.setSelected(false);
			driveStatus.setText("Automatic Mode");
			if(train != null) {
				train.setDriveMode(false);
				setAutoSpeed();
				reachedMax.setText("Speed was automatically set.");
				speed.setVisible(false);
			}
	}
	
	@FXML
	public void confirmPassword(){
		String password = enterPassword.getText();
		valuesConfirm.setText(password);
		if(password.equals("Tophat")) {
			setVisible();
			valuesConfirm.setText("Ki and Kp values can now be updated.");
			stage.close();
			
		}else {
			valuesConfirm.setText("Password incorrect, please try again.");
		}
	}
	
	@FXML
	public void Ki() {
		String inputKi = ki.getText();
		if(train != null) {
			train.setKI(Double.parseDouble(inputKi));
			ki.setVisible(false);
		}
	}

	
	public void setVisible() {
		kp.setVisible(true);
		ki.setVisible(true);
	}
	@FXML
	public void Kp() {
		String inputKp = kp.getText();
		if(train != null) {
			train.setKP(Double.parseDouble(inputKp));
			kp.setVisible(false);
		}
	}

	/**
	 *true = On 
	 *false = off 
	 */
	@FXML
	public void Lights() {
		if(lights.isSelected()) {
			if(train != null) train.setLights(true);
			lights.setText("On");
		}else {
			if(train != null) train.setLights(false);
			lights.setText("Off");
		}
	}
	
	void updateLights() {
		Boolean light = train.getLights();
		if(light == true) {
			lights.setText("On");
		}else if(light == false) {
			lights.setText("Off");
		}
	}

	@FXML
	public void rightDoor() {
		if(rightDoor.isSelected()) {
			if(train != null) train.setRightDoor(true);
			rightDoor.setText("Open");
		}else {
			if(train != null) train.setRightDoor(false);
			rightDoor.setText("Closed");
		}
	}
	
	void updateRDoor() {
		Boolean rDoor = train.rightDoorState();
		if(rDoor == true) {
			rightDoor.setText("Open");
		}else if(rDoor == false) {
			rightDoor.setText("Close");
		}
	}

	/**
	 * true = open
	 * false = closed
	 */
	
	@FXML
	public void leftDoor() {
		if(leftDoor.isSelected()) {
			if(train != null) train.setLeftDoor(true);
			leftDoor.setText("Open");
		}else {
			if(train != null) train.setLeftDoor(false);
			leftDoor.setText("Closed");
		}
	}
	void updateLDoor() {
		Boolean lDoor = train.leftDoorState();
		if(lDoor == true) {
			leftDoor.setText("Open");
		}else if(lDoor == false) {
			leftDoor.setText("Close");
		}
	}
	public void engineStatus() {
		if(!train.getEngineStatus()) {
			engineStatus.setFill(javafx.scene.paint.Color.RED);
			restartSpeed();
		}else {
			engineStatus.setFill(javafx.scene.paint.Color.GREEN);
			UpdateSpeed();
		}
	}

	public void brakeStatus() {
		if(!train.getBrakeStatus()) {
			brakeStatus.setFill(javafx.scene.paint.Color.RED);
			restartSpeed();
		}else {
			brakeStatus.setFill(javafx.scene.paint.Color.GREEN);
			UpdateSpeed();
		}
	}

	public void signalStatus() {
		if(!train.getSignalStatus()) {
			signalStatus.setFill(javafx.scene.paint.Color.RED);
			restartSpeed();
		} else {
			signalStatus.setFill(javafx.scene.paint.Color.GREEN);
			UpdateSpeed();
		}
	}	

	
	public void beaconData(String beacon) {
		if(train != null) {
			String beaconData = train.getBeacon();
			beaconStop.setText(beaconData);
		}
	}
	
	void updateBeacon() {
		String beacon = train.getBeacon();
		beaconStop.setText(beacon);
	}
	
	public void setTrain(int trainID) {
		 Train train = mySin.getTrain(trainID);
		 if(train != null) {
			 this.train = train;
		 }
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listTrainID.valueProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				setTrain(newValue);
				setKvalues();
				//manual.setSelected(true);
				//lights.setSelected(true);
			}
	    });

		// Starts the automatic update (NO TOUCHY!!)
		updateAnimation = new AnimationTimer() {
			
			@Override
			public void handle(long now) {
				update();
			}
		};
		updateAnimation.start();
		if (trainCtrl == null)
		trainCtrl = this;
		updateTrainTable();
	}

	// NOTE: This is where you get new information from your singleton
	// You can read/change fx elements linked above
	// WARNING: This assumes your singleton is updating its information
	private void update() {
		if(train == null) return;
			
		Power();
		UpdateSpeed();
		UpdateTemprature();
		engineStatus();
		brakeStatus();
		signalStatus();
		updateLights();
		updateRDoor();
		updateLDoor();
		if(TrainControllerSingleton.getMode()) {
				updateCTC();
		}else
		updateMBO();
		updateBeacon();
	}

	static TrainControllerCtrl trainCtrl;

	static void addTrainS(int trainID) {
		if (trainCtrl != null) trainCtrl.addTrain(trainID);
	}

	static void removeTrainS(int trainID) {
		if (trainCtrl != null) trainCtrl.removeTrain(trainID);
	}
	
	void addTrain(int trainID) {
		ObservableList<Integer> list = listTrainID.getItems();
		if(!list.contains(trainID)) list.add(trainID);
	}

	private void setKvalues() {
		ki.setText(Double.toString(train.getKI()));
		kp.setText(Double.toString(train.getKP()));
	}

	void removeTrain(int trainID) {
		ObservableList<Integer> list = listTrainID.getItems();
		if(list.contains(trainID)) list.remove(trainID);
	}
	
	private void updateTrainTable() {
        for(Integer trainID: mySin.getAllTrainIDs()) {
            addTrain(trainID);
        }
        
    }
}
