package application.TrainControllerHardware;

import com.fazecast.jSerialComm.*;
import application.TrainModel.TrainModelSingleton;
import application.TrainModel.TrainInterface;


public class TrainControllerHWSingleton{

	private static TrainControllerHWSingleton instance = null;
	private static TrainModelSingleton trnModelSin = TrainModelSingleton.getInstance();
	
	/**
	 * Private constructor that makes this a singleton class.
	 */
	private TrainControllerHWSingleton(){}
	
	public static TrainControllerHWSingleton getInstance(){
		if(instance == null) instance = new TrainControllerHWSingleton();
		
		return instance;
	}
	
	// these are all of the values that I actually change
	private int trainId;
	private boolean drivingMode;
	private double speed, power, ki, kp;
	private boolean brake, eBrake;
	private boolean lights, leftDoor, rightDoor;
	private double temp;
	
	// values that I'm just displaying
	private int mboAuthority, trackAuthority;
	private double mboSpeed, trackSpeed, actualSpeed;
	private boolean engineState, signalState, brakeState;
	
	// getters and setters
	public int getTrainId() {
		return trainId;
	}
	
	void setTrainId(int id) {
		trainId = id;
	}
	
	public boolean getDrivingMode() {
		return drivingMode;
	}
	
	void setDrivingMode(boolean mode) {
		drivingMode = mode;
	}
	
	void setKi(double newKi) {
		ki = newKi;
	}
	
	void setKp(double newKp) {
		kp = newKp;
	}
	
	void setSpeed(double newSpeed) {
		speed = newSpeed;
	}
	
	void setTemperature(double newTemp) {
		temp = newTemp;
	}
	
	void toggleServiceBrake() {
		brake = !brake;
	}
	
	void triggerEmergencyBrake() {
		eBrake = true;
	}
	
	void resetEmergencyBrake() {
		eBrake = false;
	}
	
	void toggleLights() {
		lights = !lights;
	}
	
	void toggleLeftDoor() {
		leftDoor = !leftDoor;
	}
	
	void toggleRightDoor() {
		rightDoor = !rightDoor;
	}
	
	/**
	 * Controls Singleton connections, doesn't update UI
	 */
	public void update(){
		TrainInterface train = trnModelSin.getTrain(trainId);
		if(train == null) return;
		
		mboAuthority = train.getMBOAuthority();
		trackAuthority = train.getTrackAuthority();
		mboSpeed = train.getMBOSpeed();
		trackSpeed = train.getTrackSpeed();
		actualSpeed = train.getSpeed();
		engineState = train.engineState();
		signalState = train.railSignalState();
		// brakeState = train.brakeState();
		
		train.setPower(power);
		// train.setTemperature(temp);
		if(train.getServiceBrake() != brake) train.setServiceBrake();
		if(!train.getEmergencyBrake() && eBrake) train.triggerEmergencyBrake();
		if(train.getEmergencyBrake() && !eBrake) train.resetEmergencyBrake();
		if(train.getLightState() != lights) train.toggleLights();
		if(train.getLeftDoorState() != leftDoor) train.toggleLeftDoors();
		if(train.getRightDoorState() != rightDoor) train.toggleRightDoors();
	}
}
