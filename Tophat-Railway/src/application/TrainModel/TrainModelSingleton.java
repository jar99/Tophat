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

import application.TrackModel.TrackBlock;

public class TrainModelSingleton implements TrainModelInterface {

	// Singleton Functions (NO TOUCHY!!)
	private static TrainModelSingleton instance = null;

	private TrainModelSingleton() {
		 trainModelHashTable = new Hashtable<>();
	}

	public static TrainModelSingleton getInstance() {
		if (instance == null) {
			instance = new TrainModelSingleton();
		}

		return instance;
	}

	// =====================================

	private Hashtable<Integer, TrainModel> trainModelHashTable;
	
    public TrainInterface createTrain(int trainID) {  	    
        return trainModelHashTable.putIfAbsent(trainID, new TrainModel(trainID));
    }

    public void makeTrain(int trainID, double x, double y, TrackBlock currentBlock, TrackBlock nextBlock) {
        TrainModel train = new TrainModel(trainID, x, y, currentBlock);
        trainModelHashTable.put(trainID, train);
    }
    
    public boolean trainExists(int trainID) {
        return trainModelHashTable.containsKey(trainID);
    }
    
    public TrainInterface getTrain(int trainID){
        return trainModelHashTable.get(trainID);
    }
    
	public void removeTrain(int tranID) {
		TrainModel train = trainModelHashTable.remove(tranID);
		if(train != null) train.remove();
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
	public Set<Integer> getAllTrainIDs() {
		return trainModelHashTable.keySet();
	}


    Collection<TrainModel> getTrains() {
        return trainModelHashTable.values();
    }
    
    @Deprecated
    /**
     * Gets the speed of the first train.
     * @return
     */
    public String getSpeed() {
    	
    	for(TrainModel train : trainModelHashTable.values()) {
    		return train.getSpeed() + "mph";
    	}
    	
    	return "0mph";
    }

	// NOTE: Singleton Connections (Put changes reads, gets, sets that you want to
	// occur here)
	// WARNING: This Only changes the singleton, not your UI. UI updates occur in
	// your UI controller
	public void update() {
		
		for(TrainModel trainModel : trainModelHashTable.values()) {
			//Any code to call for each train model update.
			trainModel.update(0);
		}
	}
}
