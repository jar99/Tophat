package application.TrackController;

import application.TrackModel.TrackLine;

public interface TrackControllerInterface {

	// =========CTC Methods==========
	
	/**
	 * 
	 * @param lineName
	 * @param trainID
	 */
	public void createTrain(String lineName, int trainID);
	
	/**
	 * @param trainID - the ID that will be changed
	 * @param blockID - pick what block to go to
	 * @param suggestedSpeed - tell the train what the suggested speed should be
	 */
	public void sendTrainToBlock(int trainID, int blockID, double suggestedSpeed);
	
	/**
	 * 
	 * @param blockID
	 * @param lineName
	 * @return
	 */
	public boolean getOccupancyCTC(String lineName, int blockID);
	
 /**
  * 
  * @param lineName
  * @param blockID
  * @return
  */
	public int getAuthorityCTC(String lineName, int blockID);
	
	/**
	 * 
	 * @param lineName - line name
	 * @param blockID - block number 
	 * @return true if block has a failure if block is operational
	 */
	public boolean isBlockBroken(String lineName, int blockID);
	
	/**
	 * Gives Track Controller a new line
	 * 
	 * @param trackLine - deep copy of the new line object with all it's data
	 */
	public void importLine(TrackLine trackLine);

	/**
	 * Tells Track Controller that a train left the track
	 * 
	 * @param trainID - ID of train which left track
	 */
	public void removeTrain(int trainID);
	
	/**
	 * Lets the CTC pick a switch and change its position.
	 * @param switchID - pick the switch id that will be changed
	 * @param switchStraight - true for straight or false for diverge
	 */
	public void manuallySetSwitch(int switchID, boolean switchStraight);
	
}
