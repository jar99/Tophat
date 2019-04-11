package application.TrainController;

import java.util.Hashtable;

import application.CTC.CTCSingleton;
import application.MBO.MBOSingleton;
import application.TrackController.TrackControllerSingleton;
import application.TrackModel.TrackModelSingleton;
import application.TrainModel.TrainInterface;
import application.TrainModel.TrainModelSingleton;

public class TrainControllerSingleton {

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
	private int trainID;
	private double numSpeed, power = 0, temperature = 0;
	private int numPower, MBOAuthority= 0, CTCAuthority = 0, numTemperature, numOfTrains, trainStatus, size;
	private boolean emergencyBrake, serviceBrake, leftDoor, rightDoor, driveMode, engineFail, brakeFail, signalFail;
	private boolean lights;
	private double speed = 0, MBOSpeed = 0, CTCSpeed = 0;

	// create hashtable for each individual train
	// public void makeTrain(put information in){

	private Hashtable<Integer, Train> trainCtrlHashTable = new Hashtable<>();

	TrainModelSingleton trnModSin = TrainModelSingleton.getInstance();
	Train train;
	private TrainInterface trainMod;

	// NOTE: Put some functions here
	// Hardware will use signalton and get information
	// Need to be able to depict different train
	// switch between software or hardware..hardware needs approved

	// Check TrainID and remove HashTable
	public Train createTrain(int trainID, TrainInterface trainMod) {
				this.trainMod = trainMod;
					Train train = new Train(trainID);
					trainCtrlHashTable.put(trainID, train);
					return train;
	}
	
	public void removeTrain(int trianID) {
			if(!trainCtrlHashTable.contains(trainID)) {
				trainCtrlHashTable.remove(trainID);
			}
	}
	// Sends TrainID as INTEGER
	public Train getTrainID(int trainID) {
			return trainCtrlHashTable.get(trainID);
	}
	

	public void setTrainID(int trainID) {
		this.trainID = trainID;
	}
	
	public int getTrainSize() {
		return trainCtrlHashTable.size();
	}
	
	public void setTrainSize(int size) {
		this.size = size;
	}
	
	// Send Speed as DOUBLE
	public double getSpeed() {
			return speed;
	}

	public void setSpeed(double speed) {
		if(speed < 0) {
			throw new IllegalArgumentException("Cannot have negative speed.");
		}
		this.speed = speed;
	}

	// Send Power as Double
	public double getPower() {
		return power;
	}

	public void setPower(double power) {
		this.power = power;
	}

	// Sends Temperature as DOUBLE
	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		if(temperature < 0) {
			throw new IllegalArgumentException("Cannot have negative temperature.");
		}
		this.temperature = temperature;
	}

	// Boolean SeriveBrake
	public boolean getServiceBrake() {
		return serviceBrake;
	}

	public void setServiceBrake(boolean serviceBrake) {
		this.serviceBrake = serviceBrake;
	}

	// Boolean EmergencyBrake
	public boolean getemergencyBrake() {
		return emergencyBrake;
	}

	public void setemergencyBrake(boolean emergencyBrake) {
		this.emergencyBrake = emergencyBrake;
	}

	/**
	 * 1 = Engine Failure, 2 = Brake Failure, 3 = Signal Lost
	 * 
	 * @return
	 */
	public int getTrainStatus() {
		return trainStatus;
	}

	public void setTrainStatus(int trainStatus) {
		this.trainStatus = trainStatus;
	}
	

	/////////////////// TRAIN FAILURES//////////////////////
	/**
	 * Engine Failure
	 * 
	 * @return
	 */
	public boolean getEngineStatus() {
		return engineFail;
	}

	public void setEngineStatus(boolean engineFail) {
		this.engineFail = engineFail;
	}

	/**
	 * Brake Failure
	 * 
	 * @return
	 */
	public boolean getBrakeStatus() {
		return brakeFail;
	}

	public void setBrakeStatus(boolean brakeFail) {
		this.brakeFail = brakeFail;
	}

	/**
	 * Signal Failure
	 * 
	 * @return
	 */
	public boolean getSignalStatus() {
		return signalFail;
	}

	public void setSignalStatus(boolean signalFail) {
		this.signalFail = signalFail;
	}

//////////////////////END OF TRAIN SHIT/////////////////////////////////////////	

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

	public boolean getLights() {
		return lights;
	}
	
	public void setLights(boolean lights) {
		this.lights = lights;
	}
	// Drive Mode
	public boolean getDriveMode() {
		return driveMode;
	}

	public void setDriveMode(boolean driveMode) {
		this.driveMode = driveMode;
	}

	
	public double getMBOSpeed() {
		return MBOSpeed;
	}
	
	public void setMBOSpeed(double MBOSpeed) {
		this.MBOSpeed = MBOSpeed;
	}
	
	public int getMBOAuthority() {
		return MBOAuthority;
	}
	
	public void setMBOAuthoirty(int MBOAuthority) {
		this.MBOAuthority = MBOAuthority;
	}
	
	public double getCTCSpeed() {
		return CTCSpeed;
	}
	
	public void setCTCSpeed(double CTCSpeed) {
		this.CTCSpeed = CTCSpeed;
	}
	
	public int getCTCAuthority() {
		return CTCAuthority;
	}
	
	public void setCTCAuthoirty(int CTCAuthority) {
		this.CTCAuthority = CTCAuthority;
	}
	
	
	// NOTE: Singleton Connections (Put changes reads, gets, sets that you want to
	// occur here)
	// WARNING: This Only changes the singleton, not your UI. UI updates occur in
	// your UI controller

 

	public void update() {
		

		
		if(trainMod == null) {
			return;
		}
		speed = trainMod.getSpeed();
		
		MBOSpeed = trainMod.getMBOSpeed();
		MBOAuthority = trainMod.getMBOAuthority();
		CTCSpeed = trainMod.getTrackSpeed();
		CTCAuthority = trainMod.getTrackAuthority();
		
		//leftDoor = trainMod.getLeftDoorState();
		
		//rightDoor = trainMod.getRightDoorState();
		  
		//engineFail = trainMod.engineState();
		
		//MBOAuthority = trainMod.getMBOAuthority();
		
		//MBOSpeed = trainMod.getMBOSpeed();
		 //trainStatus = trainMod.

	}

}
