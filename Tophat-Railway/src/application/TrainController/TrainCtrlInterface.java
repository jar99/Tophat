package application.TrainController;

public interface TrainCtrlInterface {

	public void trianID(int trainID);
	
	/**
	 * This will send speed that I have inputed
	 * @param speed
	 */
	public void Speed(double speed);
	
	public void Power(int power);
	
	public void serviceBrake(boolean serviceBrake);
	
	/**
	 * sets the temperature inside the train
	 * @param temperture
	 */
	public void temperature(double temperature);
	
	/**
	 * if true = open 
	 * if false = closed
	 * @param leftDoor
	 * @param rightDoor
	 */
	public void leftDoor(boolean leftDoor);
	
	public void rightDoor(boolean rightDoor);
	
	/**
	 * true = On
	 * false = off
	 * @param lights
	 */
	public void lights(boolean lights);
///new branch
	
	/**
	 * true = Manual
	 * False = Automatic
	 * @param drvieStatus
	 */
	public void driveMode(boolean driveMode);
	
	/**
	 * 1 = Engine Failure,
	 * 2 = Brake Failure,
	 * 3 = Signal Lost
	 * @param trainStatus
	 */
	//public void trainStatus(int trainStatus);
	
	/**
	 * Engine has failed:(
	 * @param engineFailure
	 */
	public void engineStatus(boolean engineFailure);
	
	/**
	 * The brakes went bad:'(
	 * @param brakeFailure
	 */
	public void brakeStatus(boolean brakeFailure);
	
	/**
	 * What's the f*cking wifi password?!
	 * @param signalFailure
	 */
	public void signalStatus(boolean signalFailure);


	
	
}
