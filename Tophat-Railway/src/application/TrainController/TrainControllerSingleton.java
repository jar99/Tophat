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
	// NOTE: Put some functions here
	// Hardware will use signalton and get information
	// Need to be able to depict different train
	// switch between software or hardware..hardware needs approved
	// Check TrainID and remove HashTable

	static boolean ctcMode;
	
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
	public Train getTrain(int trainID) {
			return trainCtrlHashTable.get(trainID);
	}

	public int getTrainSize() {
		return trainCtrlHashTable.size();
	}
	
	public static boolean getMode() {
		return ctcMode;
	}
	
	public static void setMode(boolean set) {
		ctcMode = set;
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
}
