package application.TrainModel;

import application.TrackModel.TrackBlock;

public class TrainModel {


    private float velocity;
    private float temperature;
    private float weight;
    private String trainID;
    private double x, y;

    private TrackBlock currentBlock;


    private TrainControllerInterface trainController;

    private TrainControllerInterface trainInterface;

    public TrainModel(String trainID) {
        this.trainID = trainID;
    }

    public TrainModel(String trainID, double x, double y, TrackBlock currentBlock) {
        this(trainID);
        this.x = x;
        this.y = y;
        this.currentBlock = currentBlock;
    }

    public void update(long delaTime){
        float power = trainInterface.getPower();
        float brake = trainInterface.getBrake();
        if(brake > 0){
            velocity = 0.0f;
        }else if(power > 0){
            velocity = ((float) delaTime / 10000) * power;
        }
    }

    public float getWeight() {
        return weight;
    }

    public float getPower(){
        return 0.0F;
        // return trainController.getPower();
    }

    public float getVelocity() {
        return velocity;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public String toString(){
        return trainID;
    }

}
