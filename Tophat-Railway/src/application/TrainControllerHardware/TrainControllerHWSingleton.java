package application.TrainControllerHardware;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortIOException;
import application.TrainModel.TrainModelSingleton;
import application.TrainModel.TrainInterface;
import application.ClockSingleton;

import java.util.LinkedList;

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
	LinkedList<Integer> trainIds = new LinkedList<>();
	boolean drivingMode;
	double speed, power, ki, kp;
	boolean brake, eBrake;
	boolean intLights, extLights, leftDoor, rightDoor;
	double temp;
	
	// values that I'm just displaying
	int mboAuthority, trackAuthority;
	double mboSpeed, trackSpeed, actualSpeed;
	boolean engineState, signalState, brakeState;
	
	// getters and setters
	public int getTrainId() {
		return trainId;
	}
	
	public boolean addTrain(int newTrainId) {
		if(trainIds.contains(trainId)) return false;
		trainIds.add(newTrainId);
		return true;
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
	
	void toggleIntLights() {
		intLights = !intLights;
	}
	
	void toggleExtLights() {
		extLights = !extLights;
	}
	
	void toggleLeftDoor() {
		leftDoor = !leftDoor;
	}
	
	void toggleRightDoor() {
		rightDoor = !rightDoor;
	}
	/**
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

    double lastError;
   

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
		
		// power
		ClockSingleton clkSin = ClockSingleton.getInstance();
		double deltaT = clkSin.getRatio();
		double newError;
		if(drivingMode) {
			newError = speed - train.getSpeed(); // manual mode
		}
		else{
			newError = trackSpeed - train.getSpeed(); // automatic mode
		}
		double np1 = (kp * newError) + (ki * laplace(deltaT, lastError, newError, power));
		double np2 = (kp * newError) + (ki * laplace(deltaT, lastError, newError, power));
		double np3 = (kp * newError) + (ki * laplace(deltaT, lastError, newError, power));
		if(np1 == np2 && np1 == np3 && np2 == np3) {
			train.setPower(np1);
			power = np1;
		}
		else if(np1 == np2) {
			train.setPower(np1);
			power = np1;
		}
		else if(np1 == np3) {
			train.setPower(np1);
			power = np1;
		}
		else if(np2 == np3) {
			train.setPower(np2);
			power = np2;
		}
		else {
			train.setPower(0.0);
			power = 0.0;
		}
		lastError = newError;
		
		if(!engineState || !signalState || !brakeState) {
			train.setPower(0.0);
			power = 0.0;
		}
		if(!drivingMode && trackAuthority == 0) {
			train.setPower(0.0);
			power = 0.0;
		}
		
		train.setTemperature(temp);
		train.setServiceBrake();
		if(!train.getEmergencyBrake() && eBrake) train.triggerEmergencyBrake();
		if(train.getEmergencyBrake() && !eBrake) eBrake = true;
		if(train.getLightState() != extLights) train.toggleLights();
		if(train.getInterierLightState() != intLights) train.toggleInterierLight();
		if(train.getLeftDoorState() != leftDoor) train.toggleLeftDoors();
		if(train.getRightDoorState() != rightDoor) train.toggleRightDoors();
	}
}
