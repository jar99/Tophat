package application.TrainController;

import java.util.Hashtable;

import application.CTC.CTCSingleton;
import application.MBO.MBOSingleton;
import application.TrackController.TrackControllerSingleton;
import application.TrackModel.TrackModelSingleton;
import application.TrainModel.TrainInterface;
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
	private int numSpeed, numPower, numTemperature;
	private boolean emergencyBrake, serviceBrake;
	
	//create hashtable for each individual train
	// public void makeTrain(put information in){ 
	
	private Hashtable <Integer, TrainCtrlInterface> trainCtrlHashTable;
	
	TrainModelSingleton trnModSin = TrainModelSingleton.getInstance();
	Train train;
	

	// NOTE: Put some functions here
	
	//Check TrainID and remove HashTable
	public void getTrains(){
		for(Integer trainID: trnModSin.getAllTrainIDs()) {
			if(!trainCtrlHashTable.contains(trainID)) {
				TrainCtrlInterface trainCtrlInterface = new Train(trainID);
				trainCtrlHashTable.put(trainID, trainCtrlInterface);
			}
		}
		for(Integer trainID: trainCtrlHashTable.keySet()) {
			if(!trnModSin.getAllTrainIDs().contains(trainID)) {
				trainCtrlHashTable.remove(trainID);
			}
		}
	}
	
	//Sends TrainID as INTEGER 
	public int getTrainID() {
		return trainID;
	}
	
	public void setTrainID(int trainID) {
		this.trainID = trainID;
	}
	
	public void getSpeed() {
		for(Integer speed: trnModSin.getAllTrainIDs()) {
			if(!trainCtrlHashTable.contains(speed)) {
				TrainCtrlInterface trainCtrlInterface = new Train(speed);
				trainCtrlHashTable.put(speed, trainCtrlInterface);
			}
		}
	}
	
	//Send Speed as STRING
	/*public String getSpeed(){
		return speed;
	}*/
	
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
	
	//Sends Temperature as STRING
	public String getTemperature() {
		return temperature;
	}
	
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	
	public int getnumTemperature() {
		return numTemperature;
	}
	
	//Set Temperature as INTEGER
	public void setnumTemperature(int numTemperature) {
		this.numTemperature = numTemperature;
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
	
	//Drive Mode
	
	// NOTE: Singleton Connections (Put changes reads, gets, sets that you want to
	// occur here)
	// WARNING: This Only changes the singleton, not your UI. UI updates occur in
	// your UI controller
	public void update() {
		// Example: get the count from a singleton and replace yours with the largest
		
		speed = trnModSin.getSpeed();
	}

}
