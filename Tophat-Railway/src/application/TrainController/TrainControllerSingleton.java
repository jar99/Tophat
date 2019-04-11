package application.TrainController;

import java.util.Hashtable;

import application.TrainModel.TrainInterface;

public class TrainControllerSingleton {

	// Singleton Functions (NO TOUCHY!!)
	private static TrainControllerSingleton instance = null;

	private TrainControllerSingleton() {
		trainCtrlHashTable = new Hashtable<>();
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
	
	// create hashtable for each individual train
	// public void makeTrain(put information in){

	private Hashtable<Integer, Train> trainCtrlHashTable;
	
	Train train;

	// NOTE: Put some functions here
	// Hardware will use signalton and get information
	// Need to be able to depict different train
	// switch between software or hardware..hardware needs approved

	// Check TrainID and remove HashTable
	public Train createTrain(int trainID, TrainInterface trainMod) {
		TrainControllerCtrl.addTrainS(trainID);
				Train train = new Train(trainID, trainMod);
				trainCtrlHashTable.put(trainID, train);
				return train;
	}
	
	public void removeTrain(int trianID) {
		TrainControllerCtrl.removeTrainS(trainID);
		trainCtrlHashTable.remove(trainID);
	}
	// Sends TrainID as INTEGER
	public Train getTrainID(int trainID) {
			return trainCtrlHashTable.get(trainID);
	}
	
	public void setTrain(int trainID) {
		 Train train = getTrainID(trainID);
		 if(train != null) {
			 this.train = train;
		 }
		
	}
	
	public int getTrainSize() {
		return trainCtrlHashTable.size();
	}
	
	// Send Speed as DOUBLE
	public double getSpeed() {
		if(train == null) return -7.7;
			return train.getSpeed();
	}

	public void setSpeed(double speed) {
		if(train == null) return;
		if(speed < 0) {
			throw new IllegalArgumentException("Cannot have negative speed.");
		}
		train.setSpeed(speed);
	}

	// Send Power as Double
	public double getPower() {
		if(train == null) return - 6.6;
		return train.getPower();
	}

	// Sends Temperature as DOUBLE
	public double getTemperature() {
		if(train == null) return - 5.5;
		return train.getTemperature();
	}

	public void setTemperature(double temperature) {
		if(train == null) return;
		if(temperature < 0) {
			throw new IllegalArgumentException("Cannot have negative temperature.");
		}
		train.setTemperature(temperature);
	}

	// Boolean SeriveBrake
	public boolean getServiceBrake() {
		if(train == null) return true;
		return train.getServiceBrake();
	}

	public void toggleServiceBrake() {
		if(train == null) return;
		train.toggleServiceBrake();
	}

	// Boolean EmergencyBrake
	public boolean getEmergencyBrake() {
		if(train == null) return true;
		return train.getEmergencyBrake();
	}

	public void toggleEmergencyBrake() {
		if(train == null) return;
		train.toggleEmergencyBrake();
	}
	
	public void setEmergencyBrake(boolean b) {
		if(train == null) return;
		if(train.getEngineStatus() != b) {
			train.toggleEmergencyBrake();
		}
	}

	public boolean getTrainMode() {
		if(train == null) return false;
		return train.getDriveMode();
	}

//////////////////////END OF TRAIN SHIT/////////////////////////////////////////	

	public boolean getLeftDoor() {
		if(train == null) return false;
		return train.leftDoorState();
	}

	public boolean getRightDoor() {
		if(train == null) return false;
		return train.rightDoorState();
	}

	public void setLeftDoor(boolean b) {
		if(train == null);
		if(train.leftDoorState() != b) {
			train.toggleLeftDoor();
		}
		
	}

	public void setRightDoor(boolean b) {
		if(train == null);
		if(train.rightDoorState() != b) {
			train.toggleRightDoor();
		}
	}
	
	public boolean getLights() {
		if(train == null) return false;
		return train.getLights();
	}
	
	public void setLights(boolean b) {
		if(train == null) return;
		if(train.getLights() != b) {
			train.toggleLights();
		}
	}
	
	// Drive Mode
	public boolean getDriveMode() {
		if(train == null) return true;
		return train.getDriveMode();
	}

	public void setDriveMode(boolean driveMode) {
		if(train == null) return;
		train.setDriveMode(driveMode);
	}

	
	public double getMBOSpeed() {
		if(train == null) return -4.4;
		return train.getMBOSpeed();
	}
	
	public int getMBOAuthority() {
		if(train == null) return -4;
		return train.getMBOAuthority();
	}
	
	public double getCTCSpeed() {
		if(train == null) return -3.3;
		return train.getCTCSpeed();
	}
	
	public int getCTCAuthority() {
		if(train == null) return -3;
		return train.getCTCAuthority();
	}
	
	
	// NOTE: Singleton Connections (Put changes reads, gets, sets that you want to
	// occur here)
	// WARNING: This Only changes the singleton, not your UI. UI updates occur in
	// your UI controller

 

	public void update() {
		for(Train train : trainCtrlHashTable.values()) {
			//Any code to call for each train model update.
			train.update();
		}
	}

	public boolean getEngineStatus() {
		if(train == null) return true;
		return train.getEngineStatus();
	}

	public boolean getBrakeStatus() {
		if(train == null) return true;
		return train.getBrakeStatus();
	}

	public boolean getSignalStatus() {
		if(train == null) return true;
		return train.getSignalStatus();
	}

	public void setKP(double kp) {
		if(train == null) return;
		train.setKP(kp);
	}
	
	public void setKI(double ki) {
		if(train == null) return;
		train.setKI(ki);
	}

	public double getKI() {
		if(train == null) return 1.0;
		return train.getKI();
	}

	public double getKP() {
		if(train == null) return 1.0;
		return train.getKP();
	}
}
