package application.TrainController;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.shape.Circle;

public class TrainControllerCtrl implements Initializable {

	// Links to your Singleton (NO TOUCHY!!)
	private TrainControllerSingleton mySin = TrainControllerSingleton.getInstance();


	private AnimationTimer updateAnimation;
	

	// NOTE: This is where you link to elements in your FXML file
	// Example:(fx:id="counter")
	// WARNING: Your fx:id and variable name Must Match!
	// Links to FXML elements
	@FXML
	private Label counter, CTCSpeed, CTCAuthority;
	@FXML
	private TextField speed, power, ki, kp, temp;
	@FXML 
	private Label actualSpeed, actualPower, currentTemp, driveStatus, MBOSpeed, MBOAuthority;
	@FXML
	private Button confirmSpeed, confirmPower, confirmKi, confirmKp, confirmTemp;
	@FXML
	private ToggleButton emergencyBrake, serviceBrake, lights, rightDoor, leftDoor;
	@FXML
	private CheckBox manual = new CheckBox(), automatic;
	@FXML
	private ComboBox<Integer> listTrainID;
	@FXML
	private Circle engineStatus, brakeStatus, signalStatus;
	
	
	// NOTE: This is where you build UI functionality
	// functions can be linked through FX Builder or manually
	// Control Functions
	
	@FXML
	public void Speed() { //inputSpeed.isEmpty();
		String inputSpeed = speed.getText();
		if(!inputSpeed.isEmpty()) {
			System.out.println(inputSpeed);
			double ctrlSpeed = Double.parseDouble(inputSpeed);
			mySin.setSpeed(ctrlSpeed);
		}
	}
	
	private void restartSpeed() {
		mySin.setSpeed(0);
	}
	
	//need to calculate this 
	void Power() {
		actualPower.setText(Double.toString(mySin.getPower()) + "Kwatts");
	}
	
	void UpdateSpeed() {
		actualSpeed.setText(Double.toString(mySin.getSpeed()) + "mph");
	}
	
	@FXML
	public void serviceBrake() {
		//breaking distance 
		//calculate a safe braking distance
		if(serviceBrake.isSelected()) {
			serviceBrake.setText("Slowing down Train.");
			restartSpeed();
		}else {
			serviceBrake.setText("Brake is off.");
		}
		mySin.toggleServiceBrake();
	}
	
	@FXML
	public void emergencyBrake() {
		if(emergencyBrake.isSelected()) {
			mySin.setSpeed(0);
			emergencyBrake.setText("EMERGENCY STOP!!");
			emergencyBrake.setStyle("-fx-background-color:red");
			restartSpeed();
			//emergencyBrake.setStyle("-fx-text-fill: black");

		} else {
			emergencyBrake.setStyle("-fx-background-color:grey");
			emergencyBrake.setText("EMERGENCY BRAKE");
		}
		
		mySin.toggleEmergencyBrake();
	}
	
	@FXML
	public void ManualMode() {
			manual.setSelected(true);
			automatic.setSelected(false);
			driveStatus.setText("Manual Mode");
			mySin.setDriveMode(true);
	}
	
	@FXML
	public void AutomaticMode() {
			automatic.setSelected(true);
			manual.setSelected(false);
			driveStatus.setText("Automatic Mode");
			mySin.setDriveMode(false);
	}
	
	@FXML
	public void Ki() {
		String inputKi = ki.getText();
		mySin.setKI(Double.parseDouble(inputKi));
	}
	
	@FXML
	public void Kp() {
		String inputKp = kp.getText();
		mySin.setKP(Double.parseDouble(inputKp));
	}
	
	
	/**
	 *true = On 
	 *false = off 
	 */
	@FXML
	public void Lights() {
		if(lights.isSelected()) {
			mySin.setLights(true);
			lights.setText("On");
		}else {
			mySin.setLights(false);
			lights.setText("Off");
		}
	}
	
