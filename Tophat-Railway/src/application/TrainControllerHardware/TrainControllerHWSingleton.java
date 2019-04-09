package application.TrainControllerHardware;

import com.fazecast.jSerialComm.*;
import application.TrainModel.TrainModelSingleton;
import application.TrainModel.TrainInterface;


public class TrainControllerHWSingleton{

	private static TrainControllerHWSingleton instance = null;
	
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
	private boolean lights, lDoor, rDoor, temp;
	
	/**
	 * Controls Singleton connections, doesn't update UI
	 */
	public void update(){
		
	}
}
