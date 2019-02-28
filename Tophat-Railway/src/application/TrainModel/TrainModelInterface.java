package application.TrainModel;

import application.TrackModel.TrackBlock;

public interface TrainModelInterface {

    TrainModel getTrain(int trainID);
    
    TrainModel[] getAllTrains();

    TrainModel createTrain(int trainID);
    
    
    void makeTrain(int trainID, double x, double y, TrackBlock currentBlock, TrackBlock nextBlock);
    
    TrainModel removeTrain(int trainID);

    boolean dispatchTrain(int trainID);
 
}
