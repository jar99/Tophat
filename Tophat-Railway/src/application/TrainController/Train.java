package application.TrainController;

public class Train implements TrainCtrlInterface {
	//update from train model get necessary information and update hashtable
	//update hashtable from controller assign each variable to necessary train
	
	private TrainControllerSingleton mySin = TrainControllerSingleton.getInstance();

	//do power calculation in here
	
	private int speed, power, trainID;
	private boolean serviceBrake, emergencyBrake, lights, rightDoor, leftDoor, driveStatus;
	private boolean manual = false, automatic = true, engineStatus = false, brakeStatus = false, signalStatus = false;
	

	public Train(Integer trainID) {
		this.trainID = trainID;
	}

	public void trianID(int trainID) {
		mySin.getTrainID(trainID);
		
	}
	public void Speed(double speed) {
		if(speed == 0) {
			mySin.setSpeed(0);
		}else
			mySin.setSpeed(speed);
	}
	
	public void Power(int Power) {
		mySin.getnumPower();
	}
	
	public void temperature(double temperature) {
		mySin.getTemperature();
	}
	
	public void serviceBrake(boolean serviceBrake) {
		mySin.getServiceBrake();
	}
	
	public void emergencyBrake(boolean emergencyBrake) {
		mySin.getemergencyBrake();
	}
	
	/**
	 * true = Manual
	 * False = Automatic
	 * @param drvieStatus
	 */
	public void driveMode(boolean drvieMode) {
			mySin.getDriveMode();
	}
	
	/*public void trainStatus(int trainStatus) {
		trainStatus = mySin.getTrainStatus();
		switch(trainStatus) {
			case 1:
				engineStatus = true;
			case 2:
				brakeStatus = true;
			case 3:
				signalStatus = true;
		}
	}*/
	
	public void engineStatus(boolean engineFail) {
		engineFail = mySin.getEngineStatus();
	}
	
	public void brakeStatus(boolean brakeFailure) {
		brakeFailure = mySin.getBrakeStatus();
	}
	
	public void signalStatus(boolean signalFailure) {
		signalFailure = mySin.getSignalStatus();
	}
	
	public void leftDoor(boolean leftDoor) {
		leftDoor = mySin.getLeftDoor();
	}
	
	public void rightDoor(boolean rightDoor) {
		rightDoor = mySin.getRightDoor();
	}
	
}
