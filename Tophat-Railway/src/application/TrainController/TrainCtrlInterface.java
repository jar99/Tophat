package application.TrainController;



public interface TrainCtrlInterface {

	int getTrianID();

	void setSpeed(double speed);

	double getSpeed();
	
	double getMaxSpeed();

	void setPower(double power);

	double getPower();

	void setTemperature(double temp);

	double getTemperature();

	void toggleServiceBrake();

	boolean getServiceBrake();

	boolean getEmergencyBrake();

	void toggleEmergencyBrake();

	void setDriveMode(boolean drvieMode);

	boolean getDriveMode();

	boolean engineStatus();

	boolean brakeStatus();

	 void toggleLeftDoor();

	 void toggleRightDoor();

	 void toggleInteriorLights();
	 
	 void setMode(boolean set);
	 
	 String getBeacon();

}