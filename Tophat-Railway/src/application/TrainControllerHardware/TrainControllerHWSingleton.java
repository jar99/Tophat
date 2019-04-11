package application.TrainControllerHardware;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortIOException;
import application.TrainModel.TrainModelSingleton;
import application.TrainModel.TrainInterface;

public class TrainControllerHWSingleton implements TrainControllerHWInterface{

	private static TrainControllerHWSingleton instance = null;
	private static SerialPort primaryPort;
	
	/**
	 * Private constructor that makes this a singleton class.
	 */
	private TrainControllerHWSingleton() throws SerialPortIOException{
		trainId = -1;
		String primary = "COM16";
		primaryPort = SerialPort.getCommPort(primary);
		primaryPort.addDataListener(new TrainControllerDataListener());
		if(!primaryPort.openPort()) throw new SerialPortIOException("Unable to open port");
	}
	
	public static TrainControllerHWSingleton getInstance(){
		try {
			if(instance == null) instance = new TrainControllerHWSingleton();
		}
		catch(SerialPortIOException e) {
			System.err.println("Oops");
		}
		
		return instance;
	}
	
	// these are all of the values that I actually change
	int trainId;
	boolean drivingMode;
	double speed, power, ki, kp;
	boolean brake, eBrake;
	boolean lights, leftDoor, rightDoor;
	double temp;
	
	// values that I'm just displaying
	int mboAuthority, trackAuthority;
	double mboSpeed, trackSpeed, actualSpeed;
	boolean engineState, signalState, brakeState;
	
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
		this.ki = newKi;
	}
	
	void setKp(double newKp) {
		this.kp = newKp;
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
		TrainModelSingleton trnModelSin = TrainModelSingleton.getInstance();
		TrainInterface train = trnModelSin.getTrain(trainId);
		if(train == null) return;
		
		if(primaryPort.isOpen()) {
			byte[] vals = new byte[1];
			if(eBrake) vals[0] = 0x01;
			else vals[0] = 0x00;
			primaryPort.writeBytes(vals, 1);
		}
		mboAuthority = train.getMBOAuthority();
		trackAuthority = train.getTrackAuthority();
		mboSpeed = train.getMBOSpeed();
		trackSpeed = train.getTrackSpeed();
		actualSpeed = train.getSpeed();
		engineState = train.engineState();
		signalState = train.railSignalState();
		brakeState = train.brakeOperationState();
		
		train.setPower(power);
		train.setTemperature(temp);
		train.setServiceBrake();
		if(!train.getEmergencyBrake() && eBrake) train.triggerEmergencyBrake();
		if(train.getEmergencyBrake() && !eBrake) eBrake = true;
		if(train.getLightState() != lights) train.toggleLights();
		if(train.getLeftDoorState() != leftDoor) train.toggleLeftDoors();
		if(train.getRightDoorState() != rightDoor) train.toggleRightDoors();
	}
}
