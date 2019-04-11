package application.TrainModel;
/**
 * This is the TrainModelSingleton this class is used to communicate with all of the train model objects.
 * 
 * @author jar254
 * @version 1.0
 *
 */

import java.util.Collection;
import java.util.Hashtable;
import java.util.Set;

import application.MBO.MBOSingleton;
import application.TrackModel.TrackModelSingleton;
import application.TrainController.TrainControllerSingleton;

public class TrainModelSingleton implements TrainModelInterface {

	// Singleton Functions (NO TOUCHY!!)
	private static TrainModelSingleton instance;

	private TrainModelSingleton() {
		 trainModelHashTable = new Hashtable<>();
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
	
    public TrainInterface createTrain(int trainID) {  	
    	if(trainExists(trainID)) return null;
    	TrainModel train = new TrainModel(trainID, TrackModelSingleton.getInstance(), MBOSingleton.getInstance());
//    	
    	TrainControllerSingleton trnCtrl = TrainControllerSingleton.getInstance();
    	trnCtrl.createTrain(trainID, train); //Create this method.
    	
    	train.dispatch();
    	
        return trainModelHashTable.putIfAbsent(trainID, train);
    }
    
    TrainModel createTrain(int trainID, int passanger, double speed) {
    	if(trainExists(trainID)) return null;
    	
    	TrainModel train = new TrainModel(trainID, new application.TrainModel.Test.TrainModelTrackTest(1/2, 10, 20.0), new application.TrainModel.Test.MBOConnection(), passanger, speed);
    	train.dispatch();
        return trainModelHashTable.putIfAbsent(trainID, train);
		
	}
    
    
    public boolean trainExists(int trainID) {
        return trainModelHashTable.containsKey(trainID);
    }
    
    public TrainInterface getTrain(int trainID){
        return trainModelHashTable.get(trainID);
    }
    
	public boolean removeTrain(int trainID) {
		TrainModel train = trainModelHashTable.remove(trainID);
		if(train != null) {
			train.remove();
		}
		try {
		TrainControllerSingleton trnCtrl = TrainControllerSingleton.getInstance();
		trnCtrl.removeTrain(trainID);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return train != null;
	}
	
	public boolean dispatchTrain(int trainID) {
		TrainModel train = trainModelHashTable.get(trainID);
		if(train != null) return train.dispatch();
		return false;
	}
    
	/**
	 * Returns the count of how many trains there are.
	 * return: int count of trains.
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

	// NOTE: Singleton Connections (Put changes reads, gets, sets that you want to
	// occur here)
	// WARNING: This Only changes the singleton, not your UI. UI updates occur in
	// your UI controller
    
    private long last;
	public void update() {
		if(disabled) return;
		
		for(TrainModel trainModel : trainModelHashTable.values()) {
			//Any code to call for each train model update.
			trainModel.update();
		}
	}

	@Override
	public void setTrainAuthority(int trainID, int authority) {
		TrainModel train = trainModelHashTable.get(trainID);
		if(train == null) return;
		train.setMBOAuthority(authority);
		
	}
	
	@Override
	public void setTrainSuggestedSpeed(int trainID, double speed) {
		TrainModel train = trainModelHashTable.get(trainID);
		if(train == null) return;
		train.setMBOSuggestedSpeed(speed);
		
	}
}