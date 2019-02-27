package application.TrainModel;

import application.TrackModel.TrackBlock;
import application.TrackModel.TrackModelSingleton;

import java.util.Collection;
import java.util.HashMap;

public class TrainModelSingleton implements TrainModelInterface {
    private static TrainModelSingleton ourInstance = new TrainModelSingleton();

    public static TrainModelSingleton getInstance() {
        return ourInstance;
    }

    private HashMap<String, TrainModel> trainModelHashMap;


    private TrainModelSingleton() {
        trainModelHashMap = new HashMap<>();
    }

    public TrainModel getTrain(String trainID){
        return trainModelHashMap.get(trainID);
    }

    @Override
    public TrainModel createTrain(String trainID) {
        TrainModel train = new TrainModel(trainID);
        trainModelHashMap.put(trainID, train);
        return train;
    }

    @Override
    public boolean dispatchTrain(String trainID) {
        return trainModelHashMap.put(trainID, new TrainModel(trainID)) != null;
    }

    @Override
    public void makeTrain(int i, int i1, int i2, TrackBlock trackBlock, TrackBlock trackBlock1) {

    }

    Collection<TrainModel> getTrains() {
        return trainModelHashMap.values();
    }



    //====================================================

    // NOTE: Singleton Connections (Put changes reads, gets, sets that you want to
    // occur here)
    // WARNING: This Only changes the singleton, not your UI. UI updates occur in
    // your UI controller
    public void update() {
        // Example: get the count from a singleton and replace yours with the largest
        TrackModelSingleton tckModSin = TrackModelSingleton.getInstance();


    }

    public int getCount() {
        System.err.println("Remove this call.");
        return 1;
    }
}
