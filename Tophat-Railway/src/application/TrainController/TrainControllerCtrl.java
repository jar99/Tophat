package application.TrainController;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javax.swing.JComboBox;

import application.ClockSingleton;
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
	private ClockSingleton clkSin = ClockSingleton.getInstance();


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
	private static JComboBox<String> listTrainID = new JComboBox<>();
	@FXML
	private Circle engineStatus, brakeStatus, signalStatus;
	
	String inputSpeed, inputPower, inputKi, inputKp, inputTemp, setDriveStatus, setMBOSpeed, setMBOAuthority;
	String setCTCSpeed, setCTCAuthority;
	double temperature, modelSpeed = 0, ctrlSpeed = 0, inputMBOSpeed = 0, newError = 0, lastError = 0, actPower = 0;
	double lastPower = 0, numKi = 0, numKp = 0, s = 0, inputCTCSpeed = 0;
	//DecimalFormat mdlSpeed = new DecimalFormat("#0.00");
	int numPower, trainStatus, inputMBOAuthority = 0, inputCTCAuthority = 0, trainID = 0;


	int currentTrainID, deltaT, checkKi = 0, checkKp = 0;
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
			//mySin.setSpeed(ctrlSpeed);
			lastError = newError;
			newError = newError(ctrlSpeed, modelSpeed);	
			actualSpeed.setText(ctrlSpeed + "mph");
		}
	}
	
	private void restartSpeed() {
		mySin.setSpeed(0);
		speed.setText("0");
		ctrlSpeed = 0.0;
		actualSpeed.setText(ctrlSpeed + "mph");
	}
	
	private double newError(double ctrlSpeed, double modSpeed) {
		ctrlSpeed = ctrlSpeed - modSpeed;
		return ctrlSpeed;
	}
	
	/**
	 * 
	 * 
	 * @param deltaT = time difference (time between updates)
	 * @param a = error last
	 * @param an = error new
	 * @param b = power last 
	 * @return new power
	 */
    private double laplace(double deltaT, double a, double an, double b) {
		return b+((deltaT)/2)*(an + a);
	}
	    
	//need to calculate this 
	public void Power() {
		inputPower = power.getText();
		numPower = Integer.parseInt(inputPower);
		deltaT = clkSin.getCurrentTimeSeconds();
		s = laplace(deltaT,newError,actPower,lastError);
		if(checkKi == 1 && checkKp == 1) {
			actPower = numKp + (numKi / s);
		}
		//mySin.setPower(actPower);
		actualPower.setText(actPower + "Kwatts");
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
	
	@FXML
	public void Ki() {
		inputKi = ki.getText();
		numKi = Double.parseDouble(inputKi);
		checkKi = 1;
	}
	
	@FXML
	public void Kp() {
		inputKp = kp.getText();
		numKp = Double.parseDouble(inputKp);
		checkKp = 1;
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
	public void StationInput(double inputMBOSpeed, int inputMBOAuthority, double inputCTCSpeed, int inputCTCAuthority) {
		setMBOSpeed = Double.toString(inputMBOSpeed);
		setMBOAuthority = Integer.toString(inputMBOAuthority);
		setCTCSpeed = Double.toString(inputCTCSpeed);
		setCTCAuthority = Integer.toString(inputMBOAuthority);		
		MBOSpeed.setText(setMBOSpeed);
		MBOAuthority.setText(setMBOAuthority);
		CTCSpeed.setText(setCTCSpeed);
		CTCAuthority.setText(setCTCAuthority);
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
	
		//currentTrainID = mySin.getTrainID();
		
		modelSpeed = mySin.getSpeed();	
		inputMBOSpeed = mySin.getMBOSpeed();
		inputMBOAuthority = mySin.getMBOAuthority();
		inputCTCSpeed = mySin.getCTCSpeed();
		inputCTCAuthority = mySin.getCTCAuthority();
		
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
	}
}
