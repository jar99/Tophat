package application.TrainModel;

/**
 * This is the TrainModel object interface this contains all the required
 * functions to communicate with the train model object.
 * 
 * @author jar254
 * @version 1.0
 *
 */

public interface TrainInterface {

	void setPower(double power);

	double getPower();

	boolean hasPower();

	/**
	 * Returns a boolean value from an abient light sensor on the train.
	 * 
	 * @return true if it is dark outside.
	 */
	boolean isDark();

	/**
	 * Function returns the speed of the train.
	 * 
	 * @return double of speed in kph
	 */
	double getSpeed();

	/**
	 * Function returns the weight of the train and passengers.
	 * 
	 * @return double of weight in kg
	 */
	double getWeight();

	void setTemperature(double temperature);

	double getTemperature();

	/**
	 * Function to get the number passengers on the train.
	 * 
	 * @return int number of passengers
	 */
	int getPassengers();

	/**
	 * Returns the current track segment authority
	 * 
	 * @return
	 */
	int getTrackAuthority();

	/**
	 * Get the tracks suggested speed.
	 * 
	 * @return
	 */
	double getTrackSpeed();

	/**
	 * Returns the current mbo authority
	 * 
	 * @return
	 */
	int getMBOAuthority();

	/**
	 * Get the mbo suggested speed.
	 * 
	 * @return
	 */
	double getMBOSpeed();

	String getBeaconData();

	/**
	 * Gets the state of the left door
	 * 
	 * @return true if the left doors are open else false
	 */
	boolean getLeftDoorState();

	/**
	 * Gets the state of the right door
	 * 
	 * @return true if the right doors are open else false
	 */
	boolean getRightDoorState();

	/**
	 * Toggles the left doors to close if they are open and open if they are closed
	 * 
	 * @return true if the operation could be completed else false
	 */
	boolean toggleLeftDoors();

	/**
	 * Toggles the right doors to close if they are open and open if they are closed
	 * 
	 * @return true if the operation could be completed else false
	 */
	boolean toggleRightDoors();

	boolean getLightState();

	boolean toggleLights();

	boolean getInterierLightState();

	boolean toggleInterierLight();

	boolean getEmergencyBrake();

	boolean resetEmergencyBrake();

	boolean triggerEmergencyBrake();

	boolean getServiceBrake();

	boolean setServiceBrake();

	boolean unsetServiceBrake();

	boolean toggleServiceBrake();

	/**
	 * Reports the state of the trains engine.
	 * 
	 * @return true if the train engine is working.
	 */
	boolean engineState();

	boolean mboConnectionState();

	boolean railSignalState();

	boolean brakeOperationState();

	/**
	 * Returns the ID value associated with this train.
	 * 
	 * @return
	 */
	int getID();
	
	double getMaxSpeed();
	
	double getMaxPower();

	double getMaxAcceleration();
	
	void addTrainInformation(String message);

	boolean dispatch();

}
