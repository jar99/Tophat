package application.CTC;

public interface CTCInterface {
    /**
	 * Gives CTC a new Line
	 * 
	 * @param trackLine - deep copy of the new line object with all it's data
	 */
	public void importLine(TrackLine trackLine);
}
