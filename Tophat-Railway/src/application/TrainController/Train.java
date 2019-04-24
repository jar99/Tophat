package application.TrainController;

import application.ClockSingleton;
import application.TrainModel.TrainInterface;


public class Train implements TrainCtrlInterface {
	//do power calculation in here

	private int trainID;
	private double speed, power;
	private boolean isManual = false, set;

	private TrainInterface trainMod;
	private double ki = 0.01, kp = 0.01;

	public Train(Integer trainID, TrainInterface trainMod) {
		this.trainID = trainID;
		this.trainMod = trainMod;
	}

	public int getTrianID() {
		return trainID;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getSpeed() {
		return speed;
	}
	
	public double getActualSpeed() {
		return trainMod.getSpeed();
	}
	
	public double getMaxSpeed() {
		return trainMod.getMaxSpeed();
	}

	public void setPower(double power) {
		this.power = power;
	}

	public double getPower() {
		return power;
	}
	
	public void setMode(boolean set) {
		this.set = set;
	}
	
	public boolean getMode() {
		return set;
	}
	
	public double getTemperature() {
		return trainMod.getTemperature();
	}

	public void setTemperature(double temp) {
		trainMod.setTemperature(temp);
	}

	public void toggleServiceBrake() {
		trainMod.toggleServiceBrake();
	}

	public boolean getServiceBrake() {
		return trainMod.getServiceBrake();
	}

	public boolean getEmergencyBrake() {
		return trainMod.getEmergencyBrake();
	}

	public void toggleEmergencyBrake() {
		if(!getEmergencyBrake()) {
			trainMod.triggerEmergencyBrake();
		}else {
			trainMod.resetEmergencyBrake();
		}
	}

	/**
	 * true = Manual
	 * False = Automatic
	 * @param drvieStatus
	 */
	public void setDriveMode(boolean drvieMode) {
		isManual = drvieMode;
	}

	public boolean getDriveMode() {
		return isManual;
	}
	
	public boolean engineStatus() {
		return trainMod.engineState();
	}

	public boolean brakeStatus() {
		return trainMod.brakeOperationState();
	}

	public void toggleLeftDoor() {
		trainMod.toggleLeftDoors();
	}

	public void toggleRightDoor() {
		trainMod.toggleRightDoors();
	}

	public void toggleInteriorLights() {
		trainMod.toggleInterierLight();
	}

	public boolean leftDoorState() {
		return trainMod.getLeftDoorState();
	}

	public boolean rightDoorState() {
		return trainMod.getRightDoorState();
	}

	public boolean getLights() {
		return trainMod.getLightState();
	}

	public void toggleLights() {
		trainMod.toggleLights();
	}

	public double getMBOSpeed() {
		return trainMod.getMBOSpeed();
	}

	public int getMBOAuthority() {
		return trainMod.getMBOAuthority();
	}

	public double getCTCSpeed() {
		return trainMod.getTrackSpeed();
	}

	public int getCTCAuthority() {
		return trainMod.getTrackAuthority();
	}

	public void setKI(double ki) {
		this.ki = ki;
	}

	public void setKP(double kp) {
		this.kp = kp;
	}

	/**
	 * 
	 * @param deltaT = time difference (time between updates)
	 * @param a = error last
	 * @param an = error new
	 * @param b = power last 
	 * @return new power
	 */
    private double laplace(double deltaT, double a, double an, double b) {
		return b+((deltaT)/2)*(an + a);
	}


	 double lastError;
   
	public void update() {
		double np = 0;
		ClockSingleton clkSin = ClockSingleton.getInstance();
		double deltaT = clkSin.getRatio();
		double newError = speed - trainMod.getSpeed();
		double np1 = (kp  * newError) + (ki * laplace(deltaT, lastError, newError, power));
		double np2 = (kp  * newError) + (ki * laplace(deltaT, lastError, newError, power));
		double np3 = (kp  * newError) + (ki * laplace(deltaT, lastError, newError, power));

		  
		if(np1 >= np2 && np1 >= np3){
			np = np1;
		}else if(np2 >= np1 && np2 >= np3){
			np = np2;
		}else if(np3 >= np2 && np3>= np1){
			np = np3;
		}
		  trainMod.setPower(np);
		  lastError = newError;
		  power = np;
		
	}

	public boolean getBrakeStatus() {
		return trainMod.brakeOperationState();
	}

	public boolean getSignalStatus() {
		return trainMod.railSignalState();
	}

	public boolean getEngineStatus() {
		return trainMod.engineState();
	}

	public double getKI() {
		return ki;
	}

	public double getKP() {
		return kp;
	}

	public void setLights(boolean b) {
		if(getLights() != b) {
			toggleLights();
		}
	}

	public void setRightDoor(boolean b) {
		if(rightDoorState() != b) {
			toggleRightDoor();
		}
	}

	public void setLeftDoor(boolean b) {
		if(leftDoorState() != b) {
			toggleLeftDoor();
		}	
	}

	public void setEmergencyBrake(boolean b) {
		if(getEngineStatus() != b) {
			toggleEmergencyBrake();
		}
	}
}