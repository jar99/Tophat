package Train_Model;

public interface TrainModelInterface {

    TrainModel getTrain(String trainID);

    boolean dispatchTrain(String trainID);
}