	@FXML
	public void rightDoor() {
		if(rightDoor.isSelected()) {
			mySin.setRightDoor(true);
			rightDoor.setText("Open");
		}else {
			mySin.setRightDoor(false);
			rightDoor.setText("Closed");
		}
	}
	
	/**
	 * true = open
	 * false = closed
	 */
	@FXML
	public void leftDoor() {
		if(leftDoor.isSelected()) {
			mySin.setLeftDoor(true);
			leftDoor.setText("Open");
		}else {
			mySin.setLeftDoor(false);
			leftDoor.setText("Closed");
		}
	}
	
	@FXML
	public void Temperature() {
		String inputTemp = temp.getText();
		double temperature = Double.parseDouble(inputTemp);
		mySin.setTemperature(temperature);
		currentTemp.setText(Double.toString(mySin.getTemperature()));
		
	}
	
	
	public void engineStatus() {
		if(!mySin.getEngineStatus()) {
			engineStatus.setFill(javafx.scene.paint.Color.RED);
			restartSpeed();
		}else {
			engineStatus.setFill(javafx.scene.paint.Color.GREEN);
		}
		
	}
	
	public void brakeStatus() {
		if(!mySin.getBrakeStatus()) {
			brakeStatus.setFill(javafx.scene.paint.Color.RED);
			restartSpeed();
		} else {
			brakeStatus.setFill(javafx.scene.paint.Color.GREEN);
		}
	}
	
	public void signalStatus() {
		if(!mySin.getSignalStatus()) {
			signalStatus.setFill(javafx.scene.paint.Color.RED);
			restartSpeed();
		} else {
			signalStatus.setFill(javafx.scene.paint.Color.GREEN);
		}
	}	
	
	/**
	 * this sets the speed/authority from MBO and possibly CTC office
	 * @param inputMBOSpeed
	 * @param inputMBOAuthority
	 */
	public void StationInput(double inputMBOSpeed, int inputMBOAuthority, double inputCTCSpeed, int inputCTCAuthority) {	
		MBOSpeed.setText(Double.toString(inputMBOSpeed));
		MBOAuthority.setText(Integer.toString(inputMBOAuthority));
		CTCSpeed.setText(Double.toString(inputCTCSpeed));
		CTCAuthority.setText(Integer.toString(inputMBOAuthority));
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		listTrainID.valueProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				mySin.setTrain(newValue);
				setKvalues();
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
		
		trainCtrl = this;
	}
	
	// NOTE: This is where you get new information from your singleton
	// You can read/change fx elements linked above
	// WARNING: This assumes your singleton is updating its information
	private void update() {
	
		//currentTrainID = mySin.getTrainID();
		double inputMBOSpeed = mySin.getMBOSpeed();
		
		int inputMBOAuthority = mySin.getMBOAuthority();
		double inputCTCSpeed = mySin.getCTCSpeed();
		int inputCTCAuthority = mySin.getCTCAuthority();
		StationInput(inputMBOSpeed, inputMBOAuthority, inputCTCSpeed, inputCTCAuthority);
		Power();
		UpdateSpeed();
		
		engineStatus();
		brakeStatus();
		signalStatus();
		
	}

	static TrainControllerCtrl trainCtrl;
	
	static void addTrainS(int trainID) {
		trainCtrl.addTrain(trainID);
	}
	
	static void removeTrainS(int trainID) {
		trainCtrl.removeTrain(trainID);
	}
	
	void addTrain(int trainID) {
		ObservableList<Integer> list = listTrainID.getItems();
		if(!list.contains(trainID)) list.add(trainID);
		
	}
	
	private void setKvalues() {
		ki.setText(Double.toString(mySin.getKI()));
		kp.setText(Double.toString(mySin.getKP()));
	}

	void removeTrain(int trainID) {
		ObservableList<Integer> list = listTrainID.getItems();
		if(list.contains(trainID)) list.remove(trainID);
	}
}
