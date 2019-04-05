package application.TrainController;

import java.net.URL;
import java.util.ResourceBundle;

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
	private Train train;

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
	private Label actualSpeed, actualPower, currentTemp, driveStatus;
	@FXML
	private Button confirmSpeed, confirmPower, confirmKi, confirmKp, confirmTemp;
	@FXML
	private ToggleButton emergencyBrake, serviceBrake, lights, rightDoor, leftDoor;
	@FXML
	private CheckBox manual = new CheckBox(), automatic;
	@FXML
	private Circle engineStatus, brakeStatus, signalStatus;
	String inputSpeed, inputPower, inputKi, inputKp, inputTemp, setDriveStatus;
	double numSpeed, temperature;
	int numPower, trainStatus;
	boolean srvBrake, emgrBrake, driveManual, driveAutomatic, lightStatus, driveMode;
	// NOTE: This is where you build UI functionality
	// functions can be linked through FX Builder or manually
	// Control Functions

	@FXML
	public void Speed() {
		inputSpeed = speed.getText();
		numSpeed = Double.parseDouble(inputSpeed);
		mySin.setSpeed(numSpeed);
		actualSpeed.setText(inputSpeed + "mph");
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
		if(serviceBrake.isSelected()) {
			mySin.setServiceBrake(true);
			serviceBrake.setText("Slowing down Train.");
			for(double i = numSpeed; i >= 0; i--) {
				actualSpeed.setText(i + "mph");
			}
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
			actualSpeed.setText("0mph");
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
	
	@FXML
	public void Lights() {
		if(lights.isSelected()) {
			lights.setText("Off");
		}else
			lights.setText("On");
	}
	
	@FXML
	public void rightDoor() {
		if(rightDoor.isSelected()) {
			mySin.setRightDoor(true);
			rightDoor.setText("Open");
		}else
			mySin.setRightDoor(false);
			rightDoor.setText("Closed");
	}
	
	@FXML
	public void leftDoor() {
		if(leftDoor.isSelected()) {
			mySin.setLeftDoor(true);
			leftDoor.setText("Open");
		}else
			mySin.setLeftDoor(false);
			leftDoor.setText("Closed");
	}
	
	@FXML
	public void Temperature() {
		inputTemp = temp.getText();
		temperature = Double.parseDouble(inputTemp);
		mySin.setTemperature(temperature);
		currentTemp.setText(inputTemp);
		
	}
	
	public void trainStatus() {
		trainStatus = mySin.getTrainStatus();
		if(trainStatus == 1) {
			engineStatus.setFill(javafx.scene.paint.Color.RED);
			mySin.setTrainStatus(1);
		}else if(trainStatus == 2) {
			brakeStatus.setFill(javafx.scene.paint.Color.RED);
			mySin.setTrainStatus(2);
		}else if(trainStatus == 3) {
			signalStatus.setFill(javafx.scene.paint.Color.RED);
			mySin.setTrainStatus(3);
		}
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
		trainStatus();
		Speed();
		//actualSpeed.setText(mySin.getSpeed());
		//driveStatus.setOnAction(e -> driveStatus());
		
//		manual.setOnAction(e -> ManualMode());
//		automatic.setOnAction(e -> AutomaticMode());
		
		confirmKi.setOnAction(e -> Ki());
		confirmKp.setOnAction(e -> Kp());
		confirmTemp.setOnAction(e -> Temperature());
		emergencyBrake.setOnAction(e -> emergencyBrake());
		serviceBrake.setOnAction(e -> serviceBrake());
		lights.setOnAction(e -> Lights());
		rightDoor.setOnAction(e -> rightDoor());
		leftDoor.setOnAction(e -> leftDoor());
		
		//counter.setText(Integer.toString(count));
		//counter.setText(Integer.toString(decrease));

	}
}
