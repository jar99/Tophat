package application.CTC;

import application.TrackModel.TrackLine;

public interface CTCInterface {
    /**
	 * Gives CTC a new Line
	 * 
	 * @param trackLine - deep copy of the new line object with all it's data
	 */
	public void importLine(TrackLine trackLine);
	
	/**
	 * 
	 * @param lineName - name of the line operated on
	 * @param blockID - ID of the block operated on
	 * @return true if block is closed for maintenance and false if block is operational.
	 */
	public boolean getSectionMaintenance(String lineName, int blockID);
	
	
}

