package application.TrainModel;

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
    public boolean dispatchTrain(String trainID) {
        return trainModelHashMap.put(trainID, new TrainModel(trainID)) != null;
    }

    Collection<TrainModel> getTrains() {
        return trainModelHashMap.values();
    }
}
