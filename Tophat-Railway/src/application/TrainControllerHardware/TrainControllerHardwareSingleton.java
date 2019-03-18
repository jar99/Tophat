package application.TrainControllerHardware;

import com.fazecast.jSerialComm.*;

public class TrainControllerHardwareSingleton{

	private static TrainControllerHardwareSingleton instance = null;
	
	private TrainControllerHardwareSingleton(){}
	
	public static TrainControllerHardwareSingleton getInstance(){
		if(instance == null) instance = new TrainControllerHardwareSingleton();
		
		return instance;
	}
	
	// getters and setters
	private String drivingMode;
	private String gpsLocation;
	private String trainID;
	private String ctcSpeed, ctcAuth, mboSpeed, mboAuth;
	private String speed, power, ki, kp;
	private String brake, eBrake;
	private String engineStatus, brakeStatus, signalStatus;
	private String lights, lDoor, rDoor, temp;
	
	/**
	 * Controls Singleton connections, doesn't update UI
	 */
	public void update(){
		
	}
}
