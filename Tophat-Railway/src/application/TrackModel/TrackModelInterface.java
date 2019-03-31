package application.TrackModel;

public interface TrackModelInterface {

	// =========CTC Methods==========
	/**
	 * Get number of passengers scheduled to board at a specified station today.
	 * 
	 * @param lineName - the name of the station's line
	 * @param stationName - specified station
	 * @return the number of scheduled boarders
	 */
	public int getScheduledBoarding(String lineName, String station_name);

	/**
	 * Get number of passengers scheduled to alight at a specified station today.
	 * 
	 * @param lineName - the name of the station's line
	 * @param stationName - specified station
	 * @return the number of scheduled alighters
	 */
	public int getScheduledAlighting(String lineName, String station_name);

	// =========Track Controller Methods==========
	/**
	 * Tells Track Model to Dispatch Train
	 * 
	 * @param lineName    - the line a train is dispatched on
	 * @param trainID - ID assigned to train
	 */
	public void createTrain(String lineName, int trainID);

	/**
	 * Check that a Track Controller owns a Block
	 * 
	 * @param lineName - the name of the block's line
	 * @param blockID      - the id for the block
	 * @param controllerID - the id for the controller
	 * @return true, if owned; false, otherwise
	 */
	public boolean checkBlockOwnership(String lineName, int blockID, int controllerID);

	/**
	 * Check that a Track Controller owns a Switch
	 * 
	 * @param lineName - the name of the switch's line
	 * @param switchID     - the id for the switch
	 * @param controllerID - the id for the controller
	 * @return true, if owned; false, otherwise
	 */
	public boolean checkSwitchOwnership(String lineName, int switchID, int controllerID);

	/**
	 * Sets a switch state
	 * 
	 * @param lineName - the name of the switch's line
	 * @param switchID - the id for the switch
	 * @param straight - tells whether to set switch straight or diverging
	 */
	public void setSwitch(String lineName, int switchID, boolean straight);

	/**
	 * Sets suggested speed (meters/sec) for a block
	 * 
	 * @param lineName - the name of the block's line
	 * @param blockID        - the id for the block
	 * @param suggestedSpeed - the suggested speed
	 */
	public void setSuggestedSpeed(String lineName, int blockID, double suggestedSpeed);

	/**
	 * Sets authority (# of blocks) for a block
	 * 
	 * @param lineName - the name of the block's line
	 * @param blockID   - the id for the block
	 * @param authority - the authority
	 */
	public void setAuthority(String lineName, int blockID, int authority);

	/**
	 * Sets control authority for a block
	 * 
	 * @param lineName - the name of the block's line
	 * @param blockID       - the id for the block
	 * @param ctrlAuthority - the control authority
	 */
	public void setControlAuthority(String lineName, int blockID, boolean ctrlAuthority);

	/**
	 * Sets Light status for a block
	 * 
	 * @param lineName - the name of the block's line
	 * @param blockID - the id for the block
	 * @param green   - true for green; false for red
	 * @return false, if the block doesn't have a light
	 */
	public boolean setLightStatus(String lineName, int blockID, boolean green);

	/**
	 * Get occupancy for a block
	 * 
	 * @param lineName - the name of the block's line
	 * @param blockID - the id for the block
	 * @return true, if occupied or broken; false if unoccupied
	 */
	public boolean getOccupancy(String lineName, int blockID);

	/**
	 * Check if block is broken
	 * 
	 * @param lineName - the name of the block's line
	 * @param blockID - the id for the block
	 * @return true, if broken; false if not
	 */
	public boolean isBroken(String lineName, int blockID);

	/**
	 * Sets heating status for a block
	 * 
	 * @param lineName - the name of the block's line
	 * @param blockID - the id for the block
	 * @param heated  - true for heated; false for not
	 */
	public void setHeating(String lineName, int blockID, boolean heated);

	// =========Train Model Methods==========
	/**
	 * Updates displacement (meters) of train on track
	 * 
	 * @param trainID      - the id for the train
	 * @param displacement - the distance traveled forward
	 */
	public void updateTrainDisplacement(int trainID, double displacement);

