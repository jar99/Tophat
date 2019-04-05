package application.TrainControllerHardware;

import com.fazecast.jSerialComm.*;
import application.TrainModel.TrainModelSingleton;
import application.TrainModel.TrainInterface;


public class TrainControllerHardwareSingleton{

	private static TrainControllerHardwareSingleton instance = null;
	
	private TrainControllerHardwareSingleton(){}
	
	public static TrainControllerHardwareSingleton getInstance(){
		if(instance == null) instance = new TrainControllerHardwareSingleton();
		
		return instance;
	}
	
	// getters and setters
	private boolean drivingMode;
	private double x, y;
	private int trainID;
	private double ctcSpeed, ctcAuth, mboSpeed, mboAuth;
	private double speed, power, ki, kp;
	private boolean brake, eBrake;
	private boolean engineStatus, brakeStatus, signalStatus;
	private boolean lights, lDoor, rDoor, temp;
	
	/**
	 * Controls Singleton connections, doesn't update UI
	 */
	public void update(){
		
	}
}
