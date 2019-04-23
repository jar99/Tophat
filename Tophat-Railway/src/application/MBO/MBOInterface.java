package application.MBO;

import application.TrackModel.TrackLine;

public interface MBOInterface {

	/**
	 * Gives MBO a new Line
	 * 
	 * @param trackLine - deep copy of the new line object with all it's data
	 */
	public void importLine(TrackLine trackLine);

	void createTrain(TrackLine trackLine);
}
