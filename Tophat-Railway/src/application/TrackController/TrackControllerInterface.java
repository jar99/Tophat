package application.TrackController;

import application.TrackModel.TrackLine;

public interface TrackControllerInterface {

	// =========CTC Methods==========
	/*
	 * Get suggested speed from CTC for each block.
	 * 
	 * @param lineName - Name of the block line
	 * @param blockID - ID number for each block
	 * @return speed - Suggested speed for that block
	 */
	public double setSuggestedSpeed(String lineName, int blockID);
	
	/*
	 * Get authority from CTC
	 * 
	 * @param lineName - Name of the line
	 * @return - blockID - block the train is being dispatched to
	 */
	public int setAuthority(String lineName);
	
	/**
	 * Gives Track Controller a new line
	 * 
	 * @param trackLine - deep copy of the new line object with all it's data
	 */
	public void importLine(TrackLine trackLine);
	
	
}
