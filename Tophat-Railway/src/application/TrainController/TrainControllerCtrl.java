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
	private Label actualSpeed, actualPower, currentTemp, driveStatus;
	@FXML
	private Button confirmSpeed, confirmPower, confirmKi, confirmKp, confirmTemp;
	@FXML
	private ToggleButton emergencyBrake, serviceBrake, lights, rightDoor, leftDoor;
	@FXML
	private CheckBox manual, automatic;
	String inputSpeed, inputPower, inputKi, inputKp, inputTemp, setDriveStatus;
	int numSpeed, numPower;
	boolean srvBrake, emgrBrake, driveManual, driveAutomatic, lightStatus;
	// NOTE: This is where you build UI functionality
	// functions can be linked through FX Builder or manually
	// Control Functions
	
	public void Speed() {
		// System.out.print("Click");
		inputSpeed = speed.getText();
		numSpeed = Integer.parseInt(inputSpeed);
		mySin.setnumSpeed(numSpeed);
		actualSpeed.setText(inputSpeed + "mph");
	}
	
	public void Power() {
		inputPower = power.getText();
		numPower = Integer.parseInt(inputPower);
		mySin.setnumPower(numPower);
		actualPower.setText(inputPower + "Kwatts");
	}
	
	public void serviceBrake() {
		if(serviceBrake.isSelected()) {
			mySin.setServiceBrake(srvBrake = true);
			serviceBrake.setText("Slowing down Train.");
		}else
			serviceBrake.setText("Brake is off.");
	}
	
	public void emergencyBrake() {
		if(emergencyBrake.isSelected()) {
			mySin.setemergencyBrake(emgrBrake = true);
			emergencyBrake.setText("EMERGENCY STOP!!");
			emergencyBrake.setStyle("-fx-background-color:red");
			//emergencyBrake.setStyle("-fx-text-fill: black");

		}/*else
			emergencyBrake.setStyle("-fx-background-color:grey");
			emergencyBrake.setText("EMERGENCY BRAKE");*/
	}
	
	public String driveStatus() {
		if(manual.isSelected()) {
			driveManual = true;
			automatic.setSelected(false);
			return setDriveStatus = "Manual";

		}else if(automatic.isSelected()) {
			driveAutomatic = true;
			manual.setSelected(false);
			return setDriveStatus = "Automatic";
		}else 
			return setDriveStatus = "No Drive Mode Selected";
	}
	
	public void Ki() {
		inputKi = ki.getText();
	}
	
	public void Kp() {
		inputKp = kp.getText();
	}
	
	public void Lights() {
		if(lights.isSelected()) {
			lights.setText("Off");
		}else
			lights.setText("On");
	}
	
	public void rightDoor() {
		if(rightDoor.isSelected()) {
			rightDoor.setText("Open");
		}else
			rightDoor.setText("Closed");
	}
	
	public void leftDoor() {
		if(leftDoor.isSelected()) {
			leftDoor.setText("Open");
		}else
			leftDoor.setText("Closed");
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
		//confirmSpeed.setOnAction(e -> Speed());
		confirmPower.setOnAction(e -> Power());
		
		actualSpeed.setText(mySin.getSpeed());
		driveStatus.setText(driveStatus());
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
