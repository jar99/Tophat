package application.TrainModel;

import application.TrackModel.TrackBlock;

public interface TrainModelInterface {

    TrainModel getTrain(String trainID);

    TrainModel createTrain(String trainID);

    boolean dispatchTrain(String trainID);

    void makeTrain(int ID, double x, double y, TrackBlock currentBlock, TrackBlock nextBlock);
}
