package application.TrainController;

public class Train implements TrainCtrlInterface {
	//update from train model get necessary information and update hashtable
	//update hashtable from controller assign each variable to necessary train
	
	private TrainControllerSingleton mySin = TrainControllerSingleton.getInstance();

	
	private int speed, power, temperture, trainID;
	private boolean serviceBrake, emergencyBrake, lights, rightDoor, leftDoor;
	private boolean manual = false, automatic = true, engineStatus, brakeStatus, signalStatus;
	
	
	public Train(int trainID) {
		this.trainID = trainID;
	}
	
	public void trianID(int trainID) {
		mySin.getTrainID();
		
	}
	public void Speed(int speed) {
		 mySin.getnumSpeed();
	}
	
	public void Power(int Power) {
		mySin.getnumPower();
	}
	
	public void temperture(int temperture) {
		mySin.getnumTemperature();
	}
	
	public void serviceBrake(boolean serviceBrake) {
		mySin.getServiceBrake();
	}
	
	public void emergencyBrake(boolean emergencyBrake) {
		mySin.getemergencyBrake();
	}
	
	public void driveMode(boolean driveMode) {
		//driveMode = mySin.
		if(driveMode == true) {
			manual = true;
		}else
			automatic = true;
	}
	
}