	/**
	 * Get X Coordinate of Train
	 * 
	 * @param trainID - the id for the train
	 * @return the X axis coordinate
	 */
	public double getTrainXCoordinate(int trainID);

	/**
	 * Get Y Coordinate of Train
	 * 
	 * @param trainID - the id for the train
	 * @return the Y axis coordinate
	 */
	public double getTrainYCoordinate(int trainID);

	/**
	 * Get change in number of passengers for a train
	 * 
	 * @param trainID           - the id for the train
	 * @param currentPassengers - current number of passengers on the train
	 * @return (+) if more board; (-) if more alight
	 */
	public int getTrainPassengerChange(int trainID, int currentPassengers);

	/**
	 * Get block Length (meters) for train
	 * 
	 * @param trainID - the id for the train
	 * @return Length for the block
	 */
	public double getTrainBlockLength(int trainID);

	/**
	 * Get block Grade (%) for train
	 * 
	 * @param trainID - the id for the train
	 * @return Grade for the block
	 */
	public double getTrainBlockGrade(int trainID);

	/**
	 * Get block Speed Limit (meters/second) for train
	 * 
	 * @param trainID - the id for the train
	 * @return Speed Limit for the block
	 */
	public double getTrainBlockSpeedLimit(int trainID);

	/**
	 * Get block Elevation (meters) for train
	 * 
	 * @param trainID - the id for the train
	 * @return Elevation for the block
	 */
	public double getTrainBlockElevation(int trainID);

	/**
	 * Get block Total Elevation (meters) for train
	 * 
	 * @param trainID - the id for the train
	 * @return Total Elevation for the block
	 */
	public double getTrainBlockTotalElevation(int trainID);

	/**
	 * Check if block has a beacon for a train
	 * 
	 * @param trainID - the id for the train
	 * @return true, if the block has a beacon; false if not
	 */
	public boolean trainBlockHasBeacon(int trainID);

	/**
	 * Get block Beacon Data (meters) for train
	 * 
	 * @param trainID - the id for the train
	 * @return Beacon Data for the block
	 */
	public String getTrainBlockBeaconData(int trainID);

	/**
	 * Check if block is a station
	 * 
	 * @param trainID - the id for the train
	 * @return true, if the block is a station
	 */
	public boolean trainBlockIsStation(int trainID);

	/**
	 * Check if block is underground
	 * 
	 * @param trainID - the id for the train
	 * @return true, if the block is underground
	 */
	public boolean trainBlockIsUnderground(int trainID);

	/**
	 * Check if block has a light
	 * 
	 * @param trainID - the id for the train
	 * @return true, if the block has a light
	 */
	public boolean trainBlockHasLight(int trainID);

	/**
	 * Check if a block's light is green
	 * 
	 * @param trainID - the id for the train
	 * @return true, if green; false, if red
	 */
	public boolean trainBlockLightIsGreen(int trainID);

	/**
	 * Get block suggested speed (meters/second) for train
	 * 
	 * @param trainID - the id for the train
	 * @return Suggested Speed for block, (-1 if broken circuit)
	 */
	public double getTrainSuggestedSpeed(int trainID);

	/**
	 * Get block authority (# blocks) for for train
	 * 
	 * @param trainID - the id for the train
	 * @return block authority for train, (-1 if broken circuit)
	 */
	public double getTrainBlockAuthority(int trainID);

	/**
	 * Get whether a train has power
	 * 
	 * @param trainID - the id for the train
	 * @return true, if powered, false if not
	 */
	public double trainHasPower(int trainID);

	/**
	 * Get whether a train has crashed
	 * 
	 * @param trainID - the id for the train
	 * @return true, if crashed, false if not
	 */
	public double trainHasCrashed(int trainID);

	// =========MBO Methods==========
	/**
	 * Get state of a switch
	 * 
	 * @param lineName - the name of the switch's line
	 * @param trainID - the id for the switch
	 * @return true, if straight; false, if diverging
	 */
	public boolean getSwitchState(String lineName, int switchID);
}
