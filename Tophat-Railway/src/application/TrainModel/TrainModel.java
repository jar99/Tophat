package application.TrainModel;

import application.MBO.MBOSingleton;
import application.TrackModel.TrackBlock;
import application.TrackModel.TrackModelSingleton;
import application.TrackModel.TrackTrain;
import application.TrainController.TrainControllerSingleton;

public class TrainModel {


    private double velocity;
    private double temperature;
    private double weight;
    private int trainID;
    private double x, y;

    private TrackBlock currentBlock;

//
//    private TrainControllerInterface trainController;
//
//    private TrainControllerInterface trainInterface;

    public TrainModel(int trainID) {
        this.trainID = trainID;
    }

    public TrainModel(int trainID, double x, double y, TrackBlock currentBlock) {
        this(trainID);
        this.x = x;
        this.y = y;
        this.currentBlock = currentBlock;
    }

    public void update(int delaTime){
        callTrainController();
        double distance = velocity;
    	callTrackModel(velocity);
        callMBO();
    }

    public void sendSpeedLocation(){

    }
    
    private void callTrainController() {
    	TrainControllerSingleton tainControllerSingleton = TrainControllerSingleton.getInstance();
    	//if(tainControllerSingleton.getPower() <= 0) {
    		velocity = 5.0;
    	//}else {
    	//	velocity = 0.0;
    	//}
    	
    }
    
    private void callTrackModel(double distance) {
    	TrackModelSingleton trackModelSingleton = TrackModelSingleton.getInstance();
    	TrackTrain trackTrain = trackModelSingleton.getTrainLocation(distance);
    	x = trackTrain.getX();
    	y = trackTrain.getY();
    	
    }

    private void callMBO(){
        MBOSingleton mboSingleton = MBOSingleton.getInstance();
//        mboSingleton.sendLocation(trainID, x, y);
    }

    public double getWeight() {
        return weight;
    }

    public double getPower(){
        return 0.0F;
        // return trainController.getPower();
    }

    public double getVelocity() {
        return velocity;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }
    
    /**
     * Simple function to get a string value of location
     * @return
     */
    public String getCord() {
    	return "[" + x + ", " + y + "]";
    }

    public String toString(){
        return String.valueOf(trainID);
    }

}
