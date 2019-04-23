package application.TrainModel;
/**
 * This is the TrainModelInterface object interface this contains all the required functions
 * to communicate with the train model module.
 * 
 * @author jar254
 * @version 1.0
 *
 */

import java.util.Set;

public interface TrainModelInterface {

	/**
	 * Method to create a train just by ID
	 * 
	 * @param trainID a unique train id
	 * @return returns the newly created train or null if it already exists
	 */
	TrainInterface createTrain(int trainID);

	/**
	 * Removes the train from the update loop and calls the closing function
	 * 
	 * @param trainID the unique id of the train.
	 * @return true if the remove was successful.
	 */
	TrainInterface removeTrain(int trainID);

	/**
	 * Sets a train flag to go
	 * 
	 * @param trainID the unique id of the train
	 * @return true if the train was able to be dispatched
	 */
	boolean dispatchTrain(int trainID);

	/**
	 * Gets the a TrainInterface based on the trainID
	 * 
	 * @param trainID the unique id of the train.
	 * @return returns the TrainInterface or to null if not found
	 */
	TrainInterface getTrain(int trainID);

	/**
	 * 
	 * @param trainID the unique id of the train.
	 * @return returns true if the train exists
	 */
	boolean trainExists(int trainID);

	/**
	 * Returns the count of how many train models there are
	 * 
	 * @return train model count.
	 */
	int trainCount();

	/**
	 * Function to get a Set collection that contains all of the trainIDs
	 * 
	 * @return the set of trainIDs
	 */
	Set<Integer> getAllTrainIDs();

	/**
	 * This is called by the MBO. Sends authority to a train given an ID This is a
	 * satellite Internet connection.
	 * 
	 * @param trainID   ID of train
	 * @param authority
	 * @return returns true if message was received
	 */
	boolean setTrainAuthority(int trainID, int authority);

	/**
	 * This is called by the MBO. This sets a trains suggested speed by ID
	 * 
	 * @param trainID ID of train
	 * @param speed   in kph
	 * @return returns true if message was received
	 */
	boolean setTrainSuggestedSpeed(int trainID, double speed);

	double getXcord(int trainID);

	double getYcord(int trainID);

}
