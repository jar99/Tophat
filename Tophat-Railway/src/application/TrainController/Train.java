package application.TrainController;

public class Train {
	//update from train model get necessary information and update hashtable
	//update hashtable from controller assign each variable to necessary train
	
	private TrainControllerSingleton mySin = TrainControllerSingleton.getInstance();
	
	private int speed, power, temperture, trainID;
	private boolean serviceBrake, emergencyBrake, lights, rightDoor, leftDoor;
	private boolean manual, automatic, engineStatus, brakeStatus, signalStatus;
	
	
	public Train(int trainID, int speed, int power, int temperture) {
		this.trainID = trainID;
		this.speed = speed;
		this.power = power;
		this.temperture = temperture;
	}
	
	private void trianID(int trainID) {
		
	}
	private void Speed(int speed) {
		mySin.getnumSpeed();
	}
	
}
