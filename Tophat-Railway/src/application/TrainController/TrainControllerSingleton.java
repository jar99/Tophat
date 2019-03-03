package application.TrainController;

import java.util.Hashtable;

import application.CTC.CTCSingleton;
import application.MBO.MBOSingleton;
import application.TrackController.TrackControllerSingleton;
import application.TrackModel.TrackModelSingleton;
import application.TrainModel.TrainModelSingleton;

public class TrainControllerSingleton implements TrainControllerInterface {

	// Singleton Functions (NO TOUCHY!!)
	private static TrainControllerSingleton instance = null;

	private TrainControllerSingleton() {
	}

	public static TrainControllerSingleton getInstance() {
		if (instance == null) {
			instance = new TrainControllerSingleton();
		}

		return instance;
	}

	
	// =====================================

	// NOTE: Put your data objects here
	private String speed, power, temperature;
	private int trainID;
	private int numSpeed, numPower;
	private boolean emergencyBrake, serviceBrake;
	
	//create hashtable for each individual train
	// public void makeTrain(put information in){ 
	
	private Hashtable <Integer, Train> trainCtrlHashTable;
	TrainModelSingleton trnModSin = TrainModelSingleton.getInstance();

	// NOTE: Put some functions here
	
	//TrainID HashTable
	public Hashtable <Integer, Train> getTrain() {
		for(int i = 0; i < trnModSin.getCount(); i++) {
			trainCtrlHashTable.put(i,);
		}
		return trainCtrlHashTable;
	}
	
	
	//Send Speed as STRING
	public String getSpeed(){
		return speed;
	}
	
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	
	//Send Speed as INTEGER 
	public int getnumSpeed() {
		return numSpeed;
	}
	
	public void setnumSpeed(int numSpeed) {
		this.numSpeed = numSpeed;
	}
	
	//Send Power as STRING
	public String getPower() {
		return power;
	}
	
	public void setPower(String power) {
		this.power = power;
	}	
	
	//Send Power as INTEGER
	public int getnumPower() {
		return numPower;
	}
	
	public void setnumPower(int numPower) {
		this.numPower = numPower;
	}
	
	public String getTemperature() {
		return temperature;
	}
	
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	
	//Boolean SeriveBrake
	public boolean getServiceBrake() {
		return serviceBrake;
	}
	
	public void setServiceBrake(boolean serviceBrake) {
		this.serviceBrake = serviceBrake;
	}
	
	//Boolean EmergencyBrake 
	public boolean getemergencyBrake() {
		return emergencyBrake;
	}
	
	public void setemergencyBrake(boolean emergencyBrake) {
		this.emergencyBrake = emergencyBrake;
	}
	
	// NOTE: Singleton Connections (Put changes reads, gets, sets that you want to
	// occur here)
	// WARNING: This Only changes the singleton, not your UI. UI updates occur in
	// your UI controller
	public void update() {
		// Example: get the count from a singleton and replace yours with the largest
		
		speed = trnModSin.getSpeed();
	}

}
