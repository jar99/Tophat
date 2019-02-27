public interface TrainModelInterface {

    TrainModel getTrain(String trainID);

    boolean dispatchTrain(String trainID);
}
