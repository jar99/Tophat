package application.TrainModel;
/**
 * This is the TrainModel track interface
 * 
 * @author jar254
 * @version 1.0
 *
 */
public interface TrainModelTrackInterface {
	
	/**
	 * Function to get the number passengers on the train.
	 * @return int number of passengers
	 */
	int getPassengers();
	
	/**
	 * This function can be used to board the passengers.
	 * @param numPassangers the number of passengers who board the train.
	 * @return the amount of people that could not board.
	 */
	int boardPassengers(int numPassengers);
	
	/**
	 * This function sets how many passengers are getting off
	 * @param numPassangers the amount of passengers that should leave the train
	 * @return the amount of passengers that actually got off
	 */
	int alightPassengers(int numPassengers);
	
	/**
	 * Sets the beacon data on the train model.
	 *
	 * @param beaconData string containing the beacon data
	 */
	void setBeaconData(String beaconData);
	
	/**
	 * Call this method to derail the train.
	 */
	void trainDerails();
	

	/**
	 * Returns the ID value associated with this train.
	 * @return
	 */
	int getID();

}
