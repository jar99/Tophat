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
	private String power;
	Double temperature;
	private int trainID;
	double numSpeed;
	private int numPower, numTemperature, numOfTrains, trainStatus;
	private boolean emergencyBrake, serviceBrake, leftDoor, rightDoor, driveMode;
	private double speed;
	
	//create hashtable for each individual train
	// public void makeTrain(put information in){ 
	
	private Hashtable <Integer, Train> trainCtrlHashTable;
	
	TrainModelSingleton trnModSin = TrainModelSingleton.getInstance();
	Train train;
	

	// NOTE: Put some functions here
	//Hardware will use signalton and get information
	//Need to be able to depict different train
	//switch between software or hardware..hardware needs approved
	
	
	//Check TrainID and remove HashTable
	public void createTrain(){
		for(Integer trainID: trnModSin.getAllTrainIDs()) {
			if(!trainCtrlHashTable.contains(trainID)) {
				Train train = new Train(trainID);
				trainCtrlHashTable.put(trainID, train);
			}
		}
		
		for(Integer trainID: trainCtrlHashTable.keySet()) {
			if(!trnModSin.getAllTrainIDs().contains(trainID)) {
				trainCtrlHashTable.remove(trainID);
			}
		}
	}
	
	//Sends TrainID as INTEGER 
	public Train getTrainID(int trainID) {
		return trainCtrlHashTable.get(trainID);
	}
	
	public void setTrainID(int trainID) {
		this.trainID = trainID;
	}
	
	//Send Speed as DOUBLE 
	public double getSpeed() {
		if(speed >= 0) {
			return speed;
		}
		
		return speed;
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
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
	
	//Sends Temperature as DOUBLE
	public double getTemperature() {
		return temperature;
	}
	
	public void setTemperature(double temperature) {
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
	
	/**
	 * 1 = Engine Failure,
	 * 2 = Brake Failure,
	 * 3 = Signal Lost
	 * @return
	 */
	public int getTrainStatus() {
		return trainStatus;
	}
	
	public void setTrainStatus(int trainStatus) {
		this.trainStatus = trainStatus;
	}
	
	
	public boolean getLeftDoor() {
		return leftDoor;
	}
	
	public void setLeftDoor(boolean leftDoor) {
		this.leftDoor = leftDoor;
	}
	
	public boolean getRightDoor() {
		return rightDoor;
	}
	
	public void setRightDoor(boolean rightDoor) {
		this.rightDoor = rightDoor;
	}
	
	//Drive Mode
	public boolean getDriveMode() {
		return driveMode;
	}
	
	public void setDriveMode(boolean driveMode) {
		this.driveMode = driveMode;
	}
	
	// NOTE: Singleton Connections (Put changes reads, gets, sets that you want to
	// occur here)
	// WARNING: This Only changes the singleton, not your UI. UI updates occur in
	// your UI controller
	
	private TrainInterface trainMod; //Train Model Interface
	public void update() {
		
		//check for NULL
		speed = trainMod.getSpeed();
		
		leftDoor = trainMod.getLeftDoorState();
		
		rightDoor = trainMod.getRightDoorState();
		
		
		
		
	}

}
