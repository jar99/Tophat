package application.TrainModel;

/**
 * This is the TrainModel object interface this contains all the required
 * functions to communicate with the train model object.
 * 
 * @author jar254
 * @version 1.5
 *
 */

public interface TrainInterface {

	/**
	 * Sets the engine power of the train
	 * 
	 * @param power in kw
	 */
	void setPower(double power);

	/**
	 * Gets the engine power of the train
	 * 
	 * @return power in kw
	 */
	double getPower();

	/**
	 * Checks if the train has power
	 * 
	 * @return true if the train has power
	 */
	boolean hasPower();

	/**
	 * Returns a boolean value from an ambient light sensor on the train.
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

	/**
	 * Sets the temperature in the train
	 * 
	 * @param temperature value in degrees celsius
	 */
	void setTemperature(double temperature);

	/**
	 * Gets the temperature in the train
	 * 
	 * @return value in degrees celsius
	 */
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
	 * @deprecated use getAuthority()
	 * @return
	 */
	int getTrackAuthority();

	/**
	 * Get the tracks suggested speed.
	 * 
	 * @deprecated use getSuggestedSpeed()
	 * @return
	 */
	double getTrackSpeed();

	/**
	 * Returns the current mbo authority
	 * 
	 * @deprecated use getAuthority()
	 * @return
	 */
	int getMBOAuthority();

	/**
	 * Get the mbo suggested speed.
	 * 
	 * @deprecated use getSuggestedSpeed()
	 * @return
	 */
	double getMBOSpeed();

	/**
	 * Returns the suggested speed determined by the mode the program is running in
	 * 
	 * @return in km/h
	 */
	double getSuggestedSpeed();

	/**
	 * Returns the authority determined by the mode the program is running in
	 * 
	 * @return
	 */
	int getAuthority();

	/**
	 * Gets the current beacon data in the buffer
	 * 
	 * @return string containing beacon data
	 */
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

	/**
	 * Return the state of the exterior lights on the train
	 * 
	 * @return true if lights are on
	 */
	boolean getLightState();

	/**
	 * Turn exterior lights on or off depending on the current state
	 * 
	 * @return new state of the lights
	 */
	boolean toggleLights();

	/**
	 * Turn interior lights on or off depending on the current state
	 * 
	 * @return new state of the lights
	 */
	boolean getInteriorLightState();

	/**
	 * Turn interior lights on or off depending on the current state
	 * 
	 * @deprecated Spelling wrong use getInteriorLightState()
	 * 
	 * @return new state of the lights
	 */
	boolean getInterierLightState();

	/**
	 * Turn interior lights on or off depending on the current state
	 * 
	 * @return new state of the lights
	 */
	boolean toggleInteriorLight();

	/**
	 * Gets the state of the emergency brake
	 * 
	 * @return true if the brakes are on
	 */
	boolean getEmergencyBrake();

	/**
	 * Reset the emergency brake
	 * 
	 * @return true if the brakes are reset
	 */
	boolean resetEmergencyBrake();

	/**
	 * Trigger the emergency brake
	 * 
	 */
	boolean triggerEmergencyBrake();

	/**
	 * Gets the state of the service brake
	 * 
	 * @return true if the brakes are on
	 */
	boolean getServiceBrake();

	/**
	 * Sets the service brakes to on
	 * 
	 * @return true if the brakes are working
	 */
	boolean setServiceBrake();

	/**
	 * Sets the service brakes to off
	 * 
	 * @return true if the brakes are working
	 */
	boolean unsetServiceBrake();

	/**
	 * Changes the state of the service brake.
	 * 
	 * @return true if the operation was successful
	 */
	boolean toggleServiceBrake();

	/**
	 * Reports the state of the trains engine.
	 * 
	 * @return true if the train engine is working.
	 */
	boolean engineState();

	/**
	 * Checks the current state of the mbo signal connection.
	 * 
	 * @return true if the mbo connection is working correctly
	 */
	boolean mboConnectionState();

	/**
	 * Checks the current state of the rail signal pickup.
	 * 
	 * @return true if the rain antenna is working correctly
	 */
	boolean railSignalState();

	/**
	 * Checks the current state of the signal pickup.
	 * 
	 * @return true if the signals are working correctly
	 */
	boolean signalState();

	/**
	 * Checks the current state of the brakes.
	 * 
	 * @return true if the brakes work correctly
	 */
	boolean brakeOperationState();

	/**
	 * Returns the ID value associated with this train.
	 * 
	 * @return
	 */
	int getID();

	/**
	 * The maximum speed of the train model
	 * 
	 * @return speed in km/h
	 */
	double getMaxSpeed();

	/**
	 * The maximum power of the train model's engine
	 * 
	 * @return power in kw
	 */
	double getMaxPower();

	/**
	 * The maximum acceleration of the train model
	 * 
	 * @return acceleration in m/s^2
	 */
	double getMaxAcceleration();
	
	/**
	 * The speed limit of the track
	 * 
	 * @return speed in km/h
	 */
	double getSpeedLimit();

	/**
	 * Adds information to the train information Que
	 * 
	 * @param message
	 */
	void addTrainInformation(String message);

	/**
	 * Call this to dispatch the train to start simulating
	 * 
	 * @return
	 */
	boolean dispatch();

}
