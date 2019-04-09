package application.TrainControllerHardware;

public interface TrainControllerHWInterface {
	public int getTrainID();
	public double getPower();
	public double getSpeed();
	public boolean getLights();
	public double getTemperature();
	public boolean getLeftDoor();
	public boolean getRightDoor();
	public boolean getDriveMode();
	public boolean getServiceBrake();
	public boolean getEmergencyBrake();
	public boolean getEngineState();
	public boolean getBrakeState();
	public boolean getSignalState();
}
