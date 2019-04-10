package application.TrainController;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javax.swing.JComboBox;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
	private Label counter;
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
	private static JComboBox<String> listTrainID = new JComboBox<>();
	@FXML
	private Circle engineStatus, brakeStatus, signalStatus;
	String inputSpeed, inputPower, inputKi, inputKp, inputTemp, setDriveStatus, setMBOSpeed, setMBOAuthority;
	double temperature, modelSpeed = 0, ctrlSpeed = 0, inputMBOSpeed = 0;
	//DecimalFormat mdlSpeed = new DecimalFormat("#0.00");
	int numPower, trainStatus, inputMBOAuthority = 0, trainID = 0;


	int currentTrainID;
	boolean srvBrake, emgrBrake, driveManual, driveAutomatic, lightStatus, driveMode, engineFail, brakeFail, signalFail;
	boolean leftDoor1 = false, rightDoor1 = false;
	// NOTE: This is where you build UI functionality
	// functions can be linked through FX Builder or manually
	// Control Functions

	
	public void trainID(int trainID) {
			//listTrainID.addItem(currentTrainID);
			
	}
	
	@FXML
	public void Speed() { //inputSpeed.isEmpty();
		inputSpeed = speed.getText();
		if(!inputSpeed.isEmpty()) {
			ctrlSpeed = Double.parseDouble(inputSpeed);
			mySin.setSpeed(ctrlSpeed);
		}
	}
	
	public void restartSpeed() {
		mySin.setSpeed(0);
		speed.setText("0");
		ctrlSpeed = 0.0;
		actualSpeed.setText(ctrlSpeed + "mph");
	}
	
	//need to calculate this 
	public void Power() {
		inputPower = power.getText();
		numPower = Integer.parseInt(inputPower);
		//mySin.setnumPower(numPower);
		actualPower.setText(inputPower + "Kwatts");
	}
	
	@FXML
	public void serviceBrake() {
		//breaking distance 
		//calculate a safe braking distance
		if(serviceBrake.isSelected()) {
			mySin.setServiceBrake(true);
			serviceBrake.setText("Slowing down Train.");
			restartSpeed();
		}else
			serviceBrake.setText("Brake is off.");
	}
	
	@FXML
	public void emergencyBrake() {
		if(emergencyBrake.isSelected()) {
			mySin.setemergencyBrake(true);
			mySin.setSpeed(0);
			emergencyBrake.setText("EMERGENCY STOP!!");
			emergencyBrake.setStyle("-fx-background-color:red");
			restartSpeed();
			//emergencyBrake.setStyle("-fx-text-fill: black");

		}/*else
			emergencyBrake.setStyle("-fx-background-color:grey");
			emergencyBrake.setText("EMERGENCY BRAKE");*/
	}
	
	@FXML
	public void ManualMode() {
			driveMode = true;
			manual.setSelected(true);
			automatic.setSelected(false);
			driveStatus.setText("Manual Mode");
			mySin.setDriveMode(true);
	}
	
	@FXML
	public void AutomaticMode() {
			driveMode = false;
			automatic.setSelected(true);
			manual.setSelected(false);
			driveStatus.setText("Automatic Mode");
			mySin.setDriveMode(false);
	}
	
	public void Ki() {
		inputKi = ki.getText();
	}
	
	public void Kp() {
		inputKp = kp.getText();
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
		inputTemp = temp.getText();
		temperature = Double.parseDouble(inputTemp);
		mySin.setTemperature(temperature);
		currentTemp.setText(inputTemp);
		
	}
	
	
	public void engineStatus() {
		mySin.setSpeed(0);
		engineStatus.setFill(javafx.scene.paint.Color.RED);
		restartSpeed();
		
	}
	
	public void brakeStatus() {
		mySin.setSpeed(0);
		brakeStatus.setFill(javafx.scene.paint.Color.RED);
		restartSpeed();
	}
	
	public void signalStatus() {
		mySin.setSpeed(0);
		signalStatus.setFill(javafx.scene.paint.Color.RED);
		restartSpeed();
	}	
	
	/**
	 * this sets the speed/authority from MBO and possibly CTC office
	 * @param inputMBOSpeed
	 * @param inputMBOAuthority
	 */
	public void StationInput(double inputMBOSpeed, int inputMBOAuthority) {
		setMBOSpeed = Double.toString(inputMBOSpeed);
		setMBOAuthority = Double.toString(inputMBOAuthority);
		MBOSpeed.setText(setMBOSpeed);
		MBOAuthority.setText(setMBOAuthority);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		
		// Starts the automatic update (NO TOUCHY!!)
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
		confirmPower.setOnAction(e -> Power());
		
		
		//currentTrainID = mySin.getTrainID();
		
		
		
		modelSpeed = mySin.getSpeed();
		if(modelSpeed == 0) {
			Speed();
			actualSpeed.setText(ctrlSpeed + "mph");
		}else if(modelSpeed > 0) {
			//mySin.setSpeed(modelSpeed);
			ctrlSpeed = modelSpeed;
			actualSpeed.setText(ctrlSpeed + "mph");

		}
		
		inputMBOSpeed = mySin.getMBOSpeed();
		inputMBOAuthority = mySin.getMBOAuthority();
		
		engineFail = mySin.getEngineStatus();
		brakeFail = mySin.getBrakeStatus();
		signalFail = mySin.getSignalStatus();
		if(engineFail == true) {
			engineStatus();
		}
		if(brakeFail == true) {
			brakeStatus();
		}
		if(signalFail == true) {
			signalStatus();
		}
		
		//actualSpeed.setText(mySin.getSpeed());
		//driveStatus.setOnAction(e -> driveStatus());
		
//		manual.setOnAction(e -> ManualMode());
//		automatic.setOnAction(e -> AutomaticMode());
		
		confirmKi.setOnAction(e -> Ki());
		confirmKp.setOnAction(e -> Kp());
		confirmTemp.setOnAction(e -> Temperature());
		emergencyBrake.setOnAction(e -> emergencyBrake());
		//serviceBrake.setOnAction(e -> serviceBrake());
//		lights.setOnAction(e -> Lights());

		
		//counter.setText(Integer.toString(count));
		//counter.setText(Integer.toString(decrease));

	}
}
