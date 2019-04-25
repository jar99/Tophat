package application.TrainModel;
/**
 * This is the TrainModelSingleton this class is used to communicate with all of the train model objects.
 * 
 * @author jar254
 * @version 1.0
 *
 */

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Set;

import application.TopHatRailwayUI;
import application.MBO.MBOInterface;
import application.MBO.MBOSingleton;
import application.TrackModel.TrackModelInterface;
import application.TrackModel.TrackModelSingleton;
import application.TrainController.TrainControllerSingleton;
import application.TrainControllerHardware.TrainControllerHWSingleton;
import application.TrainModel.UI.TrainLogger;

public class TrainModelSingleton implements TrainModelInterface {

	private static final String TRAINMODFILE = "train.mod";
	
	private static boolean isCTCMode;
	
	public static boolean isCTCModeS() {
		return isCTCMode;
	}
	
	public boolean isCTCMode() {
		return isCTCModeS();
	}
	
	public static void setMode(boolean value) {
		TrainLogger.debugS("Setting the mode to " + value);
		isCTCMode = value;
		
	}

	// Singleton Functions (NO TOUCHY!!)
	private static TrainModelSingleton instance;

	private TrainModelSingleton() {
		trainModelHashTable = new Hashtable<>();
		loadFromFile();
	}

	public static TrainModelSingleton getInstance() {
		if (instance == null) {
			instance = new TrainModelSingleton();
		}

		return instance;
	}

	private boolean disabled = false;

	public boolean toggleDisable() {
		return disabled = !disabled;
	}

	// =====================================

	private Hashtable<Integer, TrainModel> trainModelHashTable;

	private int trainIDHW = -1; // Negative numbers not assigned

	public TrainInterface createTrain(int trainID) {
		if (trainExists(trainID))
			return null;
		TrainModel train = new TrainModel(trainID, TrackModelSingleton.getInstance(), MBOSingleton.getInstance());
		trainModelHashTable.put(trainID, train);

//    	if (trainIDHW < 0) { // This is the check if no train belongs to train ctr hardware
//    		TrainControllerHWSingleton trnCtrlHW = TrainControllerHWSingleton.getInstance();
//    		//TODO assigne it
//    		
//    		trainIDHW = trainID;
//    	} else {
//    		TrainControllerSingleton trnCtrl = TrainControllerSingleton.getInstance();
//    		trnCtrl.createTrain(trainID, train); //Create this method.
//    	}

		TrainControllerSingleton trnCtrl = TrainControllerSingleton.getInstance();
		trnCtrl.createTrain(trainID, train); // Create this method.

		TrainModelMainCtrl.addTrainS(trainID, train); // Setting it into the debug UI
		TopHatRailwayUI.addTrainS(trainID); //Setting it into the main UI
		
		train.dispatch();

		return train;
	}

	public TrainModel createTrain(int trainID, int passanger, double speed, TrackModelInterface track,
			MBOInterface mbo) {
		if (trainExists(trainID))
			return null;

		TrainModel train = new TrainModel(trainID, track, mbo, passanger, speed);
		trainModelHashTable.put(trainID, train);
		TrainModelMainCtrl.addTrainS(trainID, train);

		return train;

	}

	public TrainInterface removeTrain(int trainID, boolean isolate) {
		TrainModel train = trainModelHashTable.remove(trainID);
		if (train != null) {
			train.remove();
			TrainModelMainCtrl.removeTrainS(trainID, train);
			TopHatRailwayUI.removeTrainS(trainID);
		}

		if (!isolate) {
			if (trainIDHW == trainID) { // This is the check if no train belongs to train ctr hardware
				TrainControllerHWSingleton trnCtrlHW = TrainControllerHWSingleton.getInstance();
				// TODO remove it

				trainIDHW = trainID * -1;
			} else {
				TrainControllerSingleton trnCtrl = TrainControllerSingleton.getInstance();
				trnCtrl.removeTrain(trainID);
			}

		}
		
		return train;
	}

	public boolean trainExists(int trainID) {
		return trainModelHashTable.containsKey(trainID);
	}

	TrainModel getTrainModel(int trainID) {
		return trainModelHashTable.get(trainID);
	}
	
	public TrainInterface getTrain(int trainID) {
		return getTrainModel(trainID);
	}
		
	public TrainModelTrackInterface getTrainTrackInterface(int trainID) {
		return trainModelHashTable.get(trainID);
	}

	public TrainInterface removeTrain(int trainID) {
		return removeTrain(trainID, false);
	}

	public boolean dispatchTrain(int trainID) {
		TrainModel train = trainModelHashTable.get(trainID);
		if (train != null)
			return train.dispatch();
		return false;
	}

	/**
	 * Returns the count of how many trains there are. return: int count of trains.
	 */
	@Override
	public int trainCount() {
		return trainModelHashTable.size();
	}

	@Override
	public final Set<Integer> getAllTrainIDs() {
		return trainModelHashTable.keySet();
	}

	Collection<TrainModel> getTrains() {
		return trainModelHashTable.values();
	}

	private void loadFromFile() {
		File file = new File(TRAINMODFILE);
		if (!file.exists()) {
			TrainLogger.errorS("Could not find " + file.getName());
			return;
		}
		try {
			TrainFileLoader.loadFile(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// MBO interface functions
	@Override
	public boolean setTrainAuthority(int trainID, int authority) {
		TrainModel train = trainModelHashTable.get(trainID);
		if (train == null)
			return false;
		train.setMBOAuthority(authority);
		return true;

	}

	@Override
	public boolean setTrainSuggestedSpeed(int trainID, double speed) {
		TrainModel train = trainModelHashTable.get(trainID);
		if (train == null)
			return false;
		train.setMBOSuggestedSpeed(speed);
		return true;

	}

	@Override
	public double getXcord(int trainID) {
		TrainModel train = trainModelHashTable.get(trainID);
		if (train == null)
			return Double.NaN;
		return train.getX();
	}

	@Override
	public double getYcord(int trainID) {
		TrainModel train = trainModelHashTable.get(trainID);
		if (train == null)
			return Double.NaN;
		return train.getY();
	}

	// NOTE: Singleton Connections (Put changes reads, gets, sets that you want to
	// occur here)
	// WARNING: This Only changes the singleton, not your UI. UI updates occur in
	// your UI controller

	public void update() {
		if (disabled)
			return;

		for (TrainModel trainModel : trainModelHashTable.values()) {
			// Any code to call for each train model update.
			trainModel.update();
		}
	}
}