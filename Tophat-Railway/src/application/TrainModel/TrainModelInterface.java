package application.TrainModel;

import application.TrackModel.TrackBlock;

public interface TrainModelInterface {

    TrainModel getTrain(String trainID);

    TrainModel createTrain(String trainID);

    boolean dispatchTrain(String trainID);

    void makeTrain(int i, int i1, int i2, TrackBlock trackBlock, TrackBlock trackBlock1);
}
